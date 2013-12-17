package com.gmail.alexpetersonvb.graphics;

import java.awt.image.BufferedImage;

public class Sprite {
	
	private final int SIZE;
	private int x,y;
	public int[] pixels;
	private SpriteSheet sheet;
	public BufferedImage img;
	
	
	public static Sprite player = new Sprite(64,2,0, SpriteSheet.ss);
	//public static Sprite jp = new Sprite(32,0,0,SpriteSheet.ss);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet){
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
		img = getBi();
	}
	
	public BufferedImage getBi(){
		//for(int i = 0; i < pixels.length; i++){
		img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
		//img.setRGB(0, 0, 16, 16, this.pixels, 0, this.pixels.length);
		int count = 0;
		for(int y = 0; y < 64; y++){
			for(int x = 0; x < 64; x++){
				img.setRGB(x, y, pixels[count]);
				count++;
			}
		}
			//img.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
		//}
		return img;
	}

	
	private void load(){
		for(int y = 0; y < SIZE; y++ ){
			for(int x = 0; x < SIZE; x++){
				pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
	

}
