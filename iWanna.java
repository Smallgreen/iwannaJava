import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import acm.util.*;
import java.util.*;
import java.awt.Color.*;
import java.lang.System.*;
import java.lang.String.*;
import java.lang.Thread.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
//copy needed constants that supposed to be in KeyEvent

/**
 * The final project. 
 * Name: I wanna be maroon
 * 
 * Description:
 * This game is one of the game series named 'I wanna be the guy'. The character is named kid.
 * It has the same control as the original iWanna game, but the missions and the boss are different. 

 * How to play:

 * control:
 * press '➡' (right arrow) to move right;
 * press '⬅' (left arrow) to move left;
 * press 'shift' to jump;
 * press 'R' to restart the game;

 * Jumping: the player is able to jump at most twice before he or she hit any brick.
 * Boss: the boss has 50 hp, and one shot takes one hp, which means player should shoot it for 50 times.
 * Game over: if the kid hit the spikes or the barrage at the boss map, the kid will die.
 * Game win: if the player kill the boss, the game will win.
 * 
 * 
 * 
 * @Mengjie Wang
 */
public class iWanna extends GraphicsProgram {

    // initial size of the window
    public static int 
    APPLICATION_WIDTH = 960,
    APPLICATION_HEIGHT = 630;
    
    // constants
    static int VK_RIGHT = 39;
    static int VK_LEFT = 37;
    static int VK_R = 82;
    static int VK_SHIFT = 16;
    static int VK_Z = 90;

    private int shot = 50;

    public static double speed = 5;
    public double angle;
    public double angleSpike;
    public double angleReverseSpike;
    public double angleBarrage = 200;
    public double anglePlus = 30;

    //instance variables
    private brick [] []bricks;
    private Spike [] []spike;
    private reverseSpike [] [] reverseSpike;

    private int row = APPLICATION_HEIGHT/30, 
    col = APPLICATION_WIDTH/30;

    //colors
    public static Color maroon = new Color (110,16,16);
    public static Color grey = new Color (192,192,192);

    private int width, height;
    private int jumpCounter = 0;
    private RandomGenerator rand = new RandomGenerator();
    private boolean gameOver = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean canJump = false;
    private boolean hitBrick = false;
    private boolean visibleBoss = false;
    private boolean visible = false;
    private boolean mapdrawn = false;
    private boolean mediumTrans = false;
    private boolean mediumMap1 = false;
    private boolean hardMap1 = false;
    private boolean bossMap = false;
    private boolean restart = false;
    private boolean continueBarrage = true;

    private Barrage barrage;
    private GImage medium;
    private GImage hard;
    private GImage boss;
    private GImage gameoverpic;
    private GLabel hpLabel; // the label counts the collisions on the screen
    public Kid kid;

    /* set up the game */
    public void run() {

        addKeyListeners();

        // call drawGraphics to draw initial graphics
        drawInitialGraphics();

    }

    public void moveRight(){
        //kid.setImage("kid.gif");
        kid.setxSpeed(speed);

    }

    public void moveLeft(){

        //kid.setImage("kidLeft.gif");
        kid.setxSpeed(-speed);

        //setSpeed,no move. animation
    }

