package javahacker;

import cards.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DeckBuild{

	private static final int MAX_USER_CARD = 40;
	private static final int MAX_TOTAL_CARD = 100;
	private static final int CardListHeight = 150;
	private JLayeredPane MainDeckPanel;
	private JPanel UserDeckPanel;
	private JPanel CardListPanel;
	private JScrollPane UserScrollPane;
	private JScrollPane TotalScrollPane;
	private CardDrag dragaction;
	private JLabel UserDeckLabel;
	private JLabel CardListLabel;
	
	private ArrayList<Card> userCardList;
	private ArrayList<Card> totalCardList;
	
	
	private ConcreteCard[] totalCards;
	private ConcreteCard[] userCards;
	
	public DeckBuild()
	{
		totalCards = new ConcreteCard[MAX_TOTAL_CARD];
		for(int i=0; i<MAX_TOTAL_CARD; i++)
		{
			totalCards[i] = new ConcreteCard();
			totalCards[i].setSize(200,200);
		}
			
		userCards = new ConcreteCard[MAX_USER_CARD];
		for(int i=0; i<MAX_USER_CARD; i++)
		{
			userCards[i] = new ConcreteCard();
			userCards[i].setSize(200,200);
		}
	}
	public DeckBuild(int x)
	{
		if(x == 0)
		{
			totalCards = new ConcreteCard[MAX_TOTAL_CARD];
			for(int i=0; i<MAX_TOTAL_CARD; i++)
			{
				totalCards[i] = new ConcreteCard();
				totalCards[i].setSize(200,200);
			}
				
			userCards = new ConcreteCard[MAX_USER_CARD];
			for(int i=0; i<MAX_USER_CARD; i++)
			{
				userCards[i] = new ConcreteCard();
				userCards[i].setSize(200,200);
			}
			
			loadCardList(	"cards.SlimeCard", "cards.TinyAttackSkillCard", "cards.TinyAttackSkillCard", 
							"cards.SwordSkillCard", "cards.SwordSkillCard" );
			loadUserCard(	"cards.AncientGateCard", "cards.AncientGateCard", "cards.AncientGateCard", "cards.AncientGateCard",
								"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", 
								"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard",
								"cards.TinyAttackSkillCard", "cards.TinyAttackSkillCard", "cards.TinyAttackSkillCard",
								"cards.SwordSkillCard", "cards.SwordSkillCard");
			Build();
			getMainPanel().setBounds(0,0,1000,700);
		}
		
	}
	public void Build()
	{
		MainDeckPanel = new JLayeredPane();
		UserDeckPanel = new JPanel();
		CardListPanel = new JPanel();
		UserDeckLabel = new JLabel("UserDeck:");
		CardListLabel = new JLabel("CardList:");
		dragaction = new CardDrag( MainDeckPanel, CardListPanel, UserDeckPanel);
		
		MainDeckPanel.setSize(1000,700);
		UserDeckLabel.setBounds(20, 20, 100, 20);
		CardListLabel.setBounds(850, 20, 100, 20);
		
		CardListPanel.setPreferredSize(new Dimension(100,CardListHeight));
		CardListPanel.setMinimumSize(new Dimension(100,CardListHeight));
		CardListPanel.setMaximumSize(new Dimension(100,CardListHeight));
		
		UserDeckPanel.setPreferredSize(new Dimension(1200,600));
		UserDeckPanel.setMinimumSize(new Dimension(1200,600));
		UserDeckPanel.setMaximumSize(new Dimension(1200,600));
		
		MainDeckPanel.setLayout(null);
		UserDeckPanel.setLayout(new GridLayout(4,0));
		CardListPanel.setLayout(new GridLayout(0,1));
		
		MakeUserList();
		MakeCardList();
		
		
		UserScrollPane = new JScrollPane(UserDeckPanel);
		TotalScrollPane = new JScrollPane(CardListPanel);
		
		UserScrollPane.setBounds(20,50,800,650);
		TotalScrollPane.setBounds(850,50,150,650);
		
		UserScrollPane.setSize(new Dimension(800,650));
		TotalScrollPane.setSize(new Dimension(120,650));
		
		TotalScrollPane.addMouseListener(dragaction);
		TotalScrollPane.addMouseMotionListener(dragaction);
		UserScrollPane.addMouseListener(dragaction);
		UserScrollPane.addMouseMotionListener(dragaction);
		
		MainDeckPanel.add(UserDeckLabel, JLayeredPane.DEFAULT_LAYER);
		MainDeckPanel.add(CardListLabel, JLayeredPane.DEFAULT_LAYER);
		MainDeckPanel.add(UserScrollPane, JLayeredPane.DEFAULT_LAYER);
		MainDeckPanel.add(TotalScrollPane, JLayeredPane.DEFAULT_LAYER);
	}
	public void MakeCardList()
	{
		CardListPanel.removeAll();
		Iterator<Card> CardIterator = totalCardList.iterator();
		int i = 0;
		while(CardIterator.hasNext())
		{
			try{
				totalCards[i].copy((Card)CardIterator.next());
			}catch(Exception e)
			{
				System.out.println(e.toString() + "Error on MakeCardList");
			}i++;
		}
		CardListPanel.setPreferredSize(new Dimension(100,CardListHeight*totalCardList.size()));
		CardListPanel.setMinimumSize(new Dimension(100,CardListHeight*totalCardList.size()));
		CardListPanel.setMaximumSize(new Dimension(100,CardListHeight*totalCardList.size()));
		for(int k=0; k<i; k++)
			CardListPanel.add(totalCards[k]);
	}
	public void MakeUserList()
	{
		Iterator<Card> CardIterator = userCardList.iterator();
		int i = 0;
		while(CardIterator.hasNext())
		{
			try{
			userCards[i].copy((Card)CardIterator.next());
			}catch(Exception e)
			{
				System.out.println(e.toString() + "Error on MakeUserList");
			}
			i++;
		}
		for(int k=0; k<i; k++)
			UserDeckPanel.add(userCards[k]);
	}
	public void repaintList()
	{
		Iterator<Card> CardIterator = totalCardList.iterator();
		int i=0;
		while(CardIterator.hasNext())
		{
			try{
				Card X = (Card)CardIterator.next();
				if(! (totalCards[i].getCard()==X))
					totalCards[i].copy(X);
			}catch(Exception e)
			{
				e.printStackTrace();
				if(CardIterator.hasNext())
					CardIterator.next();
			}i++;
		}
		i=0;
		CardIterator = userCardList.iterator();
		while(CardIterator.hasNext())
		{
			try{
				Card X = (Card)CardIterator.next();
				if(!(userCards[i].getCard()==X))
					userCards[i].copy(X);
			}catch(Exception e)
			{
				e.printStackTrace();
				if(CardIterator.hasNext())
					CardIterator.next();
			}i++;
		}
	}
	public void loadCardList(String... CardName)
	{
		totalCardList = new ArrayList<Card>();
		try{
			for(int i=0; i<CardName.length; i++)
				totalCardList.add((Card)(Class.forName(CardName[i]).newInstance()));
		}catch(Exception e){e.printStackTrace();}
	}
	public void loadUserCard(String... CardName)
	{
		userCardList = new ArrayList<Card>();
		try{
		for(int i=0; i<CardName.length; i++)
			userCardList.add((Card)(Class.forName(CardName[i]).newInstance()));
		}catch(Exception e){e.printStackTrace();}
	}
	public ArrayList<Card> getUserDeck()
	{
		return userCardList;
	}
	public ArrayList<Card> getTotalCardList()
	{
		return totalCardList;
	}
	public void WriteFile(BufferedWriter writer)
	{
		try{
			writer.write("DeckBuild");
			writer.newLine();
			
			writer.write("CardList");
			writer.newLine();
			writer.write(new Integer(totalCardList.size()).toString());
			writer.newLine();
			Iterator<Card> CardIterator = totalCardList.iterator();
			while(CardIterator.hasNext())
			{
				try{
					String X = CardIterator.next().getClass().getName();
					writer.write(X);
					writer.newLine();
				}catch(Exception e){}
			}
			
			writer.write("UserCard");
			writer.newLine();
			writer.write(new Integer(userCardList.size()).toString());
			writer.newLine();
			CardIterator = userCardList.iterator();
			while(CardIterator.hasNext())
			{
				try{
					String X = CardIterator.next().getClass().getName();
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
			boolean CardsLoad = false;
			boolean UserCardLoad = false;
			while(CardsLoad == false || UserCardLoad == false)
			{
				line = reader.readLine();
				if(line.equals("CardList"))
				{
					int CardsListNumber = Integer.valueOf(reader.readLine());
					if(totalCardList != null)
					{
						try{
							for(int i=0; i<totalCardList.size(); i++)
								CardListPanel.remove(totalCards[i]);
						}catch(Exception e){}
						totalCardList.clear();
					}
					String[] CardListLoadString = new String[CardsListNumber];
					for(int i=0; i<CardsListNumber; i++)
						CardListLoadString[i] = reader.readLine();
					loadCardList(CardListLoadString);
					MakeCardList();
					CardsLoad = true;
				}else if(line.equals("UserCard"))
				{
					int CardsListNumber = Integer.valueOf(reader.readLine());
					if(userCardList != null)
					{
						try{
							for(int i=0; i<userCardList.size(); i++)
								UserDeckPanel.remove(userCards[i]);
						}catch(Exception e){}
						userCardList.clear();
					}
					String[] CardListLoadString = new String[CardsListNumber];
					for(int i=0; i<CardsListNumber; i++)
						CardListLoadString[i] = reader.readLine();
					loadUserCard(CardListLoadString);
					MakeUserList();
					UserCardLoad = true;
				}
			}
		}catch(Exception e){}
	}
	public JLayeredPane getMainPanel()
	{
		return MainDeckPanel;
	}
	public static void main(String[] args)
	{
		JFrame mainframe = new JFrame("test");
		mainframe.setSize(1200,800);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setLayout(null);
		
		DeckBuild Deck = new DeckBuild();
		
		Deck.loadCardList(	"cards.CentaurSkeletonCard", "cards.CentaurSkeletonCard", "cards.CentaurSkeletonCard",
							"cards.CentaurSkeletonCard","cards.CentaurSkeletonCard","cards.CentaurSkeletonCard",
							"cards.CentaurSkeletonCard","cards.CentaurSkeletonCard","cards.CentaurSkeletonCard");
		Deck.loadUserCard(	"cards.HellGateCard", "cards.HellGateCard", "cards.HellGateCard", "cards.HellGateCard",
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", 
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", 
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard",
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard",
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard",
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard",
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard",
							"cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard", "cards.SlimeCard");
		Deck.Build();
		Deck.getMainPanel().setBounds(0,0,1000,700);
		
		mainframe.add(Deck.getMainPanel());
		mainframe.setVisible(true);
	}
	public class CardDrag extends MouseAdapter implements MouseListener, MouseMotionListener
	{
		private JLayeredPane MainPanel;
		private JPanel CardList;
		private JPanel UserDeck;
		private Component DragObjectParent;
		private Component TargetObjectParent;
		private ConcreteCard MoveItem;
		private ConcreteCard DragCard;
		private ConcreteCard TargetCard;
		private int MainPanelAdjustX;
		private int MainPanelAdjustY;
		
		public CardDrag(JLayeredPane MainPanel, JPanel CardList, JPanel UserDeck)
		{
			this.MainPanel = MainPanel;
			this.CardList = CardList;
			this.UserDeck = UserDeck;
			
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
			int dragIndex, targetIndex;
			if(DragObjectParent == CardList)
				dragIndex = totalCardList.indexOf(DragCard.getCard());
			else
				dragIndex = userCardList.indexOf(DragCard.getCard());
			if(TargetObjectParent == CardList)
				targetIndex = totalCardList.indexOf(TargetCard.getCard());
			else
				targetIndex = userCardList.indexOf(TargetCard.getCard());
			
			
			if(DragObjectParent != TargetObjectParent)
			{
				if(DragObjectParent == CardList)
				{
					//int dragIndex = totalCardList.indexOf(DragCard.getCard());
					//int targetIndex = userCardList.indexOf(TargetCard.getCard());
					
					totalCardList.set(dragIndex, TargetCard.getCard());
					userCardList.set(targetIndex, DragCard.getCard());
					
					//totalCardList.remove(DragCard.getCard().getClass().getName());
					//userCardList.remove(TargetCard.getCard().getClass().getName());
					//totalCardList.add(TargetCard.getCard().getClass().getName());
					//userCardList.add(DragCard.getCard().getClass().getName());
					//Collections.sort(totalCardList);
					//Collections.sort(userCardList);
					
					//repaintList();
					//MainPanel.repaint();
				}else if(DragObjectParent == UserDeck)
				{
					//int dragIndex = userCardList.indexOf(DragCard.getCard());
					//int targetIndex = totalCardList.indexOf(TargetCard.getCard());
					
					userCardList.set(dragIndex, TargetCard.getCard());
					totalCardList.set(targetIndex, DragCard.getCard());
					//totalCardList.remove(TargetCard.getCard().getClass().getName());
					//userCardList.remove(DragCard.getCard().getClass().getName());
					//totalCardList.add(DragCard.getCard().getClass().getName());
					//userCardList.add(TargetCard.getCard().getClass().getName());
					//Collections.sort(totalCardList);
					//Collections.sort(userCardList);
					
					//repaintList();
					//MainPanel.repaint();
				}
			}else
			{
				if(DragObjectParent == CardList)
				{
					totalCardList.set(dragIndex, TargetCard.getCard());
					totalCardList.set(targetIndex, DragCard.getCard());
				}else
				{
					userCardList.set(dragIndex, TargetCard.getCard());
					userCardList.set(targetIndex, DragCard.getCard());
				}
			}
			repaintList();
			MainPanel.repaint();
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
