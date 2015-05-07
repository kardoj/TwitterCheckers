package ee.tlu.classes;

import java.awt.Color;
import java.awt.Graphics;

public class Board {

	public static final int SIDE = 100;
	
	public Board(){}
	
	public void draw(Graphics g){
		int color = 1; // 0 - hele, 1 - tume
		int x = 0;
		int y = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(color == 0){
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.BLACK);
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
			x = 0;
			y += SIDE;
		}		
	}
	
}
