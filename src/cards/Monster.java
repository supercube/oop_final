package cards;

import javahacker.*;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javahacker.Constant;

public abstract class Monster {
	
	protected int maxHP, maxMP, maxAGI, maxSIGHT;
	protected int HP, MP, AGI, SIGHT;
    protected MonsterCard card;
	protected int img_id;
	protected int[] cds;
	protected int TTA;
	protected int count_down;
	protected int posX, posY;
	protected int targetX, targetY;
	protected int direction;
	protected int team;
	protected Random rnd;
	
	protected int keep_distance;
	protected int MP_regen_count_down;
	protected int MP_regen_time = 250;
	
	public abstract Image getImage();
	public abstract ArrayList<Skill> oneTimeStep(Arena arena);
	
	public Monster(MonsterCard card, int team, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int posX, int posY){
		this.card = card;
		this.team = team;
		this.maxHP = maxHP;
		this.maxMP = maxMP;
		this.maxAGI = maxAGI;
		this.maxSIGHT = maxSIGHT;
		this.posX = posX;
		this.posY = posY;
		HP = maxHP;
		MP = maxMP;
		SIGHT = maxSIGHT;
		adjustAGIAndTTA(maxAGI);
		cds = new int[2];
		cds[0] = 0;
		cds[1] = 0;
		rnd = new Random();
		keep_distance = 0;
	}
	
