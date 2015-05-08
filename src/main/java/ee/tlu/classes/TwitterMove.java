package ee.tlu.classes;

/*
 * Klass ühe käigu salvestamiseks. Kasutan kontrollimaks, milline
 * eelmine käik oli, et Twitter'ist mitte proovida kaks korda sama käiku teha.
 * Samuti saab siia sisse salvestada viimase käigu teinud tiimi, ei ole vaja eraldi
 * muutujat, mis peaks meeles, milline tiim viimati käis.
 */
public class TwitterMove {
	private int fromRow;
	private int fromColumn;
	private int toRow;
	private int toColumn;
	private int team;
	
	public TwitterMove(int fromRow, int fromColumn, int toRow, int toColumn, int team) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
		this.team = team;
	}
	
	public boolean isEqualTo(TwitterMove move){
		boolean fromRowEquals = fromRow == move.getFromRow();
		boolean fromColumnEquals = fromColumn == move.getFromColumn();
		boolean toRowEquals = toRow == move.getToRow();
		boolean toColumnEquals = toColumn == move.getToColumn();
		if(fromRowEquals && fromColumnEquals && toRowEquals && toColumnEquals){
			return true;
		}
		return false;
	}
	
	public int getFromRow(){
		return fromRow;
	}
	
	public int getFromColumn(){
		return fromColumn;
	}
	
	public int getToRow(){
		return toRow;
	}
	
	public int getToColumn(){
		return toColumn;
	}
	
	public int getTeam(){
		return team;
	}
	
	public String toString(){
		return "Tiim: " + team + ", reast: " + fromRow + ", tulbast: " + fromColumn + ", ritta: " + toRow + ", tulpa: " + toColumn;
	}
}
