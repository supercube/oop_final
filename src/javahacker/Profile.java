package javahacker;

import cards.*;
import java.util.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import mainframe.*;

public class Profile extends JPanel{
	
	// Status
	Status status;
	
	// buttons
	private JButton ProfileButton;
	private JButton DeckButton;
	private JButton QuestButton;
	private JButton MapButton;
	private JButton SaveButton;
	private JButton LoadButton;
	private JButton ExitButton;
	// panels
	private CharacterProfile ProfilePanel;
	private JLayeredPane DeckPanel;
	private Quest QuestPanel;
	private WorldMap_s1 MapPanel;
	private JPanel SavePanel;
	private JPanel LoadPanel;
	private JPanel ExitPanel;
	// button icon
	private ImageIcon EnterIcon;
	private ImageIcon NormalIcon;
	// button font
	private Font font;
	private Font NormalFont;
	private Font SelectFont;
	private Font labelFont;
	// main frame
	private MainFrame mainFrame;
	private BGMPlayer player;
	private StartPanel startPanel;
	private MainPanel mainPanel;
	// other
	private DeckBuild deck;
	
	public Profile( MainFrame _m, BGMPlayer _p, StartPanel _s, MainPanel _m2 )
	{
		this.setSize(1200,700);
		this.setBounds(0,0,1200,700);
		this.setLayout(null);
		status = Status.PROFILE;
		
		// link mainframe and bgmplayer
		mainFrame = _m;
		player = _p;
		startPanel = _s;
		mainPanel = _m2;
		// new button
		ProfileButton = new JButton("Profile");
		DeckButton = new JButton("Deck");
		QuestButton = new JButton("Quest");
		MapButton = new JButton("Map");
		SaveButton = new JButton("Save");
		LoadButton = new JButton("Load");
		ExitButton = new JButton("Exit");
		// add icon
		NormalIcon = new TransparentIcon("Images/Other/MenuButton.png", Color.white).getIcon();
		EnterIcon = new TransparentIcon("Images/Other/MenuButtonEnter.png", Color.white).getIcon();
		
		// add mouse listener
		ProfileButton.addMouseListener(new StatusChange(Status.PROFILE, ProfileButton));
		DeckButton.addMouseListener(new StatusChange(Status.DECK, DeckButton));
		QuestButton.addMouseListener(new StatusChange(Status.QUEST, QuestButton));
		MapButton.addMouseListener(new StatusChange(Status.MAP, MapButton));
		SaveButton.addMouseListener(new StatusChange(Status.SAVE, SaveButton));
		LoadButton.addMouseListener(new StatusChange(Status.LOAD, LoadButton));
		ExitButton.addMouseListener(new StatusChange(Status.EXIT, ExitButton));
		// new panel
		ProfilePanel = new CharacterProfile(0);
		MapPanel = new WorldMap_s1(this);
		QuestPanel = new Quest();
		deck = new DeckBuild(0);
		DeckPanel = deck.getMainPanel();
		LoadPanel = new Load(this, deck, QuestPanel, ProfilePanel);
		SavePanel = new Save(deck, QuestPanel, ProfilePanel);
		// add button
		ProfileButton.setBounds(25,50,150,60);
		DeckButton.setBounds(25,130,150,60);
		QuestButton.setBounds(25,210,150,60);
		MapButton.setBounds(25,290,150,60);
		SaveButton.setBounds(25,370,150,60);
		LoadButton.setBounds(25,450,150,60);
		ExitButton.setBounds(25,600,150,60);
		setForn(ProfileButton, DeckButton, QuestButton, MapButton, SaveButton, LoadButton, ExitButton);
		AddButtons(ProfileButton, DeckButton, QuestButton, MapButton, SaveButton, LoadButton, ExitButton);
		
		// add panel
		ProfilePanel.setBounds(200,0,1000,700);
		this.add(ProfilePanel);
		MapPanel.setBounds(200,0,1000,700);
		this.add(MapPanel);
		QuestPanel.setBounds(200,0,1000,700);
		this.add(QuestPanel);
		DeckPanel.setBounds(200, 0, 1000, 700);
		this.add(DeckPanel);
		LoadPanel.setBounds(200,0,1000,700);
		this.add(LoadPanel);
		SavePanel.setBounds(200,0,1000,700);
		this.add(SavePanel);
		MapPanel.setVisible(false);
		DeckPanel.setVisible(false);
		QuestPanel.setVisible(false);
		LoadPanel.setVisible(false);
		SavePanel.setVisible(false);
		
		ChangePanelStatus(true);
		
		
		MapReset();
	}
	
