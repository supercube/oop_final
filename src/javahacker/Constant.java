package javahacker;

import cards.*;

public class Constant {
	
	public static final int CARDSIZE_X = 100;
	public static final int CARDSIZE_Y = 150;
	public static final int NAME_HEIGHT = 15;
	public static final int HP_BALL_WIDTH = 15;
	public static final int SPECIAL_HEIGHT = 25;
	
	public static final int INTERVAL = 500;
	public static final int BATTLEFIELD_WIDTH = 1200;
	public static final int BATTLEFIELD_HEIGHT = 500;
	public static final int CARDPANEL_WIDTH = 1200;
	public static final int CARDPANEL_HEIGHT = 200;
	public static final int MAXHAND = 10;
	public static final int MAXPORTAL = 3;
	public static final int SLOWTTA = 100;
	public static final int PORTALSLOWTTA = 1000;
	public static final int DRAW_COUNT_DOWN = 1000;
	public static final int ENDING_COUNT_DOWN = 300;
	public static final int BODY_COUNT_DOWN = 1000;
	public static enum CardType{
		PLAYER, JVM, MONSTER, SKILL, EQUIPMENT, PORTAL, EMPTY, UNKNOWN
	}
	public static enum GameStatus{
		UNDEFINED, INIT, INGAME, PAUSE, GAMEOVER, WIN, END
	}
	
	public static final Card[][] enemy_decks;
	static {
		enemy_decks = new Card[3][];
		enemy_decks[0] = new Card[20];
		enemy_decks[0][0] = new AncientGateCard();
		enemy_decks[0][1] = new AncientGateCard();
		enemy_decks[0][2] = new AncientGateCard();
		enemy_decks[0][3] = new AncientGateCard();
		enemy_decks[0][4] = new AncientGateCard();
		enemy_decks[0][5] = new SlimeCard();
		enemy_decks[0][6] = new SlimeCard();
		enemy_decks[0][7] = new SlimeCard();
		enemy_decks[0][8] = new SlimeCard();
		enemy_decks[0][9] = new SlimeCard();
		enemy_decks[0][10] = new HealthSkillCard();
		enemy_decks[0][11] = new HealthSkillCard();
		enemy_decks[0][12] = new HealthSkillCard();
		enemy_decks[0][13] = new HealthSkillCard();
		enemy_decks[0][14] = new BearCard();
		enemy_decks[0][15] = new BearCard();
		enemy_decks[0][16] = new LionPunchSkillCard();
		enemy_decks[0][17] = new LionPunchSkillCard();
		enemy_decks[0][18] = new TinyAttackSkillCard();
		enemy_decks[0][19] = new TinyAttackSkillCard();
		
		enemy_decks[1] = new Card[30];
		enemy_decks[1][0] = new HellGateCard();
		enemy_decks[1][1] = new HellGateCard();
		enemy_decks[1][2] = new HellGateCard();
		enemy_decks[1][3] = new HellGateCard();
		enemy_decks[1][4] = new HellGateCard();
		enemy_decks[1][5] = new SlimeCard();
		enemy_decks[1][6] = new SlimeCard();
		enemy_decks[1][7] = new SlimeCard();
		enemy_decks[1][8] = new SlimeCard();
		enemy_decks[1][9] = new SlimeCard();
		enemy_decks[1][10] = new SkullCard();
		enemy_decks[1][11] = new SkullCard();
		enemy_decks[1][12] = new SkullCard();
		enemy_decks[1][13] = new SkullCard();
		enemy_decks[1][14] = new WitchCard();
		enemy_decks[1][15] = new WitchCard();
		enemy_decks[1][16] = new WitchCard();
		enemy_decks[1][17] = new CentaurSkeletonCard();
		enemy_decks[1][18] = new CentaurSkeletonCard();
		enemy_decks[1][19] = new BearCard();
		enemy_decks[1][20] = new BearCard();
		enemy_decks[1][21] = new TinyAttackSkillCard();
		enemy_decks[1][22] = new StabSkillCard();
		enemy_decks[1][23] = new StabSkillCard();
		enemy_decks[1][24] = new WizardFireSkillCard();
		enemy_decks[1][25] = new WizardFireSkillCard();
		enemy_decks[1][26] = new LionPunchSkillCard();
		enemy_decks[1][27] = new LionPunchSkillCard();
		enemy_decks[1][28] = new TornadoSkillCard();
		enemy_decks[1][29] = new TornadoSkillCard();
		
		enemy_decks[2] = new Card[35];
		enemy_decks[2][0] = new HellGateCard();
		enemy_decks[2][1] = new HellGateCard();
		enemy_decks[2][2] = new HellGateCard();
		enemy_decks[2][3] = new HellGateCard();
		enemy_decks[2][4] = new HellGateCard();
		enemy_decks[2][5] = new SlimeCard();
		enemy_decks[2][6] = new SlimeCard();
		enemy_decks[2][7] = new SlimeCard();
		enemy_decks[2][8] = new SkullCard();
		enemy_decks[2][9] = new SkullCard();
		enemy_decks[2][10] = new SkullCard();
		enemy_decks[2][11] = new SkullCard();
		enemy_decks[2][12] = new SkullCard();
		enemy_decks[2][13] = new WitchCard();
		enemy_decks[2][14] = new WitchCard();
		enemy_decks[2][15] = new WitchCard();
		enemy_decks[2][16] = new WitchCard();
		enemy_decks[2][17] = new CentaurSkeletonCard();
		enemy_decks[2][18] = new CentaurSkeletonCard();
		enemy_decks[2][19] = new BearCard();
		enemy_decks[2][20] = new BearCard();
		enemy_decks[2][21] = new TinyAttackSkillCard();
		enemy_decks[2][22] = new StabSkillCard();
		enemy_decks[2][23] = new StabSkillCard();
		enemy_decks[2][24] = new WizardFireSkillCard();
		enemy_decks[2][25] = new WizardFireSkillCard();
		enemy_decks[2][26] = new LionPunchSkillCard();
		enemy_decks[2][27] = new LionPunchSkillCard();
		enemy_decks[2][28] = new TornadoSkillCard();
		enemy_decks[2][29] = new TornadoSkillCard();
		enemy_decks[2][30] = new TornadoSkillCard();
		enemy_decks[2][31] = new HealthSkillCard();
		enemy_decks[2][32] = new HealthSkillCard();
		enemy_decks[2][33] = new HealthSkillCard();
		enemy_decks[2][34] = new HealthSkillCard();
		
	}
}
