package javahacker;

import java.io.*;
import java.util.*;
import javax.swing.*;

import cards.Card;
import cards.CentaurSkeletonCard;
import cards.HellGateCard;
import cards.LeadingRoleCard;
import cards.LionPunchSkillCard;
import cards.SlimeCard;
import cards.TinyAttackSkillCard;
import cards.WitchCard;
import cards.WizardFireSkillCard;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class WorldMap_s1 extends JLayeredPane{

	private Icon icon;
	private JLabel backgroundimg;
	private JLabel[] road;
	private MapNode[] Node;
	private int ActivateID;
	private JButton Character;
	private int previousX = 0;
	private Thread CharacterThread;
	private String imgPath = "Images/Other/network.jpg";
	private int NodesNumber = 7;
	private boolean EventTrigger;
	private Profile ParentProfile;
	
	// Event
	private Random random;
	/*
	public static void main(String[] args)
	{
		JFrame mainframe = new JFrame("test");
		mainframe.setSize(1000,700);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setLayout(null);
		
		// set Map
		WorldMap_s1 x = new WorldMap_s1();
		
		mainframe.add(x);
		mainframe.setVisible(true);			
	}*/
	
	public WorldMap_s1(Profile ParentProfile)
	{
		this.ParentProfile = ParentProfile;
		
		// character
		Character = new JButton();
		this.Character.setSize(70,70);
		icon = new ImageIcon("Images/Other/right1.png");
		this.Character.setIcon(icon);
		this.Character.setContentAreaFilled(false);
		this.Character.setBorder(null);
		this.Character.setBounds(100,100,70,70);
		this.add(this.Character, JLayeredPane.POPUP_LAYER);
		previousX = 0;
		CharacterThread = new CharacterMoving(Character);
		CharacterThread.start();
		// background
		icon = new ImageIcon(imgPath);
		backgroundimg = new JLabel(icon);
		backgroundimg.setSize(1000,700);
		add(backgroundimg, JLayeredPane.DEFAULT_LAYER);
		setSize(1000,700);
		setBounds(0,0,1000,700);
		// nodes and road
		Node = new MapNode[NodesNumber];
		
		// Event handel
		EventTrigger = false;
		random = new Random();
		
		setNodes();	
		setActivate(0);
	}
	public void resetQuest(ArrayList<QuestItem> QuestList)
	{
		Iterator<QuestItem> QuestIterator = QuestList.iterator();
		QuestItem item;
		for(int i=0; i<Node.length; i++)
			Node[i].setIcon(null);
		while(QuestIterator.hasNext())
		{
			try{
				item = QuestIterator.next();
				Node[item.getActivateNode()].setIcon((new TransparentIcon("Images/Other/EventNode.png", Color.white)).getIcon());
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void setNodes()
	{
		// addnode X, Y, size, ID, NodeType
		AddNode(160,106,1,0, 1);
		AddNode(357,356,0,1, 2);
		AddNode(467,171,0,2, 2);
		AddNode(662,179,0,3, 2);
		AddNode(552,373,1,4, 2);
		AddNode(326,607,2,5, 1);
		AddNode(961,188,2,6, 1);
		// set Neighbor ID, neighbors
		setNodeNeighbor(0, Node[1]);
		setNodeNeighbor(1, Node[0], Node[2], Node[4]);
		setNodeNeighbor(2, Node[1], Node[3]);
		setNodeNeighbor(3, Node[2], Node[4]);
		setNodeNeighbor(4, Node[1], Node[3], Node[5], Node[6]);
		setNodeNeighbor(5, Node[4]);
		setNodeNeighbor(6, Node[4]);
	}
	public void AddNode(int x, int y, int size, int NodeID, int NodeType)
	{
		Node[NodeID] = new MapNode(x, y, size, NodeID, new DoubleClick(NodeID), NodeType);
		this.add(Node[NodeID], JLayeredPane.MODAL_LAYER);
	}
	public void setNodeNeighbor(int NodeID, MapNode... nodes)
	{
		Node[NodeID].setNeighbor(nodes);
	}
	public void setActivate(int NodeID)
	{
		Node[NodeID].Activate();
		ActivateID = NodeID;
		// determind right or left
		if(Node[NodeID].getLocation().x > previousX && !((CharacterMoving)CharacterThread).getDir())
			((CharacterMoving)CharacterThread).setDir(true);
		else if(Node[NodeID].getLocation().x < previousX && ((CharacterMoving)CharacterThread).getDir())
			((CharacterMoving)CharacterThread).setDir(false);	
		Character.setBounds(Node[NodeID].getLocation().x-35, Node[NodeID].getLocation().y-50, 70,70);
	}
	public void resetNode(int NodeID)
	{
		Node[NodeID].InActivate();
		previousX = Node[NodeID].getLocation().x;
	}
	public MapNode getNode(int NodeID)
	{
		return Node[NodeID];
	}
	public class CountyEventTrigger extends JPanel{
		private int PositionX, PositionY;
		private JLabel[] Choose;
		
		public CountyEventTrigger(int x, int y)
		{
			PositionX = x;
			PositionY = y;
			if(random.nextDouble() < 0.5)
			{
				Choose = new JLabel[2];
				this.setBounds(PositionX, PositionY, 100,40);
			}else
			{
				Choose = new JLabel[3];
				Choose[2] = new JLabel("Quest");
				Choose[2].setBounds(0,40,80,20);
				Choose[2].addMouseListener(new QuestListener(this));
				this.add(Choose[2]);
				this.setBounds(PositionX, PositionY, 200,60);
			}
			Choose[0] = new JLabel("Talk");
			Choose[1] = new JLabel("Exit");
			Choose[0].setBounds(0,0,80,20);
			Choose[1].setBounds(0,20,80,20);
			Choose[0].addMouseListener(new TalkListener(Choose[0]));
			Choose[1].addMouseListener(new ExitListener(this));
			this.setLayout(null);
			this.add(Choose[0]);
			this.add(Choose[1]);
			this.setBackground(Color.white);
			
			EventTrigger = true;
		}
		public class TalkListener extends MouseAdapter{
		
			private JLabel Target;
			
			public TalkListener(JLabel target)
			{
				Target = target;
			}
			public void mouseClicked(MouseEvent e)
			{
				Target.setText("Hi!");
			}
		}
		public class ExitListener extends MouseAdapter{
			private JPanel eventPanel;
			public ExitListener(JPanel eventPanel)
			{
				this.eventPanel = eventPanel;
			}
			public void mouseClicked(MouseEvent e)
			{
				EventTrigger = false;
				eventPanel.setVisible(false);
			}
		}
		public class QuestListener extends MouseAdapter{
			private JPanel eventPanel;
			private TextArea questText;
			private int QuestPlace;
			
			public QuestListener(JPanel eventPanel)
			{
				QuestPlace = random.nextInt(4);
				QuestPlace ++;
				questText = new TextArea("Monster Defense\n at Node " + QuestPlace, 120,60, TextArea.SCROLLBARS_VERTICAL_ONLY);
				questText.setEditable(false);
				questText.setBounds(80, 0, 120,60);
				add(questText);
				this.eventPanel = eventPanel;
			}
			public void mouseClicked(MouseEvent e)
			{
				QuestData data = new QuestData(QuestPlace, "Monster Defense","", "Defense Node "+QuestPlace,"./Images/Other/QuestGrass.jpg");
				QuestItem item = new QuestItem(data);
				ParentProfile.AddQuest(item);
				ParentProfile.MapReset();
				EventTrigger = false;
				eventPanel.setVisible(false);
			}
		
		}
	}
	public class BattleFieldEventTrigger extends JPanel{
	
		private int PositionX, PositionY;
		private JLabel[] Choose;
		
		public BattleFieldEventTrigger(int x, int y)
		{
			PositionX = x;
			PositionY = y;
			Choose = new JLabel[2];
			Choose[0] = new JLabel("Enter");
			Choose[1] = new JLabel("Exit");
			Choose[0].setBounds(0,0,100,20);
			Choose[1].setBounds(0,20,100,20);
			Choose[0].addMouseListener(new EnterListener(this));
			Choose[1].addMouseListener(new ExitListener(this));
			this.setLayout(null);
			this.add(Choose[0]);
			this.add(Choose[1]);
			this.setBackground(Color.white);
			this.setBounds(PositionX, PositionY, 100,40);
			EventTrigger = true;
		}
		public class EnterListener extends MouseAdapter{
			private JPanel eventPanel;
			public EnterListener(JPanel eventPanel)
			{
				this.eventPanel = eventPanel;
			}
			public void mouseClicked(MouseEvent e)
			{
				EventTrigger = false;
				eventPanel.setVisible(false);

				// battle
				ParentProfile.battle(ActivateID);
			}
		}
		public class ExitListener extends MouseAdapter{
			private JPanel eventPanel;
			public ExitListener(JPanel eventPanel)
			{
				this.eventPanel = eventPanel;
			}
			public void mouseClicked(MouseEvent e)
			{
				EventTrigger = false;
				eventPanel.setVisible(false);
			}
		}
	}
	// mouse listener
	public class DoubleClick extends MouseAdapter{
		private int NodeID;
		public DoubleClick(int ID)
		{
			NodeID = ID;
		}
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(Node[ActivateID].IsNeighbor((MapNode)(e.getSource())) && EventTrigger == false)
				{
					// detect double click
					if(e.getClickCount() == 1)
					{
						resetNode(ActivateID);
						setActivate(NodeID);
					}
				}else if(e.getClickCount() == 2 && NodeID == ActivateID && EventTrigger == false)	// Event Trigger
				{
					int PositionX = ((Component)e.getSource()).getLocation().x+20;
					int PositionY = ((Component)e.getSource()).getLocation().y;
					if(Node[ActivateID].getNodeType() == 1)
						add(new CountyEventTrigger(PositionX, PositionY), JLayeredPane.DRAG_LAYER);
					else if(Node[ActivateID].getNodeType() == 2)
						add(new BattleFieldEventTrigger(PositionX, PositionY), JLayeredPane.DRAG_LAYER);
				}
			}
		}
	}
}
