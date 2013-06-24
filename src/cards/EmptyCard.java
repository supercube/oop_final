package cards;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.*;

public class EmptyCard extends Card{
		
		private static Image holder;
		
		public EmptyCard(){
			setType(Constant.CardType.EMPTY);
			setHolderImage(new ImageIcon("Images/Card/MonsterCardHolder.png").getImage());
			resetCardImage();
		}
		
		
		
		public void resetCardImage(){
			cardImage = config.createCompatibleImage(Constant.CARDSIZE_X, Constant.CARDSIZE_Y, Transparency.BITMASK);
			Graphics g = ((BufferedImage)cardImage).createGraphics();
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
