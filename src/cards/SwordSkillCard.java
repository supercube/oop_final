package cards;
import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.*;

public class SwordSkillCard extends SkillCard{
	
	private static Image[] imgs;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		
		imgs = new Image[6];
		imgs[0] = (new TransparentIcon("Images/Skill/sword1.png", Color.white)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Skill/sword2.png", Color.white)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Skill/sword3.png", Color.white)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Skill/sword4.png", Color.white)).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Skill/sword5.png", Color.white)).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Skill/sword6.png", Color.white)).getIcon().getImage();
		
		largeImage = new ImageIcon("Images/Card/Sword.png").getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/Sword_symbol.png", Color.white)).getIcon().getImage();
	}
	
	
	public SwordSkillCard(MonsterCard target){
		super(target);
		reset();
		resetCardImage();
	}
	
	public SwordSkillCard(){
		super(null);
		reset();
		resetCardImage();
	}
	
	public Skill newSkill(Monster caster, int posX, int posY){
		return new SwordSkill(this, caster, ttl, HP, AGI, SIGHT, posX, posY);
	}
	
	public void reset(){
		HP = 10;
		AGI = 20;
		SIGHT = 20;
		img_id = 0;
		ttl = 70;
		setName("Sword");
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
		return 150;
	}
	
	public int getMPConsume(){
		return 3;
	}
	
	public Image getImage(int img_id){
		return imgs[img_id];
		
	}
	
	public int getImageWidth(){
		return 40;
	}
	public int getImageHeight(){
		return 40;
	}
}
