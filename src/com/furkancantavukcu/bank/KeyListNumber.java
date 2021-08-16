package com.furkancantavukcu.bank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListNumber implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//hangi tusa basildigini okuyoruz
		char caracter = e.getKeyChar();
		// bu if sart kontrolünde sadece 0 ile 9 arasinda rakamlarin girilebilecegini belirtiyoruz
		if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {

            e.consume();
        }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
