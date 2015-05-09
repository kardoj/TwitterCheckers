package ee.tlu.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ActionMessage {
	private String message;
	private int x;
	private int y;
	private Color shadowColor;
	private Color color;
	private int size;
	
	public ActionMessage(String message, int x, int y){
		this.message = message;
		this.x = x;
		this.y = y;
		color = Color.WHITE;
		shadowColor = Color.BLACK;
		size = 20;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public void draw(Graphics g){
		g.setFont(new Font("TimesRoman", Font.BOLD, size));
		
		// Joonistan "varju"
		g.setColor(shadowColor);
		g.drawString(message, x-1, y-1);
		
		// Joonistan teksti
		g.setColor(color);
		g.drawString(message, x, y);
	}

}
