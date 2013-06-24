package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javahacker.Constant;
import javahacker.TransparentIcon;
import javahacker.Constant.CardType;

import javax.swing.ImageIcon;

public abstract class MonsterCard extends Card implements DImage{
	protected int HP, MP, AGI, SIGHT;
	protected int summonCount;
	protected String name;
	protected SkillCard[] skills;
	protected EquipCard[] equips;
	
	private static Image holder;
	
	public abstract Monster newMonster(int team, int x, int y);
	public abstract Image getSymbolImage();
	//public abstract Image getDeadImage();
	
	public MonsterCard(){
		setType(Constant.CardType.MONSTER);
		skills = new SkillCard[2];
		equips = new EquipCard[2];
		skills[0] = null;
		skills[1] = null;
		equips[0] = null;
		equips[1] = null;
		setHolderImage((new TransparentIcon("Images/Card/MonsterCardHolder.png", Color.black)).getIcon().getImage());
	}
    
    public final void setHP(int HP){
    	this.HP = HP;
    }

    public final void setMP(int MP){
    	this.MP = MP;
    }
    
    public final void setAGI(int AGI){
    	this.AGI = AGI;
    }
    
    public final void setSIGHT(int SIGHT){
    	this.SIGHT = SIGHT;
    }
    
    public final void setSummonCount(int summonCount){
    	this.summonCount = summonCount;
    }
    
    public final void setName(String name){
        this.name = name;
    }
    
    public final String getName(){
        return name;
    }
    
    public final int getAGI(){
        return AGI;
    }

    public final int getMP(){
        return MP;
    }
    
    public final int getHP(){
        return HP;
    }
    
    public final int getSIGHT(){
        return SIGHT;
    }
    
    public final int getSummonCount(){
        return summonCount;
    }
    
    public boolean setSkill(SkillCard skill, int id){
    	if(id < 0 || id > 1)
    		return false;
    	
    	skills[id] = skill;
    	resetCardImage();
        return true;
    }
    
    public boolean setEquip(EquipCard equip, int id){
    	if(id < 0 || id > 1)
    		return false;
    	
    	equips[id] = equip;
    	resetCardImage();
        return true;
    }
    
    public SkillCard getSkill(int id){
    	if(id < 0 || id > 1)
    		return null;
    				
        return skills[id];
    }
    
    public EquipCard getEquip(int id){
    	if(id < 0 || id > 1)
    		return null;
    				
        return equips[id];
    }
    
    
    public Image getHolderImage(){
		return holder;
	}
    
	public void setHolderImage(Image holder){
		this.holder = holder;
	}
	
	
}
