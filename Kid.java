import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import acm.util.*;
import java.util.*;
import java.awt.Color.*;
import java.lang.System.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
/** 
 * A class for peggle balls in peggleBallGame. 
 */

public class Kid extends GCompound implements Runnable{
    // instance variables
    private iWanna game; // the main game
    private double size;

    private double speed, angle;
    private GImage kid;
    private static final double DELAY = 20;

    private double jumpTime=0;

    private boolean changeImage = false;
    private boolean kidisDie = false;
    private static final int 
    gravity = 1;
    public int fallingSpeed = 0;
    private int jumpPower = -5;

    /** the constructor, create the ball */
    public Kid(double size,iWanna game) {
        // save the parameters in instance variables
        this.game = game;
        this.size = size;

        // create the kid    
        kid = new GImage("kid.gif");
        kid.setSize(size, size);

        add(kid);
    }

    public void run() {
        while(!game.getGameIsOver()){
            kidisDie = false;
            oneTimeStep();
            pause(DELAY);
        }
    }

    public void oneTimeStep() {   

        applyGravity();
        move(speed, fallingSpeed); // move the kid

        if(game.getmapdrawn()){

            //System.out.println("check");
            this.game.checkBrick();
            this.game.checkMaps();
            this.game.checkSpike();

        }

        changeImage();
        // if(onTheBrick){
        if(jumpTime< 20 && game.moveUpward()){
            jump();
            jumpTime++;

        }
        if(!game.moveUpward()){
            jumpTime = 0;

        }

        //boolean change direction

        //eles if there is no bricks there,then call fall method
        //game.checkBrick(this); // check for bricks
    }

    public void setxSpeed(double xspeed){

        speed = xspeed;

    }

    public void changeImage(){
        if(changeImage){
            if(game.getmoveLeft()){
                kid.setImage("kidLeft.gif");
                kid.setSize(size, size);
            }
            else if(game.getmoveRight()){

                kid.setImage("kid.gif");
                kid.setSize(size, size);

            }
            changeImage = false;
        }

    }

    public void setchangeImage(boolean changeImage){

        this.changeImage = changeImage;

    }

    public boolean getchangeImage(){

        return changeImage;

    }

    public void jump(){

        fallingSpeed = jumpPower;

    }

    public void applyGravity(){
        //setLocation(getX(),getY()+fallingSpeed);
        if(!game.gethitBrick()){

            fallingSpeed = fallingSpeed + gravity;

        }
        ///kid.move(speed,fallingSpeed);

    }

    public void stopFalling(){

        fallingSpeed = 0;

    }


}
