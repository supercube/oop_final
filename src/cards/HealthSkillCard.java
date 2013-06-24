package cards;

import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;


public class HealthSkillCard extends SkillCard{
	
	private static Image symbolImage;
	
	private static Image[] imgs;
	private static Image largeImage;
	
	
	static {
		
		imgs = new Image[4];
		imgs[0] = (new TransparentIcon("Images/Skill/Health1.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Skill/Health2.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Skill/Health3.png", Color.white)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Skill/Health4.png", Color.white)).getIcon().getImage();
		
		largeImage = new ImageIcon("Images/Card/Health.png").getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/HealthSymbol.png", Color.white)).getIcon().getImage();
	}
	
	
	public HealthSkillCard(MonsterCard target){
		super(target);
		reset();
		resetCardImage();
	}
	
	public HealthSkillCard(){
		super(null);
		reset();
		resetCardImage();
	}
	
	public Skill newSkill(Monster caster, int posX, int posY){
		return new HealthSkill(this, caster, ttl, HP, AGI, SIGHT, posX, posY);
	}
	
	public void reset(){
		HP = 10;
		AGI = 10;
		SIGHT = 60;
		img_id = 0;
		ttl = 600;
		setName("Health");
	}
	
	public void resetCardImage(){
		cardImage = config.createCompatibleImage(Constant.CARDSIZE_X, Constant.CARDSIZE_Y, Transparency.BITMASK);
		Graphics g = ((BufferedImage)cardImage).createGraphics();
		g.drawImage(largeImage, 0, 0, null);
		g.drawImage(getHolderImage(), 0, 0, null);
		g.dispose();
	}
	
	public Image getSymbolImage() {
		return symbolImage;
	}
	
	public void act(Monster target){
		/*int hp = target.getHP();
        if (hp > 1){
        	target.setHP(hp - 1);
        }else{
        	target.setHP(0);
        	getCaster().getKillReward();
        }*/
    }
	
	public void act(Obstacle ob){
        int hp = ob.getHP();
        if (hp > 1){
            ob.setHP(hp - 1);
        }else{
        	ob.setHP(0);
        }
    }
	
	
	public int getCD(){
		return 600;
	}
	
	public int getMPConsume(){
		return 10;
	}
	
	public Image getImage(int img_id){
		return imgs[img_id];
		
	}
	
	public int getImageWidth(){
		return 100;
	}
	public int getImageHeight(){
		return 100;
	}


	
}

