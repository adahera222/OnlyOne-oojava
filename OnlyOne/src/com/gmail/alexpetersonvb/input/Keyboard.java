package com.gmail.alexpetersonvb.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	private boolean[] keys = new boolean[200];
	public boolean left, right, jump, sright, sleft, q;

	public Keyboard(){
		
	}
	
	public void update(){
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		jump = keys[KeyEvent.VK_SPACE];
		sright = keys[KeyEvent.VK_CLOSE_BRACKET];
		sleft = keys[KeyEvent.VK_OPEN_BRACKET]; 
		q = keys[KeyEvent.VK_Q];
		
		/*
		for(int i = 0; i < keys.length; i++){
			if(keys[i]){
				System.out.println("key: " + i);
			}
		}*/
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	
	public void keyTyped(KeyEvent e) {
		
	}

}
