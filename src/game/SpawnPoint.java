package game;

public class SpawnPoint {
	int x;
	int y;
	Hitbox h;
	
	public SpawnPoint(int xx, int yy){
		x=xx;
		y=yy;
		h=new Hitbox(x,y,50,50);
	}
	
	public Hitbox getHitbox(){
		return h;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void setXY(int xx, int yy){
		x=xx;
		y=yy;
		h.setX(x);
		h.setY(y);
	}
	
}
