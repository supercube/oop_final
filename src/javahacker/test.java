package javahacker;

import java.awt.Dimension;
import java.awt.Toolkit;

import cards.*;


public class test {
	
	public static void main(String[] args){
		Card[] deck = new Card[8];
		deck[0] = new SlimeCard();
		deck[1] = new SlimeCard();
		deck[7] = new TinyAttackSkillCard((MonsterCard)deck[0], true);
		((MonsterCard)deck[0]).setSkill((SkillCard)deck[7], 0);
		((MonsterCard)deck[0]).setSkill((SkillCard)deck[7], 1);
		
		
		deck[2] = new SlimeCard();
		deck[3] = new SlimeCard();
		((MonsterCard)deck[3]).setSkill((SkillCard)deck[7], 0);
		deck[4] = new SlimeCard();
		((MonsterCard)deck[4]).setSkill((SkillCard)deck[7], 1);
		deck[5] = new CentaurSkeletonCard();
		deck[6] = new CentaurSkeletonCard();
		((MonsterCard)deck[6]).setSkill((SkillCard)deck[7], 1);
		LeadingRoleCard playerCard = new LeadingRoleCard();
		new ArenaPark(deck, playerCard).fight();
	}
}
