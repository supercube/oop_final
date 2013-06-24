package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Cell;
import javahacker.Constant;




public class StabSkill extends Skill{
	
	private int StabDir;
	private double SpeedX, SpeedY;
	public StabSkill(SkillCard card, Monster caster, int ttl, int maxHP, int maxAGI, int maxSIGHT, int targetX, int targetY) {
		super(card, caster, ttl, maxHP, maxAGI, maxSIGHT, targetX, targetY);
		this.posX = caster.getX();
		this.posY = caster.getY();
		int diffx = targetX - posX;
		int diffy = targetY - posY;
		if(diffx >= 0)
			StabDir = 0;
		else
			StabDir = 2;
		SpeedX = Math.cos(Math.atan2((double)diffy, (double)diffx)) * maxAGI;
		SpeedY = Math.sin(Math.atan2((double)diffy, (double)diffx)) * maxAGI;
		//System.out.println("Speed: " + SpeedX + " " + SpeedY);
		int rad  = Math.min(caster.getImageHeight(), caster.getImageWidth())/2 + SIGHT;
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
		img_id = StabDir;
	}
	
	public void act(Monster m){
		m.HP -= 3;
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
		if(ttl%40 == 0)
		{
			this.posX += (int)SpeedX;
			this.posY += (int)SpeedY;
		}
		if(ttl == 0){
			return true;
		}else if(ttl  == 35){
			img_id = StabDir + (img_id+1)%2;
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

