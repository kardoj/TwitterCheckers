package ee.tlu;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JPanel;

import ee.tlu.classes.Board;
import ee.tlu.classes.PieceManipulator;

import static ee.tlu.classes.Board.SIDE;

public class Checkers extends JApplet {	
	JPanel panel = new JPanel();
	
	Board board = new Board();
	PieceManipulator pieceManipulator = new PieceManipulator();
	
	
	public void init(){
		pieceManipulator.createInitialGamePieces();
		setLayout();
		setVisible(true);
		pieceManipulator.move(2, 2, 4, 4);
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
}
