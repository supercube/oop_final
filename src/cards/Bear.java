package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;
import javahacker.Cell;
import javahacker.Constant;

public class Bear extends Monster{
	private int count;
	
	protected Bear(MonsterCard card, int team, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int x, int y){
		super(card, team, maxHP, maxMP, maxAGI, maxSIGHT, x, y);
		img_id = 0;
	}

	public Image getImage() {
		return card.getImage(img_id);
	}
	
	public ArrayList<Skill> oneTimeStep(Arena arena){
		ArrayList<Skill> strategy = new ArrayList<Skill>();
		/* check whether is dead */
		if(HP <= 0){
			if(direction == 0){ // right
				img_id = 5;
			}else{
				img_id = 4;
			}
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
			System.out.println("bear dir " + direction);
			if(direction == 0)	// right
				img_id = (img_id+1)%2;
			else
				img_id = 2+(img_id+1)%2;
		}
		count = (count + 1) % 20;
		count_down--;
		return strategy;
	}
}
