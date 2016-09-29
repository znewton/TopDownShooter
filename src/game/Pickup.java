package game;

import java.awt.*;

//@SuppressWarnings("all")
public class Pickup extends EnvObj{
	/**
	 * Initializes a Pickup object at (xx,yy) with w: w, h: h, name: n, default-color: c, and value: v.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 * @param n
	 * @param c
	 * @param v
	 */
	public Pickup(int xx, int yy, int w, int h, String n, Color c, int v, int sw, int sh){
		super(xx,yy,w,h,n,false,sw,sh);
		value = v;
		if(n.substring(0,4).equals("coin")){
			coin=true;
		} else if(n.substring(0,4).equals("heal")){
			heal=true;
		} else if(n.substring(0,4).equals("ammo")){
			ammo=true;
		}
		sWidth=sw;
		sHeight=sh;
	}
	
	/*------DATA-------*/
	/**
	 * Returns whether or not this pickup object is to heal Player.
	 * @return
	 */
	public boolean isHeal(){
		return heal;
	}
	/**
	 * Returns whether or not this pickup object is a score increaser.
	 * @return
	 */
	public boolean isCoin(){
		return coin;
	}
	/**
	 * Returns whether or not this pickup object is a score increaser.
	 * @return
	 */
	public boolean isAmmo(){
		return ammo;
	}
	/**
	 * Returns value of this Pickup object. Eg. Amount to heal, increase score by, etc.
	 * @return
	 */
	public int getValue(){
		return value;
	}
	/**
	 * Returns whether this Pickup object still "exists". Essentially, if it is able to be interacted with.
	 * @return
	 */
	public boolean isExisting(){
		return existing;
	}
	/**
	 * Returns whether or not this Pickup object can be removed.
	 * @return
	 */
	public boolean isRemovable(){
		return removable;
	}
	/**
	 * Sets object to not "existing". Typically triggers pickup animation or removable.
	 */
	public void used(){
		existing=false;
	}
	/**
	 * Sets removable attribute to true. Typically after pickup animation is completed.
	 * NOTE: does NOT immediately remove object.
	 */
	public void remove(){
		removable=true;
	}
	
	/*------GRAPHICS-----*/
	/**
	 * Draws pickup Object. Dependent upon type.
	 */
	public void draw(Graphics g){
		if(super.rendered()){
			if(coin){
				g.setColor(new Color(200,120,0));
				g.fillOval(super.getX()-super.getCamX(),super.getY()-super.getCamY(),25,25);
			} else if(heal){
				g.setColor(new Color(0,220,0));
				g.fillRect(super.getX()+7-super.getCamX(),super.getY()-super.getCamY(),11,25);
				g.fillRect(super.getX()-super.getCamX(),super.getY()+7-super.getCamY(),25,11);
			} else if(ammo){
				g.setColor(new Color(0,220,0));
				g.fillRect(super.getX()-super.getCamX(),super.getY()-super.getCamY(),25,7);
				g.fillRect(super.getX()-super.getCamX(),super.getY()+9-super.getCamY(),25,7);
				g.fillRect(super.getX()-super.getCamX(),super.getY()+18-super.getCamY(),25,7);
			}
		}
	}
	
	boolean coin=false;
	boolean heal=false;
	boolean ammo=false;
	boolean existing=true;
	boolean removable=false;
	int sWidth;
	int sHeight;
	int value;
}