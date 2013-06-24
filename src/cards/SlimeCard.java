package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;


public class SlimeCard extends MonsterCard{
	
	
	private static Image[] imgs;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		imgs = new Image[9];
		imgs[0] = (new TransparentIcon("Images/Monster/Slime.png", new Color(0,0,0))).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Monster/Slime_2.png", new Color(0,0,0))).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Monster/Slime_3.png", new Color(0,0,0))).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Monster/Slime_4.png", new Color(0,0,0))).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Monster/Red_Slime.png", new Color(0,0,0))).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Monster/Red_Slime_2.png", new Color(0,0,0))).getIcon().getImage();
		imgs[6] = (new TransparentIcon("Images/Monster/Red_Slime_3.png", new Color(0,0,0))).getIcon().getImage();
		imgs[7] = (new TransparentIcon("Images/Monster/Red_Slime_4.png", new Color(0,0,0))).getIcon().getImage();
		imgs[8] = (new TransparentIcon("Images/Monster/Slime_dead.png", Color.black)).getIcon().getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/Slime_Symbol.png", Color.black)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/large_Slime.png").getImage();
	}
	
	
	public SlimeCard(){
		reset();
	}
	
	public Monster newMonster(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new Slime(this, team, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(3);
		setMP(3);
		setAGI(10);
		setSIGHT(100);
		setSummonCount(10);
		setSkill(new TinyAttackSkillCard(),0);
		setName("Slime");
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
		return 30;
	}
	public Image getSymbolImage() {
		return symbolImage;
	}
}
