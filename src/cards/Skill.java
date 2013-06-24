package cards;

import java.awt.Image;
import java.util.Random;

import javahacker.Arena;
import javahacker.Constant;

public abstract class Skill {
	
	protected int team;
	protected int maxHP, maxAGI, maxSIGHT;
	protected int HP, AGI, SIGHT;
    protected SkillCard card;
    protected Monster caster;
	protected int img_id;
	protected int[] cds;
	protected int TTA;
	protected int ttl;
	protected int count_down;
	protected int posX, posY;
	protected int targetX, targetY;
	protected int direction;
	protected Random rnd;
	
	public abstract Image getImage();
	public abstract boolean oneTimeStep(Arena arena);
	public abstract void act(Monster m);
	public abstract void act(Portal p);
	
	public Skill(SkillCard card, Monster caster, int ttl, int maxHP, int maxAGI, int maxSIGHT, int targetX, int targetY){
		this.card = card;
		this.caster = caster;
		this.team = caster.getTeam();
		this.ttl = ttl;
		this.maxHP = maxHP;
		this.maxAGI = maxAGI;
		this.maxSIGHT = maxSIGHT;
		this.targetX = targetX;
		this.targetY = targetY;
				
		
		HP = maxHP;
		SIGHT = maxSIGHT;
		adjustAGIAndTTA(maxAGI);
		cds = new int[2];
		cds[0] = 0;
		cds[1] = 0;
		rnd = new Random();
	}
	
	public void move(){
		if(targetX >= 0  && targetY >= 0 && targetX < Constant.BATTLEFIELD_WIDTH && targetY < Constant.BATTLEFIELD_HEIGHT){
			if(targetX != posX || targetY != posY){
				int diffX = targetX - posX;
				int diffY = targetY - posY;
				int absX = Math.abs(diffX);
				int absY = Math.abs(diffY);
				if(diffX < 0 && direction != 1){
					direction = 1;
				}else if(diffX > 0 && direction != 0){
					direction = 0;
				}
				if( absX >= absY){
					if(absX <= 15){
						posX = targetX;
					}else{
						posX += 15*(diffX/absX);
					}
				}else{
					if(absY <= 15){
						posY = targetY;
					}else{
						posY += 15*(diffY/absY);
					}
				}
				
			}else{
				targetX = -1;
				targetY = -1;
			}
		}
	}
	
	public int getTeam(){
		return team;
	}
	
	public int getImageWidth(){
		return card.getImageWidth();
	}
	public int getImageHeight(){
		return card.getImageHeight();
	}
	public void adjustAGIAndTTA(int AGI){
		this.AGI = AGI;
		TTA = Constant.SLOWTTA - AGI;
	}
	
	public int getSightRange(){
		return SIGHT;
	}
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
}