	public Profile()
	{
		this.setSize(1200,700);
		this.setBounds(0,0,1200,700);
		this.setLayout(null);
		status = Status.PROFILE;
		// new button
		ProfileButton = new JButton("Profile");
		DeckButton = new JButton("Deck");
		QuestButton = new JButton("Quest");
		MapButton = new JButton("Map");
		SaveButton = new JButton("Save");
		LoadButton = new JButton("Load");
		ExitButton = new JButton("Exit");
		// add icon
		NormalIcon = new TransparentIcon("Images/Other/MenuButton.png", Color.white).getIcon();
		EnterIcon = new TransparentIcon("Images/Other/MenuButtonEnter.png", Color.white).getIcon();
		
		// add mouse listener
		ProfileButton.addMouseListener(new StatusChange(Status.PROFILE, ProfileButton));
		DeckButton.addMouseListener(new StatusChange(Status.DECK, DeckButton));
		QuestButton.addMouseListener(new StatusChange(Status.QUEST, QuestButton));
		MapButton.addMouseListener(new StatusChange(Status.MAP, MapButton));
		SaveButton.addMouseListener(new StatusChange(Status.SAVE, SaveButton));
		LoadButton.addMouseListener(new StatusChange(Status.LOAD, LoadButton));
		ExitButton.addMouseListener(new StatusChange(Status.EXIT, ExitButton));
		// new panel
		ProfilePanel = new CharacterProfile(0);
		MapPanel = new WorldMap_s1(this);
		QuestPanel = new Quest();
		deck = new DeckBuild(0);
		DeckPanel = deck.getMainPanel();
		LoadPanel = new Load(this, deck, QuestPanel, ProfilePanel);
		SavePanel = new Save(deck, QuestPanel, ProfilePanel);
		// add button
		ProfileButton.setBounds(25,50,150,60);
		DeckButton.setBounds(25,130,150,60);
		QuestButton.setBounds(25,210,150,60);
		MapButton.setBounds(25,290,150,60);
		SaveButton.setBounds(25,370,150,60);
		LoadButton.setBounds(25,450,150,60);
		ExitButton.setBounds(25,600,150,60);
		setForn(ProfileButton, DeckButton, QuestButton, MapButton, SaveButton, LoadButton, ExitButton);
		AddButtons(ProfileButton, DeckButton, QuestButton, MapButton, SaveButton, LoadButton, ExitButton);
		
		// add panel
		ProfilePanel.setBounds(200,0,1000,700);
		this.add(ProfilePanel);
		MapPanel.setBounds(200,0,1000,700);
		this.add(MapPanel);
		QuestPanel.setBounds(200,0,1000,700);
		this.add(QuestPanel);
		DeckPanel.setBounds(200, 0, 1000, 700);
		this.add(DeckPanel);
		LoadPanel.setBounds(200,0,1000,700);
		this.add(LoadPanel);
		SavePanel.setBounds(200,0,1000,700);
		this.add(SavePanel);
		MapPanel.setVisible(false);
		DeckPanel.setVisible(false);
		QuestPanel.setVisible(false);
		LoadPanel.setVisible(false);
		SavePanel.setVisible(false);
		
		ChangePanelStatus(true);
		
		
		MapReset();
	}
	
	public class StatusChange extends MouseAdapter{
		private Status type;
		private JButton button;
		public  StatusChange(Status inputstatus, JButton button)
		{
			type = inputstatus;
			this.button = button;
		}
		
