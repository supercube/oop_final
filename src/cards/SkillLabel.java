package cards;

import javax.swing.Icon;
import javax.swing.JLabel;

public class SkillLabel extends JLabel{
	private int id;
	public SkillLabel(String str, int id){
		super(str);
		this.id = id;
	}
	public SkillLabel(Icon icon, int id){
		super(icon);
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
