package com.gmail.alexpetersonvb.graphics;


import java.awt.Color;
import java.util.Random;

public class Screen {

	private int width, height;
	public int[] pixels;
	public int[] tiles = new int[256 * 64];
	public int scr;
	public int[] pit = new int [258];
	
	private Random random = new Random();
	
	public Screen(int width,int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < 256 * 64; i++){
			tiles[i] = Color.cyan.getRGB(); //random.nextInt(0xffffff);
		}
		for(int i = 0; i < 64; i++){
			tiles[64*9 + i] = 0x4DBD33;//0x000000;
			tiles[64*10 + i] = 0x663300; //0x000000;
		}
		for(int i = 64 * 1; i < 64*3; i++){
			if(random.nextBoolean()){
				tiles[i] = Color.WHITE.getRGB();
			}
		}
		for (int i = 5; i < 11; i++){
			tiles[64 * i] = 0x000000;
		}
		for(int i = 7; i < 64; i++){
			if(random.nextInt(5) == 1){
				//tiles[64 * 9 + i] = Color.cyan.getRGB(); //0x000000;
				//tiles[64 * 9 + i + 1] = Color.cyan.getRGB();//0x000000;
				pit[i] = 1;
				pit[i + 1] = 1;
				tiles[64*10 + i] = 0x663300; //0x000000;
			}
		}
		
		for(int i = 1; i < 4; i++){
			tiles[64 * 9 + i] = 0xFF0F00;
		}
		//tiles[64 * 9 + 3] = 0xFFFFFF;
	}
	
	public void newLv(){
		pixels = new int[width * height];
		
		for(int i = 0; i< 64; i++){
			pit[i] = 0;
		}
		
		for(int i = 0; i < 256 * 64; i++){
			tiles[i] = Color.cyan.getRGB(); //random.nextInt(0xffffff);
		}
		for(int i = 0; i < 64; i++){
			tiles[64*9 + i] = 0x4DBD33;//0x000000;
			tiles[64*10 + i] = 0x663300; //0x000000;
		}
		for(int i = 64 * 1; i < 64*3; i++){
			if(random.nextBoolean()){
				tiles[i] = Color.WHITE.getRGB();
			}
		}
		for (int i = 5; i < 11; i++){
			tiles[64 * i] = 0x000000;
		}
		for(int i = 7; i < 64; i++){
			if(random.nextInt(5) == 1){
				tiles[64 * 9 + i] = 0x000000;
				tiles[64 * 9 + i + 1] = 0x000000;
				pit[i] = 1;
				pit[i + 1] = 1;
				tiles[64*10 + i] = 0x663300; //0x000000;
			}
		}
		
		for(int i = 1; i < 4; i++){
			tiles[64 * 9 + i] = 0xFF0F00;
		}
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length;i++){
			pixels[i] = 0;
		}
	}
	
	public void render(){
		for(int y = 0; y < height; y++){
			if(y <  0 || y >= height) break;
			for(int x = 0; x<width; x++){
				if(x < 0 || x >= width)break;
				int xx = x + scr;
				int tileIndex = ((xx >> 4) & 63) + ((y >> 4) & 63) * 64;
				pixels[x + y * width] = tiles[tileIndex];
			}
		}
	}
	
	public void setScr(int scroll){
		 scr = scroll;
	}

	/*public void renderPlayer(int xpos, int ypos) {
		for(int y = 0; y < 16; y++){
			for(int x = 0; x < 16; x++){
				if(x < -16 || x >= width || y < 0 || y >= height) break;
				pixels[x + y * width] = Sprite.player.pixels[x+y*16];
			}
			
		}
		
	}*/
	
}
