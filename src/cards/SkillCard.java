package cards;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javahacker.Constant;
import javahacker.Constant.CardType;

import javax.swing.ImageIcon;

public abstract class SkillCard extends Card implements DImage{
	
	private static Image holder;
	private MonsterCard target;
	
	
	protected static Image[] imgs;
	protected static int no_imgs;
	
	public abstract Image getSymbolImage();
	
	public SkillCard(MonsterCard target){
		this.target = target;
		setType(Constant.CardType.SKILL);
		setHolderImage((new ImageIcon("Images/CardHolder.png")).getImage());
	}
	protected int img_id;
	protected int ttl;
	
	public boolean vanish(){
		return ttl <= 0;
	}
	
	public abstract void act(Obstacle ob);
	
	public static int getCD(){
		return 10000;
	}
	
	public static int getMPConsume(){
		return 10000;
	}
	
	public Image getHolderImage(){
		return holder;
	}
	
	public void setHolderImage(Image holder){
		SkillCard.holder = holder;
	}
	
}
