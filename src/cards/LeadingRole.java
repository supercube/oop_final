package cards;

import java.awt.Image;

import javahacker.Arena;
import javahacker.Constant;

public class LeadingRole extends Monster{
	private int count;
	
	protected LeadingRole(MonsterCard card, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int posX, int posY){
		super(card, maxHP, maxMP, maxAGI, maxSIGHT, posX, posY);
		img_id = 0;
		targetX = -1;
		targetY = -1;
		img_id = card.rnd.nextInt(4);
		direction = img_id/2;
	}

	public Image getImage() {
		return card.getImage(img_id);
	}
	
	public void setTargetPosition(int targetX, int targetY){
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public void oneTimeStep(Arena arena){
		/* check whether is dead */
		if(HP <= 0){
			return;
		}
		
		
		/* cds count down */
		for(int i = 0; i < cds.length; i++)
			if(cds[i] > 0)
				cds[i]--;
		
		
		/* action count down */
		if(count_down <= 0){
			count_down = TTA + 1;
			/* do commands */
			move();
		}else if(count == 0){
			img_id = 2 * direction + (img_id+1)%2;
		}
		count = (count + 1) % 40;
		count_down--;
	}
}
