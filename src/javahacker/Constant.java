package javahacker;

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
	public static final int SLOWTTA = 50;
	public static enum CardType{
		MONSTER, SKILL, EQUIPMENT
	}
	public static enum GameStatus{
		UNDEFINED, INIT, INGAME, GAMEOVER, WIN, END
	}
}
