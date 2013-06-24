package javahacker;

import java.awt.*;

import javax.swing.*;

import cards.LeadingRole;
import cards.PortalCard;
import cards.SkillCard;

import java.awt.event.*;
import java.util.ArrayList;

public class BattleFieldPanel extends JPanel{
	

	private double unit_size;
	private int width ,height;
	/* image lists to draw */
	private ImageCell[] imgcs;
	private ImageCell[] backgroundcs;
	private ImageCell[] foregroundcs;
	private int imgcs_len, bgcs_len, fgcs_len, maximgcs;
	
	/* interaction with arena */
	private Arena arena;
	private int _counter;
	
	public BattleFieldPanel(int width, int height, Arena arena){
		this.width = width;
		this.height = height;
		
		this.arena = arena;
		unit_size = ((double)Toolkit.getDefaultToolkit().getScreenResolution())/ 72;
		maximgcs = width * height;
		imgcs = new ImageCell[maximgcs];
		backgroundcs = new ImageCell[maximgcs];
		foregroundcs = new ImageCell[maximgcs];
		
		reset();
	}
	
	public void reset(){
		for(int i = 0; i < maximgcs; i++ ){
			imgcs[i] = null;
			backgroundcs[i] = null;
			foregroundcs[i] = null;
		}
			
		imgcs_len = 0;
		bgcs_len = 0;
		fgcs_len = 0;
		_counter = 0;
	}
	
	/* add normal image to imgcs */
	public int addToPaint(Image img, int x, int y, int width, int height, int id){
		/*if(x < 0 || x >= Constant.BATTLEFIELD_WIDTH || y < 0 || y >= Constant.BATTLEFIELD_HEIGHT)
			return -1;*/
		if(id >= maximgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y, width, height);
		if(id < 0 && imgcs_len < maximgcs - 1){
			imgcs[imgcs_len] = imgc;
			id = imgcs_len;
		}else if(id < maximgcs){
			imgcs[id] = imgc;
		}
		
		if(id >= imgcs_len){
			imgcs_len = id + 1;
		}
		return id;
	}
	
