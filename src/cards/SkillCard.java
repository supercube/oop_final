package cards;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javahacker.Constant;
import javahacker.TransparentIcon;



public abstract class SkillCard extends Card implements DImage{
	protected int HP, AGI, SIGHT;
	protected String name;
	
	private static Image holder;
	private MonsterCard target;
	
	
	protected static Image[] imgs;
	protected static int no_imgs;
	
	
	public abstract Skill newSkill(Monster caster, int targetX, int targetY);
	public abstract Image getSymbolImage();
	
	public SkillCard(MonsterCard target){
		this.target = target;
		setType(Constant.CardType.SKILL);
		setHolderImage((new TransparentIcon("Images/Card/SkillCardHolder.png", Color.black)).getIcon().getImage());
	}
	protected int img_id;
	protected int ttl;
	
	public boolean vanish(){
		return ttl <= 0;
	}
	
	public abstract void act(Obstacle ob);
	
	public abstract int getCD();
	
	public abstract int getMPConsume();
	
	public final void setName(String name){
        this.name = name;
    }
    
    public final String getName(){
        return name;
    }
	
	public Image getHolderImage(){
		return holder;
	}
	
	public void setHolderImage(Image holder){
		SkillCard.holder = holder;
	}
	
}
