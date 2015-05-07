package ee.tlu.classes;

import java.awt.Color;
import java.awt.Graphics;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

import static ee.tlu.classes.Board.SIDE;

public class PieceManipulator {

	public static final Color firstTeamColor = Color.BLUE;
	public static final Color secondTeamColor = Color.GREEN;
	public static final Color highlightedColor = Color.CYAN;
	
	private List<GamePiece> activeGamePieces = new ArrayList<>();
	private int teamOnePieceCount = 12;
	private int teamTwoPieceCount = 12;
	
	public PieceManipulator(){}
	
	public void createInitialGamePieces(){
		int[][] initialBoardState = {
				{2, 0, 2, 0, 2, 0, 2, 0},
				{0, 2, 0, 2, 0, 2, 0, 2},
				{2, 0, 2, 0, 2, 0, 2, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 0, 1, 0, 1, 0, 1},
				{1, 0, 1, 0, 1, 0, 1, 0},
				{0, 1, 0, 1, 0, 1, 0, 1}};
		
		int x = 0;
		int y = 0;
		for (int i = 0; i < initialBoardState.length; i++) {
			for (int j = 0; j < initialBoardState[i].length; j++) {
				if(initialBoardState[i][j] == 1){
					activeGamePieces.add(new GamePiece(i+1, j+1, 1));
				} else if(initialBoardState[i][j] == 2){
					activeGamePieces.add(new GamePiece(i+1, j+1, 2));
				}
				x += SIDE;
			}
			x = 0;
			y += SIDE;
		}
	}
	
	public void draw(Graphics g){
		for(GamePiece piece: activeGamePieces){
			piece.draw(g);
		}
	}
	
	public void remove(int row, int column){
		int position = findGamePiecePositionInList(row, column);
		activeGamePieces.remove(position);
	}
	
	public int findGamePiecePositionInList(int row, int column){
		for(int i=0; i<activeGamePieces.size(); i++){
			boolean rowsMatch = activeGamePieces.get(i).getRow() == row;
			boolean columnsMatch = activeGamePieces.get(i).getColumn() == column;
			if(rowsMatch && columnsMatch){
				return i;
			}
		}
		return -1;
	}
	
	public void add(GamePiece piece){
		activeGamePieces.add(piece);
	}
	
	public boolean isPlayerPiece(int row, int column){
		int position = findGamePiecePositionInList(row, column);
		if(position >= 0){
			if(activeGamePieces.get(position).getTeam() == 1){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAPiece(int row, int column){
		int position = findGamePiecePositionInList(row, column);
		if(position != -1){
			return true;
		}
		return false;
	}
	
	public void move(int fromRow, int fromColumn, int toRow, int toColumn){
		if(possibleToMove(fromRow, fromColumn, toRow, toColumn)){
			int movingPieceIndex = findGamePiecePositionInList(fromRow, fromColumn);
			GamePiece movingPiece = activeGamePieces.get(movingPieceIndex);
			movingPiece.setRow(toRow);
			movingPiece.setColumn(toColumn);
		} else {
			System.out.println("Sellist käiku ei saa teha!");
		}
	}
	
	private boolean possibleToMove(int fromRow, int fromColumn, int toRow, int toColumn){
		if(!hasAPiece(toRow, toColumn) && withinReach(fromRow, fromColumn, toRow, toColumn)){
			return true;
		}
		return false;
	}
	
	private boolean withinReach(int fromRow, int fromColumn, int toRow, int toColumn){
		// TODO Kontroll, kas saab liikuda
		return true;
	}
		
}
