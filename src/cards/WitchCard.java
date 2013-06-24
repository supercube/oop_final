package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;
public class WitchCard extends MonsterCard{
	
	private int img_id;
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		imgs = new Image[10];
		
		imgs[0] = (new TransparentIcon("Images/Monster/WitchLeft1.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Monster/WitchLeft2.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Monster/WitchLeft3.png", Color.white)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Monster/WitchLeft4.png", Color.white)).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Monster/WitchRight1.png", Color.white)).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Monster/WitchRight2.png", Color.white)).getIcon().getImage();
		imgs[6] = (new TransparentIcon("Images/Monster/WitchRight3.png", Color.white)).getIcon().getImage();
		imgs[7] = (new TransparentIcon("Images/Monster/WitchRight4.png", Color.white)).getIcon().getImage();
		imgs[8] = (new TransparentIcon("Images/Monster/Witch_dead1.png", Color.white)).getIcon().getImage();
		imgs[9] = (new TransparentIcon("Images/Monster/Witch_dead2.png", Color.white)).getIcon().getImage();
		
		symbolImage = (new TransparentIcon("Images/Symbol/WitchSymbol.png", Color.white)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/WizardCard.png").getImage();
	}
	
	
	public WitchCard(){
		reset();
	}
	
	public Monster newMonster(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new Witch(this, team, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(15);
		setMP(25);
		setAGI(25);
		setSIGHT(250);
		setSummonCount(3);
		setSkill(new WizardFireSkillCard(), 0);
		setName("Witch");
		img_id = rnd.nextInt(8);
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
		return 55;
	}
	public int getImageHeight(){
		return 80;
	}
	public Image getSymbolImage() {
		return symbolImage;
	}
}
