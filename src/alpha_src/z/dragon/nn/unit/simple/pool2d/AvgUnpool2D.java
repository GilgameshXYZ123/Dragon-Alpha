/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z.dragon.nn.unit.simple.pool2d;

import z.dragon.engine.Tensor;
import z.util.lang.annotation.Passed;

/**
 *
 * @author Gilgamesh
 */
@Passed("CudaFloat32Base")
public class AvgUnpool2D extends Unpool2D
{
    public AvgUnpool2D(
            int kernel_height, int kernel_width, 
            int stride_height, int stride_width, 
            int padding_height, int padding_width,
            int output_height, int output_width) 
    {
        super(kernel_height, kernel_width,
              stride_height, stride_width,
              padding_height, padding_width,
              output_height, output_width); 
    }
   
    //<editor-fold defaultstate="collapsed" desc="running area">
    @Override
    protected Tensor __forward__(Tensor X) {
        return eg.upool2D_avg(X, IH, IW, FH, FW, sh, sw, ph, pw);
    }

    @Override
    protected Tensor __backward__(Tensor deltaY, boolean grad_inplace, boolean backward_grads) {
        if(!backward_grads) return null;
        
        Tensor deltaX = eg.pool2D_avg(deltaY, FH, FW, sh, sw, ph, pw);
        
        //when deltaX is cauculated, deltaY is not needed
        if(grad_inplace) deltaX.dual(()-> { deltaY.delete(); });
       
        return deltaX;
    }
    //</editor-fold>
}
