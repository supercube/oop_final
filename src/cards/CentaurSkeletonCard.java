package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;
public class CentaurSkeletonCard extends MonsterCard{
	
	private int img_id;
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		no_img = 6;
		imgs = new Image[no_img];
		
		imgs[0] = (new TransparentIcon("Images/Monster/Centaur_skeleton_1.png", Color.black)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Monster/Centaur_skeleton_2.png", Color.black)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Monster/Centaur_skeleton_3.png", Color.black)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Monster/Centaur_skeleton_4.png", Color.black)).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Monster/Centaur_skeleton_dead1.png", Color.black)).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Monster/Centaur_skeleton_dead2.png", Color.black)).getIcon().getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/CentaurSkeleton_Symbol.png", Color.black)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/large_CentaurSkeleton.jpg").getImage();
	}
	
	
	public CentaurSkeletonCard(){
		reset();
	}
	
	public Monster newMonster(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new CentaurSkeleton(this, team, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(20);
		setMP(20);
		setAGI(60);
		setSIGHT(150);
		setSummonCount(2);
		setSkill(new SwordSkillCard(), 0);
		setName("CentaurSkeleton");
		img_id = rnd.nextInt(4);
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
	public Image getSymbolImage() {
		return symbolImage;
	}
}
