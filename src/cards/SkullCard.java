package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;
public class SkullCard extends MonsterCard{
	
	private int img_id;
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		imgs = new Image[5];
		
		imgs[0] = (new TransparentIcon("Images/Monster/SkullLeft1.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Monster/SkullLeft2.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Monster/SkullRight1.png", Color.white)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Monster/SkullRight2.png", Color.white)).getIcon().getImage();
		imgs[4]  = (new TransparentIcon("Images/Monster/SkullDeath.png", Color.white)).getIcon().getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/SkullSymbol.png", Color.white)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/SkullCard.png").getImage();
	}
	
	
	public SkullCard(){
		reset();
	}
	
	public Monster newMonster(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new Skull(this, team, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(6);
		setMP(7);
		setAGI(10);
		setSIGHT(100);
		setSummonCount(4);
		setSkill(new StabSkillCard(), 0);
		setName("Skull");
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
		return 30;
	}
	public int getImageHeight(){
		return 50;
	}
	public Image getSymbolImage() {
		return symbolImage;
	}
}
