/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package z.dragon.nn.unit;

/**
 *
 * @author Gilgamesh
 */
public interface Train2Eval 
{
    public boolean training();
    public Train2Eval train();
    public Train2Eval eval();
}
