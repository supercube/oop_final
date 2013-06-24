package cards;

import java.awt.Image;
import java.util.ArrayList;

import javahacker.Arena;

public class LeadingRole extends Monster{
	private int count;
	private int HP_regen_count_down;
	private int HP_regen_time = 400;
	
	protected LeadingRole(MonsterCard card, int team, int maxHP, int maxMP, int maxAGI, int maxSIGHT, int posX, int posY){
		super(card, team, maxHP, maxMP, maxAGI, maxSIGHT, posX, posY);
		img_id = 0;
		targetX = -1;
		targetY = -1;
		img_id = card.rnd.nextInt(4);
		direction = img_id/2;
		HP_regen_count_down = HP_regen_time;
		cds = new int[4];
		cds[0] = 0;
		cds[1] = 0;
		cds[2] = 0;
		cds[3] = 0;
	}
	
	
	// override useSkill
	public Skill useSkill(int id, int x, int y){
		if(id < 0 || id > 3 || card.skills[id] == null)
			return null;
		int MPConsume = card.skills[id].getMPConsume();
		if(cds[id] <= 0 && MP >= MPConsume){
			cds[id] = card.skills[id].getCD();
			MP -= MPConsume;
			return card.skills[id].newSkill(this, x, y);
		}
		return null;
	}
	
	public Image getImage() {
		return card.getImage(img_id);
	}
	
	public void setTargetPosition(int targetX, int targetY){
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public ArrayList<Skill> oneTimeStep(Arena arena){
		/* check whether is dead */
		ArrayList<Skill> strategy = new ArrayList<Skill>();
		if(HP <= 0){
			img_id = 4 + direction;
			return null;
		}
		
		
		/* cds count down */
		for(int i = 0; i < cds.length; i++){
			if(cds[i] > 0){
				cds[i]--;
			}
		}
		
		/* HP regen count down */
		HP_regen_count_down--;
		if(HP_regen_count_down <= 0){
			if(HP < maxHP)
				HP++;
			HP_regen_count_down = HP_regen_time;
		}
		
		/* MP regen */
		MPRegen();
		
		/* action count down */
		if(count_down <= 0){
			count_down = TTA + 1;
			/* do commands */
			move(arena);
		}else if(count == 0){
			img_id = 2 * direction + (img_id+1)%2;
		}
		count = (count + 1) % 40;
		count_down--;
		return strategy;
	}
}
