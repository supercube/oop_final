package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import javahacker.Constant;
import javahacker.TransparentIcon;

public class LeadingRoleCard extends MonsterCard{


	private static Image[] imgs;
	private static Image largeImage;
	private static Image symbolImage;
	static {
		imgs = new Image[6];
		
		imgs[0] = (new TransparentIcon("Images/Monster/right1.png", Color.black)).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/Monster/right2.png", Color.black)).getIcon().getImage();
		imgs[2] = (new TransparentIcon("Images/Monster/left1.png", Color.black)).getIcon().getImage();
		imgs[3] = (new TransparentIcon("Images/Monster/left2.png", Color.black)).getIcon().getImage();
		imgs[4] = (new TransparentIcon("Images/Monster/Centaur_skeleton_dead1.png", Color.black)).getIcon().getImage();
		imgs[5] = (new TransparentIcon("Images/Monster/Centaur_skeleton_dead2.png", Color.black)).getIcon().getImage();
		symbolImage = (new TransparentIcon("Images/Symbol/CentaurSkeleton_Symbol.png", Color.black)).getIcon().getImage();
		largeImage = new ImageIcon("Images/Card/large_CentaurSkeleton.jpg").getImage();
	}
	
	
	public LeadingRoleCard(){
		
		skills = new SkillCard[4];
		skills[0] = null;
		skills[1] = null;
		skills[2] = null;
		skills[3] = null;
		reset();
	}
	// override setSkill
	public boolean setSkill(SkillCard skill, int id){
    	if(id < 0 || id > 3)
    		return false;
    	
    	skills[id] = skill;
    	resetCardImage();
        return true;
    }
	// orverrid getSkill
	public SkillCard getSkill(int id){
    	if(id < 0 || id > 3)
    		return null;			
        return skills[id];
    }
	
	public Monster newMonster(int team, int x, int y){
		
		if(summonCount <= 0)
			return null;
		
		summonCount--;
		return new LeadingRole(this, team, HP, MP, AGI, SIGHT, x, y);
	}
	
	public void reset(){
		setHP(40);
		setMP(20);
		setAGI(70);
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
	
	public Image getSymbolImage() {
		return symbolImage;
	}
	
}
