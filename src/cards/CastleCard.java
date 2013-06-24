package cards;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;

public class CastleCard extends PortalCard{
		
	private static Image[] imgs;
	private static Image largeImage;
	private static Image symbolImage;
	
	static {
			imgs = new Image[2];
			imgs[0] = (new TransparentIcon("Images/Portal/castle.png", Color.white)).getIcon().getImage();
			imgs[1] = (new TransparentIcon("Images/Portal/castle_dead.png", Color.white)).getIcon().getImage();
			symbolImage = null;
			largeImage = null;
		}
		
		
	public CastleCard(){
		reset();
	}
	
	public Portal newPortal(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new Castle(this, team, HP, AGI, x, y);
	}
	
	public void reset(){
		setHP(10);
		setAGI(0);
		setSummonCount(1);
		setName("Castle");
	}
	
	public void resetCardImage(){
	}
	
	public Image getImage(int img_id){
		return imgs[img_id];
	}
	
	public int getImageWidth(){
		return 150;
	}
	public int getImageHeight(){
		return 150;
	}
	public Image getSymbolImage() {
		return null;
	}
}