		public void mouseClicked(MouseEvent e)
		{
			ChangePanelStatus(false);
			status = type;
			ChangePanelStatus(true);
		}
		public void mouseEntered(MouseEvent e)
		{
			button.setIcon(EnterIcon);
		}
		public void mouseExited(MouseEvent e)
		{
			button.setIcon(NormalIcon);
		}
	}
	public void AddButtons(JButton... buttons)
	{
		for(int i=0; i<buttons.length; i++)
		{
			buttons[i].setIcon(NormalIcon);
			buttons[i].setVerticalTextPosition(JLabel.CENTER);
			buttons[i].setHorizontalTextPosition(JLabel.CENTER);
			buttons[i].setBorder(null);
			buttons[i].setContentAreaFilled(false);
			this.add(buttons[i]);
		}
	}
	public void ChangePanelStatus(boolean OnOff)
	{
		// font
		if(OnOff)
			labelFont = SelectFont;
		else
			labelFont = NormalFont;
		switch(status)
		{
			case PROFILE:
				ProfilePanel.setVisible(OnOff);
				ProfileButton.setFont(labelFont);
				break;
			case DECK:
				DeckPanel.setVisible(OnOff);
				DeckButton.setFont(labelFont);
				break;
			case QUEST:
				QuestPanel.setVisible(OnOff);
				QuestButton.setFont(labelFont);
				break;
			case MAP:
				MapPanel.setVisible(OnOff);
				MapButton.setFont(labelFont);
				break;
			case SAVE:
				SavePanel.setVisible(OnOff);
				SaveButton.setFont(labelFont);
				break;
			case LOAD:
				LoadPanel.setVisible(OnOff);
				LoadButton.setFont(labelFont);
				break;
			case EXIT:
				//ExitPanel.setVisible(OnOff);
				//ExitButton.setFont(labelFont);
				mainFrame.remove( this );
				mainFrame.setVisible( false );
				player.stop();
				mainFrame = new MainFrame();
				//this.setVisible( false );
				//mainFrame.add( startPanel );
				//mainFrame.repaint();
				//mainPanel.repaint();
				//player.next();
				break;
			default:
				break;
		}
	}
	public void setForn(JButton... buttons)
	{
		font = buttons[0].getFont();
		NormalFont = font.deriveFont((float)20.0);
		SelectFont = font.deriveFont((float)40.0);
		
		for(int i=0; i<buttons.length; i++)
		{
			buttons[i].setForeground(Color.white);
			buttons[i].setFont(NormalFont);
		}
	}
	public static void main(String[] args)
	{
		JFrame mainframe = new JFrame("test");
		mainframe.setSize(1300,800);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setLayout(null);
		
		
		Profile p = new Profile();
		
		
		mainframe.add(p);
		mainframe.setVisible(true);
	}
	public static enum Status
	{
		PROFILE, DECK, QUEST, MAP, SAVE, LOAD, EXIT;
	}
	// link Panels
	public void MapReset()
	{
		MapPanel.resetQuest(QuestPanel.getQuestList());
	}
	public void AddQuest(QuestItem item)
	{
		QuestPanel.addQuest(item);
	}
	public void battle(int node_id)
	{
		Thread BattleThread = new Battle(node_id);
		BattleThread.start();
		/*
		try{
		BattleThread.join();
		}catch(Exception e)
		{
			e.printStackTrace();
		}*/
	}
	public class Battle extends Thread
	{
		private Card[] enemy_deck;
		public Battle(int node_id){
			switch(node_id){
				case 2:
					enemy_deck = Constant.enemy_decks[1];
					break;
				case 3:
					enemy_deck = Constant.enemy_decks[2];
					break;
				default:
					enemy_deck = Constant.enemy_decks[0];
					break;
			}
		}
		public void run()
		{
			// set Battle Field event;
			ArrayList<Card> UserDeck = deck.getUserDeck();
			ArrayList<Card> total = deck.getTotalCardList();
			Iterator<Card> CardIterator = UserDeck.iterator();
			Card[] tmpuserdeck = new Card[UserDeck.size()];
			int count = 0;
			try{
				for(int i=0; i<UserDeck.size(); i++)
				{
					Card card = (Card)CardIterator.next();
					if(!(card instanceof EmptyCard)){
						tmpuserdeck[count] = card;
						count++;
					}
				}
			}catch(Exception e){e.printStackTrace();}
			Card[] userdeck = new Card[count];
			for(int i=0; i<count; i++)
			{
				userdeck[i] = tmpuserdeck[i];
			}
			LeadingRoleCard playerCard = new LeadingRoleCard();
			
			
			ArrayList<String> PlayerSkillList = ProfilePanel.getCharacterSkill();
			Iterator<String> SkillIterator = PlayerSkillList.iterator();
			try{
				for(int i=0; i<PlayerSkillList.size(); i++)
				{
					SkillCard skilcard = (SkillCard)(Class.forName((String)SkillIterator.next()).newInstance());
					playerCard.setSkill(skilcard, i); 
				}
			}catch(Exception e){e.printStackTrace();}
			
			
			ArrayList<Card> reward = new ArenaPark(userdeck, enemy_deck, playerCard).fight();
			for(Card c: reward){
				total.add(c);
			}
			deck.MakeCardList();
		}
	}
}


