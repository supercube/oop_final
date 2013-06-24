package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Cell;
import javahacker.Constant;

public class Witch extends Monster{
	private int count;
	
	protected Witch(MonsterCard card, int team, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int x, int y){
		super(card, team, maxHP, maxMP, maxAGI, maxSIGHT, x, y);
		img_id = 0;
		keep_distance = 100;
	}

	public Image getImage() {
		return card.getImage(img_id);
	}
	
	public ArrayList<Skill> oneTimeStep(Arena arena){
		ArrayList<Skill> strategy = new ArrayList<Skill>();
		/* check whether is dead */
		if(HP <= 0){
			img_id = 8 + direction;
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
			
			if(direction == 1)	// right
				img_id = (img_id+1)%4;
			else
				img_id = 4+(img_id+1)%4;
		}
		count = (count + 1) % 20;
		count_down--;
		return strategy;
	}
}
