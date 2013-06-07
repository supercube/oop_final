package cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javahacker.TransparentIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConcreteCard extends JLabel{
	
	private Card card;
	private JLabel Monster;
	private JLabel MonsterName;
	private JLabel HP,MP;
	private JLabel[] SE;
	
	public ConcreteCard(){}
	public ConcreteCard(Card card){
		this.card = card;
		this.setSize(100,150);
		this.setLayout(null);
		reset(card);
		
	}
	
	public void AddLabels(JLabel... Labels)
	{
		for(int i=0; i<Labels.length; i++)
			this.add(Labels[i]);
	}
	
	public void reset(Card card){
		if(card instanceof MonsterCard){
			MonsterCard mcard = (MonsterCard)card;
			// Monster Name
			MonsterName = new JLabel(mcard.getName());
			MonsterName.setBounds(0,0,100,15);
				// set Text Font
				Font font = MonsterName.getFont();
				Font labelFont = font.deriveFont((float) 10.0);
				MonsterName.setForeground(Color.black);
				MonsterName.setFont(labelFont);
				MonsterName.setHorizontalAlignment(JLabel.CENTER);
				MonsterName.setVerticalAlignment(JLabel.CENTER);

			
			// HP,MP
			HP = new JLabel((new TransparentIcon("Images/HP.png", Color.white)).getIcon());
			MP = new JLabel((new TransparentIcon("Images/MP.png", Color.white)).getIcon());
				// set Text Font
				HP.setText( Integer.toString(mcard.getHP())); MP.setText(Integer.toString(mcard.getMP()));
				HP.setVerticalTextPosition(JLabel.CENTER);
				HP.setHorizontalTextPosition(JLabel.CENTER);
				MP.setVerticalTextPosition(JLabel.CENTER);
				MP.setHorizontalTextPosition(JLabel.CENTER);
				HP.setForeground(Color.white); MP.setForeground(Color.white);
			HP.setBorder(null); MP.setBorder(null);
			HP.setBounds(-5,125,30,30); MP.setBounds(75,125,30,30);
			
			// skill equipment
			SE = new JLabel[4];
			for(int i=0; i<2; i++)
			{
				try{
					SE[i] = new JLabel(new ImageIcon(mcard.getSkill(i).getSymbolImage()));
				}catch(Exception e){}
				
				try{
					SE[i+2] = new JLabel(new ImageIcon(mcard.getEquip(i).getSymbolImage()));
				}catch(Exception e){}
				
				if(SE[i] == null)
				{
					SE[i] = new JLabel();
					//System.out.println("no image");
				}
				if(SE[i+2] == null)
				{
					SE[i+2] = new JLabel();
					//System.out.println("no image");
				}
				SE[i].setBorder(null);
				SE[i+2].setBorder(null);
			}
			SE[0].setBounds(0,100,25,25);
			SE[1].setBounds(25,125,25,25);
			SE[2].setBounds(50,125,25,25);
			SE[3].setBounds(75,100,25,25);
			AddLabels(SE);
			
			// Monster Image
			Monster = new JLabel();
			Monster.setIcon(new ImageIcon(card.getCardImage()));
			Monster.setBorder(null);
			Monster.setBounds(0,0,100,150);
			
			AddLabels(MonsterName, HP, MP, Monster);
		}
	}
	public void copy(ConcreteCard inputcard){
		this.card = inputcard.card;
		reset(inputcard.card);
	}
}
