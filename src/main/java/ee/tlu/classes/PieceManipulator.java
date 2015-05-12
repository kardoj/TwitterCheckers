package ee.tlu.classes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class PieceManipulator {

	public static final Color firstTeamColor = Color.LIGHT_GRAY;
	public static final Color secondTeamColor = Color.BLUE;
	
	private List<GamePiece> activeGamePieces = new ArrayList<>();
	
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
		activeGamePieces.remove(position);
	}
	
	private boolean isAllowedMove(TwitterMove move){
		int toRow = move.getToRow();
		int toColumn = move.getToColumn();
		int fromRow = move.getFromRow();
		int fromColumn = move.getFromColumn();
		int moverTeam = move.getTeam();
		int jumpRow = (fromRow + toRow) / 2;
		int jumpColumn = (fromColumn + toColumn) / 2;
		
		if (fromRow - toRow == 2 || fromRow - toRow == -2 && hasAPiece(jumpRow, jumpColumn)) {
			remove(jumpRow, jumpColumn);
			return true;
		}else if(fromRow - toRow == 2 || fromRow - toRow == -2 && !hasAPiece(jumpRow, jumpColumn)){
			return false;
		}
		// TODO Kontroll, kas saab liikuda või on tegemist ära võtmisega.
		// Teise nupu peale astumine on juba eraldi kontrollitud, seda ei ole vaja uuesti teha.
		// Nupu võtmisel on hea kontrollida, et kui astutakse kaks rida kaugemale, peab keskmises
		// reas kindlasti vastase nupp olema. Siis leida listist see nupp ja ära kustutada.
		// Nuppe saab ära võtta ainult ühe kaupa ja mängu lõpu tingimus on ka juba ära kontrollitud.
		// Kui saab liikuda, return true, muidu false.
		return true;
	}
		
}
