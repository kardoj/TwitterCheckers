package ee.tlu.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Board {

	public static final int SIDE = 70;
	public static final int startX = SIDE/2;
	public static final int startY = SIDE/2;
	private final Color LIGHT = Color.WHITE;
	private final Color DARK = Color.BLACK;
	
	public Board(){}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.setFont(new Font("timesRoman", Font.BOLD, 20));
		
		// Numbrid vasakult paremale
		int x = startX;
		int y = SIDE/4+5;
		for(int i = 0; i < 8; i++){
			g.drawString(String.valueOf(i+1), x+SIDE/2-5, y);
			x += SIDE;
		}
		
		// Numbrid ülevalt alla
		x = startX-SIDE/4-5;
		y = startY;
		for(int i = 0; i < 8; i++){
			g.drawString(String.valueOf(i+1), x, y+SIDE/2+5);
			y += SIDE;
		}
		
		// Mängulaud
		int color = 1; // 0 - hele, 1 - tume
		x = startX;
		y = startY;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(color == 0){
					g.setColor(LIGHT);
				} else {
					g.setColor(DARK);
				}
				g.fillRect(x, y, SIDE, SIDE);
				if(j != 7){
					if(color == 0){
						color = 1;
					} else {
						color = 0;
					}
				}
				x += SIDE;
			}
			x = startX;
			y += SIDE;
		}		
	}
	
}
