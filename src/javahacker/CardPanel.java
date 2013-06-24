package javahacker;

import java.awt.*;

import javax.swing.*;

import cards.*;

import java.awt.event.*;
import java.util.ArrayList;

public class CardPanel extends JLayeredPane implements ActionListener{
	

	private int width, height;
	private double unit_size;
	private Shift shift;
	private ConcreteCard[] hands;
	private ConcreteCard[] portals;
	private PortalSpace[] portalSpaces;
	private ShiftLabel left, right;
	private int leftCard, rightCard;
	private Timer timer;
	private ConcreteCard deck;
	private JLabel no_draw;
	private JLabel draw_count_bar;
	/* image lists to draw */
	private ImageCell[] imgcs;
	private ImageCell[] backgroundcs;
	private ImageCell[] foregroundcs;
	private int imgcs_len, bgcs_len, fgcs_len, maximgcs;

	
	
	/* interaction with arena */
	private int counter;
	private Arena arena;
	public CardPanel(int width, int height, Arena arena){
		this.width = width;
		this.height = height;
		this.arena = arena;
		
		setLayout(null);
		
		timer = new Timer(Constant.INTERVAL, this);
		unit_size = ((double)Toolkit.getDefaultToolkit().getScreenResolution())/ 72;
		
		maximgcs = width * height;
		imgcs = new ImageCell[maximgcs];
		backgroundcs = new ImageCell[maximgcs];
		foregroundcs = new ImageCell[maximgcs];
		hands = new ConcreteCard[Constant.MAXHAND];
		for(int i = 0; i < Constant.MAXHAND; i++){
			hands[i] = new ConcreteCard(1);
			this.add(hands[i]);
		}
		deck = new ConcreteCard(5);
		deck.copy(new UnknownCard());
		deck.setBounds(1080, 20, Constant.CARDSIZE_X, Constant.CARDSIZE_Y);
		this.add(deck);
		
		no_draw = new JLabel(arena.no_draw + "/" + (arena.deck.length-arena.nextToDraw));
		no_draw.setFont(no_draw.getFont().deriveFont(15));
		no_draw.setForeground(Color.white);
		no_draw.setBounds(1120, 185, 40, 10);
		this.add(no_draw);
		
		draw_count_bar =  new JLabel(new ImageIcon("Images/Other/green.png"));
		draw_count_bar.setBounds(1080, 175, 0, 5);
		this.add(draw_count_bar);
		
		left = new ShiftLabel((new TransparentIcon("Images/Other/left.png", Color.white)).getIcon(), Shift.LEFT);
		left.setBounds(10, 50, 50, 85);
		right = new ShiftLabel((new TransparentIcon("Images/Other/right.png", Color.white)).getIcon(), Shift.RIGHT);
		right.setBounds(490, 50, 50, 85);
		this.add(left);
		this.add(right);
		
		portals = new ConcreteCard[Constant.MAXPORTAL];
		portalSpaces = new PortalSpace[Constant.MAXPORTAL]; 
		for(int i = 0; i < Constant.MAXPORTAL; i++){
			portals[i] = new ConcreteCard(2);
			portals[i].setBounds(600+ i*110, 20, Constant.CARDSIZE_X, Constant.CARDSIZE_Y);
			this.add(portals[i]);
			portalSpaces[i] = new PortalSpace(portals[i]);
			portals[i].add(portalSpaces[i]);
			//portals[i].addMouseListener(portalSpaces[i]);
		}
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
		for(int i = 0; i < arena.portal_card.length; i++){
			arena.portal_card[i] = null;
		}
		imgcs_len = 0;
		bgcs_len = 0;
		fgcs_len = 0;
		counter = 0;
		shift = Shift.STABLE;
		leftCard = 0;
		rightCard = 3;
		adjustHands();
	}
	