    //create a bouncy ball when keyboard is pressed
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == VK_RIGHT){
            moveRight();
            //kid.setImage();
            if(!moveRight){

                kid.setchangeImage(true);
            }
            moveRight = true;

        }

        if(e.getKeyCode() == VK_LEFT){
            //kid.setImage("kidLeft.gif");
            moveLeft();

            if(!moveLeft){

                kid.setchangeImage(true);
            }
            moveLeft = true;
        }

        if(e.getKeyCode() == VK_SHIFT){

            pause(20);
            jumpCounter++;
            if(jumpCounter<2){
                canJump = true;
            }
            else{
                canJump = false;

            }
        }

        if(e.getKeyCode() == VK_R){

            /**if(gameOver){
            remove(gameoverpic);
            //gameoverpic.setVisible(false);

            // draw game win label
            GLabel winLabel = new GLabel ("Congratulation!",width/4+30, height/2);
            winLabel.setFont (new Font ("Sanserif", Font.BOLD,40));
            winLabel.setColor(Color.red);
            add(winLabel);

            System.out.println("gameover"+gameOver);
            }*/
            //System.out.println("r"+e.getKeyCode());

            gameoverpic.setVisible(false);

            mapdrawn = false;
            //gameOver = false;

            //when redraw the map just reset the kid's location

            if(mediumMap1 || hardMap1){
                //stop check brick for kids

                gameOver = false;
                remove(kid);

                //System.out.println("gameOver"+gameOver);
                //kid.setLocation(60,height-70);
                //new Thread(kid).start();

                kid = new Kid(35,this);
                add(kid,60,height-70);

                new Thread(kid).start();

                mapdrawn = true;

            }

            else if(bossMap){

                /**while(gameOver){
                pause(200);
                }*/

                gameOver = true;
                shot = 50;
                //remove(kid);
                /**kid = new Kid(35,this);
                add(kid,width/2-40,height-60);
                new Thread(kid).start();
                mapdrawn = true;
                gameOver = false;*/

                removeAll();
                bossMap();
                //barrage();
                mapdrawn = true;
                gameOver = false;

                //bossMap = false;
                /**
                if(mediumTrans){
                firstMapMedium();
                mediumMap1 = true;
                remove(kid);
                kid = new Kid(35,this);
                add(kid,width - 100,height-70);

                new Thread(kid).start();

                mapdrawn = true;
                //kid.setLocation(width - 100,height-70);
                }
                else{
                firstMapHard();
                hardMap1 = true;
                remove(kid);
                kid = new Kid(35,this);
                add(kid,width - 100,height-70);

                new Thread(kid).start();

                mapdrawn = true;
                //kid.setLocation(width - 100,height-70);
                }*/

                /**System.out.println("gameOver1: "+ gameOver);
                if (!gameOver){
                barrage();
                }

                System.out.println("gameOver2: "+ gameOver);*/

            }

            else{

                remove(kid);

                kid = new Kid(35,this);
                add(kid,width/2-35,height-400);

                new Thread(kid).start();

                mapdrawn = true;
            }

        }

        if(e.getKeyCode() == VK_Z){
            // create a cannon ball
            Bullet bullet = new Bullet(5, 10, this);

            // add to kid
            add(bullet, kid.getX(), kid.getY()+10);

            // start the animation of the ball
            new Thread(bullet).start(); 

        }

    }

    //create a bouncy ball when key is released 
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == VK_RIGHT){
            moveRight = false;

            kid.setxSpeed(0);
        } 

        if(e.getKeyCode() == VK_LEFT){
            moveLeft = false;
            kid.setxSpeed(0);
        } 

        if(e.getKeyCode() == VK_SHIFT){
            //jump();
            canJump = false;

        }

    }

    public boolean moveUpward(){
        return canJump;

    }

    public boolean getmoveLeft(){
        return moveLeft;

    }

    public boolean getmoveRight(){
        return moveRight;

    }

    public boolean gethitBrick(){
        return hitBrick;

    }

    public boolean getmapdrawn(){
        return mapdrawn;
    }

    public boolean getbossMap(){
        return bossMap;
    }

    public boolean getRestart(){
        return restart;
    }

    public void checkCollisionBoss(Bullet bullet){

        if(mapdrawn && bossMap && bullet.isAlive() && boss.getBounds().intersects(bullet.getBounds())){

            shot--;
            hpLabel.setLabel("BOSS HP:"+shot);
            bullet.die();

        }

        if(shot == 0){gameWin();}

    }

    public void checkCollision(Barrage barrage){

        // x,y coordinattes of blackholes 
        double x = barrage.getX();
        double y = barrage.getY();
        double size = barrage.getWidth();
        double angle = barrage.getAngle();

        /**collision between blackholes and wall
         * the constants are used to offset the width of menu and window */

        //System.out.println(x+"  "+y);
        if(!gameOver && mapdrawn){
            //change the label when fire planet hits the destination label
            if(kid.getBounds().intersects(barrage.getBounds())){
                gameOver();
            }

            if(x>width || x<0 || y<0 || y> height){

                barrage.diebarrage();

            }

        }
    }

    //check whether there are bricks under kid or not
    public void checkBrick(){

        hitBrick = false;
        //int x = kid.getX()/30;
        //int y = kid.getY()/30;

        for(int i = 0; i < row; i++ ){
            for(int j = 0; j < col; j++ ){
                //System.out.println("mapdrawn2: "+ mapdrawn);
                //brick b = bricks[i][j];

                double x = kid.getX();
                double y = kid.getY();

                double a = bricks[i][j].getX();
                double b = bricks[i][j].getY();
                /*if (!hitBrick) {
                System.out.println("i: "+ i+ "j: " + j +  bricks[i][j]);
                return;
                }*/

                if(kid.getBounds().intersects(bricks[i][j].getBounds()) && bricks[i][j].hasBrick() ){
                    hitBrick = true;

                    angle = getAngle(kid,bricks[i][j]);

                    //System.out.println("x"+ x+"  y"+y);
                    pause(20);
                    //boundright
                    if(angle<-45 && angle>-135){

                        double y2 = b-35;
                        if(y2<0){
                            y2 = 0;

                        }
                        kid.setLocation(x,y2);

                        //System.out.println("bottom"+"x"+ x+"  y"+y2);
                        kid.stopFalling();

                    }
                    else if(angle<45 && angle>-45){

                        moveRight = false;

                        double x2 = x-1;
                        /**if(x2<0){
                        x2 = 0;

                        }*/
                        kid.setLocation(x2,y);

                        //System.out.println("right"+"x"+ x2+"  y"+y);
                        kid.setxSpeed(0);

                    }

                    if(angle>45 && angle <135){

                        double y3 = y+5;
                        /**if(y3>=col){
                        y3 = col-1;

                        }*/
                        kid.setLocation(x,y3);

                        canJump = false;
                        //System.out.println("top"+"x"+ x+"  y"+y3);
                        kid.stopFalling();
                        hitBrick = false;

                    }

                    if(angle>135 && angle< 225){

                        moveLeft = false;

                        double x3 = x+2;
                        /**if(x3>=row){
                        x3 = row-1;

                        }
                        if(x3==0 || y==0){
                        System.out.println("here");

                        }*/
                        kid.setLocation(x3,y);

                        //System.out.println("left"+"x"+ x3+"  y"+y);

                        kid.setxSpeed(0);

                    }

                    jumpCounter = 0;
                }
            }

        }
    }

    public void checkSpike(){
        for(int i = 0; i < row; i++ ){
            for(int j = 0; j < col; j++ ){

                if((kid.getBounds().intersects(spike[i][j].getBounds())&& spike[i][j].hasSpike()) 
                || (kid.getBounds().intersects(reverseSpike[i][j].getBounds())&& reverseSpike[i][j].hasReverseSpike())){
                    //spike calculate overlap 

                    angleSpike = getAngle(kid,spike[i][j]);
                    angleReverseSpike = getAngle(kid,reverseSpike[i][j]);

                    double distSpike = GMath.distance(kid.getX(), kid.getY(),
                            spike[i][j].getX(), spike[i][j].getY());

                    //double overlapSpike = kid.getWidth()/2+spike[i][j].getWidth()/2 - distSpike;
                    double distReverseSpike = GMath.distance(kid.getX(), kid.getY(),
                            reverseSpike[i][j].getX(), reverseSpike[i][j].getY());

                    //System.out.println(angleSpike+"   "+distSpike);

                    if((angleSpike <45 && angleSpike>-45 && distSpike < 10  )
                    || (angleSpike <135 && angleSpike<-135 && distSpike < 45 )
                    || (angleSpike <-45 && angleSpike>-135 && distSpike < 20 )
                    ||(angleReverseSpike > 225 && angleReverseSpike < 315 && distReverseSpike < 50)){
                        //up sipke
                        //(moveRight && overlapSpike > 37) || (moveLeft && overlapSpike > 10.5) || overlapReverseSpike < -30){
                        //System.out.println("spike");
                        gameOver();
                    }

                }
            }
        }

    }

    public void checkMaps(){

        if((kid.getBounds().intersects(medium.getBounds()))&&visible){
            mediumMap1 = true;
            removeAll();
            firstMapMedium();

            mediumTrans = true;
        }

        if((kid.getBounds().intersects( hard.getBounds())) && visible){
            hardMap1 = true;
            removeAll();
            firstMapHard();

            mediumTrans = false;
        }

        if((hardMap1 || mediumMap1) && (kid.getBounds().intersects( boss.getBounds())) && !visible && visibleBoss){
            //System.out.println("boss");
            mediumMap1 = false;
            hardMap1 = false;
            bossMap = true;
            removeAll();
            bossMap();
            barrage();
        }

    }

    public void barrage(){
        while(true){

            anglePlus = 30;

            //System.out.println("gameover3:"+gameOver);

            /**if(!continueBarrage){
            continueBarrage = true;
            }*/

            System.out.println("aaaa");
            while(!gameOver && bossMap){
                System.out.println("barrage");

                angleBarrage = 200;
                anglePlus = anglePlus-5;
                if(anglePlus<10){
                    anglePlus = 30;
                }

                for(int i = 0; i<(int)145/anglePlus ;i++){

                    if(angleBarrage == 345 || angleBarrage > 345){

                        angleBarrage = 200;

                    }
                    //angle if less then 0,reset angle, plus10 everytime
                    barrage = new Barrage (3,angleBarrage,this);
                    add(barrage,width/2,30);

                    angleBarrage = angleBarrage+anglePlus;
                    new Thread(barrage).start();

                    pause(20);
                    //System.out.println("gameover4:"+gameOver);
                }
                pause(800);

            }
            
            continue;


        }
    }

    public void bossMap(){
        mapdrawn = false;
        width = getWidth(); // width of the window
        height = getHeight(); // height of the window

        // set the background color
        GRect court = new GRect(0, 0,width, height);
        court.setFilled(true);
        court.setColor(grey);
        add(court);

        visible = false;
        visibleBoss = false;

        medium.setVisible(visible);
        hard.setVisible(visible);
        boss.setVisible(visibleBoss);

        boolean hasBrick = false;
        bricks = new brick [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks
                if(i<1||i>row-2||j>col-2 || j<1 ){
                    hasBrick = true;
                }
                else{
                    hasBrick = false;
                }

                bricks[i][j] = new brick (30,hasBrick,this);
                add(bricks[i][j],j*30,i*30);

            }
        }

        boolean hasSpike = false;
        spike = new Spike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks

                hasSpike = false;

                spike[i][j] = new Spike (30,hasSpike,this);
                add(spike[i][j],j*30-15,i*30-5);
            }
        }

        boolean hasReverseSpike = false;
        reverseSpike = new reverseSpike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add spike

                hasReverseSpike = false;

                reverseSpike[i][j] = new reverseSpike (30,hasReverseSpike,this);
                add(reverseSpike[i][j],j*30-15,i*30-25);
            }
        }

        gameoverpic = new GImage ("gameover.png");
        gameoverpic.setVisible(false);
        gameoverpic.setSize (500,200);
        add(gameoverpic,250,200);

        hpLabel = new GLabel ("BOSS HP:"+shot,width*3/4, 50);
        hpLabel.setFont (new Font ("Sanserif", Font.BOLD,15));
        hpLabel.setColor(Color.red);
        add(hpLabel);

        boss = new GImage("boss.jpeg");
        boss.setSize(150, 160);
        add(boss,width-150,height-190);

        mapdrawn = true;
        
        kid = new Kid(35,this);
        add(kid,width/2-40,height-60);

        new Thread(kid).start();
        
        

    }

    public void firstMapMedium(){

        mapdrawn = false;
        width = getWidth(); // width of the window
        height = getHeight(); // height of the window

        // set the background color
        GRect court = new GRect(0, 0,width, height);
        court.setFilled(true);
        court.setColor(grey);
        add(court);

        visible = false;
        visibleBoss = true;

        boolean hasBrick = false;
        bricks = new brick [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks
                if((i<6 && j<11)||(i<8 && j>10 && j<23)||(i<15 && j>22 && j<col) 
                || j<1 || (j>col-2 && i<row-3)|| i>row-2 || (i>16 && i<row-1 && j>9 && j<13) || (i>16 && i<18 && j<23 && j>19)
                ||(i>13 && i<15 && j>0 && j<8)||(i>10 && i<13 && j>12 && j<20) ){
                    hasBrick = true;
                }
                else{
                    hasBrick = false;
                }

                bricks[i][j] = new brick (30,hasBrick,this);
                add(bricks[i][j],j*30,i*30);
            }
        }

        boolean hasSpike = false;
        spike = new Spike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks
                if((j>3 &&j<7 && i<row-1 && i>row-3)||(j>12 &&j<23 && i<row-1 && i>row-3)||(j>25 &&j<28 && i<row-1 && i>row-3)){
                    hasSpike = true;
                }
                else{
                    hasSpike = false;
                }

                spike[i][j] = new Spike (30,hasSpike,this);
                add(spike[i][j],j*30-15,i*30-5);
            }
        }

        boolean hasReverseSpike = false;
        reverseSpike = new reverseSpike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add spike
                if((i>14 && i<16 && j>0 && j<8)||(i<7 && i>5 && j<11 && j>0)||(i<9 && i>7 && j<23 && j>10)
                ||(i<16 && i>14 && j<col-1 && j>22)){
                    hasReverseSpike = true;

                    //System.out.println("spike"+hasReverseSpike);
                }

                else{
                    hasReverseSpike = false;
                }

                reverseSpike[i][j] = new reverseSpike (30,hasReverseSpike,this);
                add(reverseSpike[i][j],j*30-15,i*30-25);
            }
        }

        gameoverpic = new GImage ("gameover.png");
        gameoverpic.setVisible(false);
        gameoverpic.setSize (500,200);
        add(gameoverpic,250,200);

        medium.setVisible(visible);
        hard.setVisible(visible);

        boss = new GImage("boss.jpeg");
        boss.setSize(50, 60);
        add(boss,width-50,height-90);

        mapdrawn = true;

        kid = new Kid(35,this);
        add(kid,60,height-70);

        new Thread(kid).start();
        

    }

    public void firstMapHard(){
        mapdrawn = false;
        visible = false;

        width = getWidth(); // width of the window
        height = getHeight(); // height of the window

        // set the background color
        GRect court = new GRect(0, 0,width, height);
        court.setFilled(true);
        court.setColor(grey);
        add(court);
        visible = false;
        visibleBoss = true;

        boolean hasBrick = false;
        bricks = new brick [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks
                if((i<6 && j<11)||(i<8 && j>10 && j<16)||(i<3 && j>15 && j<23)
                ||(i>11 && i<15 && j>22 && j<25)||(i>11 && i<15 && j>26 && j<col)
                || j<1 || (j>col-2 && i<row-3)|| i>row-2 || (i>16 && i<row-1 && j>9 && j<13) 
                ||(i>13 && i<15 && j>0 && j<8)||(i>10 && i<13 && j>12 && j<20) ||(i<9 && j>22 && j<col)
                ){
                    hasBrick = true;
                }
                else{
                    hasBrick = false;
                }

                bricks[i][j] = new brick (30,hasBrick,this);
                add(bricks[i][j],j*30,i*30);
            }
        }

        boolean hasSpike = false;
        spike = new Spike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks
                if((j>3 &&j<7 && i<row-1 && i>row-3)||(j>12 &&j<28 && i<row-1 && i>row-3)){
                    hasSpike = true;
                }
                else{
                    hasSpike = false;
                }

                spike[i][j] = new Spike (30,hasSpike,this);
                add(spike[i][j],j*30-15,i*30-5);
            }
        }

        boolean hasReverseSpike = false;
        reverseSpike = new reverseSpike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add spike
                if((i>14 && i<16 && j>0 && j<8)||(i<7 && i>5 && j<11 && j>0)||(i<9 && i>7 && j<16 && j>10)
                ||(i<4 && i>2 && j<23 && j>15)||(i<16 && i>14 && j<25 && j>22)||(i<16 && i>14 && j<col-1 && j>26)
                ||(i>8 && i<10 && j>22 && j<col-1)){
                    hasReverseSpike = true;

                    //System.out.println("spike"+hasReverseSpike);
                }

                else{
                    hasReverseSpike = false;
                }

                reverseSpike[i][j] = new reverseSpike (30,hasReverseSpike,this);
                add(reverseSpike[i][j],j*30-15,i*30-25);
            }
        }

        gameoverpic = new GImage ("gameover.png");
        gameoverpic.setVisible(false);
        gameoverpic.setSize (500,200);
        add(gameoverpic,250,200);

        medium.setVisible(visible);
        hard.setVisible(visible);

        boss = new GImage("boss.jpeg");
        boss.setSize(50, 60);
        add(boss,width-50,height-90);

        mapdrawn = true;

        kid = new Kid(35,this);
        add(kid,60,height-70);

        new Thread(kid).start();

        
    }

    private void drawInitialGraphics() {
        visible = true;

        mapdrawn = false;
        width = getWidth(); // width of the window
        height = getHeight(); // height of the window

        // set the background color
        GRect court = new GRect(0, 0,width, height);
        court.setFilled(true);
        court.setColor(grey);
        add(court);

        boolean hasBrick = false;
        bricks = new brick [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks
                if(i<2 || j<2 || j>col-3 || i>row-3 || (i>15 && i<18 && j>9 && j<13) || (i>15 && i<18 && j<23 && j>19)){
                    hasBrick = true;
                }
                else{
                    hasBrick = false;
                }

                bricks[i][j] = new brick (30,hasBrick,this);
                add(bricks[i][j],j*30,i*30);
            }
        }
        boolean hasSpike = false;
        spike = new Spike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add bricks

                hasSpike = false;

                spike[i][j] = new Spike (30,hasSpike,this);
                add(spike[i][j],j*30-15,i*30-5);
            }
        }

        boolean hasReverseSpike = false;
        reverseSpike = new reverseSpike [row][col];
        //draw bricks full top row
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                //add spike

                hasReverseSpike = false;

                reverseSpike[i][j] = new reverseSpike (30,hasReverseSpike,this);
                add(reverseSpike[i][j],j*30-15,i*30-25);
            }
        }

        gameoverpic = new GImage ("gameover.png");
        gameoverpic.setVisible(false);
        gameoverpic.setSize (500,200);
        add(gameoverpic,250,200);

        //draw topic label
        GLabel topicLabel = new GLabel ("I WANNA BE MAROON",200,250);
        topicLabel.setFont (new Font ("Sanserif", Font.BOLD,50));
        topicLabel.setColor(maroon);
        add(topicLabel);

        //draw medium label
        GLabel mediumLabel = new GLabel ("medium",302,480);
        mediumLabel.setFont (new Font ("Sanserif", Font.BOLD,20));
        mediumLabel.setColor(maroon);
        add(mediumLabel);

        medium = new GImage("medium.jpg");
        medium.setSize(30, 30);
        add(medium, 325,435);

        //draw hard label
        GLabel hardLabel = new GLabel ("hard",625,480);
        hardLabel.setFont (new Font ("Sanserif", Font.BOLD,20));
        hardLabel.setColor(maroon);
        add(hardLabel);

        hard = new GImage("hard.jpg");
        hard.setSize(30, 30);
        add(hard, 630,435);

        kid = new Kid(35,this);
        add(kid,width/2-35,height-400);

        new Thread(kid).start();


        mapdrawn = true;
        
    }

    /** return the angle of movement */ 
    public double getAngle() {
        return angle;
    }

    private void gameOver() {
        //gameIsOver = true;
        gameOver = true;

        //draw gameover label
        //gameoverpic = new GImage ("gameover.png");
        gameoverpic.setVisible(true);
        //gameoverpic.setSize (500,200);
        //add(gameoverpic,250,200);
        continueBarrage = false;

    }

    public boolean getGameIsOver(){ 
        return gameOver;
    }

    private void gameWin() {

        // draw game win label
        GLabel winLabel = new GLabel ("Congratulation!",width/4+30, height/2);
        winLabel.setFont (new Font ("Sanserif", Font.BOLD,40));
        winLabel.setColor(Color.red);
        add(winLabel);

        shot = 0;

        gameOver = true;
    }

    private double getAngle(Kid kid, brick brick){
        return GMath.angle (kid.getX() + kid.getWidth()/2,
            kid.getY() + kid.getWidth()/2,
            brick.getX()+brick.getWidth()/2 ,brick.getY() + brick.getWidth()/2
        );

    }

    private double getAngle(Kid kid, Spike spike){
        return GMath.angle (kid.getX() + kid.getWidth()/2,
            kid.getY() + kid.getWidth()/2,
            spike.getX()+spike.getWidth()/2 ,spike.getY() + spike.getWidth()/2
        );

    }

    private double getAngle(Kid kid, reverseSpike reversespike){
        return GMath.angle (kid.getX() + kid.getWidth()/2,
            kid.getY() + kid.getWidth()/2,
            reversespike.getX()+ reversespike.getWidth()/2 , reversespike.getY() +  reversespike.getWidth()/2
        );

    }
}