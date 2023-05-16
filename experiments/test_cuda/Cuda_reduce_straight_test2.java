package test.cuda;


import static z.dragon.alpha.Alpha.alpha;
import z.dragon.engine.Engine;
import z.dragon.engine.Result;
import z.dragon.engine.Result.IndexedResult;
import z.dragon.engine.Result.IndexedValue;
import z.dragon.engine.Tensor;
import z.dragon.engine.cuda.impl.Cuda;
import z.dragon.engine.cuda.impl.CudaException;
import z.util.lang.SimpleTimer;
import z.util.math.ExRandom;
import z.util.math.vector.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gilgamesh
 */
public class Cuda_reduce_straight_test2 
{
    static { alpha.home("C:\\Users\\Gilgamesh\\Desktop\\Dragon-alpha");}
    static Engine eg = alpha.engine.cuda_float32(0, alpha.engine.memp1());
    static ExRandom exr = new ExRandom();
    
    public static void testCorrect(int height, int width) 
    {
        int stride = (width + 3) >> 2 << 2;
        int length = height * width;
        int lengthv = height * stride;
        System.out.println("testCorrect: ");
        System.out.format("[height, width]   = [%d, %d]\n", height, width);
        System.out.format("[length, lengthv] = [%d, %d]\n", length, lengthv);
        
        float[] X = Vector.randomFloatVector(length, -3, 3);
        Vector.println(X, 0, 10);
        Tensor tX = eg.tensor(X, length);
        
        //CPU-------------------------------------------------------------------
//        float y1 = Vector.maxValue(X);
//        int index1 = Vector.maxValueIndex(X);
        float y1 = Vector.minValue(X);
        int index1 = Vector.minValueIndex(X);

        System.out.println("CPU: " + y1 + ", " + index1);
        
        //GPU-------------------------------------------------------------------
//        IndexedValue<Float> rt = eg.straight_max_indexed(tX).get();
        IndexedValue<Float> rt = eg.straight_min_indexed(tX).get();
        
        float y2 = rt.value();
        int index2 = rt.index();
        
        System.out.println("GPU: " + y2 + ", " + index2);
        
        //compare---------------------------------------------------------------
        float div = Math.abs((y1 - y2) / (y1 + y2));
        System.out.println("div: " + div);
        if(div >= 1e-4) throw new RuntimeException();
        if(index1 != index2 && X[index1] != X[index2]) throw new RuntimeException();
    }
    
    public static void testSpeed(int height, int width) 
    {
        int stride = (width + 3) >> 2 << 2;
        int length = height * width;
        int lengthv = height * stride;
        
        System.out.println("testSpeed: ");
        System.out.format("[length, lengthv] = [%d, %d]\n", length, lengthv);
        
        float[] X = Vector.randomFloatVector(length);
        Tensor tX = eg.tensor(X, length);
        
        float alpha = exr.nextFloat();
        float beta = exr.nextFloat();
        float gamma = exr.nextFloat();
        
        SimpleTimer timer = new SimpleTimer();
        timer.record();
        int nIter = 1000;
        for(int i=0; i<nIter; i++)
        {
            eg.straight_quadratic(tX, alpha, beta, gamma).get();
//            eg.reduce_min(tX).get();
//            eg.reduce_max(tX).get();
        }
        Cuda.deviceSynchronize();
        timer.record();
        float time = (float) timer.timeStampDifMills()/nIter;
	int data_size = (lengthv) * 4 * 1;
	float speed =  ((float)data_size) / (1 << 30) / (time * 1e-3f);
        System.out.format("Time = %f, Speed = %f GB/s\n", time, speed);
        eg.delete(tX);
    }
    
    public static void main(String[] args) 
    {
        try
        {
            for(int h=1; h<=20; h++)
                for(int w=1; w<=256; w++) testCorrect(h, w);
            
            for(int h=100; h<=105; h++)
                for(int w= 340; w<=400; w++) testCorrect(h, w);
        
            for(int h=1023; h<=1025; h++)
                for(int w= 34; w<=63; w++) testCorrect(h, w);
            
            testCorrect(1024, 1024);
            testCorrect(1024, 256);
            testCorrect(1023, 1023);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(CudaException.lastException());
    } 
}
