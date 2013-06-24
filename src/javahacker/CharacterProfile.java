package javahacker;

import cards.*;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class CharacterProfile extends JLayeredPane{

	private JLabel background;
	private JLabel Logo;
	
	private JPanel TotalCardPanel;
	private JScrollPane CardScrollPane;
	private JLabel TotalCardLabel;
	private static final int CardListWidth = 100;
	
	private JLabel SkillLabel;
	private ConcreteCard[] SkillCards;
	private ConcreteCard[] TotalCards;
	private ArrayList<String> SkillCardList;
	private ArrayList<String> TotalCardList;
	
	private CardDrag Drag;
	
	public CharacterProfile()
	{
		this.setSize(1000,700);
		//this.setBackground(Color.blue);
		this.setLayout(null);
		
		background = new JLabel(new ImageIcon("Images/Other/Hacker_large.png"));
		background.setBounds(0,0,1000,700);
		Logo = new JLabel(new ImageIcon("Images/Other/HackerLogo.png"));
		Logo.setBounds(400,350,530,150);
		Logo.addMouseListener(new LogoListener());
		
		TotalCardLabel = new JLabel("Skill List:");
		TotalCardLabel.setFont(TotalCardLabel.getFont().deriveFont((float)20.0));
		TotalCardLabel.setForeground(Color.white);
		TotalCardLabel.setBounds(380,460,100,40);
		TotalCardLabel.setVisible(false);
			
		TotalCardPanel = new JPanel();
		TotalCardPanel.setBackground(Color.black);
		TotalCardPanel.setBorder(null);
		TotalCardPanel.setPreferredSize(new Dimension(CardListWidth, 150));
		TotalCardPanel.setMinimumSize(new Dimension(CardListWidth, 150));
		TotalCardPanel.setMaximumSize(new Dimension(CardListWidth, 150));
		TotalCardPanel.setLayout(new GridLayout(1,0));
		TotalCardPanel.setLocation(400, 450);
		
		SkillLabel = new JLabel("Skill:");
		SkillLabel.setFont(SkillLabel.getFont().deriveFont((float)20.0));
		SkillLabel.setForeground(Color.white);
		SkillLabel.setBounds(380,260,100,40);
		SkillLabel.setVisible(false);
		SkillCards = new ConcreteCard[4];
		SkillCards[0] = new ConcreteCard();
		SkillCards[1] = new ConcreteCard();
		SkillCards[2] = new ConcreteCard();
		SkillCards[3] = new ConcreteCard();
		newCard(SkillCards[0], 400,300);
		newCard(SkillCards[1], 550,300);
		newCard(SkillCards[2], 700,300);
		newCard(SkillCards[3], 850,300);
		
		CardScrollPane = new JScrollPane(TotalCardPanel);
		CardScrollPane.setBackground(Color.black);
		CardScrollPane.setBorder(null);
		CardScrollPane.setBounds(400, 500, 500, 170);
		
		CardScrollPane.setVisible(false);
		SkillCards[0].setVisible(false);
		SkillCards[1].setVisible(false);
		SkillCards[2].setVisible(false);
		SkillCards[3].setVisible(false);
		
		Drag = new CardDrag(this, TotalCardPanel);
		TotalCardPanel.addMouseListener(Drag);
		TotalCardPanel.addMouseMotionListener(Drag);
		this.addMouseListener(Drag);
		this.addMouseMotionListener(Drag);
		
		this.add(CardScrollPane, JLayeredPane.POPUP_LAYER);
		this.add(SkillLabel, JLayeredPane.DEFAULT_LAYER);
		this.add(TotalCardLabel, JLayeredPane.DEFAULT_LAYER);
		this.add(Logo, JLayeredPane.DEFAULT_LAYER);
		this.add(background, JLayeredPane.DEFAULT_LAYER);
	}
	public CharacterProfile(int x)
	{
		this();
		loadSkillCard("cards.TinyAttackSkillCard", "cards.StabSkillCard", "cards.WizardFireSkillCard", "cards.LionPunchSkillCard");
		loadCardList("cards.SwordSkillCard","cards.TinyAttackSkillCard", "cards.StabSkillCard", "cards.WizardFireSkillCard", "cards.HealthSkillCard", "cards.LionPunchSkillCard", "cards.TornadoSkillCard");
		MakeEquipSkill();
		MakeCardList();
		repaint();
	}
	public ArrayList<String> getCharacterSkill()
	{
		return SkillCardList;
	}
	public ArrayList<String> getTotalSkill()
	{
		return TotalCardList;
	}
	public void newCard(ConcreteCard card, int X, int Y)
	{
		card.setBounds(X, Y, 100, 150);
		card.copy(new cards.CentaurSkeletonCard());
		this.add(card, JLayeredPane.POPUP_LAYER);
	}
	public void loadSkillCard(String... CardName)
	{
		if(CardName.length <= 4)
		{
			SkillCardList = new ArrayList<String>();
			for(int i=0; i<CardName.length; i++)
				SkillCardList.add(CardName[i]);
		}
	}
	public void loadCardList(String... CardName)
	{
		TotalCardList = new ArrayList<String>();
		for(int i=0; i<CardName.length; i++)
			TotalCardList.add(CardName[i]);
	}
	public void MakeEquipSkill()
	{
		Iterator<String> CardIterator = SkillCardList.iterator();
		int i = 0;
		SkillCards[i].copy(new HellGateCard());
		while(CardIterator.hasNext())
		{
			try{
				
				SkillCards[i].copy( (Card)(Class.forName((String)CardIterator.next()).newInstance()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}i++;
		}
	}
	public void MakeCardList()
	{
		TotalCards = new ConcreteCard[TotalCardList.size()];
		Iterator<String> CardIterator = TotalCardList.iterator();
		int i = 0;
		while(CardIterator.hasNext())
		{
			try{
				TotalCards[i] = new ConcreteCard();
				TotalCards[i].copy( (Card)(Class.forName((String)CardIterator.next()).newInstance()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}i++;
		}
		TotalCardPanel.setPreferredSize(new Dimension(TotalCardList.size()*CardListWidth, 150));
		TotalCardPanel.setMinimumSize(new Dimension(TotalCardList.size()*CardListWidth, 150));
		TotalCardPanel.setMaximumSize(new Dimension(TotalCardList.size()*CardListWidth, 150));
		for(int k=0; k<i; k++)
			TotalCardPanel.add(TotalCards[k]);
	}
	public void WriteFile(BufferedWriter writer)
	{
		try{
			writer.write("CharacterProfile");
			writer.newLine();
			
			// total cards
			writer.write("TotalCardList");
			writer.newLine();
			writer.write(new Integer(TotalCardList.size()).toString());
			writer.newLine();
			Iterator<String> CardIterator = TotalCardList.iterator();
			while(CardIterator.hasNext())
			{
				try{
					String X = (String)CardIterator.next();
					writer.write(X);
					writer.newLine();
				}catch(Exception e){}
			}
			
			// skill
			writer.write("SkillCardList");
			writer.newLine();
			writer.write(new Integer(SkillCardList.size()).toString());
			writer.newLine();
			CardIterator = SkillCardList.iterator();
			while(CardIterator.hasNext())
			{
				try{
					String X = (String)CardIterator.next();
					writer.write(X);
					writer.newLine();
				}catch(Exception e){}
			}
			
		}catch(Exception E){}
	}
	public void LoadFromFile(BufferedReader reader)
	{
		try{
			String line = null;
			boolean TotalCardsLoad = false;
			boolean SkillCardLoad = false;
			while(TotalCardsLoad == false || SkillCardLoad == false)
			{
				line = reader.readLine();
				if(line.equals("TotalCardList"))
				{
					int CardsListNumber = Integer.valueOf(reader.readLine());
					if(TotalCardList != null)
					{
						try{
							for(int i=0; i<TotalCardList.size(); i++)
								TotalCardPanel.remove(TotalCards[i]);
						}catch(Exception e){}
						TotalCardList.clear();
					}
					String[] CardListLoadString = new String[CardsListNumber];
					for(int i=0; i<CardsListNumber; i++)
						CardListLoadString[i] = reader.readLine();
					loadCardList(CardListLoadString);
					MakeCardList();
					TotalCardsLoad = true;
				}else if(line.equals("SkillCardList"))
				{
					int CardsListNumber = Integer.valueOf(reader.readLine());
					if(SkillCardList != null)
					{
						try{
							for(int i=0; i<SkillCardList.size(); i++)
								//SkillCards[i].copy(new emptyCard());
								;
						}catch(Exception e){}
						SkillCardList.clear();
					}
					String[] CardListLoadString = new String[CardsListNumber];
					for(int i=0; i<CardsListNumber; i++)
						CardListLoadString[i] = reader.readLine();
					loadSkillCard(CardListLoadString);
					SkillCardLoad = true;
				}
			}
		}catch(Exception e){}
		MakeEquipSkill();
	}
	public class LogoListener extends MouseAdapter{
		
		private boolean OnOff;
		public  LogoListener()
		{
			OnOff = false;	
		}
		public void mouseClicked(MouseEvent e)
		{
			if(OnOff == false)
			{
				Logo.setLocation(400,100);
				TotalCardLabel.setVisible(true);
				CardScrollPane.setVisible(true);
				SkillLabel.setVisible(true);
				SkillCards[0].setVisible(true);
				SkillCards[1].setVisible(true);
				SkillCards[2].setVisible(true);
				SkillCards[3].setVisible(true);
				OnOff = true;
			}else
			{
				Logo.setLocation(400,350);
				TotalCardLabel.setVisible(false);
				CardScrollPane.setVisible(false);
				SkillLabel.setVisible(false);
				SkillCards[0].setVisible(false);
				SkillCards[1].setVisible(false);
				SkillCards[2].setVisible(false);
				SkillCards[3].setVisible(false);
				OnOff = false;
				
			}
		}
	}
	public class CardDrag extends MouseAdapter implements MouseListener, MouseMotionListener
	{
		private JLayeredPane MainPanel;
		private JPanel CardList;
		private Component DragObjectParent;
		private Component TargetObjectParent;
		private ConcreteCard MoveItem;
		private ConcreteCard DragCard;
		private ConcreteCard TargetCard;
		private int MainPanelAdjustX;
		private int MainPanelAdjustY;
		
		public CardDrag(JLayeredPane MainPanel, JPanel CardList)
		{
			this.MainPanel = MainPanel;
			this.CardList = CardList;
			
			MoveItem = new ConcreteCard();
			MoveItem.setSize(100, 150);
			MoveItem.setLocation(100, 100);
			MoveItem.setVisible(false);
			MainPanel.add(MoveItem, JLayeredPane.DRAG_LAYER);
		}
		public void mousePressed(MouseEvent e)
		{
			Component c = null;
			DragCard = null;
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				MainPanelAdjustX = MainPanel.getLocation().x;
				MainPanelAdjustY = MainPanel.getLocation().y;
				c = MainPanel.findComponentAt(	e.getLocationOnScreen().x-MainPanelAdjustX,
												e.getLocationOnScreen().y-MainPanelAdjustY);
				
				if(c instanceof JLabel && c.getParent() instanceof ConcreteCard)
				{
					DragCard = (ConcreteCard)c.getParent();
					DragCard.setVisible(false);
					DragObjectParent = DragCard.getParent();
					MoveItem.copy(DragCard.getCard());
					MoveItem.setVisible(true);
					MoveItem.setLocation(e.getLocationOnScreen().x-50-MainPanelAdjustX, e.getLocationOnScreen().y-75-MainPanelAdjustY);
				}
			}
		}
		public void mouseReleased(MouseEvent e)
		{
			if(DragCard == null)
				return;
			
			// get target
			MoveItem.setVisible(false);
			Component c = MainPanel.findComponentAt(e.getLocationOnScreen().x-MainPanelAdjustX, e.getLocationOnScreen().y-MainPanelAdjustY);
			if(c instanceof JLabel && c.getParent() instanceof ConcreteCard)
			{
				TargetCard = (ConcreteCard)(c.getParent());
				TargetObjectParent = TargetCard.getParent();
			}else
			{
				MoveItem.setVisible(false);
				DragCard.setVisible(true);
				return;
			}
			
			// swap
			DragCard.setVisible(true);
			if(DragObjectParent != TargetObjectParent)
			{
				if(DragObjectParent == CardList)
				{
					if(TargetCard == SkillCards[0])
					{
						SkillCardList.set(0, DragCard.getCard().getClass().getName());
						SkillCards[0].copy(DragCard.getCard());
					}
					if(TargetCard == SkillCards[1])
					{
						SkillCardList.set(1, DragCard.getCard().getClass().getName());
						SkillCards[1].copy(DragCard.getCard());
					}
					if(TargetCard == SkillCards[2])
					{
						SkillCardList.set(2, DragCard.getCard().getClass().getName());
						SkillCards[2].copy(DragCard.getCard());
					}
					if(TargetCard == SkillCards[3])
					{
						SkillCardList.set(3, DragCard.getCard().getClass().getName());
						SkillCards[3].copy(DragCard.getCard());
					}
					
					MainPanel.repaint();
				}else if(DragObjectParent == MainPanel)
				{
					if(DragCard == SkillCards[0])
					{
						SkillCardList.set(0, TargetCard.getCard().getClass().getName());
						SkillCards[0].copy(TargetCard.getCard());
					}
					if(DragCard == SkillCards[1])
					{
						SkillCardList.set(1, TargetCard.getCard().getClass().getName());
						SkillCards[1].copy(TargetCard.getCard());
					}
					if(DragCard == SkillCards[2])
					{
						SkillCardList.set(2, TargetCard.getCard().getClass().getName());
						SkillCards[2].copy(TargetCard.getCard());
					}
					if(DragCard == SkillCards[3])
					{
						SkillCardList.set(3, TargetCard.getCard().getClass().getName());
						SkillCards[3].copy(TargetCard.getCard());
					}
					
					MainPanel.repaint();
				}
			}
		}
		public void mouseDragged(MouseEvent e)
		{
			if(DragCard != null)
			{
				MoveItem.setLocation(e.getLocationOnScreen().x-50-MainPanelAdjustX, e.getLocationOnScreen().y-75-MainPanelAdjustY);
			}
		}
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				Component c = null;
				c = MainPanel.findComponentAt(	e.getLocationOnScreen().x-MainPanel.getLocation().x,
												e.getLocationOnScreen().y-MainPanel.getLocation().y);
				
				if(c instanceof JLabel && c.getParent() instanceof ConcreteCard)
				{
					ConcreteCard RightClickedCard = (ConcreteCard)c.getParent();
					RightClickedCard.MonsterDetailChange();
				}
			}
		}
		public void mouseMoved(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
	}
}