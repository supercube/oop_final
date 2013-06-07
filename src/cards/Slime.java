package cards;

import java.awt.Image;
import java.util.ArrayList;
import javahacker.*;

public class Slime extends Monster{
	private int count;
	
	protected Slime(MonsterCard card, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int x, int y){
		super(card, maxHP, maxMP, maxAGI, maxSIGHT, x, y);
		img_id = 0;
		count = 0;
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
			img_id = (img_id+1)%4;
		}
		count = (count + 1) % 20;
		count_down--;
	}
}
