package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Cell;
import javahacker.Constant;




public class LionPunchSkill extends Skill{
	
	private double Radius;
	private double theta;
	private int originX;
	private int originY;
	
	public LionPunchSkill(SkillCard card, Monster caster, int ttl, int maxHP, int maxAGI, int maxSIGHT, int targetX, int targetY) {
		super(card, caster, ttl, maxHP, maxAGI, maxSIGHT, targetX, targetY);
		
		int diffx = targetX - posX;
		int diffy = targetY - posY;
		
		Radius = maxAGI;
		theta = 0.0;
		originX = caster.getX();
		originY = caster.getY();
		
		this.posX = originX + (int)Radius;
		this.posY = originY + (int)Radius;
		
		img_id = 0;
	}
	
	public void act(Monster m){
		m.HP -= 4;
	}
	
	public void act(Portal p){
		p.HP -= 1;
	}
	
	public int getCD(){
		return card.getCD();
	}
	
	public int getMPConsume(){
		return card.getMPConsume();
	}
	
	public Image getImage(){
		return card.getImage(img_id);
	}

	public boolean oneTimeStep(Arena arena) {
		ttl -= 1;
		if(ttl%3 == 0)
		{
			theta += 10;
			this.posX = originX + (int)(Radius * Math.cos(Math.toRadians(theta)));
			this.posY = originY + (int)(Radius * Math.sin(Math.toRadians(theta)));
		}
		if(ttl == 0){
			return true;
		}else if(ttl % 10 == 0){
			img_id = (img_id+1)%2;
			ArrayList<Cell> cells = arena.getSight(this);
			for(Cell c: cells){
				if(c.getTeam() != team){
					if(c.getType() == Constant.CardType.MONSTER || c.getType() == Constant.CardType.PLAYER){
						act(((Monster)c.getObject()));
					}else if(c.getType() == Constant.CardType.PORTAL || c.getType() == Constant.CardType.JVM){
						act((Portal)c.getObject());
					}
				}
			}
		}
		
		return false;
	}
	
}

