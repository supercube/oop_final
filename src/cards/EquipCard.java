package cards;

import java.awt.Color;
import java.awt.Image;

import javahacker.Constant;
import javahacker.TransparentIcon;
import javahacker.Constant.CardType;

import javax.swing.ImageIcon;

public abstract class EquipCard extends Card{
	private int adjustHP, adjustMP, adjustAGI, adjustSIGHT;
	
	private static Image holder;
	
	public abstract Image getSymbolImage();
	
	public EquipCard(){
		setType(Constant.CardType.EQUIPMENT);
		setHolderImage((new TransparentIcon("Images/Card/EquipCardHolder.png", new Color(0,0,0))).getIcon().getImage());
	}
	
    protected final void setAdjustHP(int adjustHP){
    	this.adjustHP = adjustHP;
    }
    
    protected final void setAdjustMP(int adjustMP){
    	this.adjustMP = adjustMP;
    }
    
    protected final void setAdjustAGI(int adjustAGI){
    	this.adjustAGI = adjustAGI;
    }
    
    protected final void setAdjustSIGHT(int adjustSIGHT){
    	this.adjustSIGHT = adjustSIGHT;
    }

    protected final int getadjustAGI(){
        return adjustAGI;
    }

    protected final int getadjustMP(){
        return adjustMP;
    }
    
    protected final int getadjustHP(){
        return adjustHP;
    }
    
    protected final int getadjustSIGHT(){
        return adjustSIGHT;
    }
    
    public Image getHolderImage(){
		return holder;
	}
	
	public void setHolderImage(Image holder){
		EquipCard.holder = holder;
	}
	
	
}
