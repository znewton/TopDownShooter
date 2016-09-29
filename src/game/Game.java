package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("all")
public class Game extends JPanel{
	public static final int FRAME_HEIGHT = 1024;	//25 frame height pixels used up by header
	public static final int FRAME_WIDTH = 1024;		// 1 meter = 25 pixels
	static final int LEVEL_SIZE=1;
	
	
	public Game(){		//constructs levels and adds player
		
		
		player = new Player(10,10);
		ui = new UI(FRAME_WIDTH,FRAME_HEIGHT);
		
		generateLevel();
		
//		level.addPickup(732,300,25,25,"coin",null,1);
//		level.addPickup(732,0,25,25,"coin",null,5);
//		level.addPickup(732,-500,25,25,"coin",null,10);
//		level.addPickup(900,-550,25,25,"heal",null,10);
		int bulOff = 7;
		int bulDel = 30;
		addKeyListener(new KeyAdapter(){ //necessary for key presses and releases
			@Override
			public void keyPressed(KeyEvent evt){ //for when key is pressed
				switch(evt.getKeyCode()){
					case KeyEvent.VK_UP: //keys are coded as "KeyEvent.VK_" and then the key name or letter in caps
						if(bc>=bulDel && player.getAmmo()>0){
							level.shoot(player.getX()+bulOff,player.getY()+bulOff,1);
							player.useAmmo();
							bc=0;
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(bc>=bulDel && player.getAmmo()>0){
							level.shoot(player.getX()+bulOff,player.getY()+bulOff,2);
							player.useAmmo();
							bc=0;
						}						
						break;
					case KeyEvent.VK_LEFT:
						if(bc>=bulDel && player.getAmmo()>0){
							level.shoot(player.getX()+bulOff,player.getY()+bulOff,0);
							player.useAmmo();
							bc=0;
						}						
						break;
					case KeyEvent.VK_DOWN:
						if(bc>=bulDel && player.getAmmo()>0){
							level.shoot(player.getX()+bulOff,player.getY()+bulOff,3);
							player.useAmmo();
							bc=0;
						}						
						break;
					case KeyEvent.VK_W: //keys are coded as "KeyEvent.VK_" and then the key name or letter in caps
						if(player.getHitbox().getTop()>=0)player.accUp();
						else player.decUp();
						break;
					case KeyEvent.VK_D:
						if(player.getHitbox().getRight()<=level.getWidth())player.accRight();
						else player.decRight();
						break;
					case KeyEvent.VK_A:
						if(player.getHitbox().getLeft()>=0)player.accLeft();
						else player.decLeft();
						break;
					case KeyEvent.VK_S:
						if(player.getHitbox().getBottom()<=level.getHeight())player.accDown();
						else player.decDown();
						break;
					case KeyEvent.VK_SHIFT:
						player.run();
						break;
					case KeyEvent.VK_P:
						if(running){
							running = false;
							System.out.println("PAUSE");
						} else{
							running = true;
							System.out.println("PLAY");
						}
						break;
					case KeyEvent.VK_BACK_SPACE:
						running=true;
						gameover=false;
						player = new Player(10,10);
						survivalTime=0;
						generateLevel();
						counter=0;
				}
			}
			@Override
			public void keyReleased(KeyEvent evt){ //for when key is released
				switch(evt.getKeyCode()){
					case KeyEvent.VK_RIGHT:
						shot=false;
						break;
					case KeyEvent.VK_LEFT:
						shot=false;
						break;
					case KeyEvent.VK_UP:
						shot=false;
						break;
					case KeyEvent.VK_DOWN:
						shot=false;
						break;
					case KeyEvent.VK_D:
						player.decRight();
						break;
					case KeyEvent.VK_A:
						player.decLeft();
						break;
					case KeyEvent.VK_W:
						player.decUp();
						break;
					case KeyEvent.VK_S:
						player.decDown();
						break;
					case KeyEvent.VK_SHIFT:
						player.walk();
						break;
				}
			}
		});
		
		setFocusable(true);
	}
	/**
	 * Randomly Generates level.
	 */
	public void generateLevel(){
		level = new Level(FRAME_WIDTH/2,FRAME_HEIGHT/2,FRAME_WIDTH*LEVEL_SIZE,FRAME_HEIGHT*LEVEL_SIZE,"Generated",FRAME_WIDTH,FRAME_HEIGHT);
		camera = new Camera(0,0, FRAME_WIDTH, FRAME_HEIGHT, level.getWidth(),level.getHeight());
		player.setXY(level.getStartX(),level.getStartY());
		Random gen = new Random();
		int rx;
		int ry;
		int rw;
		int rh;
		int numRocks=15*LEVEL_SIZE;
		for(int i = 0; i < numRocks;i++){
			rw = gen.nextInt(10)*10+50;
			rh = rw*(gen.nextInt(4)+5)/7;
			rx = gen.nextInt((level.getWidth()-rw)/10)*10+50;
			ry = gen.nextInt((level.getWidth()-rh)/10)*10+50;
			level.addEnvObj(rx, ry, rw, rh, "Rock"+i, true, FRAME_WIDTH, FRAME_HEIGHT);
		}
		int sx=0;
		int sy=0;
		int numSpawns=10*LEVEL_SIZE;
		String c="";
		Hitbox temp=new Hitbox(sx,sy,50,50);
		for(int i=0;i<numSpawns;i++){
			boolean spawnable=false;
			while(!spawnable){
				sx = gen.nextInt(level.getWidth()/10-20)*10+50;
				sy = gen.nextInt(level.getWidth()/10-20)*10+50;
				temp.setX(sx);
				temp.setY(sy);
				spawnable=true;
				for(int j=0;j<level.getObjListSize();j++){
					c=temp.getCollision(level.getEnvObj(j).getHitbox());
					if(!c.equals("null")){
						spawnable=false;
					}
				}
			}
			level.addSpawn(sx, sy);
		}
		level.spawn(s);
		s++;
		if(s>=level.getSpListSize()) s=0;
	}
	/**
	 * Draws reference grid.
	 * @param g
	 */
	public void drawGrid(Graphics g){
		g.setColor(new Color(50,50,50));
		int freq = 10;
		for(int i=0;i<level.getWidth();i+=1){
			g.drawLine(level.getWidth()/freq*i-camera.getX(), 0-camera.getY(), level.getWidth()/freq*i-camera.getX(), level.getHeight()-camera.getY());
		}
		for(int i=0;i<level.getWidth();i+=1){
			g.drawLine(0-camera.getX(),level.getHeight()/freq*i-camera.getY(), level.getWidth()-camera.getX(), level.getHeight()/freq*i-camera.getY());
		}
	}
	/**
	 * Renders level frame by frame.
	 * @param g
	 */
	@Override
	public void paint(Graphics g){
		if(running){
			super.paint(g);			//resets canvas
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1024,1024);
			drawGrid(g);
			g.setColor(Color.WHITE);
			
			//sc of 60 is 1 second
			
			if(sc>(60*(spawnTime-2))){
				if(sc%10==0){
					spawnWarn=!spawnWarn;
				}
				if(spawnWarn){
					g.setColor(Color.WHITE);
					g.fillOval(level.getSpawn(s).getX()-camera.getX(), level.getSpawn(s).getY()-camera.getY(), 40, 40);
				}
			}
			if(sc>=60*spawnTime){
				level.spawn(s);
				sc=0;
				s++;
				if(s>=level.getSpListSize()){
					s=0;
				}
			} else{
				sc++;
			}
			
			
			player.move();
			camera.move(player.getX(),player.getY());
			level.moveLevel(camera.getX(), camera.getY(), player.getX(), player.getY()); //moves Environment
			
			player.giveCamXY(camera.getX(), camera.getY());
			checkPlayerCollisions();
			checkEnemyCollisions();
			checkBulletCollisions();
			
			ui.givePlayerStats(player.getHealth(),player.getScore(),player.getAmmo(), survivalTime);
			
			
			level.drawLevel(g);		//draws level
			player.draw(g);						//draws player
			ui.drawUI(g);
			
			if(player.getHealth()<=0){
				gameover=true;
				running=false;
			}
			if(sc%60==0){
				System.out.println(sc/60 + "," + s);
				survivalTime++;
			}
			if(survivalTime%spawnTime ==0){
				spawnTime++;
			}
			bc++;
			//System.out.println((level.getEnvObj(0).getName()) + " y= " + level.getEnvObj(0).getY());
		} else if(gameover){
			gameOver(g);
		} else if(levelIntro){
			levelIntro(g);
		}
	}
	
