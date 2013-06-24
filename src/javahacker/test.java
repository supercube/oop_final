package javahacker;


import cards.*;


public class test {
	
	public static void main(String[] args){
		LeadingRoleCard playerCard = new LeadingRoleCard();
		playerCard.setSkill(new SwordSkillCard(), 0);
		playerCard.setSkill(new TornadoSkillCard(), 1);
		playerCard.setSkill(new HealthSkillCard(), 2);
		
		new ArenaPark(Constant.enemy_decks[1], Constant.enemy_decks[1], playerCard).fight();
	}
}
