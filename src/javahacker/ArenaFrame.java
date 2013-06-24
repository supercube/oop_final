package javahacker;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import cards.ConcreteCard;
import cards.LeadingRole;
import cards.LeadingRoleCard;
import cards.PortalCard;
import cards.Skill;

import java.util.ArrayList;

public class ArenaFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener{
		
	private Container c;
	private BattleFieldPanel battle_field;
	private CardPanel card_panel;
	private int width, height;
	private int posX, posY;
	private Arena arena;
	private int skill_id;
	public ArenaFrame(String title, Arena arena, String background, String battleBG, String cardBG){
		setTitle(title);
		this.arena = arena;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		posX = (width-Constant.BATTLEFIELD_WIDTH)/2;
		posY = (height-Constant.BATTLEFIELD_HEIGHT-Constant.CARDPANEL_HEIGHT)/2;
		skill_id = 0;
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		c = getContentPane();
		c.setPreferredSize(new Dimension(width, height));
		c.setLayout(null);
		
		
		battle_field = new BattleFieldPanel(Constant.BATTLEFIELD_WIDTH, Constant.BATTLEFIELD_HEIGHT, arena);
		battle_field.setBounds(posX, posY, Constant.BATTLEFIELD_WIDTH, Constant.BATTLEFIELD_HEIGHT);
		battle_field.setOpaque(false);
		c.add(battle_field);
		
		card_panel = new CardPanel(Constant.CARDPANEL_WIDTH, Constant.CARDPANEL_HEIGHT, arena);
		card_panel.setBounds(posX, posY+Constant.BATTLEFIELD_HEIGHT, Constant.CARDPANEL_WIDTH, Constant.CARDPANEL_HEIGHT);
		card_panel.setOpaque(false);
		c.add(card_panel);
		
		setSize(width, height);
		
		setBackground(background);
		setBattleFieldBackground(battleBG);
		setCardPanelBackground(cardBG);
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setFocusable(true);
	}
	
	public void setBackground(String background){
		((JPanel)c).setOpaque(false);
		ImageIcon imgi = new ImageIcon(background);
		JLabel img_label = new JLabel(imgi);
		this.getLayeredPane().add(img_label, new Integer(Integer.MIN_VALUE)); 
		img_label.setBounds(0, 0, width, height); 
	}
	
	public void setBattleFieldBackground(String background){
		((JPanel)c).setOpaque(false);
		ImageIcon imgi = new ImageIcon(background);
		JLabel img_label = new JLabel(imgi);
		this.getLayeredPane().add(img_label, new Integer(Integer.MIN_VALUE+1)); 
		img_label.setBounds(posX, posY, Constant.BATTLEFIELD_WIDTH, Constant.BATTLEFIELD_HEIGHT); 
	}
	
	public void setCardPanelBackground(String background){
		((JPanel)c).setOpaque(false);
		ImageIcon imgi = new ImageIcon(background);
		JLabel img_label = new JLabel(imgi);
		this.getLayeredPane().add(img_label, new Integer(Integer.MIN_VALUE+1)); 
		img_label.setBounds(posX, posY+Constant.BATTLEFIELD_HEIGHT, Constant.CARDPANEL_WIDTH, Constant.CARDPANEL_HEIGHT); 
	}
	
	public int addToBattleField(Image img, int x, int y, int width, int height, int id){
		return battle_field.addToPaint(img, x, y, width, height, id);
	}
	
	public int addToBackground(Image img, int x, int y, int width, int height, int id){
		return battle_field.addToBackground(img, x, y, width, height, id);
	}
	
	public int addToForeground(Image img, int x, int y, int width, int height, int id){
		return battle_field.addToForeground(img, x, y, width, height, id);
	}
	
	public boolean addToHand(ConcreteCard card){
		return card_panel.addToHand(card);
	}

	
	public boolean removeFromBattleField(int id){
		return battle_field.removeFromCell(id);
	}
	
	public boolean removeFromBackground(int id){
		return battle_field.removeFromBackground(id);
	}
	
