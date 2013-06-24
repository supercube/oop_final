package cards;

import java.util.Random;
import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Cell;
import javahacker.Constant;




public class TornadoSkill extends Skill{
	
	private double Radius;
	private Random random;
	
	public TornadoSkill(SkillCard card, Monster caster, int ttl, int maxHP, int maxAGI, int maxSIGHT, int targetX, int targetY) {
		super(card, caster, ttl, maxHP, maxAGI, maxSIGHT, targetX, targetY);
		this.posX = caster.getX();
		this.posY = caster.getY();
		int diffx = targetX - posX;
		int diffy = targetY - posY;
		
		Radius = Math.atan2((double)diffy, (double)diffx);
		random = new Random();
		
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
		if(diffx >= 0){
			direction = 2;
		}else{
			direction = 0;
		}
		img_id = direction;
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
		if(ttl%5 == 0)
		{
			this.posX += (int)(Math.cos(Radius+2*random.nextDouble()-1)*maxAGI);
			this.posY += (int)(Math.sin(Radius+2*random.nextDouble()-1)*maxAGI);
		}
		if(ttl == 0){
			return true;
		}else if(ttl % 10 == 0){
			img_id = direction + (img_id+1)%2;
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

