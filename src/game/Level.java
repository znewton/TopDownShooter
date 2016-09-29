package game;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

//@SuppressWarnings("all")
public class Level{
	/**
	 * Initializes Level Object with Player Start position (x,y), w: w, h: h, and name: n.
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param n
	 */
	public Level(int x, int y, int w, int h, String n,int fw, int fh){
		startX=x;
		startY=y;
		width=w;
		height=h;
		name=n;
		frameW=fw;
		frameH=fh;
	}
	
	/*------DATA--------*/
	/**
	 * Returns Player Start position x value.
	 * @return
	 */
	public int getStartX(){
		return startX;
	}
	/**
	 * Return player start postition Y value.
	 * @return
	 */
	public int getStartY(){
		return startY;
	}
	/**
	 * Return width of level.
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * Return height of level.
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * Returns size of Environment Object list.
	 * @return
	 */
	public int getObjListSize(){
		return eol.size();
	}
	/**
	 * Returns size of Pickup Object List.
	 * @return
	 */
	public int getPupListSize(){
		return pup.size();
	}
	/**
	 * Returns size of Enemy object list.
	 * @return
	 */
	public int getEneListSize(){
		return ene.size();
	}
	/**
	 * Returns size of SpawnPoint object list.
	 * @return
	 */
	public int getSpListSize(){
		return sp.size();
	}
	public int getBulListSize(){
		return bul.size();
	}
	
	/**
	 * Returns Environment Object at index i in Environment Object list.
	 * @param i
	 * @return
	 */
	public EnvObj getEnvObj(int i){
		return eol.get(i);
	}
	/**
	 * Returns Pickup object at index i in Pickup object list.
	 * @param i
	 * @return
	 */
	public Pickup getPickup(int i){
		return pup.get(i);
	}
	/**
	 * Returns Enemy object at index i in Enemy object list.
	 * @param i
	 * @return
	 */
	public Enemy getEnemy(int i){
		return ene.get(i);
	}
	public Bullet getBullet(int i){
		return bul.get(i);
	}
	public SpawnPoint getSpawn(int i){
		return sp.get(i);
	}
	/**
	 * Removes Pickup object at index i in Pickup object list.
	 * @param i
	 */
	public void removePickup(int i){
		pup.remove(i);
	}
	/**
	 * Removes Enemy Object at index i in Pickup object List.
	 * @param i
	 */
	public void removeEnemy(int i){
		ene.remove(i);
	}
	public void removeBullet(int i){
		bul.remove(i);
	}
	
	/**
	 * Adds an Environment Object to the Environment object list.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 * @param n
	 * @param col
	 */
	public void addEnvObj(int xx, int yy, int w, int h, String n, boolean col, int sw, int sh){
		eol.add(new EnvObj(xx, yy, w, h, n, col, sw, sh));
	}
	/**
	 * Adds a Pickup object to the Pickup object list.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 * @param n
	 * @param c
	 * @param v
	 */
	public void addPickup(int xx, int yy, int w, int h, String n, Color c, int v, int sw, int sh){
		pup.add(new Pickup(xx, yy, w, h, n, c, v,sw,sh));
	}
	/**
	 * Adds an Enemy object to the Enemy object list.
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
	public void addEnemy(int xx, int yy, int w, int h, int d, int r, int s, int sh, int sw){
		ene.add(new Enemy(xx, yy, w, h, d, r, s, sh, sw,width,height));
	}
	/** Adds an Enemy object to the Enemy object list.
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
	public void addSpawn(int xx, int yy){
		sp.add(new SpawnPoint(xx, yy));
	}
	public void shoot(int xx, int yy, int dir){
		bul.add(new Bullet(xx,yy,dir));
	}
	
	public void spawn(int n){
		if(n>=sp.size())return;
		SpawnPoint spawn = sp.get(n);
		spawnCount++;
		for(int i=0;i<spawnCount*2;i++){
//			System.out.println("Spawn: "+n);
			addEnemy(spawn.getX()+i, spawn.getY(), 25, 25, 1, 200, 1, frameW, frameH);
		}
	}
	/**
	 * Returns the name of the level.
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/*--------GRAPHICS-----------*/
	/**
	 * Loops through object lists, calling each object's draw function.
	 * @param g
	 */
	public void drawLevel(Graphics g){
		for(int i=0;i<eol.size();i++){
			eol.get(i).draw(g);
		}
		for(int i=0;i<pup.size();i++){
			pup.get(i).draw(g);
		}
		for(int i=0;i<ene.size();i++){
			ene.get(i).draw(g);
			//System.out.println("drawing "+i);
		}
		for(int i=0;i<bul.size();i++){
			bul.get(i).draw(g);
		}
	}
	/**
	 * Loops through object list, updating each objects local camera X and Y values to xx and yy respectively.
	 * @param xx
	 * @param yy
	 */
	public void moveLevel(int xx, int yy, int px, int py){
		for(int i=0;i<eol.size();i++){
			eol.get(i).moveDraw(xx,yy);
		}
		for(int i=0;i<pup.size();i++){
			pup.get(i).moveDraw(xx,yy);
		}
		for(int i=0;i<ene.size();i++){
			ene.get(i).moveDraw(xx,yy);
			ene.get(i).move(px,py);
		}
		for(int i=0;i<bul.size();i++){
			bul.get(i).moveDraw(xx, yy);
			bul.get(i).move();
		}
	}
	
	int startX;
	int startY;
	int height;
	int width;
	int frameW;
	int frameH;
	int spawnCount=0;
	String name;
	ArrayList<EnvObj> eol = new ArrayList<EnvObj>();
	ArrayList<Pickup> pup = new ArrayList<Pickup>();
	ArrayList<Enemy> ene = new ArrayList<Enemy>();
	ArrayList<SpawnPoint> sp = new ArrayList<SpawnPoint>();
	ArrayList<Bullet> bul = new ArrayList<Bullet>();
}