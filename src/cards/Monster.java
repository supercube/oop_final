package cards;

import javahacker.*;
import java.awt.Image;
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
	
	public abstract Image getImage();
	public abstract void oneTimeStep(Arena arena);
	
	public Monster(MonsterCard card, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int posX, int posY){
		this.card = card;
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
				System.out.println("here " + posX + ", " + posY + " abs:" + absX + "," + absY);
				
			}else{
				targetX = -1;
				targetY = -1;
			}
		}
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
	
	public int getX(){
		return posX;
	}
	public int getY(){
		return posY;
	}
	
	
	public void getKillReward(){}
}
