package ee.tlu.classes;

import static ee.tlu.classes.Board.SIDE;

/*
 * Klass koordinaatide lihtsustamiseks. Nii saab nuppe liigutada kasutades
 * rea ja tulba numbrit. Coordinates leiab ise külje järgi vastavad x ja y.
*/

public class CoordinateCalculator {
	
	// Private konstruktor, et objekti ei loodaks
	private CoordinateCalculator(){}
	
	public static int getXFromColumn(int column){
		return ((column-1)*SIDE);
	}
	
	public static int getYFromRow(int row){
		return ((row-1)*SIDE);
	}
	
	public static int getColumnFromX(int x){
		return (x / SIDE)+1;
	}
	public static int getRowFromY(int y){
		return (y / SIDE)+1;
	}
}
