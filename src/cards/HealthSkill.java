package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Cell;
import javahacker.Constant;




public class HealthSkill extends Skill{
	
	private double SpeedX, SpeedY;
	//private int direction;
	public HealthSkill(SkillCard card, Monster caster, int ttl, int maxHP, int maxAGI, int maxSIGHT, int targetX, int targetY) {
		super(card, caster, ttl, maxHP, maxAGI, maxSIGHT, targetX, targetY);
		this.posX = caster.getX();
		this.posY = caster.getY();
		int diffx = targetX - posX;
		int diffy = targetY - posY;
		SpeedX = Math.cos(Math.atan2((double)diffy, (double)diffx)) * maxAGI;
		SpeedY = Math.sin(Math.atan2((double)diffy, (double)diffx)) * maxAGI;
		int rad  = 80;
		if(Math.abs(diffx) < rad){
			this.posX = targetX;
		}else if(diffx < 0){
			this.posX = posX - rad;
		}else{
			this.posX = posX + rad;
		}
		
		if(Math.abs(diffy) < rad){
			this.posY = targetY;
		}else if(diffy < 0){
			this.posY = posY - rad;
		}else{
			this.posY = posY + rad;
		}
		
		img_id = 0;
	}
	
	public void act(Monster m){
		m.HP += 1;
	}
	
	public void act(Portal p){
		;
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
		if(ttl%100 == 0)
		{
			maxSIGHT -= 10;
		}
		if(ttl%150 == 0)
		{
			img_id++;
		}
		if(ttl == 0){
			return true;
		}else if(ttl % 50 == 0){
			ArrayList<Cell> cells = arena.getSight(this);
			for(Cell c: cells){
				if(c.getTeam() == team){
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

