package game;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

//@SuppressWarnings("all")
public class Enemy{
	public static final int movAdj=1;
	
	/**
	 * Creates new Enemy object at (xx,yy) w: w, h: h, name: n, default-color: c, type: t, damage: d,
	 * Range of x movement: r, Jumping Frequency: f, horizontal velocity: s.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 * @param n
	 * @param c
	 * @param t
	 * @param d
	 * @param r
	 * @param f
	 * @param s
	 */
	public Enemy(int xx, int yy, int w, int h, int d, int r, int s,int sw, int sh, int lw, int lh){
		x=xx;
		y=yy;
		width=w;
		height=h;
		color=Color.GREEN;
		rangeCenterX=xx;
		rangeCenterY=yy;
		damage=d;
		range=r;
		detectRange=range*3/2;
		xVel=s;
		yVel=s;
		sWidth=sw;
		sHeight=sh;
		lwidth = lw;
		lheight = lh;
		Random gen = new Random();
		int dir=gen.nextInt(3);
//		System.out.println("Dir: "+dir);
		if(dir==0){
			right=true;
			left=false;
			up=true;
			down=false;
		} else if(dir==1){
			right=true;
			left=false;
			up=false;
			down=true;
		}else if(dir==2){
			right=false;
			left=true;
			up=true;
			down=false;
		}else if(dir==3){
			right=false;
			left=true;
			up=false;
			down=true;
		}
		
		hitbox=new Hitbox(xx,yy,w,h);
	}
	
	/*--------DATA---------*/
	/**
	 * Returns whether or not this Enemy is "alive".
	 * @return
	 */
	public boolean isAlive(){
		return alive;
	}
	/**
	 * Returns whether or not this Enemy is "removable".
	 * Typically removable after death animation (if any) is completed.
	 * @return
	 */
	public boolean isRemovable(){
		return removable;
	}
	/**
	 * Returns this Enemy's top left X coordinate.
	 * @return
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns this Enemy's top left Y coordinate.
	 * @return
	 */
	public int getY(){
		return y;
	}
	/**
	 * Returns this Enemy's width attribute.
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * Returns this Enemy's height attribute.
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * Returns this Enemy's name value.
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Returns whether this Enemy is collidable (always yes for Enemy).
	 * @return
	 */
	public boolean collidable(){
		return true;
	}
	/**
	 * Returns this Enemy's hitbox object.
	 * @return
	 */
	public Hitbox getHitbox(){
		return hitbox;
	}
	/**
	 * Returns damage output of this Enemy.
	 * @return
	 */
	public int getDamage(){
		return damage;
	}
	/**
	 * Manually sets the position of this Enemy to (xx,yy).
	 * @param xx
	 * @param yy
	 */
	public void setXY(int xx, int yy){
		x=xx;
		y=yy;
		hitbox.setX(x);
		hitbox.setY(y);
	}
	public void setX(int xx){
		x=xx;
		hitbox.setX(x);
	}
	public void setY(int yy){
		y=yy;
		hitbox.setY(y);
	}
	/**
	 * "Kills" the enemy. (Sets alive status to false).
	 */
	public void killed(){
		alive=false;
	}
	/**
	 * Sets removable attribute to true.
	 * NOTE: does NOT actually remove object at this moment.
	 */
	public void remove(){
		removable=true;
	}
	
	public boolean rendered(){
		if(x-cameraX > sWidth || x+width-cameraX < 0 || y-cameraY > sHeight || y+height-cameraY < 0){
			return false;
		}
		return true;
	}
	
	/*---------MOVEMENT--------*/
	/**
	 * Moves this Enemy according to its horizontal range and velocity.
	 */
	public void move(int px, int py){
		if(dcount>=15){
			detect=true;
			dcount=0;
		}
		chooseDir(px,py);
		
		if(right){
//			System.out.println("right");
			x+=xVel*movAdj;
		} else if(left){
//			System.out.println("left");
			x-=xVel*movAdj;
		}
		if(up){
//			System.out.println("up");
			y-=yVel*movAdj;
		} else if(down){
//			System.out.println("down");
			y+=yVel*movAdj;
		}
//		System.out.println("Enemy: ("+x+","+y+"), xVel: "+xVel+", left: " +(x+width>=xStart+xRange) + ", right: "+(x<=xStart));
		hitbox.setX(x);
		hitbox.setY(y);
		dcount++;
	}
	private void chooseDir(int px, int py){
		if(detect && (px<x+detectRange && px>x-detectRange) && (py<y+detectRange && py>y-detectRange)){						//change to if player in range, otherwise roaming
//			System.out.println("Detected");
//			right=false;
//			left=false;
//			up=false;
//			down=false;
			if(px<x){
				left=true;
				right=false;
			} else {
				right=true;
				left=false;
			}
			if(py>y){
				down=true;
				up=false;
			} else{
				up=true;
				down=false;
			}
			rangeCenterX=x;
			rangeCenterY=y;
			detect=false;
		}
		if(true){
			if(x+width>=rangeCenterX+range || x+width>=lwidth){
//				System.out.println("Choose Left");
				right=false;
				left=true;
			} else if(x<=rangeCenterX-range || x<=0){
//				System.out.println("Choose right");
				left=false;
				right=true;
			}
			if(y+height>=rangeCenterY+range || y+height>=lheight){
//				System.out.println("Choose up");
				down=false;
				up=true;
			} else if(y<=rangeCenterY-range || y<=0){
//				System.out.println("Choose down");
				up=false;
				down=true;
			}
		}
	}
	
	/*-----------GRAPHICS----------*/
	/**
	 * Draws this Enemy.
	 * @param g
	 */
	public void draw(Graphics g){
		if(rendered()){
			g.setColor(color);
			//System.out.println("("+x+","+y+")");
			g.fillRect(x-cameraX,y-cameraY,width,height);
			g.setColor(new Color(255,0,0,100));
			g.drawRect(x-detectRange-cameraX, y-detectRange-cameraY, detectRange*2, detectRange*2);
			g.setColor(new Color(0,255,0,100));
			g.drawRect(rangeCenterX-range-cameraX, rangeCenterY-range-cameraY, range*2, range*2);
		}
	}
	/**
	 * Sets local Camera X and Y values.
	 * @param xx
	 * @param yy
	 */
	public void moveDraw(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	
	/*--------COLLISIONS---------*/
	
	/**
	 * Sets direction based on input String n.
	 * @param n
	 */
	public void changeDir(String n){
		if(n.equals("right")){
			right=true;
			left=false;
		} else if(n.equals("left")){
			right=false;
			left=true;
		} else if(n.equals("down")){
			down=true;
			up=false;
		} else if(n.equals("up")){
			down=false;
			up=true;
		}
	}
	
	String type;
	String name;
	Hitbox hitbox;
	boolean alive=true;
	boolean removable=false;
	boolean right;
	boolean left;
	boolean up;
	boolean down;
	boolean detect=false;
//	boolean grounded=false;
	Color color;
	int dcount=0;
	int detectRange;
	int range;
	int rangeCenterX;
	int rangeCenterY;
	int width;
	int height;
	int damage;
	int yVel;
	int xVel;
//	int yVelCap;
//	int xVelCap;
	int x;
	int y;
	int cameraX;
	int cameraY;
	int sWidth, sHeight;
	int lwidth, lheight;
	
}