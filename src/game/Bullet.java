package game;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	int x;
	int y;
	static final int width=10;
	static final int height=10;
	static final int s = 6;
	int dir; //0=left, 1=up, 2=right, 3=down
	int cameraX;
	int cameraY;
	Hitbox hitbox;
	
	public Bullet(int xx, int yy, int d){
		x=xx;
		y=yy;
		dir=d;
		hitbox = new Hitbox(x,y,width,height);
	}
	
	public void move(){
		if(dir==0){
			x-=s;
		} else if(dir==1){
			y-=s;
		} else if(dir==2){
			x+=s;
		} else if(dir==3){
			y+=s;
		}
		hitbox.setX(x);
		hitbox.setY(y);
	}
	
	public Hitbox getHitbox(){
		return hitbox;
	}
	
	public void moveDraw(int xx, int yy){
		cameraX=xx;
		cameraY=yy;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(x-cameraX, y-cameraY, width, height);
	}
	
}
