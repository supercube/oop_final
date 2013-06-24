package javahacker;
import java.util.ArrayList;
public class Cell {
	private Object obj;
	private Constant.CardType type;
	private int team;
	public Cell(Constant.CardType type, Object obj, int team){
		this.type = type;
		this.obj = obj;
		this.team = team;
	}
	
	public Constant.CardType getType(){
		return type;
	}
	
	public Object getObject(){
		return obj;
	}
	
	public int getTeam(){
		return team;
	}
}
