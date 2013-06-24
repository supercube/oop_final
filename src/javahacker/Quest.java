package javahacker;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Quest extends JPanel{

	private ArrayList<QuestItem> QuestList;
	private JScrollPane scrollPane;
	private JPanel QuestListPane;
	private JLabel Title;
	private JLabel QuestDetail;
	private JScrollPane scrollQuestDetail;
	private static int QuestHeight = 100;
	private static int QuestCount;
	private static QuestItem prevQuest;
	private LineBorder QuestChosenBorder;
	
	public Quest()
	{
		this.setSize(1000,700);
		this.setBackground(Color.black);
		this.setLayout(null);
		
		Title = new JLabel("Quest List:");
		Title.setBounds(100,10,500,40);
		setFont(Title, 30, Color.white);
		
		QuestList = new ArrayList<QuestItem>();
		
		QuestListPane = new JPanel();
		QuestCount = 1;
		QuestListPane.setPreferredSize(new Dimension(450,QuestCount*QuestHeight));
		QuestListPane.setMinimumSize(new Dimension(450,QuestCount*QuestHeight));
		QuestListPane.setMaximumSize(new Dimension(450,QuestCount*QuestHeight));		
		QuestListPane.setLayout(new GridLayout(0,1));
		
		QuestDetail = new JLabel();
		QuestDetail.setPreferredSize(new Dimension(250, 580));
		QuestDetail.setMinimumSize(new Dimension(250, 580));
		QuestDetail.setMaximumSize(new Dimension(250, 580));
		QuestDetail.setHorizontalTextPosition(JLabel.CENTER);
		QuestDetail.setVerticalTextPosition(JLabel.CENTER);
		Font font = QuestDetail.getFont();
		font.deriveFont((float)40.0);	
		QuestDetail.setForeground(Color.white);
		QuestDetail.setFont(font);
		
		prevQuest = null;
		QuestChosenBorder = new LineBorder(Color.red, 2);
		
		scrollPane = new JScrollPane(QuestListPane);
		scrollQuestDetail = new JScrollPane(QuestDetail);
		
		scrollPane.setBounds(100,80,470,600);
		scrollQuestDetail.setBounds(620,80,270,600);
		
		// test for default quest
		//QuestData data = new QuestData(1, "SmallMission","", "Kill Slime","./Images/QuestGrass.jpg");
		//QuestItem item = new QuestItem(data);
		//addQuest(item);
		
		this.add(scrollPane);
		this.add(scrollQuestDetail);
		this.add(Title);
	}
	public void addQuest(QuestItem Item)
	{
		QuestList.add(Item);
		QuestListPane.add(Item);
		Item.addMouseListener(new QuestListener(Item));
		QuestCount++;
		QuestListPane.setPreferredSize(new Dimension(450,QuestCount*QuestHeight));
		QuestListPane.setMinimumSize(new Dimension(450,QuestCount*QuestHeight));
		QuestListPane.setMaximumSize(new Dimension(450,QuestCount*QuestHeight));
	}
	public void removeQuest(QuestItem Item)
	{
		QuestList.remove(Item);
		QuestListPane.remove(Item);
		QuestCount--;
		QuestListPane.setPreferredSize(new Dimension(450,QuestCount*QuestHeight));
		QuestListPane.setMinimumSize(new Dimension(450,QuestCount*QuestHeight));
		QuestListPane.setMaximumSize(new Dimension(450,QuestCount*QuestHeight));
	}
	public ArrayList<QuestItem> getQuestList()
	{
		return QuestList;
	}
	public void WriteFile(BufferedWriter writer)
	{
		QuestItem X;
		try{
			writer.write("Quest");
			writer.newLine();
			writer.write(new Integer(QuestList.size()).toString());
			writer.newLine();
			Iterator<QuestItem> QuestIterator = QuestList.iterator();
			while(QuestIterator.hasNext())
			{
				try{
					X = (QuestItem)QuestIterator.next();
					writer.write(new Integer(X.getActivateNode()).toString());
					writer.newLine();
					writer.write(X.getTitle());
					writer.newLine();
					writer.write(X.getContent());
					writer.newLine();
					writer.write(X.getDetail());
					writer.newLine();
					writer.write(X.getBackgroundImg());
					writer.newLine();
				}catch(Exception e){}
			}
		}catch(Exception e){}
	}
	public void LoadFromFile(BufferedReader reader)
	{
		try{
			String line = null;
			int ActivateNode;
			String title;
			String content;
			String detail;
			String detailImg;
			// init
			QuestListPane.removeAll();
			QuestList.clear();
			QuestCount = 0;
			
			int QuestNumber = Integer.valueOf(reader.readLine());
			for(int i=0; i<QuestNumber; i++)
			{
				ActivateNode = Integer.valueOf(reader.readLine());
				title = reader.readLine();
				content = reader.readLine();
				detail = reader.readLine();
				detailImg = reader.readLine();
				addQuest( new QuestItem(new QuestData(ActivateNode, title, content, detail, detailImg)));
			}
		}catch(Exception e){}
	}
	public void setFont(JLabel C, float size, Color color)
	{
		Font font = C.getFont();
		Font NEWFont = font.deriveFont(size);
		
		// font
		C.setForeground(color);
		C.setFont(NEWFont);
		
		// text position
		C.setVerticalTextPosition(JLabel.CENTER);;
	}
	public static void main(String[] args)
	{
		JFrame mainframe = new JFrame("test");
		mainframe.setSize(1200,800);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.setLayout(null);
		
		
		Quest quest = new Quest();
		quest.setLocation(0,0);
		
		QuestData data = new QuestData(1, "newtest","test for quest item", "Kill Slime","./Images/QuestGrass.jpg");
		QuestItem item = new QuestItem(data);
		
		for(int i=0; i<6; i++)
		{
			quest.addQuest(item);
			data = new QuestData(1, "testtest","test for quest item");
			item = new QuestItem(data);
		}
		mainframe.add(quest);
		mainframe.setVisible(true);
	}
	private class QuestListener extends MouseAdapter
	{
		private QuestItem quest;
		
		public QuestListener(QuestItem inputItem)
		{
			quest = inputItem;
		}
		public void mouseClicked(MouseEvent e)
		{
			QuestDetail.setText(quest.getDetail());
			if(quest.getBackgroundImg() != null)
			{
				QuestDetail.setIcon(new TransparentIcon(quest.getBackgroundImg(), Color.white).getIcon());
			}
			quest.setBorder(QuestChosenBorder);
			if(prevQuest != null && prevQuest != quest)
				prevQuest.setBorder(null);
			prevQuest = quest;
		}
	}
}

