package javahacker;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Load extends JPanel{

	private JLabel Save1;
	private JLabel Save2;
	private JLabel PrevChosen;
	private LineBorder FileChosenBorder;
	private DeckBuild deck;
	private Quest quest;
	private CharacterProfile character;
	private Profile profile;
	public Load(Profile profile, DeckBuild deck, Quest quest, CharacterProfile Character)
	{
		this.profile = profile;
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
	public void LoadFile(String FileName)
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(FileName)));
        
			String line = null;
			while ((line=reader.readLine()) != null) {
				if(line.equals("DeckBuild"))
					deck.LoadFromFile(reader);
				if(line.equals("Quest"))
					quest.LoadFromFile(reader);
				if(line.equals("CharacterProfile"))
					character.LoadFromFile(reader);
			}
			profile.MapReset();
			reader.close();  // Close to unlock.
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
					LoadFile(FileName);
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