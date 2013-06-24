package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javahacker.Constant;
import javahacker.TransparentIcon;

import javax.swing.ImageIcon;

public class UnknownCard extends Card{
	private static Image holder;
	private static Image largeImage;
	
	public UnknownCard(){
		setType(Constant.CardType.UNKNOWN);
		setHolderImage((new TransparentIcon("Images/Card/MonsterCardHolder.png", Color.black)).getIcon().getImage());
		largeImage = new ImageIcon("Images/Card/CardBack.png").getImage();
		resetCardImage();
	}
	
	
	
	public void resetCardImage(){
		cardImage = config.createCompatibleImage(Constant.CARDSIZE_X, Constant.CARDSIZE_Y, Transparency.BITMASK);
		Graphics g = ((BufferedImage)cardImage).createGraphics();
		g.drawImage(largeImage, 0, 0, null);
		g.drawImage(getHolderImage(), 0, 0, null);
		g.dispose();
	}
	
    public Image getHolderImage(){
		return holder;
	}
    
	public void setHolderImage(Image holder){
		this.holder = holder;
	}
}
