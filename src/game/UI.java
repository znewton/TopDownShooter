package game;

import java.awt.*;

//@SuppressWarnings("all")
public class UI{
	/**
	 * Initializes UI object with w: w, h: h. Typically set to screen width and height.
	 * @param w
	 * @param h
	 */
	public UI(int w, int h){
		width=w;
		height=h;
	}
	/**
	 * Sets local health and score variables. Typically to those of the Player.
	 * @param h
	 * @param s
	 */
	public void givePlayerStats(int h, int s, int a, int st){
		health=h;
		score=s;
		ammo=a;
		survTime = st;
	}
	/**
	 * Draws UI object.
	 * @param g
	 */
	public void drawUI(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,width,35);
		g.setColor(Color.WHITE);
		g.fillRect(0,35,width,5);
		drawHealthBar(g);
		drawScore(g);
		drawAmmo(g);
		drawTime(g);
	}
	/**
	 * Draws Health bar graphic within the UI bar.
	 * @param g
	 */
	public void drawHealthBar(Graphics g){
//		if(health>=50){
//			g.setColor(new Color(255-255*(health-50)/50,220,0));
//		} else if(health<50){
//			g.setColor(new Color(255,220*health/50,0));
//		}
		g.setColor(Color.WHITE);
		for(int i=0;i<Math.ceil(health/10.0);i++){
			g.fillRect(width-200+i*20, 0, 15, 25);
		}
//		g.fillRect(width-200,0,health*2,25);
	}
	/**
	 * Draws Score counter within the UI bar.
	 * @param g
	 */
	public void drawScore(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("SCORE:  X "+score,width-300,20);
	}
	public void drawAmmo(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("Ammo:  X "+ammo,width-400,20);
	}
	public void drawTime(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("Time Survived: "+survTime,50,20);
	}
	
	int ammo;
	int health;
	int score;
	int width;
	int height;
	int survTime;
}