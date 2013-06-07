package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javahacker.Constant;
import javahacker.TransparentIcon;

public class LeadingRoleCard extends MonsterCard{


	private int img_id;
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	
	static {
		no_img = 10;
		imgs = new Image[no_img];
		
		imgs[0] = (new TransparentIcon("Images/Centaur_skeleton_1.png", Color.black)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Centaur_skeleton_2.png", Color.black)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Centaur_skeleton_3.png", Color.black)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Centaur_skeleton_4.png", Color.black)).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Red_Slime.png", new Color(0,0,0))).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Red_Slime_2.png", new Color(0,0,0))).getIcon().getImage();
		imgs[6] = (new TransparentIcon("Images/Red_Slime_3.png", new Color(0,0,0))).getIcon().getImage();
		imgs[7] = (new TransparentIcon("Images/Red_Slime_4.png", new Color(0,0,0))).getIcon().getImage();
		imgs[8] = (new TransparentIcon("Images/Slime_dead.png", new Color(0,0,0))).getIcon().getImage();
		imgs[9] = (new TransparentIcon("Images/Red_Slime_dead.png", new Color(0,0,0))).getIcon().getImage();
		
		largeImage = (new TransparentIcon("Images/CentaurSkeleton.jpg", new Color(0,0,0))).getIcon().getImage();
	}
	
	
	public LeadingRoleCard(){
		reset();
	}
	
	public Monster newMonster(int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new LeadingRole(this, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(20);
		setMP(20);
		setAGI(20);
		setSIGHT(10);
		setSummonCount(1);
		setName("CentaurSkeleton");
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
		return 70;
	}
	public int getImageHeight(){
		return 70;
	}

}
