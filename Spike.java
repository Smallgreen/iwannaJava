import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.Color.*;
/**
 * spike.
 * 
 * A class for spikes in iwannaGame. 
 */

public class Spike extends GCompound {

    // constants
    //private static final double DELAY = 20;

    // instance variables
    private iWanna game; // the main game
    private double size;
    private boolean hasSpike = false;

    /** the constructor, create the ball */
    public Spike(double size, boolean hasSpike, iWanna game) {
        // save the parameters in instance variables
        this.game = game;
        this.size = size;
        this.hasSpike = hasSpike;

        
        // create the bricks
        GImage spike = new GImage("spike1_32x32.png");
        spike.setSize(size*2, size*2);
        spike.setVisible(hasSpike);
        add(spike);

    }

    //return the boolean hasBrick
    public boolean hasSpike(){

        return hasSpike;

    }

    
    
}