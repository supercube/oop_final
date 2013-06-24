package cards;

import java.awt.Image;
import java.util.ArrayList;
import javahacker.*;

public class Slime extends Monster{
	private int count;
	
	protected Slime(MonsterCard card, int team, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int x, int y){
		super(card, team, maxHP, maxMP, maxAGI, maxSIGHT, x, y);
		img_id = 0;
		count = 0;
	}

	public Image getImage() {
		return card.getImage(img_id);
	}

	public ArrayList<Skill> oneTimeStep(Arena arena){
		ArrayList<Skill> strategy = new ArrayList<Skill>();
		/* check whether is dead */
		if(HP <= 0){
			img_id = 8;
			return null;
		}
		
		
		/* cds count down */
		for(int i = 0; i < cds.length; i++)
			if(cds[i] > 0)
				cds[i]--;
		
		/* MP regen */
		MPRegen();
		
		/* action count down */
		if(count_down <= 0){
			count_down = TTA + 1;
			doStrategy(arena, strategy);
			move(arena);
		}else if(count == 0){
			img_id = (img_id+1)%4;
			
		}
		count = (count + 1) % 20;
		count_down--;
		return strategy;
	}
}
