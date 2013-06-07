package javahacker;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import cards.ConcreteCard;
import cards.LeadingRole;
import cards.LeadingRoleCard;

import java.util.ArrayList;

public class ArenaFrame extends JFrame implements MouseListener{
		
	private Container c;
	private BattleFieldPanel battle_field;
	private CardPanel card_panel;
	private LeadingRole player;
	private int width, height;
	private int posX, posY;
	public ArenaFrame(String title, LeadingRole player, String background, String battleBG, String cardBG){
		setTitle(title);
		this.player = player;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		posX = (width-Constant.BATTLEFIELD_WIDTH)/2;
		posY = (height-Constant.BATTLEFIELD_HEIGHT-Constant.CARDPANEL_HEIGHT)/2;
		
		System.out.printf("(%d, %d), (%d, %d)", posX, posY, width, height);
		addKeyListener(new Adapter());
		addMouseListener(this);
		
		setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		c = getContentPane();
		c.setPreferredSize(new Dimension(width, height));
		c.setLayout(null);
		
		
		battle_field = new BattleFieldPanel(Constant.BATTLEFIELD_WIDTH, Constant.BATTLEFIELD_HEIGHT);
		battle_field.setBounds(posX, posY, Constant.BATTLEFIELD_WIDTH, Constant.BATTLEFIELD_HEIGHT);
		battle_field.setOpaque(false);
		c.add(battle_field);
		
		card_panel = new CardPanel(Constant.CARDPANEL_WIDTH, Constant.CARDPANEL_HEIGHT);
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
	
	public int addToArenaIOPanel(Image img, int x, int y, int width, int height, int id){
		return battle_field.addToPaint(img, x, y, width, height, id);
	}
	
/*public int addToBackground(Image img, int x, int y, int id){
		return battle_field.addToBackground(img, x, y, id);
	}
	
	public int addToBackground(Image img, int x, int y){
		return addToBackground(img, x, y, -1);
	}
	
	public int addToForeground(Image img, int x, int y, int id){
		return battle_field.addToForeground(img, x, y, id);
	}
	
	public int addToForeground(Image img, int x, int y){
		return addToForeground(img, x, y, -1);
	}*/
	
	public boolean addToHand(ConcreteCard card, int no){
		return card_panel.addToHand(card, no);
	}

	
	public boolean removeFromIOPanel(int id){
		return battle_field.removeFromCell(id);
	}
	
	public void reset(){
		battle_field.reset();
	}
	
private class Adapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
            /* player key 
            switch(key){
            	case KeyEvent.VK_UP:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.UP));
            		break;
            	case KeyEvent.VK_DOWN:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.DOWN));
            		break;
            	case KeyEvent.VK_LEFT:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.LEFT));
            		break;
            	case KeyEvent.VK_RIGHT:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.RIGHT));
            		break;
            	case KeyEvent.VK_Z:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.Z));
            		break;
            	case KeyEvent.VK_X:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.X));
            		break;
            	case KeyEvent.VK_SPACE:
            		if(_cmds.size() > 1)
        				_cmds.remove(_cmds.size() - 1);
            		_cmds.add(new Command(POOConstant.Cmd.SPACE));
            		break;
            	default:;
            }*/
            
            /* functional key 
            switch(key){
            	case KeyEvent.VK_F1:
            		_game._fog = !_game._fog;
            		break;
            	case KeyEvent.VK_F2:
            		_game._player_control = !_game._player_control;
            		break;
            	case KeyEvent.VK_SPACE:
            	case KeyEvent.VK_ENTER:
            		if(_game._status != POOConstant.Game.WIN && _game._status != POOConstant.Game.GAMEOVER)
            			break;
            	case KeyEvent.VK_F9:	// restart game
            		_game._status = POOConstant.Game.UNDEFINED;
        			break;
            	case KeyEvent.VK_ESCAPE:
            		_game._status = POOConstant.Game.END;
            		System.exit(0);
            		break;
            	
            	default:;
            }*/
        }
	}

	/* mouse events */
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(x >= posX && x < posX + Constant.BATTLEFIELD_WIDTH){
			if(y >= posY){
				if(y < posY + Constant.BATTLEFIELD_HEIGHT){
					System.out.println("battle field" + (x - posX) + ", " + (y - posY));
					player.setTargetPosition(x - posX, y - posY);
					return;
				}else if(y < posY + Constant.BATTLEFIELD_HEIGHT + Constant.CARDPANEL_HEIGHT){
					System.out.println("card panel");
					return;
				}
			}
		}
		System.out.println("background " + x + ", " + y);
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}