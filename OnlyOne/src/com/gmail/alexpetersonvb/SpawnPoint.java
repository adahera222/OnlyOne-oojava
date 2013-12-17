package com.gmail.alexpetersonvb;

public class SpawnPoint {

	public boolean carried = false;
	public int tilex; 
	
	//(int)(p.x + (scr * 3))/48
	
	public void set(int x, int scr){
		tilex = (int)x + (scr * 3)/48;
	}
	
	
}
