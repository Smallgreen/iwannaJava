import acm.program.*;
import acm.graphics.*;
import java.awt.*;

/**
 * BouncyCannonBall.java <p>
 * 
 * A class for balls shot from cannon in peggleGame. <p>
 */
public class Bullet extends GCompound implements Runnable {
    // constants
    private static final double 
    DELAY = 20;
    // instance variables
    private iWanna game; // the main game
    private double size;
    private GOval bullet;
    private boolean isAlive = true;
    private double speed; // speed and direction of movement

    /** the constructor, create the ball */
    public Bullet(double size, double speed, iWanna game) {
        // save the parameters in instance variables
        this.game = game;
        this.size = size;
        this.speed = speed;

        // create the ball
        bullet = new GOval(size, size);
        add(bullet);
        bullet.setFilled(true);
        bullet.setFillColor(Color.BLACK);  
    }

    /** the run method, to animate the ball */
    public void run() {
        while (isAlive && !game.getGameIsOver()) {
            oneTimeStep();
            pause(DELAY);      
        }   
        if(!isAlive){explode();}
        else{removeAll();}
    }

    // in each time step, move the ball and bounce if hit the wall
    private void oneTimeStep() {
        bullet.movePolar(speed, 0); // move the ball
        //else if(this.getmoveRight()){bullet.movePolar(speed,180);}
        
        game.checkCollisionBoss(this); // check if hit anything
    
    }


    // show explosion and disappear
    private void explode() {
        // remove the ball
        removeAll();

    }
    
        /** stop the animation */
    public void die() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

}