	public boolean addToHand(ConcreteCard card){
		for(int no = 0; no < Constant.MAXHAND; no++){
			if(hands[no].getCard() == null){
				hands[no].copy(card);
				return true;
			}
		}
		return false;
	}
	
	public boolean addToPortal(ConcreteCard card, int no){
		if(no < 0 || no > Constant.MAXPORTAL || portals[no] == null || !(card.getCard() instanceof PortalCard))
			return false;
		
		portals[no].copy(card);
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
	
	public void removePortalCard(int id){
		portals[id].remove();
	}
	
	private Component DragObject;
	private int AdjustmentX, AdjustmentY;
	private int OriginX, OriginY;
	
	public void doDrag(int x, int y){
		
		DragObject = null;
		Component c = findComponentAt(x, y).getParent();
		//System.out.println(c.getClass().getName());
		
		if(c instanceof ConcreteCard){
			if(((ConcreteCard)c).type == 1){
				DragObject = c;
				setLayer(DragObject, JLayeredPane.DRAG_LAYER);
				OriginX = DragObject.getLocation().x;
				OriginY = DragObject.getLocation().y;
				AdjustmentX = DragObject.getLocation().x - x;
				AdjustmentY = DragObject.getLocation().y - y;
			}else if(((ConcreteCard)c).type == 5){
				if(!arena.draw(1) || !arena.hasNext()){
					deck.copy(new EmptyCard());
					no_draw.setText("0/0");
				}
			}
		}
	}
	
	public void releaseDrag(int x, int y){
		if(DragObject == null)
			return;
		
		Card cardDragged = ((ConcreteCard)DragObject).getCard();
		Card cardBase = null; 
		DragObject.setVisible(false);
		Component c = findComponentAt(x, y);
		if(!(c instanceof ConcreteCard) && !(c instanceof SkillLabel) && c != null)
			c = c.getParent();
		
		if(c instanceof ConcreteCard){
			cardBase = ((ConcreteCard)c).getCard();
			if(((ConcreteCard)c).type == 1) {
				ConcreteCard tmp = new ConcreteCard(cardBase);
				((ConcreteCard)c).copy(cardDragged);
				((ConcreteCard)DragObject).copy(tmp);
			}else if(((ConcreteCard)c).type == 2){
				if(cardDragged instanceof PortalCard){
					((ConcreteCard)c).remove();
					((ConcreteCard)c).copy(cardDragged);
					((ConcreteCard)DragObject).remove();
					this.getParent().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
					for(int i= 0; i < portals.length; i++){
						if(portals[i] == (ConcreteCard)c){
							arena.portal_card[i] = (PortalCard)((ConcreteCard)c).getCard();
							arena.battlefield_todo = 1;
							arena.toAdd = arena.portal_card[i];
							break;
						}
					}
					
					
				}
			}else if(((ConcreteCard)c).type == 3){
				if(cardDragged instanceof MonsterCard){
					((ConcreteCard)c).remove();
					((ConcreteCard)c).copy(cardDragged);
					((ConcreteCard)DragObject).remove();
				}
			}else if(((ConcreteCard)c).type == 5){
				((ConcreteCard)DragObject).remove();
				arena.draw_count_down -= Constant.DRAW_COUNT_DOWN/2;
			}
			
		}else if(c instanceof SkillLabel ){
			Card card = ((ConcreteCard)DragObject).getCard();
			if(card instanceof SkillCard){
				int id = ((SkillLabel)c).getId();
				((MonsterCard)(((ConcreteCard)c.getParent()).getCard())).setSkill((SkillCard)card, id);
				((ConcreteCard)DragObject).remove();
				((ConcreteCard)c.getParent()).reload();
			}
		}
		
		DragObject.setVisible(true);
		DragObject.setLocation(OriginX, OriginY);
		setLayer(DragObject, JLayeredPane.DEFAULT_LAYER);
		DragObject = null;
	}
	
	public void dragging(int x, int y){
		if(DragObject != null)
		{
			DragObject.setLocation(x+AdjustmentX, y+AdjustmentY);
		}
	}
	
	public void changeDetail(int x, int y){
		Component c = findComponentAt(x, y).getParent();
		
		if(c instanceof ConcreteCard){
			((ConcreteCard)c).MonsterDetailChange();
		}
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
		if(DragObject == null){
			switch(shift){
				case STABLE:
					break;
				case LEFT:
					if(leftCard > 0){
						leftCard--;
						rightCard--;
						adjustHands();
					}
					break;
				case RIGHT:
					if(rightCard < Constant.MAXHAND - 1){
						leftCard++;
						rightCard++;
						adjustHands();
					}
					break;
				default:;
			}
		}
	}
	
	public static enum Shift{
		STABLE, LEFT, RIGHT
	}
	
	public void doDoubleClick(int x, int y){
		
		Component c = findComponentAt(x, y).getParent();
		//System.out.println(c.getClass().getName());
		
		if(c instanceof ConcreteCard){
			for(int i= 0; i < portals.length; i++){
				if(portals[i] == (ConcreteCard)c){
					portalSpaces[i].doDoubleClick();
					break;
				}
			}
		}
	}
	
	private class PortalSpace extends JLabel{
		boolean OnOff;
		ConcreteCard card;
		ConcreteCard[] monsters;
		public PortalSpace(ConcreteCard card){
			this.card = card;
			monsters = new ConcreteCard[2];
			for(int i = 0; i < 2; i++){
				monsters[i] = new ConcreteCard(3);
				monsters[i].setBounds(0 + (Constant.CARDSIZE_X+10)*i, 0, Constant.CARDSIZE_X,  Constant.CARDSIZE_Y);
				this.add(monsters[i]);
			}
			OnOff = false;
			setSize(330,150);
			setBounds(Constant.CARDSIZE_X+10, 0, (Constant.CARDSIZE_X+10)*3, Constant.CARDSIZE_Y);
			setVisible(false);
		}
		public void doDoubleClick(){	
			if(card.getCard() != null && card.getCard() instanceof PortalCard){
				
				if(OnOff){
					if(monsters[0].getCard() != null && monsters[0].getCard() instanceof MonsterCard){
						((PortalCard)card.getCard()).setMonster((MonsterCard)(monsters[0].getCard()), 0);
						card.reload();
					}
					if(monsters[1].getCard() != null && monsters[1].getCard() instanceof MonsterCard){
						((PortalCard)card.getCard()).setMonster((MonsterCard)(monsters[1].getCard()), 1);
						card.reload();
					}
					card.setBounds(card.getLocation().x, card.getLocation().y, Constant.CARDSIZE_X, Constant.CARDSIZE_Y);
					setVisible(false);
					OnOff = false;
				}else{
					card.setBounds(card.getLocation().x, card.getLocation().y, (Constant.CARDSIZE_X+10)*3, Constant.CARDSIZE_Y);
					PortalCard pcard = (PortalCard)card.getCard();
					monsters[0].copy(pcard.getMonster(0));
					monsters[1].copy(pcard.getMonster(1));
					if(monsters[0].getCard() == null){
						monsters[0].copy(new EmptyCard());
					}
					if(monsters[1].getCard() == null){
						monsters[1].copy(new EmptyCard());
					}
					setVisible(true);
					OnOff = true;
				}
			}
		}
	}
	public void redraw(){
		for(int i = 0; i < portals.length; i++)
			portals[i].reload();
		if(!(deck.getCard() instanceof EmptyCard)){
			no_draw.setText(arena.no_draw + "/" + (arena.deck.length-arena.nextToDraw));
			//draw_count_bar.setBounds(980, 190, 20, 5);
			draw_count_bar.setBounds(1080, 175, 100 - (int)(100*(((double)arena.draw_count_down)/(double)Constant.DRAW_COUNT_DOWN)), 5);
		}else{
			no_draw.setText("0/0");
		}
	}
}