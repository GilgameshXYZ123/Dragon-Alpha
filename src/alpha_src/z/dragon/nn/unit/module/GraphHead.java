/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z.dragon.nn.unit.module;

import java.util.Collection;
import java.util.HashSet;
import z.dragon.common.state.State;
import z.dragon.engine.Engine;
import z.dragon.engine.Parameter.ParamMap;
import z.dragon.engine.Parameter.ParamSet;
import z.dragon.engine.Tensor;
import z.dragon.engine.Tensor.TensorSet;
import z.dragon.nn.unit.Trace;
import z.dragon.nn.unit.BaseUnit;
import z.dragon.nn.unit.Unit;

@SuppressWarnings("unchecked")
public class GraphHead extends BaseUnit
{
    private final Unit module;
    
    //each element of graphs maps to one output of this
    private UnitMap<Object>[] arcs;//HashSet<Integer>
    protected UnitSet nexts = new UnitSet();
    
    private Tensor[] X;//input
    private Tensor[] deltaX;//input.gradient
   
    protected GraphHead(Unit module) { this.module = module; }

    //<editor-fold defaultstate="collapsed" desc="function: others">
    @Override public void append(String pre, StringBuilder sb) {}
    
    @Override public Collection<Unit> next() { return nexts; }
    
    @Override public boolean isComplex() { return false; }
    
    @Override public boolean need_grads() { return false; }
    
    @Override public void params(ParamSet set) {}
    @Override public void param_map(ParamMap<String> map) {}
    
    @Override public void state(State map) {}
    @Override public void update_state(State dic, boolean partial) {}

    @Override public void vars(TensorSet vars) { vars.add(X); vars.add(deltaX); }
    @Override public void gc() { 
        eg.delete(X); X = null; 
        eg.delete(deltaX); deltaX = null;
        nexts.clear(); 
    }
    
    @Override public void __init__(Engine eg) {}
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="running-area: forward-propagation">
    @Override
    public Tensor[] forward(Tensor... input) {
        nexts.clear(); X = input;//all one-off Unit reference has been cleared
        arcs = new UnitMap[input.length];
        
        //if(X[i].need_grads = true): the next node needs to compute gradient for X
        //else: the next node does not need to compute gradient for X
        for(int i=0; i<X.length; i++) { 
            Trace Xtrace = X[i].trace();//usually, when trace == null, it's at the start node of compute graph
            if(Xtrace == null) X[i].setTrace(this, i, X[i].need_grad());//module.starts = head.nexts
            else {//X[i].trace != null
                Xtrace.callback(module, i);//module has received tour ith input
                boolean need_grads = (X[i].need_grad() || Xtrace.need_grads());
                X[i].setTrace(this, i, need_grads);//module.starts = head.nexts
            }
        }
        return X;
    }
    
    @Override
    protected synchronized void traceBack(Unit next, int out_index, int next_in_index) {
        nexts.add(next); //module.starts = head.nexts
        
        if(arcs[out_index] == null) arcs[out_index] = new UnitMap<>(2);
        UnitMap<Object> graph = arcs[out_index];
        
        Object value = graph.get(next);
        if (value == null) graph.put(next, next_in_index);
        else if (value instanceof Integer) {//tensor[out_index].used_size = 1
            HashSet<Integer> indexes = new HashSet<>(4);
            indexes.add((Integer) value);
            indexes.add(next_in_index);
            graph.put(next, indexes);
        }
        else {//tensor[out_index].used_size >= 2
            HashSet<Integer> indexes = (HashSet<Integer>) value;
            indexes.add(next_in_index);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="running-area: backward-propagation">
    @Override //deltaX will be setted, so there is not to call head.backward()
    public Tensor[] collectGradientFromNext() {
        //collect gradient form the start module, and give the gradients to module
        //module give it to its precursor in compute graph
        boolean allNull = true;
        deltaX = new Tensor[arcs.length];
        
        for (int i=0; i <arcs.length; i++)  {
            if(arcs[i] == null) continue;
            
            deltaX[i] = this.aggregateGradient(arcs[i]);
            arcs[i] = null;
            
            //if deltaX[i] != null: allNull = (allNull && false) = false
            allNull = allNull && (deltaX[i] == null);//at least one grad != null
        }
        arcs = null;
        
        //if all gradients from the next Units are all null:
        //means all next scales doesn't do backward prop, so the Modile Head needn't do either
        return (allNull? null : deltaX);
    }

    @Override 
    public Tensor[] backward(Tensor... gradient) { 
        return deltaX = gradient;
    }
    
    @Override 
    public Tensor gradient(int index) {
        if(index > deltaX.length || index < 0) throw new IllegalArgumentException("tensor index out of range");
        return (deltaX == null? null : deltaX[index]); 
    }
    //</editor-fold>
}
