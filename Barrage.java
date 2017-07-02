import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.util.*;
import java.awt.Color.*;
import java.lang.System.*;

/**
 * Barrage.java <p>
 * 
 * A class for two balckholes that bounce inside limits set by SpaceTravel. <p>
 */
public class Barrage extends GCompound implements Runnable {
    // constants
    private static final double DELAY = 20;

    // instance variables
    private iWanna game; // the main game
    private GOval barrage;
    private double speed, angle;
    private double size = 50; // size of smallest ring
    public GOval center;
    private boolean isAliveBarrage = true;
    private RandomGenerator rand = new RandomGenerator();

    public Barrage( double speed, double angle, iWanna game) {
        // save the parameters in instance variables
        this.game = game;
        this.speed = speed;
        this.angle = angle;

        // draw blackholes
        barrage = new GOval(20,20);
        add(barrage);
        barrage.setFilled(true);
        barrage.setFillColor(rand.nextColor());  
    }

    /** the constructor, create the blackhole */
    public void run() {

        while(isAliveBarrage && !game.getGameIsOver()){
            oneTimeStep();
            pause(DELAY);
        }

        if(!isAliveBarrage || game.getbossMap()){disappear();}
        else{removeAll();}
        
    }

    public void oneTimeStep() {   
        movePolar(speed, angle); // move the blackhole
        if(!game.getGameIsOver() || !game.getmapdrawn()){game.checkCollision(this);} // check for collision
    }

    /** return the current angle */
    public double getAngle() {
        return angle;
    }

    /** set the angle */
    public void setAngle(double angle) {
        this.angle = angle;
    }  

    public void setSpeed(double speed){
        this.speed = speed;
    }
    
    
    // show explosion and disappear
    private void disappear() {
        // remove the ball
        removeAll();

    }
    
        /** stop the animation */
    public void diebarrage() {
        isAliveBarrage = false;
    }

    public boolean isAlive() {
        return isAliveBarrage;
    }


}
