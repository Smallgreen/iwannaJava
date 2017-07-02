import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.Color.*;
/**
 * spike.
 * 
 * A class for spikes in iwannaGame. 
 */

public class reverseSpike extends GCompound {

    // constants
    //private static final double DELAY = 20;

    // instance variables
    private iWanna game; // the main game
    private double size;
    private boolean hasReverseSpike = false;

    /** the constructor, create the ball */
    public reverseSpike(double size, boolean hasReverseSpike, iWanna game) {
        // save the parameters in instance variables
        this.game = game;
        this.size = size;
        this.hasReverseSpike = hasReverseSpike;

        
        // create the bricks
        GImage reverseSpike = new GImage("Spike2_32x32.png");
        reverseSpike.setSize(size*2, size*2);
        reverseSpike.setVisible(hasReverseSpike);
        add(reverseSpike);

    }

    //return the boolean hasBrick
    public boolean hasReverseSpike(){

        return hasReverseSpike;

    }

    
    
}