	public void move(Arena arena){
		int step = 15 + AGI / 10;
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
					if(absX <= step){
						posX = targetX;
					}else{
						posX += step*(diffX/absX);
					}
				}else{
					if(absY <= step){
						posY = targetY;
					}else{
						posY += step*(diffY/absY);
					}
				}
			}else{
				targetX = -1;
				targetY = -1;
			}
		}
	}
	
	public Skill useSkill(int id, int x, int y){
		if(id < 0 || id > 1 || card.skills[id] == null)
			return null;
		int MPConsume = card.skills[id].getMPConsume();
		if(cds[id] <= 0 && MP >= MPConsume){
			cds[id] = card.skills[id].getCD();
			MP -= MPConsume;
			return card.skills[id].newSkill(this, x, y);
		}
		return null;
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
	
	public int getTeam(){
		return team;
	}
	public MonsterCard getCard(){
		return card;
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
	public String getName(){
		return card.name;
	}
	public int getHP(){
		return HP;
	}
	public int getMP(){
		return MP;
	}
	
	public int getMaxHP(){
		return maxHP;
	}
	public int getMaxMP(){
		return maxMP;
	}
	public void MPRegen(){
		MP_regen_count_down--;
		if(MP_regen_count_down <= 0){
			MP = MP + (1+ maxMP/10);
			if(MP > maxMP)
				MP = maxMP;
			MP_regen_count_down = MP_regen_time;
		}
	}
	
	protected void doStrategy(Arena arena, ArrayList<Skill> out){
		int finalTargetX = -1, finalTargetY = -1;
		int minDistance = Integer.MAX_VALUE;
		ArrayList<Cell> cells = arena.getSight(this);
		for(Cell c: cells){
			if(c.getTeam() != team){
				if(c.getType() == Constant.CardType.MONSTER || c.getType() == Constant.CardType.PLAYER){
					targetX = ((Monster)c.getObject()).getX();
					targetY = ((Monster)c.getObject()).getY();
					int distanceX = Math.abs(targetX - posX);
					int distanceY = Math.abs(targetY - posY);
					int distance =  distanceX + distanceY;
					if(distance < minDistance){
						finalTargetX = targetX;
						finalTargetY = targetY;
						minDistance = distance;
					}
					for(int id = 0; id < 2; id++){
						SkillCard scard = card.getSkill(id);
						if( scard != null && !(scard instanceof HealthSkillCard)){
							int min = Math.min(getImageWidth(), getImageHeight())/2 + scard.SIGHT/2;
							int max = min + Math.min(((Monster)c.getObject()).getImageHeight(), ((Monster)c.getObject()).getImageWidth())/2; 
							
							int maxDistance;
							
							if(scard instanceof TornadoSkillCard){
								maxDistance = 500;
							}else if(scard instanceof WizardFireSkillCard){
								maxDistance = 150;
							}else if(scard instanceof LionPunchSkillCard){
								maxDistance = 40;
							/* near attack */
							}else if(scard instanceof StabSkillCard || scard instanceof SwordSkillCard){
								maxDistance = 10;
							}else{
								maxDistance = 3;
							}
							
							if(distanceX < max + maxDistance && distanceY < max + maxDistance){
								Skill s = useSkill(id, targetX, targetY);
								if(s != null){
									out.add(s);
								}
							}
							if(distanceX < max + keep_distance - 5 && distanceY < max + keep_distance - 5){
								finalTargetX = rnd.nextInt(1200);
								finalTargetY = rnd.nextInt(500);
								minDistance = 0;
							}
						}
					}
				}else if(c.getType() == Constant.CardType.PORTAL || c.getType() == Constant.CardType.JVM){
					targetX = ((Portal)c.getObject()).getX();
					targetY = ((Portal)c.getObject()).getY();
					int distanceX = Math.abs(targetX - posX);
					int distanceY = Math.abs(targetY - posY);
					int distance =  distanceX + distanceY;
					if(distance < minDistance){
						finalTargetX = targetX;
						finalTargetY = targetY;
						minDistance = distance;
					}
					for(int id = 0; id < 2; id++){
						SkillCard scard = card.getSkill(id);
						if( scard != null && !(scard instanceof HealthSkillCard)){
							int min = Math.min(getImageWidth(), getImageHeight())/2 + scard.SIGHT/2;
							int max = min + Math.min(((Portal)c.getObject()).getImageHeight(), ((Portal)c.getObject()).getImageWidth())/2; 
							
							int maxDistance;
							
							if(scard instanceof TornadoSkillCard){
								maxDistance = 500;
							}else if(scard instanceof WizardFireSkillCard){
								maxDistance = 150;
							}else if(scard instanceof LionPunchSkillCard){
								maxDistance = 40;
							/* near attack */
							}else if(scard instanceof StabSkillCard || scard instanceof SwordSkillCard){
								maxDistance = 10;
							}else{
								maxDistance = 3;
							}
							
							if(distanceX < max + maxDistance && distanceY < max + maxDistance){
								Skill s = useSkill(id, targetX, targetY);
								if(s != null){
									out.add(s);
								}
							}
							if(distanceX < max + keep_distance - 5 && distanceY < max + keep_distance - 5){
								finalTargetX = rnd.nextInt(1200);
								finalTargetY = rnd.nextInt(500);
								minDistance = 0;
							}
						}
					}
				}
			}else{
				if(c.getType() == Constant.CardType.MONSTER || c.getType() == Constant.CardType.PLAYER){
					targetX = ((Monster)c.getObject()).getX();
					targetY = ((Monster)c.getObject()).getY();
					int distanceX = Math.abs(targetX - posX);
					int distanceY = Math.abs(targetY - posY);
					for(int id = 0; id < 2; id++){
						SkillCard scard = card.getSkill(id);
						if( scard != null && scard instanceof HealthSkillCard && ((Monster)c.getObject()).getHP() < ((Monster)c.getObject()).maxHP){
							int min = Math.min(getImageWidth(), getImageHeight())/2 + scard.SIGHT/2;
							int max = min + Math.min(((Monster)c.getObject()).getImageHeight(), ((Monster)c.getObject()).getImageWidth())/2; 
							
							int maxDistance = 100;
							
							if(distanceX < max + maxDistance && distanceY < max + maxDistance){
								Skill s = useSkill(id, targetX, targetY);
								if(s != null){
									out.add(s);
								}
							}
						}
					}
				}
			}
		}
		if(finalTargetX == -1){
			targetX = arena.getJVM(team).getX();
			targetY = arena.getJVM(team).getY();
		}else{
			targetX = finalTargetX;
			targetY = finalTargetY;
		}
	}
}
