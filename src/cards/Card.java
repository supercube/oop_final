package cards;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Random;

import javahacker.Constant;
import javahacker.Constant.CardType;

public abstract class Card{
	private Constant.CardType type;
	public static final Random rnd = new Random();
	public abstract Image getHolderImage();
    public abstract void setHolderImage(Image holder);

	protected Image cardImage;
	
	protected final static GraphicsConfiguration config;
	static {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		config = device.getDefaultConfiguration();
	}
	
	
	
	public void setType(Constant.CardType type){
		this.type = type;
	}
	
	public Constant.CardType getType(){
		return type;
	}
	
	
	
	public Image getCardImage() {
		return cardImage;
	}
	
	public abstract void resetCardImage();
	
}
