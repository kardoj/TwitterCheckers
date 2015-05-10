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
	
	public boolean hasAPiece(int row, int column){
		int position = findGamePiecePositionInList(row, column);
		if(position != -1){
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
		if(!hasAPiece(toRow, toColumn) && hasAPiece(fromRow, fromColumn) && isCorrectTeamPiece(move) && withinReach(move)){
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
	
	private boolean withinReach(TwitterMove move){
		int toRow = move.getToRow();
		int toColumn = move.getToColumn();
		int fromRow = move.getFromRow();
		int fromColumn = move.getFromColumn();
		int moverTeam = move.getTeam();
		
		// TODO Kontroll, kas saab liikuda või on tegemist ära võtmisega
		// Teise nupu peale astumine on juba eraldi kontrollitud, seda ei ole vaja uuesti teha
		// Samuti peaks siin sees käsitlema nupu äravõtmist.
		// Hea on kontrollida, et kui astutakse kaks rida kaugemale, peab keskmises
		// reas kindlasti vastase nupp olema. Siis leida listist see nupp ja ära kustutada.
		// Nuppe saab ära võtta ainult ühe kaupa ja mängu lõpu tingimus on ka juba ära kontrollitud.
		// Kui saab liikuda, return true, muidu false.
		
		return true;
	}
		
}
