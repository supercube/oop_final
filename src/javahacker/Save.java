package javahacker;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Save extends JPanel{

	private JLabel Save1;
	private JLabel Save2;
	private JLabel PrevChosen;
	private LineBorder FileChosenBorder;
	private DeckBuild deck;
	private Quest quest;
	private CharacterProfile character;
	
	public Save(DeckBuild deck, Quest quest, CharacterProfile Character)
	{
		this.setSize(1000,700);
		//this.setBackground(Color.blue);
		this.setLayout(null);
		
		PrevChosen = null;
		Save1 = new JLabel("Save1");
		Save1.addMouseListener(new LoadListener(Save1, "save1.txt"));
		Save1.setBounds(200,100,200,40);
		
		Save2 = new JLabel("Save2");
		Save2.addMouseListener(new LoadListener(Save2, "save2.txt"));
		Save2.setBounds(200,200,200,40);
		
		FileChosenBorder = new LineBorder(Color.red, 2);
		this.deck = deck;
		this.quest = quest;
		this.character = Character;
		
		this.add(Save1);
		this.add(Save2);
	}
	public void SaveFile(String FileName)
	{
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FileName)));
			character.WriteFile(writer);
			deck.WriteFile(writer);
			quest.WriteFile(writer);
			writer.close();  // Close to unlock.
			
		}
		catch (IOException e)    {   }
	}
	public class LoadListener extends MouseAdapter{
	
		private JLabel Target;
		private String FileName;
		
		public LoadListener(JLabel Target, String LoadFileName)
		{
			this.Target = Target;
			FileName = LoadFileName;
		}
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(e.getClickCount() == 2)
				{
					SaveFile(FileName);
				}
				if(PrevChosen != Target)
				{
					if(PrevChosen != null)
						PrevChosen.setBorder(null);
					Target.setBorder(FileChosenBorder);
					PrevChosen = Target;
				}
			}
		}
		
	}
	public static void main(String[] args)
	{
		new test();
	}
}