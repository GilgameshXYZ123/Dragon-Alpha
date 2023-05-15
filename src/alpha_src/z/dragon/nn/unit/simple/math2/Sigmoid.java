/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z.dragon.nn.unit.simple.math2;

import z.dragon.engine.Tensor;
import z.util.lang.annotation.Passed;

/**
 *
 * @author Gilgamesh
 */
@Passed("CudaFloat32Base")
public class Sigmoid extends SimpleInplaceFunction<Sigmoid>
{
    public Sigmoid(boolean inplace)  {
        super(inplace); 
    }
    
    //<editor-fold defaultstate="collapsed" desc="running-area">
    @Override
    protected Tensor __forward__(Tensor X, boolean inplace) {
        return eg.sigmoid(inplace, X);
    }

    @Override
    protected Tensor __backward__(Tensor deltaY, boolean grad_inplace, boolean backward_grads) {
        if(!backward_grads) return null;
        return (isHoldY()?
                eg.sigmoid_deltaX_v1(grad_inplace, deltaY, holdY()): //V1: Y is not changed
                eg.sigmoid_deltaX_v2(grad_inplace, deltaY, holdX()));//V2: X is not changed
    }
    //</editor-fold>
}