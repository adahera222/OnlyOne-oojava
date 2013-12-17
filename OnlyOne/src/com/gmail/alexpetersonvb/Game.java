package com.gmail.alexpetersonvb;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.gmail.alexpetersonvb.graphics.Screen;
import com.gmail.alexpetersonvb.graphics.Sprite;
import com.gmail.alexpetersonvb.graphics.SpriteSheet;
import com.gmail.alexpetersonvb.input.Keyboard;

public class Game extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static String title = "You only get one";
	private boolean running = false;
	private Keyboard key;
	private JFrame frame;
	private Thread thread;
	public int scr = 0;
	private Player p = new Player();
	public boolean won = false;
	public boolean displayDebug = false;
	public int timesWon = 0;
	
	
	
	
	private Screen screen;
	
	private BufferedImage spike = new BufferedImage(48,48,BufferedImage.TYPE_INT_RGB);
	
	private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	//private BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(){
		
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		
		screen = new Screen(width,height);
		frame = new JFrame();
		
		key = new Keyboard();
		addKeyListener(key);
	}
	
	public synchronized void start(){
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}

	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		try{
			this.spike = ImageIO.read(SpriteSheet.class.getResource("/textures/spikes.png"));
		} catch (IOException e) {
			
		}
		while(running){
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frame.setTitle(title + " | " + updates + " ups " + frames + " fps");
				frames = 0;
				updates = 0;
				
			}
			
		}
	}
	
	/*
	 * 
var positionX = 100.0;
var positionY = 175.0;
var velocityX = 4.0;
var velocityY = 0.0;
var gravity = 0.5;
var onGround = false;

window.addEventListener("mousedown", StartJump, false);
window.addEventListener("mouseup", EndJump, false);

Loop();

function StartJump()
{
    if(onGround)
    {
        velocityY = -12.0;
        onGround = false;
    }
}

function EndJump()
{
    if(velocityY < -6.0)
        velocityY = -6.0;
}

function Loop()
{
    Update();
    Render();
    window.setTimeout(Loop, 33);    
}

function Update()
{
    velocityY += gravity;
    positionY += velocityY;
    positionX += velocityX;
    
    if(positionY > 175.0)
    {
        positionY = 175.0;
        velocityY = 0.0;
        onGround = true;
    }
    
    if(positionX < 10 || positionX > 190)
        velocityX *= -1;
}

	 */
	
	public void update(){
		key.update();
		/*
		if(key.sleft){
			scr -= 3;
			screen.setScr(scr);
		}
		
		if(key.sright){
			scr += 3;
			screen.setScr(scr);
		}
		*/
		if(key.left || key.right && !p.dead){
			if(key.left && !(scr == 0 && p.x == 48)){
				p.xspeed = -5;
			}
			if(key.right){
				p.xspeed = 5;
			}
		}else{
			p.xspeed = 0;
		}
		
		
		
		displayDebug = key.q;
		//System.out.println(p.overTile);
		if(key.jump && p.jumpping == false && p.underTile == false){
			p.jumpping = true;
			p.yspeed = -12; //12
		}
		p.underTile = false;
		
		if(p.x <= 100){
			if(scr >= 1){
				p.x += 6;
				scr -= 3;
				screen.setScr(scr);
			}
		}
		
		if(scr == 0 && p.x < 48){
			p.x = 48;
			p.xspeed = 0;
		}
		
		if(scr >= 730 && p.x > 803){
			//System.out.println("DONE");
			scr = 0;
			screen.setScr(scr);
			p.x = 48;
			p.y = 368;
			screen.newLv();
			timesWon++;
			won = true;
		}
		
		if(p.x >= 650){
			if(!(scr >= 733)){
				p.x -= 6;
				scr += 3;
				screen.setScr(scr);
			}
		}
		
		//System.out.println(scr);
		
		//velocityY += gravity;
	    //positionY += velocityY;
	    //positionX += velocityX;
		//System.out.println("Speed: " + p.yspeed + " Y: " + p.y);
		p.yspeed += p.g;
		p.y += p.yspeed;
		p.x += p.xspeed;
		/*if(p.y >= 368){
			p.yspeed = 0;
			p.y = 368;
			p.jumpping = false;
		}*/
		
		for(int i = 0; i < 256; i++){
			if(screen.pit[i] == 1){
				if(p.x + 32 >= i*48  - (scr *3)  && p.x + 32 <= (i + 1)*48 - (scr *3)){
					p.overTile = true;
					p.underTile = true;
				}else{
					
				}
			}
		}	
		
		
		
		if(p.overTile){
			
		}else{
			if(p.y >= 368){
				p.yspeed = 0;
				p.y = 368;
				p.jumpping = false;
			}
		}
			/*else{
				if(p.y >= 368){
					p.yspeed = 0;
					p.y = 368;
					p.jumpping = false;
				}
			}*/
		
		
		
		for(int i = 0; i < 256; i++){
			if(screen.pit[i] == 1){
				if(p.x + 32 >= i*48  - (scr *3)  && p.x + 32 <= (i + 1)*48 - (scr *3) && p.y - 32 >= 400){
					scr = 0;
					screen.setScr(scr);
					p.x = 48;
					p.y = 368;//368
					p.dead = true;
				}
			}
		}
		p.overTile = false;
		/*
		if(p.y >= 368){
			p.yspeed = 0;
			p.y = 368;
			p.jumpping = false;
		}*/
		
		//p.x = (p.x + p.xspeed * p.delta);
		//p.y = (p.y - p.yspeed + 1/2 * -9.8 * p.delta * p.delta);
		
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		screen.render();
		//screen.renderPlayer(xpos, ypos);
		
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0,0, getWidth(), getHeight(), null);
		//g.fillRect(25 + xpos, 382 + ypos, 32, 32);
		//g.drawString("X: " + p.x + " Y: " + p.y, 900 - 120, 11);
		if(scr > 50){
			g.setColor(Color.gray);
			if(scr > 100){
				g.setColor(Color.LIGHT_GRAY);
			}
		}
		g.drawString("Press a and d or <- and -> to move space to jump, Don't fall...", 0, 11);
		g.setColor(Color.RED);
		//System.out.println(scr);
		for(int i = 0; i < 256; i++){
			if(screen.pit[i] == 1){
				g.drawImage(spike, i * 48 - (scr * 3), 432, null);
			}
		}
		if(displayDebug){
			for(int i = 0; i < 256; i++){
				if(screen.pit[i] == 1){
					//g.fillRect;
					g.setColor(Color.RED);
					g.drawRect(i * 48 - (scr * 3), 380, 48, 48);
					g.setColor(Color.black);
					g.drawString("Over Tile: " + p.underTile, 900 - 170, 25);
					//System.out.println((int)(p.x + (scr * 3))/48);
					g.drawString("Tile: " + (int)(p.x + (scr * 3))/48 + " X: " + p.x + " Y: " + p.y, 900 - 170, 11);
				}
			}
		}
		if(won){
			//g.setFont(Font.BOLD);
			g.drawString("You have won: " + timesWon,10,30);
		}
		//g.drawLine((int)p.x, (int)p.y, 0, 0);
		//g.fillRect((int)p.x - 36, (int)p.y - 36, 36, 36);
		if(!p.dead){
			g.drawImage(Sprite.player.img, (int)p.x, (int)p.y, null);
		}else{
			g.setColor(Color.RED);
			//g.setFont(Font.SANS_SERIF);
			g.drawString("You died! However you beat " + timesWon +  " Levels" , getWidth() / 2 - 70, getHeight() / 2 -30);
		}
		g.dispose();
		bs.show();
		
	}
	
	
	public static void main(String[] args){
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
		System.out.println(Color.cyan.getRGB());
		
	}
	
}
