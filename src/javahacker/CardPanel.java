package javahacker;

import java.awt.*;

import javax.swing.*;

import cards.ConcreteCard;

import java.awt.event.*;
import java.util.ArrayList;

public class CardPanel extends JPanel implements ActionListener{
	

	private int width, height;
	private double unit_size;
	private Shift shift;
	/* image lists to draw */
	private ImageCell[] imgcs;
	private ImageCell[] backgroundcs;
	private ImageCell[] foregroundcs;
	private int imgcs_len, bgcs_len, fgcs_len, maximgcs;
	private ConcreteCard[] hands;
	private ShiftLabel left, right;
	private int leftCard, rightCard;
	private Timer timer;
	/* interaction with arena */
	
	private int counter;
	
	public CardPanel(int width, int height){
		this.width = width;
		this.height = height;
		timer = new Timer(Constant.INTERVAL, this);
		unit_size = ((double)Toolkit.getDefaultToolkit().getScreenResolution())/ 72;
		
		maximgcs = width * height;
		imgcs = new ImageCell[maximgcs];
		backgroundcs = new ImageCell[maximgcs];
		foregroundcs = new ImageCell[maximgcs];
		hands = new ConcreteCard[Constant.MAXHAND];
		setLayout(null);
		for(int i = 0; i < Constant.MAXHAND; i++){
			hands[i] = new ConcreteCard();
			this.add(hands[i]);
		}
		
		left = new ShiftLabel((new TransparentIcon("Images/left.png", Color.white)).getIcon(), Shift.LEFT);
		left.setBounds(10, 50, 50, 85);
		right = new ShiftLabel((new TransparentIcon("Images/right.png", Color.white)).getIcon(), Shift.RIGHT);
		right.setBounds(Constant.CARDPANEL_WIDTH-600, 50, 50, 85);
		this.add(left);
		this.add(right);
		reset();
		timer.start();
		Thread.yield();
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
		counter = 0;
		shift = Shift.STABLE;
		leftCard = 0;
		rightCard = 4;
		adjustHands();
	}
	
	public boolean addToHand(ConcreteCard card, int no){
		if(no < 0 || no > Constant.MAXHAND || hands[no] == null)
			return false;
		
		hands[no].copy(card);
		return true;
	}
	
	/* add normal image to imgcs */
	public int addToCell(Image img, int x, int y, int id){
		if(id >= maximgcs)
			return -1;
		
		ImageCell imgc = new ImageCell(img, x, y);
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
	
	/* add background image to backgroundcs */
	public int addToBackground(Image img, int x, int y, int id){
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
	}
	
	/* add foreground image to foregroundcs */
	public int addToForeground(Image img, int x, int y, int id){
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
	}
	
	public boolean removeFromCell(int id){
		if(id >= imgcs_len || id < 0)
			return false;
		
		imgcs[id] = null;
		if(id == imgcs_len - 1)
			imgcs_len--;
		
		return true;
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		inGamePaint(g);
	}
	
	public void inGamePaint(Graphics g) {
		
		/* draw units */
		for(int i = 0; i < imgcs_len; i++){
			if(imgcs[i] != null){
				g.drawImage(imgcs[i].img, imgcs[i].x, imgcs[i].y, this);
				System.out.println("draw something");
			}
		}
	}

	private void adjustHands(){
		for(int i = 0; i < leftCard; i++){
			hands[i].setVisible(false);
		}
		for(int i = leftCard; i <= rightCard; i++){
			hands[i].setBounds(60 + (i-leftCard)*(Constant.CARDSIZE_X + 10), 20, Constant.CARDSIZE_X, Constant.CARDSIZE_Y);
			hands[i].setVisible(true);
		}
		for(int i = rightCard + 1; i < Constant.MAXHAND; i++){
			hands[i].setVisible(false);
		}
	}
	
	private class ShiftLabel extends JLabel implements MouseListener{
		ImageIcon icon;
		Shift type;
		public ShiftLabel(ImageIcon icon, Shift type){
			this.type = type;
			this.icon = icon;
			addMouseListener(this);
		}
		public void mouseEntered(MouseEvent e) {
			if(type == Shift.LEFT){
				shift = Shift.LEFT;
			}else if(type == Shift.RIGHT){
				shift = Shift.RIGHT;
			}
			setIcon(icon);
		}
		public void mouseExited(MouseEvent e) {
			shift = Shift.STABLE;
			setIcon(null);
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	private class ImageCell{
		public Image img;
		public int x, y;
		public boolean seen;
		public ImageCell(Image img, int x, int y){
			this.img = img;
			this.x = x;
			this.y = y;
			this.seen = false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		counter++;
		switch(shift){
			case STABLE:
				break;
			case LEFT:
				if(leftCard > 0){
					leftCard--;
					rightCard--;
					adjustHands();
					System.out.println("in");
				}
				break;
			case RIGHT:
				System.out.println("***" + counter);
				if(rightCard < Constant.MAXHAND - 1){
					leftCard++;
					rightCard++;
					adjustHands();
				}
				break;
			default:;
		}
	}
	
	public static enum Shift{
		STABLE, LEFT, RIGHT
	}
	
}