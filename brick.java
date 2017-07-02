import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.Color.*;
/**
 * peggleball.
 * 
 * A class for peggle balls in peggleBallGame. 
 */

public class brick extends GCompound {

    // constants
    //private static final double DELAY = 20;

    // instance variables
    private iWanna game; // the main game
    private double size;
    private boolean hasBrick = false;

    /** the constructor, create the ball */
    public brick(double size, boolean hasBrick, iWanna game) {
        // save the parameters in instance variables
        this.game = game;
        this.size = size;
        this.hasBrick = hasBrick;

        
        // create the bricks
        GImage brick = new GImage("brick.jpg");
        brick.setSize(size, size);
        brick.setVisible(hasBrick);
        add(brick);

    }

    //return the boolean hasBrick
    public boolean hasBrick(){

        return hasBrick;

    }

    
    
}