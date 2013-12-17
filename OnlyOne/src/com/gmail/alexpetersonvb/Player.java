package com.gmail.alexpetersonvb;

import java.awt.image.BufferedImage;

import com.gmail.alexpetersonvb.graphics.Sprite;

public class Player {
	public long past;
	public double delta = 0.016666;
	public double x,y; 
	public boolean jumpping = false;
	public double g = .5;
	public boolean jetpack = false;
	public double xspeed = 5;
	public double yspeed = 0;
	public BufferedImage img;
	public SpawnPoint sp = new SpawnPoint();
	public boolean overTile = false;
	public boolean underTile = false;
	public boolean dead =false;
	
	//public int xpos = 0;
	//public int ypos =  368;
	
	public Player(){
		x = 48;
		y = 368;
	}
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	public void jump(){
		past = System.currentTimeMillis();
		yspeed = 5;
		
	}
	
	public void endJump(){
		
		yspeed = 0;
	}
	
	public BufferedImage getBi(){
		//for(int i = 0; i < pixels.length; i++){
		img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
		//img.setRGB(0, 0, 16, 16, this.pixels, 0, this.pixels.length);
		int count = 0;
		for(int y = 0; y < 64; y++){
			for(int x = 0; x < 64; x++){
				img.setRGB(x, y, Sprite.player.pixels[count]);
				count++;
			}
		}
			//img.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
		//}
		return img;
	}
}
