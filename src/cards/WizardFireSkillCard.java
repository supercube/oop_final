package cards;

import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;


public class WizardFireSkillCard extends SkillCard{
	
	private static Image symbolImage;
	
	private static Image[] imgs;
	private static Image largeImage;
	
	
	static {
		
		imgs = new Image[4];
		imgs[0] = (new TransparentIcon("Images/Skill/wizard_fire1.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Skill/wizard_fire2.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Skill/wizard_fire3.png", Color.white)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Skill/wizard_fire4.png", Color.white)).getIcon().getImage();
		
		largeImage = new ImageIcon("Images/Card/wizard_fire.png").getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/WizardFire_Symbol.png", Color.white)).getIcon().getImage();
	}
	
	
	public WizardFireSkillCard(MonsterCard target){
		super(target);
		reset();
		resetCardImage();
	}
	
	public WizardFireSkillCard(){
		super(null);
		reset();
		resetCardImage();
	}
	
	public Skill newSkill(Monster caster, int posX, int posY){
		return new WizardFireSkill(this, caster, ttl, HP, AGI, SIGHT, posX, posY);
	}
	
	public void reset(){
		HP = 10;
		AGI = 10;
		SIGHT = 12;
		img_id = 0;
		ttl = 150;
		setName("WizardFire");
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
		return 200;
	}
	
	public int getMPConsume(){
		return 3;
	}
	
	public Image getImage(int img_id){
		return imgs[img_id];
		
	}
	
	public int getImageWidth(){
		return 25;
	}
	public int getImageHeight(){
		return 25;
	}


	
}

