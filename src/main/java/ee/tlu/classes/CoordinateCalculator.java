package ee.tlu.classes;

import static ee.tlu.classes.Board.SIDE;
import static ee.tlu.classes.Board.startX;
import static ee.tlu.classes.Board.startY;

/*
 * Klass koordinaatide lihtsustamiseks. Nii saab nuppe liigutada kasutades
 * rea ja tulba numbrit. Coordinates leiab ise külje järgi vastavad x ja y.
*/

public class CoordinateCalculator {
	
	// Private konstruktor, et objekti ei loodaks
	private CoordinateCalculator(){}
	
	public static int getXFromColumn(int column){
		return (((column-1)*SIDE)+startX);
	}
	
	public static int getYFromRow(int row){
		return (((row-1)*SIDE)+startY);
	}
	
	public static int getColumnFromX(int x){
		return ((x-startX) / SIDE)+1;
	}
	public static int getRowFromY(int y){
		return ((y-startY) / SIDE)+1;
	}
}
