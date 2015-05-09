package ee.tlu.classes;

import java.awt.Graphics;

import static ee.tlu.classes.Board.SIDE;
import static ee.tlu.classes.PieceManipulator.firstTeamColor;
import static ee.tlu.classes.PieceManipulator.secondTeamColor;

public class GamePiece {
	private int column;
	private int row;
	private int team; // 1 - alumine (mängija), 2 - arvuti (AI)
	
	public GamePiece(int row, int column, int team){
		this.column = column;
		this.row = row;
		this.team = team;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getTeam() {
		return team;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public void draw(Graphics g){
		if(team == 1){
			g.setColor(firstTeamColor);
		} else {
			g.setColor(secondTeamColor);
		}
		g.fillOval(CoordinateCalculator.getXFromColumn(column), CoordinateCalculator.getYFromRow(row), SIDE, SIDE);
	}
}