class QuestItem extends JPanel
{
	QuestData data;
	JLabel Title;
	JLabel Content;
	
	public QuestItem(QuestData data)
	{
		this.data = data;
		Title = new JLabel(data.getTitle());
		Content = new JLabel(data.getContent());
		Title.setBounds(0,0,300,30);
		Content.setBounds(0,40,300,60);
		setFont(Title, 20, Color.black);
		setFont(Content, 15, Color.black);
		
		this.setLayout(new GridLayout(2,1));
		this.setSize(300,200);
		this.add(Title);
		this.add(Content);
	}
	public void setFont(JLabel C, float size, Color color)
	{
		Font font = C.getFont();
		Font NEWFont = font.deriveFont(size);
		
		// font
		C.setForeground(color);
		C.setFont(NEWFont);
		
		// text position
		C.setVerticalTextPosition(JLabel.CENTER);
		//C.setHorizontalTextPosition(JLabel.CENTER);
		C.setBorder(null);
	}
	public int getActivateNode()
	{
		return data.getActivateNode();
	}
	public String getTitle()
	{
		return Title.getText();
	}
	public String getContent()
	{
		return Content.getText();
	}
	public String getDetail()
	{
		return data.getDetail();
	}
	public String getBackgroundImg()
	{
		return data.getBackgroundImg();
	}
}

class QuestData
{
	private int ActivateNode;
	private String QuestTitle;
	private String QuestContent;
	private String QuestDetail;
	private String QuestDetailBackground;
	
	public QuestData(int ActivateNode, String title, String content)
	{
		this.ActivateNode = ActivateNode;
		this.QuestTitle = title;
		this.QuestContent = content;
		this.QuestDetail = "Nothing";
		this.QuestDetailBackground = "Images/Other/QuestGrass.jpg";
	}
	public QuestData(int ActivateNode, String title, String content, String detail)
	{
		this.ActivateNode = ActivateNode;
		this.QuestTitle = title;
		this.QuestContent = content;
		this.QuestDetail = detail;
		this.QuestDetailBackground = "Images/Other/QuestGrass.jpg";
	}
	public QuestData(int ActivateNode, String title, String content, String detail, String detailImg)
	{
		this.ActivateNode = ActivateNode;
		this.QuestTitle = title;
		this.QuestContent = content;
		this.QuestDetail = detail;
		this.QuestDetailBackground = detailImg;
	}
	public int getActivateNode()
	{
		return ActivateNode;
	}
	public String getTitle()
	{
		return QuestTitle;
	}
	public String getContent()
	{
		return QuestContent;
	}
	public String getDetail()
	{
		return QuestDetail;
	}
	public String getBackgroundImg()
	{
		return QuestDetailBackground;
	}
}