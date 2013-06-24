package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;

public class HellGate extends Portal{
	private int count;
	
	protected HellGate(PortalCard card, int team, int maxHP, int maxAGI, int x, int y){
		super(card, team, maxHP, maxAGI, x, y);
		img_id = 0;
	}

	public Image getImage() {
		return card.getImage(img_id);
	}
	
	public ArrayList<Monster> oneTimeStep(Arena arena){
		
		/* check whether is dead */
		if(HP <= 0){
			img_id = 2;
			return null;
		}
		ArrayList<Monster> mlist = new ArrayList<Monster>();
		
		/* cds count down */
		for(int i = 0; i < cds.length; i++)
			if(cds[i] > 0)
				cds[i]--;
		
		
		/* action count down */
		if(count_down <= 0){
			count_down = TTA + 1;
			tryToNew(mlist);
			
			return mlist;
			
		}else if(count == 0){
			img_id = (img_id+1)%2;
		}
		count = (count + 1) % 20;
		count_down--;
		return mlist;
	}
}