	/**
	 * Loops through each object type. Interacts player with objects as necessary.
	 */
	public void checkPlayerCollisions(){
		String c;
		Hitbox h;
		EnvObj o;
		Pickup p;
		Enemy e;
		for(int i=0;i<level.getObjListSize();i++){
			h=level.getEnvObj(i).getHitbox(); 	//gets object hitbox
			o=level.getEnvObj(i);
			c=player.getHitbox().getCollision(h);			//checks player hitbox with object hitbox
			if(!(c.equals("null"))){
				if(c.equals("bottom")){
					player.stopY();
					player.setXY(player.getX(),(h.getTop()-(player.getHitbox().getBottom()-player.getHitbox().getTop())));
				} else if(c.equals("top")){
					player.stopY();
					player.setXY(player.getX(),h.getBottom());
				} else if(c.equals("right")){
					player.stopX();
					player.setXY((h.getLeft()-(player.getHitbox().getRight()-player.getHitbox().getLeft())), player.getY());
				} else if(c.equals("left")){
					player.stopX();
					player.setXY(h.getRight(), player.getY());
				}
			}
		}
		for(int i=0;i<level.getPupListSize();i++){
			h=level.getPickup(i).getHitbox(); 	//gets object hitbox
			p=level.getPickup(i);
			c=player.getHitbox().getCollision(h);
			if(!c.equals("null") && p.isExisting()){
				if(p.isHeal()){
					player.heal(p.getValue());
				} else if(p.isCoin()){
					player.addScore(p.getValue());
				} else if(p.isAmmo()){
					player.addAmmo(p.getValue());
				}
				p.used();
			}else if(!p.isExisting()){
				p.remove();
				if(p.isRemovable()){
					level.removePickup(i);
					i--;
				}
			}
		}
		for(int i=0;i<level.getEneListSize();i++){
			h=level.getEnemy(i).getHitbox(); 	//gets object hitbox
			e=level.getEnemy(i);
			c=player.getHitbox().getCollision(h);
			if(!(c.equals("null") && e.isAlive())){
				if(c.equals("bottom")){
					player.stopY();
					player.setXY(player.getX(),(h.getTop()-(player.getHitbox().getBottom()-player.getHitbox().getTop())));
//					player.miniJump();
					player.takeDamage(e.getDamage());
				} else if(c.equals("top")){
					player.stopY();
					player.setXY(player.getX(),h.getBottom());
//					player.miniJumpDown();
					player.takeDamage(e.getDamage());
				} else if(c.equals("right")){
					player.stopX();
					player.setXY((h.getLeft()-(player.getHitbox().getRight()-player.getHitbox().getLeft())), player.getY());
//					player.jumpBack("right");
					player.takeDamage(e.getDamage());
				} else if(c.equals("left")){
					player.stopX();
					player.setXY(h.getRight(), player.getY());
//					player.jumpBack("left");
					player.takeDamage(e.getDamage());
				} else if(!e.isAlive()){
					e.remove();
					if(e.isRemovable()){
						level.removeEnemy(i);
						i--;
					}
				}
			}
		}
	}
	
