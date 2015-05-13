package ee.tlu.classes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class PieceManipulator {

	public static final Color firstTeamColor = Color.LIGHT_GRAY;
	public static final Color secondTeamColor = Color.BLUE;
	
	private List<GamePiece> activeGamePieces = new ArrayList<>();
	private int firstTeamPieceCount = 0;
	private int secondTeamPieceCount = 0;
	
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

		for (int i = 0; i < initialBoardState.length; i++) {
			for (int j = 0; j < initialBoardState[i].length; j++) {
				if(initialBoardState[i][j] == 1){
					activeGamePieces.add(new GamePiece(i+1, j+1, 1));
				} else if(initialBoardState[i][j] == 2){
					activeGamePieces.add(new GamePiece(i+1, j+1, 2));
				}
			}
		}
		
		firstTeamPieceCount = 12;
		secondTeamPieceCount = 12;
	}
	
	public int getFirstTeamPieceCount(){
		return firstTeamPieceCount;
	}
	
	public int getSecondTeamPieceCount(){
		return secondTeamPieceCount;
	}
	
	public void draw(Graphics g){
		for(GamePiece piece: activeGamePieces){
			piece.draw(g);
		}
	}
	
	private int findGamePiecePositionInList(int row, int column){
		for(int i=0; i<activeGamePieces.size(); i++){
			boolean rowsMatch = activeGamePieces.get(i).getRow() == row;
			boolean columnsMatch = activeGamePieces.get(i).getColumn() == column;
			if(rowsMatch && columnsMatch){
				return i;
			}
		}
		return -1;
	}
	
	private boolean hasAPiece(int row, int column){
		int position = findGamePiecePositionInList(row, column);
		boolean gamePieceExists = position != -1;
		if(gamePieceExists){
			return true;
		}
		return false;
	}
	
	// Tagastab true kui käidi ja false kui ei saanud käia.
	public boolean move(TwitterMove newMove){
		int toRow = newMove.getToRow();
		int toColumn = newMove.getToColumn();
		if(possibleToMove(newMove)){
			GamePiece movingPiece = getMovingPiece(newMove);
			movingPiece.setRow(toRow);
			movingPiece.setColumn(toColumn);
			return true;
		}
		return false;
	}
	
	private boolean possibleToMove(TwitterMove move){
		int fromRow = move.getFromRow();
		int fromColumn = move.getFromColumn();
		int toRow = move.getToRow();
		int toColumn = move.getToColumn();
		if(hasAPiece(fromRow, fromColumn) && isCorrectTeamPiece(move) && isAllowedMove(move) && !hasAPiece(toRow, toColumn)){
			return true;
		}
		return false;
	}
	
	private GamePiece getMovingPiece(TwitterMove move){
		int movingPieceIndex = findGamePiecePositionInList(move.getFromRow(), move.getFromColumn());
		boolean movingPieceExists = movingPieceIndex != -1;
		if(movingPieceExists){
			GamePiece movingPiece = activeGamePieces.get(movingPieceIndex);
			return movingPiece;
		}
		return null;
	}
	
	private boolean isCorrectTeamPiece(TwitterMove move){
		GamePiece movingPiece = getMovingPiece(move);
		if(movingPiece.getTeam() == move.getTeam()){
			return true;
		}
		return false;
	}
	
	private void remove(int row, int column){
		int position = findGamePiecePositionInList(row, column);
		subtractOneFromTeamPieceCount(position);
		activeGamePieces.remove(position);
	}
	
	private void subtractOneFromTeamPieceCount(int piecePositionInList){
		int team = activeGamePieces.get(piecePositionInList).getTeam();
		if(team == 1){
			firstTeamPieceCount--;
		} else if(team == 2){
			secondTeamPieceCount--;
		}
	}
	
	private boolean isAllowedMove(TwitterMove move){
		int toRow = move.getToRow();
		int toColumn = move.getToColumn();
		int fromRow = move.getFromRow();
		int fromColumn = move.getFromColumn();
		int moverTeam = move.getTeam();
		int jumpRow = (fromRow + toRow) / 2;
		int jumpColumn = (fromColumn + toColumn) / 2;
		int diagRow = fromRow-toRow;
		
		boolean possibleRowNumbers = fromRow >= 1 && fromRow <= 8 && toRow >= 1 && toRow <= 8;
		boolean possibleColumnNumbers = fromColumn >= 1 && fromColumn <= 8 && toColumn >= 1 && toColumn <= 8;
		
		//Liikumise ja äravõtmise kontroll
		if(possibleRowNumbers && possibleColumnNumbers){
			if (moverTeam == 1 && diagRow == 1 && (fromColumn == toColumn + 1 || fromColumn == toColumn - 1) && !hasAPiece(toRow, toColumn)){
				return true;
			}else if (moverTeam == 2 && diagRow == -1 && (fromColumn == toColumn + 1 || fromColumn == toColumn - 1) && !hasAPiece(toRow, toColumn)){
				return true;
			}else if (moverTeam == 1 && fromRow - toRow == 2 && hasAPiece(jumpRow, jumpColumn) && !hasAPiece(toRow, toColumn)) {
				remove(jumpRow, jumpColumn);
				return true;
			}else if (moverTeam == 2 && fromRow - toRow == -2 && hasAPiece(jumpRow, jumpColumn) && !hasAPiece(toRow, toColumn)) {
				remove(jumpRow, jumpColumn);
				return true;
			}
		}
		return false;
	}
		
}
