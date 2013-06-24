package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;
public class BearCard extends MonsterCard{
	
	private int img_id;
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		imgs = new Image[6];
		
		imgs[0] = (new TransparentIcon("Images/Monster/BearRight1.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Monster/BearRight2.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Monster/BearLeft1.png", Color.white)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Monster/BearLeft2.png", Color.white)).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Monster/Bear_dead1.png", Color.white)).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Monster/Bear_dead2.png", Color.white)).getIcon().getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/Bear_Symbol.png", Color.white)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/BearCard.png").getImage();
	}
	
	
	public BearCard(){
		reset();
	}
	
	public Monster newMonster(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new Bear(this, team, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(25);
		setMP(10);
		setAGI(50);
		setSIGHT(150);
		setSummonCount(2);
		setSkill(new LionPunchSkillCard(), 0);
		setName("Bear");
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
