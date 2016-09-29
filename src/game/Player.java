package game;

import java.awt.Graphics;
import java.awt.Color;

//@SuppressWarnings("all")
public class Player{
	static final int HEIGHT = 25;
	static final int WIDTH = 25;
	static final int movAdj=1;
	
	
	public Player(int xx, int yy){
		x=xx;
		y=yy;
		hitbox = new Hitbox(x, y, WIDTH, HEIGHT);
	}
	/*Ignore this...*/
	/*moves in meters per second
	25 pixels=1 meter
	3 frame/50 milliseconds
	(1m/1s)*(25p/1m)*(1s/1000ms)*(50ms/3f);
	1 meter per second =  pixels per frame	
	*/
	
	/*-------DATA---------*/
	/**
	 * Returns Player top left X value.
	 * @return
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns Player top left Y value.
	 * @return
	 */
	public int getY(){
		return y;
	}
	public int getHeight(){
		return HEIGHT;
	}
	public int getWidth(){
		return WIDTH;
	}
	/**
	 * Returns Player current X velocity.
	 * @return
	 */
	public int getXVel(){
		return xVel;
	}
	/**
	 * Returns Player current Y velocity.
	 * @return
	 */
	public int getYVel(){
		return yVel;
	}
	/**
	 * Returns Player movadj attribute. *UNUSED*
	 * @return
	 */
	public int getMovAdj(){
		return movAdj;
	}
	/**
	 * Returns Player hitbox object.
	 * @return
	 */
	public Hitbox getHitbox(){
		return hitbox;
	}
	/**
	 * Returns Player current health amount.
	 * @return
	 */
	public int getHealth(){
		return health;
	}
	/**
	 * Returns Player's current score.
	 * @return
	 */
	public int getScore(){
		return score;
	}
	/**
	 * Sets local camera X and Y values.
	 * @param xx
	 * @param yy
	 */
	public void giveCamXY(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	/**
	 * Manually sets position to (xx,yy).
	 * @param xx
	 * @param yy
	 */
	public void setXY(int xx,int yy){
		x=xx;
		y=yy;
	}
	public void addAmmo(int a){
		ammo+=a;
	}
	public int getAmmo(){
		return ammo;
	}
	public void useAmmo(){
		ammo--;
	}
	
	
	
	/*--------GRAPHICS--------*/
	/**
	 * Draws Player.
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(new Color(100,100,255));
		//health-=1;
		g.fillRect(x-cameraX,y-cameraY,WIDTH,HEIGHT);
	}
	
	/*--------MOVEMENT--------*/
	/**
	 * Moves Player based on Inputs and "gravity".
	 */
	public void move(){
		
		//horizontal move
		leftAcc=2;
		rightAcc=2;
		//slow down
		if(xVel>0 && !right){
			leftAcc=1;
			xVel-=leftAcc;
			leftAcc=2;
		} else if(xVel<0 && !left){
			rightAcc=1;
			xVel+=rightAcc;
			rightAcc=2;
		}
		
		if(xVel>xVelCap && !right){
			xVel-=leftAcc;
		} else if(xVel<-xVelCap && !left){
			xVel+=rightAcc;
		} else if(xVel>xVelCap && right){ //redundant, look into later
			xVel-=leftAcc;
		} else if(xVel<-xVelCap && left){
			xVel+=rightAcc;
		}
		//speed up
		if(right && xVel<xVelCap){
			xVel+=rightAcc;
		} else if(left && xVel>-xVelCap){
			xVel-=leftAcc;
		}
		
		//vertical move
		upAcc=2;
		downAcc=2;
		//slow down
		if(yVel>0 && !down){
			upAcc=1;
			yVel-=upAcc;
			upAcc=2;
		} else if(yVel<0 && !up){
			downAcc=1;
			yVel+=downAcc;
			downAcc=2;
		}
		
		if(yVel>yVelCap && !down){
			yVel-=upAcc;
		} else if(yVel<-yVelCap && !up){
			yVel+=downAcc;
		} else if(yVel>yVelCap && down){ //redundant, look into later
			yVel-=upAcc;
		} else if(yVel<-yVelCap && up){
			yVel+=downAcc;
		}
		//speed up
		if(down && yVel<yVelCap){
			yVel+=downAcc;
		} else if(up && yVel>-yVelCap){
			yVel-=upAcc;
		}
		
		x+=xVel*movAdj;
		hitbox.setX(x);
		y+=yVel*movAdj;
		hitbox.setY(y);
	}
	/**
	 * Sets x Velocity to 0.
	 */
	public void stopX(){
		xVel=0;
	}
	/**
	 * Sets y Velocity to 0.
	 */
	public void stopY(){
		yVel=0;
	}
	/**
	 * Sets boolean Left to true. Typically upon keypress.
	 */
	public void accLeft(){
		left=true;
	}
	/**
	 * Sets boolean Right to true. Typically upon keypress.
	 */
	public void accRight(){
		right=true;
	}
	/**
	 * Sets boolean Up to true. Typically upon keypress.
	 */
	public void accUp(){
		up=true;
	}
	/**
	 * Sets boolean Down to true. Typically upon keypress.
	 */
	public void accDown(){
		down=true;
	}
	/**
	 * Increases x Velocity max to set Value.
	 */
	public void run(){
		xVelCap=6;
	}
	/**
	 * Decreases x Velocity max to set Value.
	 */
	public void walk(){
		xVelCap=4;
	} 
	
	/**
	 * Instantly increases y value and y Velocity upward.
	 */
	public void miniJump(){
		y-=10;
		yVel=-15;
	}
	/**
	 * Instantly increases y value and y Velocity downward.
	 */
	public void miniJumpDown(){
		y+=10;
		yVel=5;
	}
	/**
	 * Instantly increases x value and x Velocity "right" or "left" depending on String n.
	 * @param n
	 */
	public void jumpBack(String n){
		if(n.equals("right")){
			xVel=-xVelCap*2;
		} else if(n.equals("left")){
			xVel=xVelCap*2;
		}
	}
	/**
	 * Sets boolean Right to false. Typically upon key release.
	 */
	public void decRight(){
		right=false;
	}
	/**
	 * Sets boolean Left to false. Typically upon key release.
	 */
	public void decLeft(){
		left=false;
	}
	/**
	 * Sets boolean Up to false. Typically upon key release.
	 */
	public void decUp(){
		up=false;
	}
	/**
	 * Sets boolean Down to false. Typically upon key release.
	 */
	public void decDown(){
		down=false;
	}
	
	/*-------COLLISIONS--------*/
	/**
	 * Increases player health by input n.
	 * @param n
	 */
	public void heal(int n){
		health+=n;
		if(health>100){
			health=100;
		}
	}
	/**
	 * Adds input n to Player score.
	 * @param n
	 */
	public void addScore(int n){
		score += n;
	}
	/**
	 * Decreases player health by input n.
	 * @param n
	 */
	public void takeDamage(int n){
		health-=n;
		if(health<0){
			health=0;
		}
	}
	
	Hitbox hitbox;
	boolean right;
	boolean left;
	boolean up;
	boolean down;
	boolean grounded;
	boolean wallJumpLeft;
	boolean wallJumpRight;
	int ammo=100;
	int fallDamage;
	int health=100;
	int score;
	int leftAcc=2;
	int rightAcc=2;
	int upAcc=2;
	int downAcc=2;
	int yVel;
	int xVel;
	int yVelCap = 4;
	int xVelCap = 4;
	int x;
	int y;
	int cameraX;
	int cameraY;
}