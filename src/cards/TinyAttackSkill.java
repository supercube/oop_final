package cards;

import java.awt.Image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class TinyAttackSkill extends Skill{
	
	private static Image symbolImage;
	
	private int img_id;
	private static Image[] imgs;
	private static int no_img;
	private static Image largeImage;
	
	/*
	
	static {
		symbolImage = (new TransparentIcon("Images/TAS_Symbol.png", new Color(0,0,0))).getIcon().getImage();
		no_imgs = 2;
		imgs = new Image[no_imgs];
		imgs[0] = (new TransparentIcon("Images/TAS.png", new Color(0,0,0))).getIcon().getImage();
		imgs[1] = (new TransparentIcon("Images/TAS_2.png", new Color(0,0,0))).getIcon().getImage();
	}
	
	
	public TinyAttackSkill(Monster caster, boolean isCard){
		super(caster);
		reset();
		if(isCard){
			resetCardImage();
		}
		
	}
	
	public void reset(){
		img_id = 0;
		ttl = 30;
	}
	
	public void resetCardImage(){
		cardImage = config.createCompatibleImage(Constant.CARDSIZE_X, Constant.CARDSIZE_Y, Transparency.BITMASK);
		Graphics g = ((BufferedImage)cardImage).createGraphics();
		g.drawImage(getHolderImage(), 0, 0, null);
		g.drawImage(largeImage, 10, 10, null);
		g.dispose();
	}
	
	public Image getSymbolImage() {
		return symbolImage;
	}
	
	public void act(Monster target){
        int hp = target.getHP();
        if (hp > 1){
        	target.setHP(hp - 1);
        }else{
        	target.setHP(0);
        	getCaster().getKillReward();
        }
    }
	
	public void act(Obstacle ob){
        int hp = ob.getHP();
        if (hp > 1){
            ob.setHP(hp - 1);
        }else{
        	ob.setHP(0);
        }
    }*/
	
	/*public boolean oneTimeStep((Arena arena, POOCoordinate pos){
		ttl -= 1;
		
		Cell[][] map = arena.getMap();
		
		if(ttl == 20){
			POOConstant.Type type = map[pos.x][pos.y].getType();
			if(type == POOConstant.Type.PET || type == POOConstant.Type.PLAYER){
				act((Pet)map[pos.x][pos.y].getObject());
			}else if(type == POOConstant.Type.OBSTACLE){
				act((Obstacle)map[pos.x][pos.y].getObject());
			}
		}
		if(ttl <= 15){
			_img_id = 1;
		}
		return vanish();
	}*/
	
	public static int getCD(){
		return 35;
	}
	
	public static int getMPConsume(){
		return 1;
	}
	
	public Image getImage(){
		return imgs[img_id];
	}
	
}

