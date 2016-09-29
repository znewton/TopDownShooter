package game;

@SuppressWarnings("all")
public class Camera{
	/**
	 * Initializes Camera object with top left corner at (xx,yy), w: w, h: h,
	 * and maximum right position at xMax.
	 * @param xx
	 * @param yy
	 * @param w
	 * @param h
	 * @param mx
	 */
	public Camera(int xx, int yy, int w, int h, int mx, int my){
		x=xx;
		y=yy;
		width=w;
		height=h;
		xMax=mx;
		yMax=my;
	}
	/**
	 * Returns top left camera X value.
	 * @return
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns top left Camera Y value.
	 * @return
	 */
	public int getY(){
		return y;
	}
	/**
	 * Moves Camera based on (xx,yy). Typically the input is the Player position.
	 * Stops moving at mins and maxes.
	 * @param xx
	 * @param yy
	 */
	public void move(int xx, int yy){
		if((xx<(3*width/7)+x) && x > xMin ){
			//System.out.println("Camera Y: "+y);
			x=xx-3*width/7;
		}
		if((xx>(4*width/7)+x) && x < xMax-width ){
			x=xx-4*width/7;
		}
		if((yy<(3*height/7)+y) && y > yMin){
			y=yy-3*height/7;
		}
		if((yy>((4*height)/7)+y) && y < yMax-height){
			y=yy-4*height/7;
		}
	}
	
	int x;
	int y;
	int width;
	int height;
	int xMin=0;
	int xMax;
	int yMin=0;
	int yMax;
}