package cards;

import java.awt.Image;
import javahacker.Arena;

public class CentaurSkeleton extends Monster{
	private int count;
	
	protected CentaurSkeleton(MonsterCard card, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int x, int y){
		super(card, maxHP, maxMP, maxAGI, maxSIGHT, x, y);
		img_id = 0;
	}

	public Image getImage() {
		return card.getImage(img_id);
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
			
			
		}else if(count == 0){
			img_id = (img_id+1)%2;
		}
		count = (count + 1) % 20;
		count_down--;
	}
}