	public void doPress(int x, int y){
		switch(arena.battlefield_todo){
			case 0:
				arena.player.setTargetPosition(x, y);
				break;
			case 1:
				if(x < width/2){
					arena.addPortal((PortalCard)arena.toAdd, 1, x, y);
					arena.toAdd = null;
					arena.battlefield_todo = 0;
					this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				break;
			default:
				arena.player.setTargetPosition(x, y);
				break;
		}
	}
	
	/* add background image to backgroundcs */
	public int addToBackground(Image img, int x, int y, int width, int height, int id){
		/*if(x < 0 || x >= Constant.BATTLEFIELD_WIDTH || y < 0 || y >= Constant.BATTLEFIELD_HEIGHT)
			return -1;*/
		if(id >= maximgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y, width, height);
		if(id < 0 && bgcs_len < maximgcs - 1){
			backgroundcs[bgcs_len] = imgc;
			id = bgcs_len;
		}else if(id < maximgcs){
			backgroundcs[id] = imgc;
		}
		
		if(id >= bgcs_len){
			bgcs_len = id + 1;
		}
		return id;
	}
	
	/* add foreground image to foregroundcs */
	public int addToForeground(Image img, int x, int y, int width, int height, int id){
		/*if(x < 0 || x >= Constant.BATTLEFIELD_WIDTH || y < 0 || y >= Constant.BATTLEFIELD_HEIGHT)
			return -1;*/
		if(id >= maximgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y, width, height);
		if(id < 0 && fgcs_len < maximgcs - 1){
			foregroundcs[fgcs_len] = imgc;
			id = fgcs_len;
		}else if(id < maximgcs){
			imgc.seen = foregroundcs[id].seen;
			foregroundcs[id] = imgc;
		}
		
		if(id >= fgcs_len){
			fgcs_len = id + 1;
		}
		return id;
	}
	

	
	public boolean removeFromCell(int id){
		if(id >= imgcs_len || id < 0)
			return false;
		
		imgcs[id] = null;
		if(id == imgcs_len - 1)
			imgcs_len--;
		
		return true;
	}
	
	public boolean removeFromBackground(int id){
		if(id >= bgcs_len || id < 0)
			return false;
		
		backgroundcs[id] = null;
		if(id == bgcs_len - 1)
			bgcs_len--;
		
		return true;
	}
	
	public boolean removeFromForeground(int id){
		if(id >= fgcs_len || id < 0)
			return false;
		
		foregroundcs[id] = null;
		if(id == fgcs_len - 1)
			fgcs_len--;
		
		return true;
	}
	
	public void paint(Graphics g) {
		switch(arena.game){
			case INIT:
			case INGAME:
			case GAMEOVER:
			case PAUSE:
			case WIN:
				inGamePaint(g);
				if(arena.game == Constant.GameStatus.GAMEOVER){
					g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(30.0 * unit_size)));
					g.setColor(Color.red);
					g.drawString("Game Over", width / 2 - (int)Math.round(90.0 * unit_size), height / 2 - (int)Math.round(10.0 * unit_size));
				}else if(arena.game == Constant.GameStatus.WIN){
					g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(30.0 * unit_size)));
					g.setColor(Color.red);
					g.drawString("W I N", width / 2 - (int)Math.round(50.0 * unit_size), height / 2 - (int)Math.round(10.0 * unit_size));
				}else if(arena.game == Constant.GameStatus.INIT){
					g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(30.0 * unit_size)));
					g.setColor(Color.red);
					g.drawString("Game Start", width / 2 - (int)Math.round(90.0 * unit_size), height / 2 - (int)Math.round(10.0 * unit_size));
				}
				break;
			default:;
		}
	}
	
	public void inGamePaint(Graphics g) {
			
		
		/* draw background units (dead body) */
		for(int i = 0; i < bgcs_len; i++){
			if(backgroundcs[i] != null){
				g.drawImage(backgroundcs[i].img, backgroundcs[i].x - backgroundcs[i].width/2, backgroundcs[i].y - backgroundcs[i].height/2, this);
			}
		}
		
		/* draw units */
		for(int i = 0; i < imgcs_len; i++){
			if(imgcs[i] != null){
				g.drawImage(imgcs[i].img, imgcs[i].x - imgcs[i].width/2, imgcs[i].y - imgcs[i].height/2, this);
			}
		}
		
		/* draw foreground units */
		for(int i = 0; i < fgcs_len; i++){
			if(foregroundcs[i] != null){
				g.drawImage(foregroundcs[i].img, foregroundcs[i].x - foregroundcs[i].width/2, foregroundcs[i].y - foregroundcs[i].height/2, this);
			}
		}
		
		
		/* draw player info */
		int fontSize = (int)Math.round(10.0 * unit_size);
	    g.setFont(new Font("Arial", Font.BOLD, fontSize));
		g.setColor(Color.red);
        g.drawString("HP: ", (int)Math.round(5.0 * unit_size), (int)Math.round(12.0 * unit_size));
        g.fillRect((int)Math.round(30.0 * unit_size), (int)Math.round(7.0 * unit_size), (int)Math.round(arena.player.getHP() * 4 * unit_size), (int)Math.round(5.0 * unit_size));
        g.setColor(Color.blue);
        g.drawString("MP: ", (int)Math.round(4.0 * unit_size), (int)Math.round(23.0 * unit_size));
        g.fillRect((int)Math.round(30.0 * unit_size), (int)Math.round(18.0 * unit_size), (int)Math.round(arena.player.getMP() * 4 * unit_size), (int)Math.round(5.0 * unit_size));
        for(int i = 0; i < 4; i++){
        	SkillCard scard;
        	if((scard = arena.playerCard.getSkill(i)) != null){
        		g.drawImage(scard.getSymbolImage(), 30 + i*30, 35, this);
        	}
        }
	}
	
	private class ImageCell{
		public Image img;
		public int x, y, width, height;
		public boolean seen;
		public ImageCell(Image img, int x, int y, int width, int height){
			this.img = img;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.seen = false;
		}
	}
	
	
}