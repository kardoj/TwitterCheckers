package ee.tlu;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.Timer;

import ee.tlu.classes.Board;
import ee.tlu.classes.PieceManipulator;
import ee.tlu.classes.TwitterMove;
import ee.tlu.classes.TwitterMoveParser;
import static ee.tlu.classes.Board.SIDE;

public class Checkers extends JApplet implements ActionListener {	
	JPanel panel = new JPanel();
	
	Board board = new Board();
	PieceManipulator pieceManipulator = new PieceManipulator();
	TwitterMove lastMove = null;
	TwitterMoveParser twitterMoveParser = new TwitterMoveParser("#twittercheckers");
	Timer twitterTimer = new Timer(5000, this);
	
	public void init(){
		pieceManipulator.createInitialGamePieces();
		setLayout();
		setVisible(true);
		lastMove = new TwitterMove(0, 0, 0, 0, 0);
		twitterTimer.start();
		System.out.println("Mäng on alanud!");
	}

	private void setLayout(){
		setSize(8*SIDE, 8*SIDE);
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, panel);
	}
	
	public void paint(Graphics g){
		board.draw(g);
		pieceManipulator.draw(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == twitterTimer){
			// Otsi Twitter'ist uus käik ja käi
			TwitterMove newMove = twitterMoveParser.getNewMove(lastMove);
			if(newMove.getTeam() != lastMove.getTeam()){
				System.out.println("Käin: " + newMove);
				pieceManipulator.move(newMove);
				lastMove = newMove;
				repaint();
			} else {
				System.out.println("Viimati käis tiim " + lastMove.getTeam() + ", otsin käiku...");
			}
		}	
	}
}
