package game;

import java.awt.Graphics;
import java.awt.Color;

//@SuppressWarnings("all")
public class EnvObj{
	
	/**
	 * Initializes Environment Object at (xx,yy) with w: w, h: h, name: n, collidable: col.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 * @param n
	 * @param col
	 */
	public EnvObj(int xx, int yy, int w, int h, String n, boolean col, int sw, int sh){
		x=xx;
		y=yy;
		width=w;
		height=h;
		name=n;
		collision=col;
		sWidth=sw;
		sHeight=sh;
		hitbox = new Hitbox(x, y, width, height);
	}
	
	/*---------DATA---------*/
	/**
	 * Returns Environment Object top left corner x value.
	 * @return
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns Environment Object top left corner y value.
	 * @return
	 */
	public int getY(){
		return y;
	}
	/**
	 * Returns Environment Object width.
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * Returns Environment Object height.
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * Returns Environment Object name value.
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Returns whether or not Environment Object is to be collided with.
	 * @return
	 */
	public boolean collidable(){
		return collision;
	}
	/**
	 * Returns Environment Object hitbox object attribute.
	 * @return
	 */
	public Hitbox getHitbox(){
		return hitbox;
	}
	/**
	 * Sets Environment Object's X and Y values to (xx,yy).
	 * @param xx
	 * @param yy
	 */
	public void setXY(int xx, int yy){
		x=xx;
		y=yy;
		hitbox.setX(x);
		hitbox.setY(y);
	}
	/**
	 * Returns local Camera X value.
	 * @return
	 */
	public int getCamX(){
		return cameraX;
	}
	/**
	 * Returns local Camera Y value.
	 * @return
	 */
	public int getCamY(){
		return cameraY;
	}
	
	public boolean rendered(){
		if(x-cameraX > sWidth || x+width-cameraX < 0 || y-cameraY > sHeight || y+height-cameraY < 0){
			return false;
		}
		return true;
	}
	
	/*---------GRAPHICS----------*/
	/**
	 * Draws object on canvas
	 * @param g
	 */
	public void draw(Graphics g){
		if(rendered()){
			g.setColor(Color.WHITE);
			g.fillRect(x-cameraX,y-cameraY,width,height);
		}
	}
	/**
	 * Sets  local CameraX and CameraY variables
	 * @param xx
	 * @param yy
	 */
	public void moveDraw(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	
	
	
	boolean collision;
	String name;
	int x;
	int cameraX;
	int y;
	int cameraY;
	int width;
	int height;
	int sWidth, sHeight;
	Hitbox hitbox;
}