	public boolean removeFromForeground(int id){
		return battle_field.removeFromForeground(id);
	}
	
	public void reset(){
		battle_field.reset();
	}
	
	public void removePortalCard(int id){
		card_panel.removePortalCard(id);
	}
	
		
	/* mouse events */
	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		/* left click */
		if(e.getButton() == MouseEvent.BUTTON1){
			if(x >= posX && x < posX + Constant.BATTLEFIELD_WIDTH){
				if(y >= posY){
					if(y < posY + Constant.BATTLEFIELD_HEIGHT){
						battle_field.doPress((x - posX), (y - posY));
						return;
					}else if(y < posY + Constant.BATTLEFIELD_HEIGHT + Constant.CARDPANEL_HEIGHT){
						card_panel.doDrag(x - posX, y - (posY+Constant.BATTLEFIELD_HEIGHT));
						return;
					}
				}
			}
		/* right click */
		}else if(e.getButton() == MouseEvent.BUTTON3){
			if(x >= posX && x < posX + Constant.BATTLEFIELD_WIDTH){
				if(y >= posY){
					if(y < posY + Constant.BATTLEFIELD_HEIGHT){
						Skill skill = arena.player.useSkill(skill_id, x - posX, y - posY);
						if(skill != null){
							arena.addSkill(skill);
						}
						return;
					}else if(y < posY + Constant.BATTLEFIELD_HEIGHT + Constant.CARDPANEL_HEIGHT){
						card_panel.changeDetail(x - posX, y - (posY+Constant.BATTLEFIELD_HEIGHT));
						return;
					}
				}
				
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		card_panel.releaseDrag(x - posX, y - (posY+Constant.BATTLEFIELD_HEIGHT));
		if(x >= posX && x < posX + Constant.BATTLEFIELD_WIDTH){
			if(y >= posY){
				if(y < posY + Constant.BATTLEFIELD_HEIGHT){
					return;
				}else if(y < posY + Constant.BATTLEFIELD_HEIGHT + Constant.CARDPANEL_HEIGHT){
					return;
				}
			}
		}
	}
	public void mouseDragged(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		if(x >= posX && x < posX + Constant.BATTLEFIELD_WIDTH){
			if(y >= posY){
				if(y < posY + Constant.BATTLEFIELD_HEIGHT){
					return;
				}else if(y < posY + Constant.BATTLEFIELD_HEIGHT + Constant.CARDPANEL_HEIGHT){
					card_panel.dragging(x - posX, y - (posY+Constant.BATTLEFIELD_HEIGHT));
					return;
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent e){
		if(e.getClickCount() >= 2){
			int x = e.getX();
			int y = e.getY();
			if(x >= posX && x < posX + Constant.BATTLEFIELD_WIDTH){
				if(y >= posY){
					if(y < posY + Constant.BATTLEFIELD_HEIGHT){
						
					}else if(y < posY + Constant.BATTLEFIELD_HEIGHT + Constant.CARDPANEL_HEIGHT){
						card_panel.doDoubleClick(x - posX, y - (posY+Constant.BATTLEFIELD_HEIGHT));
						return;
					}
				}
			}
		}
				
	}
	
	public void mouseMoved(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	
	private Constant.GameStatus prevGameStatus;
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()){
			case KeyEvent.VK_Z:
				skill_id = 0;
				break;
			case KeyEvent.VK_X:
				skill_id = 1;
				break;
			case KeyEvent.VK_C:
				skill_id = 2;
				break;
			case KeyEvent.VK_V:
				skill_id = 3;
				break;
			case KeyEvent.VK_SPACE:
				if(arena.game == Constant.GameStatus.PAUSE){
					arena.game = prevGameStatus;
				}else{
					prevGameStatus = arena.game;
					arena.game = Constant.GameStatus.PAUSE;
				}
				break;
			case KeyEvent.VK_ESCAPE:
				arena.game = Constant.GameStatus.END;
				break;
			default:;
		}
		
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
	public void redraw(){
		card_panel.redraw();
		repaint();
	}

}