	/**
	 * Renders GameOver Screen.
	 * @param g
	 */
	public void gameOver(Graphics g){
		counter++;
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
		if(counter>40){
			g.setColor(new Color(255,255,255));
			g.drawString("GAME  OVER",FRAME_WIDTH/2-10,FRAME_HEIGHT/2-3);
		}
		if(counter>100){
			g.setColor(new Color(255,255,255));
			g.drawString("Press BACKSPACE to restart...",FRAME_WIDTH/2-50,FRAME_HEIGHT/2-3+30);
		}
	}
	
	/**
	 * Renders LevelIntro Screen.
	 * @param g
	 */
	public void levelIntro(Graphics g){
		counter++;
		g.setColor(new Color(0,0,0));
		g.fillRect(0,0,FRAME_WIDTH,FRAME_HEIGHT);
		if(counter>20 && counter<120){
			g.setColor(new Color(255,255,255));
			g.drawString(level.getName(),FRAME_WIDTH/2-10,FRAME_HEIGHT/2-3);
		}
		if(counter==140){
			levelIntro=false;
			running=true;
			counter=0;
		}
	}
	
	/**
	 * Loops through each object type for each enemy. Interacts enemy with objects as necessary.
	 */
	public void checkEnemyCollisions(){
		for(int j=0;j<level.getEneListSize();j++){
			String c;
			Hitbox h;
			EnvObj o;
			Enemy e;
			Enemy enemy=level.getEnemy(j);
			for(int i=0;i<level.getObjListSize();i++){
				h=level.getEnvObj(i).getHitbox(); 	//gets object hitbox
				o=level.getEnvObj(i);
				c=enemy.getHitbox().getCollision(h);			//checks enemy hitbox with object hitbox
				if(!(c.equals("null"))){
					if(c.equals("bottom")){
						enemy.changeDir("up");
						enemy.setY(h.getTop()-enemy.getHeight());
					} else if(c.equals("top")){
						enemy.changeDir("down");
						enemy.setY(h.getBottom());
					}
					if(c.equals("right")){
						enemy.changeDir("left");
						enemy.setX(h.getLeft()-enemy.getWidth());
					} else if(c.equals("left")){
						enemy.changeDir("right");
						enemy.setX(h.getRight());
					}
				}
			}
			for(int i=0;i<level.getEneListSize();i++){
				h=level.getEnemy(i).getHitbox(); 	//gets object hitbox
				e=level.getEnemy(i);
				c=enemy.getHitbox().getCollision(h);
				if(!(c.equals("null") && e.isAlive())){
					if(c.equals("bottom")){
						enemy.changeDir("up");
						enemy.setY(h.getTop()-enemy.getHeight());
					} else if(c.equals("top")){
						enemy.changeDir("down");
						enemy.setY(h.getBottom());
					}
					if(c.equals("right")){
						enemy.changeDir("left");
						enemy.setX(h.getLeft()-enemy.getWidth());
					} else if(c.equals("left")){
						enemy.changeDir("right");
						enemy.setX(h.getRight());
					}
				}
			}
		}
	}
	
