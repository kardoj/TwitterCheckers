package ee.tlu;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import ee.tlu.classes.ActionMessage;
import ee.tlu.classes.Board;
import ee.tlu.classes.PieceManipulator;
import ee.tlu.classes.TwitterMove;
import ee.tlu.classes.TwitterMoveParser;
import static ee.tlu.classes.Board.SIDE;
import static ee.tlu.classes.Board.rowCount;
import static ee.tlu.classes.Board.columnCount;

public class Checkers extends JApplet implements ActionListener {	
	JPanel gameBoardPanel = new JPanel();
	
	Board board;
	PieceManipulator pieceManipulator;
	TwitterMove lastMove = null;
	TwitterMoveParser twitterMoveParser;
	Timer twitterTimer;
	ActionMessage actionMessage;
	String hashtag = null;
	
	public void init(){
		board = new Board();
		twitterTimer = new Timer(5000, this);
		pieceManipulator = new PieceManipulator();
		pieceManipulator.createInitialGamePieces();
		actionMessage = new ActionMessage("Ootan hashtag'i...", SIDE/2, rowCount*SIDE-SIDE/2+10);
		setLayout();
		setVisible(true);
		hashtag = getHashtag();
		twitterMoveParser = new TwitterMoveParser(hashtag);
		
		// Kuna enne käimist võrreldakse uut käiku viimasega, loon suvalise
		// algse pseudokäigu tiimiga kaks, et alustajaks oleks tiim 1.
		lastMove = new TwitterMove(0, 0, 0, 0, 2);
		twitterTimer.start();
		//pieceManipulator.move(new TwitterMove(6, 2, 5, 3, 1));
		actionMessage.setMessage("Mäng on alanud!");
		repaint();
	}
	
	private String getHashtag(){
		String hashtag = null;
		do{
			hashtag = (String) JOptionPane.showInputDialog(
				this, 
				"Mängu alustamiseks palun sisesta hashtag: ", 
				null,
				JOptionPane.QUESTION_MESSAGE
			);
		} while(hashtag == null || hashtag.equals(""));
		return hashtag;
	}

	private void setLayout(){
		int windowWidth = columnCount*SIDE;
		int windowHeight = rowCount*SIDE;
		setSize(windowWidth, windowHeight);
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, gameBoardPanel);
	}
	
	public void paint(Graphics g){
		board.draw(g);
		pieceManipulator.draw(g);
		actionMessage.draw(g);
	}
	
	private int getNextMoveTeam(){
		if(lastMove.getTeam() == 1){
			return 2;
		}
		return 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == twitterTimer){
			// Otsi Twitter'ist uus käik ja käi
			TwitterMove newMove = twitterMoveParser.getNewMove(lastMove);
			actionMessage.setMessage("Ootan uut käiku... (tiim " + getNextMoveTeam() + ")");
			if(newMove.getTeam() != lastMove.getTeam()){
				boolean moveWasMade = pieceManipulator.move(newMove);
				if(moveWasMade){
					actionMessage.setMessage("Käik: " + newMove.toString());
				} else {
					actionMessage.setMessage("Sellist käiku ei saa teha!");
				}
				lastMove = newMove;
				boolean firstTeamWon = newMove.getToRow() == 1 && newMove.getTeam() == 1;
				boolean secondTeamWon = newMove.getToRow() == 8 && newMove.getTeam() == 2;
				if(firstTeamWon || secondTeamWon){
					actionMessage.setMessage("TIIM " + newMove.getTeam() + " VÕITIS!");
					twitterTimer.stop();
				}
			}
			repaint();
		}
	}
}
