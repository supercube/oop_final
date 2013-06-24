package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;

public class Castle extends Portal{
	public Castle(PortalCard card, int team, int maxHP, int maxAGI, int x, int y){
		super(card, team, maxHP, maxAGI, x, y);
	}

	public Image getImage() {
		return card.getImage(img_id);
	}

	public ArrayList<Monster> oneTimeStep(Arena arena) {
		ArrayList<Monster> mlist = new ArrayList<Monster>();
		/* check whether is dead */
		if(HP <= 0){
			img_id = 1;
			return null;
		}
		return mlist;
	}
}