	public void checkBulletCollisions(){
		Random gen = new Random();
		int drop;
		for(int j=0;j<level.getBulListSize();j++){
			String c;
			Hitbox h;
			EnvObj o;
			Enemy e;
			Bullet bull=level.getBullet(j);
			for(int i=0;i<level.getObjListSize();i++){
				h=level.getEnvObj(i).getHitbox(); 	//gets object hitbox
				o=level.getEnvObj(i);
				c=bull.getHitbox().getCollision(h);			//checks enemy hitbox with object hitbox
				if(!(c.equals("null"))){
					level.removeBullet(j);
//					System.out.println("Remove");
					if(j!=0)j--;
				}
			}
			for(int i=0;i<level.getEneListSize();i++){
				h=level.getEnemy(i).getHitbox(); 	//gets object hitbox
				e=level.getEnemy(i);
				c=bull.getHitbox().getCollision(h);
				if(!(c.equals("null") && e.isAlive())){
					level.removeBullet(j);
					if(j!=0)j--;
					level.getEnemy(i).killed();
					drop = gen.nextInt(100);
					if(drop<dropChance){
						drop = gen.nextInt(10);
						if(drop>5){
							//drop resource
							level.addPickup(e.getX(), e.getY(), 25, 25, "coin", null, 1, FRAME_WIDTH, FRAME_HEIGHT);
							System.out.println("drop resource");
						} else if(drop>2){
							//drop ammo
							level.addPickup(e.getX(), e.getY(), 25, 25, "ammo", null, 20, FRAME_WIDTH, FRAME_HEIGHT);
							System.out.println("drop ammo");
						} else {
							//drop health
							level.addPickup(e.getX(), e.getY(), 25, 25, "health", null, 10, FRAME_WIDTH, FRAME_HEIGHT);
							System.out.println("drop health");
						}
					}
					return;
				}
			}
			if(bull.getHitbox().getBottom() > level.getHeight() ||
				bull.getHitbox().getRight() > level.getWidth() ||	
				bull.getHitbox().getLeft() < 0 ||
				bull.getHitbox().getTop() < 0){
				level.removeBullet(j);
				j--;
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame("Game");
		Game game= new Game();
		frame.add(game);
		game.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		
		while(true){
			game.repaint();
			Thread.sleep(1000/60);
		}
	}
	
	int survivalTime=0;
	int counter;
	int bc=0;
	int sc=0;
	int s=0;
	int score;
	int spawnTime=10;
	int dropChance=20;
	boolean shot=false;
	boolean shotRight=false;
	boolean shotLeft=false;
	boolean shotUp=false;
	boolean shotDown=false;
	boolean spawnWarn=false;
	boolean levelIntro=false;
	boolean running =true;
	boolean gameover=false;
	Player player;
	Camera camera;
	UI ui;
	Level level;
}