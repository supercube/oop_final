package cards;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;

public class AncientGateCard extends PortalCard{

	
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	
	static {
		imgs = new Image[3];
		imgs[0] = (new TransparentIcon("Images/Portal/AncientGate.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Portal/AncientGate.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Portal/AncientGate_dead.png", Color.white)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/large_AncientGate.png").getImage();
		
	}
	
	
	public AncientGateCard(){
		reset();
	}
	
	public Portal newPortal(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new AncientGate(this, team, HP, AGI, x, y);
	}
	
	public void reset(){
		setHP(70);
		setAGI(0);
		setSummonCount(1);
		setName("AncientGate");
		resetCardImage();
	}
	
	public void resetCardImage(){
		cardImage = config.createCompatibleImage(Constant.CARDSIZE_X, Constant.CARDSIZE_Y, Transparency.BITMASK);
		Graphics g = ((BufferedImage)cardImage).createGraphics();
		g.drawImage(largeImage, 0, 0, null);
		g.drawImage(getHolderImage(), 0, 0, null);
		g.dispose();
	}
	
	public Image getImage(int img_id){
		return imgs[img_id];
	}
	
	public int getImageWidth(){
		return 100;
	}
	public int getImageHeight(){
		return 140;
	}
}
