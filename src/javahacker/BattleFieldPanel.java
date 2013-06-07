package javahacker;

import java.awt.*;

import javax.swing.*;

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
	
	private int _counter;
	
	public BattleFieldPanel(int width, int height){
		this.width = width;
		this.height = height;
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
		if(x < 0 || x >= Constant.BATTLEFIELD_WIDTH || y < 0 || y >= Constant.BATTLEFIELD_HEIGHT)
			return -1;
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
	
	/* add background image to backgroundcs 
	public int addToBackground(Image img, int x, int y, int id){
		if(x < 0 || x >= Constant.BATTLEFIELD_WIDTH || y < 0 || y >= Constant.BATTLEFIELD_HEIGHT)
			return -1;
		if(id >= maximgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y);
		if(id < 0 && bgcs_len < maximgcs - 1){
			backgroundcs[bgcs_len] = imgc;
			id = bgcs_len;
		}else if(id < maximgcs){
			imgc.seen = backgroundcs[id].seen;
			backgroundcs[id] = imgc;
		}
		
		if(id >= bgcs_len){
			bgcs_len = id + 1;
		}
		return id;
	}*/
	
	/* add foreground image to foregroundcs 
	public int addToForeground(Image img, int x, int y, int id){
		if(x < 0 || x >= Constant.BATTLEFIELD_WIDTH || y < 0 || y >= Constant.BATTLEFIELD_HEIGHT)
			return -1;
		if(id >= maximgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y);
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
	}*/
	

	
	public boolean removeFromCell(int id){
		if(id >= imgcs_len || id < 0)
			return false;
		
		imgcs[id] = null;
		if(id == imgcs_len - 1)
			imgcs_len--;
		
		return true;
	}
	
	
	public void paint(Graphics g) {
		/*switch(_game._status){
			
			    
			case INGAME:
			case GAMEOVER:
			case WIN:
				inGamePaint(g);
				if(_game._status == POOConstant.Game.GAMEOVER){
					g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(30.0 * _unit_size)));
					g.setColor(Color.red);
					g.drawString("Game Over", _x / 2 - (int)Math.round(90.0 * _unit_size), _y / 2 - (int)Math.round(10.0 * _unit_size));
				}else if(_game._status == POOConstant.Game.WIN){
					g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(30.0 * _unit_size)));
					g.setColor(Color.red);
					g.drawString("W I N", _x / 2 - (int)Math.round(50.0 * _unit_size), _y / 2 - (int)Math.round(10.0 * _unit_size));
				}
				break;
			case INIT:
				g.setFont(new Font("Arial", Font.BOLD, (int)Math.round(30.0 * _unit_size)));
				g.setColor(Color.red);
				g.drawString("Game Start", _x / 2 - (int)Math.round(90.0 * _unit_size), _y / 2 - (int)Math.round(10.0 * _unit_size));
				break;
			default:;
		}*/
		inGamePaint(g);
	}
	
	public void inGamePaint(Graphics g) {
			
		
		/* draw background units (dead body)
		for(int i = 0; i < bgcs_len; i++){
			if(_fog[backgroundcs[i]._x * x_times + x_add][backgroundcs[i]._y * y_times + y_add] == POOConstant.Fog.BRIGHT){
				backgroundcs[i]._seen = true;
			}
			if(backgroundcs[i] != null && backgroundcs[i]._seen){
				g.drawImage(backgroundcs[i]._img, backgroundcs[i]._x * POOConstant.CELL_X_SIZE + backgroundcs[i]._paddingx, backgroundcs[i]._y * POOConstant.CELL_Y_SIZE + backgroundcs[i]._paddingy, this);
			}
		}*/
		
		/* draw units */
		for(int i = 0; i < imgcs_len; i++){
			if(imgcs[i] != null){// && _fog[imgcs[i]._x * x_times + x_add][imgcs[i]._y * y_times + y_add] == POOConstant.Fog.BRIGHT){
				g.drawImage(imgcs[i].img, imgcs[i].x - imgcs[i].width/2, imgcs[i].y - imgcs[i].height/2, this);
				System.out.println("**" + imgcs[i].height);
			}
		}
		
		/* draw foreground units 
		for(int i = 0; i < fgcs_len; i++){
			if(_fog[foregroundcs[i]._x * x_times + x_add][foregroundcs[i]._y * y_times + y_add] == POOConstant.Fog.BRIGHT){
				foregroundcs[i]._seen = true;
			}
			if(foregroundcs[i] != null && foregroundcs[i]._seen){
				g.drawImage(foregroundcs[i]._img, foregroundcs[i]._x * POOConstant.CELL_X_SIZE + foregroundcs[i]._paddingx, foregroundcs[i]._y * POOConstant.CELL_Y_SIZE + foregroundcs[i]._paddingy, this);
			}
		}*/
		
		/* draw fog of war 
		for(int i = 0; i < _no_fog_x; i++){
			for(int j = 0; j < _no_fog_y; j++){
				switch(_fog[i][j]){
					case UNSEEN:
						g.drawImage(_unseen, i * (POOConstant.FOG_X_SIZE), j * (POOConstant.FOG_Y_SIZE), this);
						break;
					case SEEN:
						g.drawImage(_seen, i * (POOConstant.FOG_X_SIZE), j * (POOConstant.FOG_Y_SIZE), this);
						break;
					default:;
				}
			}
		}*/
		
		/* draw player info 
		int fontSize = (int)Math.round(10.0 * _unit_size);
	    g.setFont(new Font("Arial", Font.BOLD, fontSize));
		g.setColor(Color.red);
        g.drawString("HP: ", (int)Math.round(5.0 * _unit_size), (int)Math.round(12.0 * _unit_size));
        g.fillRect((int)Math.round(30.0 * _unit_size), (int)Math.round(7.0 * _unit_size), (int)Math.round(_player.getHP() * 4 * _unit_size), (int)Math.round(5.0 * _unit_size));
        g.setColor(Color.blue);
        g.drawString("MP: ", (int)Math.round(4.0 * _unit_size), (int)Math.round(23.0 * _unit_size));
        g.fillRect((int)Math.round(30.0 * _unit_size), (int)Math.round(18.0 * _unit_size), (int)Math.round(_player.getMP() * 4 * _unit_size), (int)Math.round(5.0 * _unit_size));
        
        g.setColor(Color.yellow);
        g.drawString("Ang: ", (int)Math.round(2.0 * _unit_size), (int)Math.round(35.0 * _unit_size));
        if(_player.getAnger() >= _player.getMaxAnger() || _player.isAngry()){
        	if((_counter++)%2 == 0){
            	g.setColor(Color.red);
        	}else{
            	g.setColor(Color.orange); //new Color(255, 110, 0));
        	}
        }else{
        	g.setColor(Color.yellow);
        }
        g.fillRect((int)Math.round(30.0 * _unit_size), (int)Math.round(29.0 * _unit_size), (int)Math.round(_player.getAnger() * 4 * _unit_size), (int)Math.round(5.0 * _unit_size));
        for(int i = 0; i < _player._kill_count; i++){
        	g.drawImage(_star,(int)Math.round((5 + i * 10) * _unit_size) , (int)Math.round(40 * _unit_size), this);
        }
        
        g.setColor(Color.RED);
        g.drawString("alive: " + _game._no_living_target, _x - (int)Math.round(45 * _unit_size), _y - (int)Math.round(5 * _unit_size));
		*/
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