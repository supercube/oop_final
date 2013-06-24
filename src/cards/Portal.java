package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Constant;

public abstract class Portal {
	
	protected int team;
	protected int maxHP, maxAGI;
	protected int HP, AGI, SIGHT;
    protected PortalCard card;
	protected int img_id;
	protected int[] cds;
	protected int TTA;
	protected int count_down;
	protected int posX, posY;
	protected int targetX, targetY;
	protected int direction;
	
	public abstract Image getImage();
	
	public abstract ArrayList<Monster> oneTimeStep(Arena arena);
	
	public Portal(PortalCard card, int team, int maxHP, int maxAGI, int posX, int posY){
		this.card = card;
		this.team = team;
		this.maxHP = maxHP;
		this.maxAGI = maxAGI;
		this.posX = posX;
		this.posY = posY;
		HP = maxHP;
		adjustAGIAndTTA(maxAGI);
		cds = new int[2];
		cds[0] = 0;
		cds[1] = 0;
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
	
	public void tryToNew(ArrayList<Monster> mlist){
		MonsterCard mcard0 = card.getMonster(0);
		MonsterCard mcard1 = card.getMonster(1);
		if(mcard0 != null){
			Monster m = mcard0.newMonster(team, posX , posY - 20);
			if(m != null){
				mlist.add(m);
			}else{
				card.setMonster(null, 0);
			}
		}
		if(mcard1 != null){
			Monster m = mcard1.newMonster(team, posX , posY + 20);
			if(m != null){
				mlist.add(m);
			}else{
				card.setMonster(null, 1);
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
		TTA = Constant.PORTALSLOWTTA - AGI;
	}
	
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	
	public MonsterCard getMonster(int id){
		if(id < 0 || id > 1)
			return null;
		return card.monsters[id];
	}
}
