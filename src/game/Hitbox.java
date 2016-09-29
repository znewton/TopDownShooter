package game;

//@SuppressWarnings("all")
public class Hitbox{
	/**
	 * Initializes Hitbox object at (xx,yy) with w: w, h: h.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 */
	public Hitbox(int xx, int yy, int w, int h){
		x=xx;
		y=yy;
		width=w;
		height=h;
	}
	/*-------DATA---------*/
	/**
	 * Manually sets X value.
	 * @param xx
	 */
	public void setX(int xx){
		x=xx;
	}
	/**
	 * Manually sets Y value.
	 * @param yy
	 */
	public void setY(int yy){
		y=yy;
	}
	/**
	 * Returns the left side of the hitbox X value.
	 * @return
	 */
	public int getLeft(){
		return x;
	}
	/**
	 * Returns the top side of the hitbox Y value.
	 * @return
	 */
	public int getTop(){
		return y;
	}
	/**
	 * Returns the right side of the hitbox X value.
	 * @return
	 */
	public int getRight(){
		return x+width;
	}
	/**
	 * Returns the bottom side of the hitbox Y value.
	 * @return
	 */
	public int getBottom(){
		return y+height;
	}
	
	/*-------COLLISIONS--------*/
	/**
	 * Returns a String describing where on this object, the other object's hitbox h hits.
	 * @param h
	 * @return
	 */
	public String getCollision(Hitbox h){
		//System.out.println("h Top: " +h.getTop());
		//System.out.println("h Bottom: " +h.getBottom());
		//System.out.println("this Bottom: " +this.getBottom());
		//System.out.println("this Top: " +this.getTop());
		if((this.getBottom() >= h.getTop()) && (this.getTop() < h.getTop()) && ((this.getLeft() <h.getRight()) && (this.getRight()>h.getLeft()))){	//Bottom
			//System.out.println("bottom collision");
			return "bottom";
		}
		
		else if((this.getTop() <= h.getBottom()) && (this.getBottom() > h.getBottom()) && (this.getLeft() <h.getRight()) && (this.getRight()>h.getLeft())){	//Top
			//System.out.println("top collision");
			return "top";
		}
		if((this.getRight() >= h.getLeft()) && (this.getRight() < h.getRight()) && (this.getTop()<h.getBottom()) && (this.getBottom()>h.getTop())){			//Right
			//System.out.println("right collision");
			return "right";
		}
		else if((this.getLeft() <= h.getRight()) && (this.getLeft()>h.getLeft()) && (this.getTop()<h.getBottom()) && (this.getBottom()>h.getTop())){	//Left
			//System.out.println("left collision");
			return "left";
		}
		return "null";
	}
	
	int x;
	int y;
	int width;
	int height;
}