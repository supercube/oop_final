package cards;

import java.awt.Color;
import java.awt.Image;

import javahacker.Constant;
import javahacker.TransparentIcon;

public abstract class PortalCard extends Card implements DImage{
	
	protected int HP, AGI;
	protected int summonCount;
	protected String name;
	protected MonsterCard[] monsters;
	
	private static Image holder;
	public abstract Portal newPortal(int team, int x, int y);
	
	public PortalCard(){
		setType(Constant.CardType.PORTAL);
		monsters = new MonsterCard[2];
		monsters[0] = null;
		monsters[1] = null;
		setHolderImage((new TransparentIcon("Images/Card/PortalCardHolder.png", Color.black)).getIcon().getImage());
	}
	

	    
    
    public final void setHP(int HP){
    	this.HP = HP;
    }
    
    public final void setAGI(int AGI){
    	this.AGI = AGI;
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

    public final int getHP(){
        return HP;
    }
    
    
    public final int getSummonCount(){
        return summonCount;
    }
    
    
    public final boolean setMonster(MonsterCard monster, int id){
    	if(id < 0 || id > 1)
    		return false;
    	
    	monsters[id] = monster;
    	resetCardImage();
        return true;
    }
  
    
    public final MonsterCard getMonster(int id){
    	if(id < 0 || id > 1)
    		return null;
    	
        return monsters[id];
    }
    
	
	public Image getHolderImage(){
		return holder;
	}
    
	public void setHolderImage(Image holder){
		this.holder = holder;
	}

}
