package cards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javahacker.TransparentIcon;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class ConcreteCard extends JLabel{
	
	private Card card;
	private JLabel Main;
	private JLabel Name;
	private JLabel HP,MP;
	private JLabel[] SE;
	public int type;
	
	/* detail */
	private JLabel Namedetail;
	private JLabel HPdetail;
	private JLabel MPdetail;
	private JLabel AGIdetail;
	private JLabel SIGHTdetail;
	private JLabel SummonCountdetail;
	private SkillLabel[] Skills;
	private boolean Detail;
	
	public ConcreteCard(){
		this.setSize(100,150);
		this.setLayout(null);
		Detail = false;
	}
	
	public ConcreteCard(int type){
		this.type = type;
		this.setSize(100,150);
		this.setLayout(null);
		Detail = false;
	}
	
	public ConcreteCard(Card card){
		this.type = 0;
		this.setSize(100,150);
		this.setLayout(null);
		Detail = false;
		reset(card);
	}
	
	public void AddLabels(JLabel... Labels)
	{
		for(int i=0; i<Labels.length; i++){
			if(Labels[i] != null)
				this.add(Labels[i]);
		}
	}
	
	public void reset(Card card){
		remove();
		this.card = card;
		if(card instanceof MonsterCard){
			MonsterCard mcard = (MonsterCard)card;
			// Monster Name
			Name = new JLabel(mcard.getName());
			Name.setBounds(0,0,100,15);
				// set Text Font
				Font font = Name.getFont();
				Font labelFont = font.deriveFont((float) 10.0);
				Name.setForeground(Color.black);
				Name.setFont(labelFont);
				Name.setHorizontalAlignment(JLabel.CENTER);
				Name.setVerticalAlignment(JLabel.CENTER);

			
			// HP,MP
			HP = new JLabel((new TransparentIcon("Images/Card/hp_ball.png", Color.white)).getIcon());
			MP = new JLabel((new TransparentIcon("Images/Card/mp_ball.png", Color.white)).getIcon());
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
			SE[0].setBounds(2,105,25,25);
			SE[1].setBounds(25,123,25,25);
			SE[2].setBounds(50,123,25,25);
			SE[3].setBounds(73,105,25,25);
			AddLabels(SE);
			
			// Monster Image
			Main = new JLabel();
			Main.setIcon(new ImageIcon(card.getCardImage()));
			Main.setBorder(null);
			Main.setBounds(0,0,100,150);
			
			
			
			Namedetail = new JLabel(mcard.getName());
			HPdetail = new JLabel("HP:" + (new Integer(mcard.getHP()).toString()));
			MPdetail = new JLabel("MP:" + new Integer(mcard.getMP()).toString());
			AGIdetail = new JLabel("AGI:" + new Integer(mcard.getAGI()).toString());
			SIGHTdetail = new JLabel("SIGHT:" + new Integer(mcard.getSIGHT()).toString());
			SummonCountdetail = new JLabel("SummonCount:" + new Integer(mcard.getSummonCount()).toString());
			
			setFontPosition(Namedetail, 12f, 96, 30, 2, 0);
			Namedetail.setHorizontalAlignment(JLabel.CENTER);
			Namedetail.setVerticalAlignment(JLabel.CENTER);
			setFontPosition(HPdetail, 10f, 48, 20, 2, 30);
			setFontPosition(MPdetail, 10f, 48, 20, 50, 30);
			setFontPosition(AGIdetail, 10f, 48, 20, 2, 50);
			setFontPosition(SIGHTdetail, 10f, 48, 20, 50, 50);
			setFontPosition(SummonCountdetail, 10f, 96, 20, 2, 70);
			
			Skills = new SkillLabel[2];
			Skills[0] = new SkillLabel("1", 0);
			Skills[1] = new SkillLabel("2", 1);
			if(SE != null){
				if(SE[0] != null){
					Skills[0] = new SkillLabel(SE[0].getIcon(), 0);
				}
				if(SE[1] != null){
					Skills[1] = new SkillLabel(SE[1].getIcon(), 1);
				}
			}
			
			setFontPosition(Skills[0], 20f, 50, 60, 0, 90);
			setFontPosition(Skills[1], 20f, 50, 60, 50, 90);
			Skills[0].setBorder(new LineBorder(Color.red, 2));
			Skills[1].setBorder(new LineBorder(Color.green, 2));
			
			setVisibleLabels(Detail, Namedetail, HPdetail, MPdetail, AGIdetail, SIGHTdetail, SummonCountdetail, Skills[0], Skills[1]);
			setVisibleLabels(!Detail, Name, HP, MP);
			if(SE != null){
				for(int i = 0; i < SE.length; i++)
					if(SE[i] != null)
						SE[i].setVisible(!Detail);
			}
			AddLabels(Namedetail, HPdetail, MPdetail, AGIdetail, SIGHTdetail, SummonCountdetail, Skills[0], Skills[1]);
			AddLabels(Name, HP, MP, Main);
			
		}else if(card instanceof PortalCard){
			
			PortalCard pcard = (PortalCard)card;
			// Monster Name
			Name = new JLabel(pcard.getName());
			Name.setBounds(0,0,100,15);
				// set Text Font
				Font font = Name.getFont();
				Font labelFont = font.deriveFont((float) 10.0);
				Name.setForeground(Color.black);
				Name.setFont(labelFont);
				Name.setHorizontalAlignment(JLabel.CENTER);
				Name.setVerticalAlignment(JLabel.CENTER);

			
			// HP
			HP = new JLabel((new TransparentIcon("Images/Card/hp_ball.png", Color.white)).getIcon());
			
				// set Text Font
				HP.setText( Integer.toString(pcard.getHP())); //MP.setText(Integer.toString(mcard.getMP()));
				HP.setVerticalTextPosition(JLabel.CENTER);
				HP.setHorizontalTextPosition(JLabel.CENTER);
				HP.setForeground(Color.white);
			HP.setBorder(null); 
			HP.setBounds(-5,125,30,30); 
			
			SE = new JLabel[2];
			for(int i=0; i<2; i++)
			{
				try{
					SE[i] = new JLabel(new ImageIcon(pcard.getMonster(i).getSymbolImage()));
				}catch(Exception e){}
				
				if(SE[i] == null)
				{
					SE[i] = new JLabel();
				}
				SE[i].setBorder(null);
			}
			SE[0].setBounds(25,123,25,25);
			SE[1].setBounds(50,123,25,25);
			AddLabels(SE);
			
			// Monster Image
			Main = new JLabel();
			Main.setIcon(new ImageIcon(card.getCardImage()));
			Main.setBorder(null);
			Main.setBounds(0,0,100,150);
			
			AddLabels(Name, HP, Main);
			
		}else if(card instanceof SkillCard){
			SkillCard scard = (SkillCard)card;
			Name = new JLabel(scard.getName());
			Name.setBounds(0,0,100,15);
				// set Text Font
				Font font = Name.getFont();
				Font labelFont = font.deriveFont((float) 10.0);
				Name.setForeground(Color.black);
				Name.setFont(labelFont);
				Name.setHorizontalAlignment(JLabel.CENTER);
				Name.setVerticalAlignment(JLabel.CENTER);

			
			//MP
			MP = new JLabel((new TransparentIcon("Images/Card/mp_ball.png", Color.white)).getIcon());
				// set Text Font
				MP.setText(Integer.toString(scard.getMPConsume()));
				MP.setVerticalTextPosition(JLabel.CENTER);
				MP.setHorizontalTextPosition(JLabel.CENTER);
				MP.setForeground(Color.white);
				MP.setBorder(null);
				MP.setBounds(75,125,30,30);
				
			// Monster Image
			Main = new JLabel();
			Main.setIcon(new ImageIcon(card.getCardImage()));
			Main.setBorder(null);
			Main.setBounds(0,0,100,150);
			
			AddLabels(Name, MP, Main);
			
			
		}else if(card instanceof EmptyCard || card instanceof UnknownCard ){
			//Image
			Main = new JLabel();
			Main.setIcon(new ImageIcon(card.getCardImage()));
			Main.setBorder(null);
			Main.setBounds(0,0,100,150);
			AddLabels(Main);
		}
	}
	
	public void setFontPosition(JLabel L, float FontSize, int SizeX, int SizeY, int PositionX, int PositionY)
	{
		L.setFont(L.getFont().deriveFont(FontSize));
		L.setForeground(Color.red);
		
		L.setLocation(new Point(PositionX, PositionY));
		L.setSize(new Dimension(SizeX, SizeY));
	}
	public void setVisibleLabels(boolean b, JLabel... Labels)
	{
		for(int i=0; i<Labels.length; i++)
			Labels[i].setVisible(b);
	}
	public void MonsterDetailChange()
	{
		if(card != null && card instanceof MonsterCard)
		{
			if(Detail == false)
			{
				setVisibleLabels(true, Namedetail, HPdetail, MPdetail, AGIdetail, SIGHTdetail, SummonCountdetail, Skills[0], Skills[1]);
				setVisibleLabels(false, Name, HP, MP);
				if(SE != null){
					for(int i = 0; i < SE.length; i++)
						if(SE[i] != null)
							SE[i].setVisible(false);
				}
				Detail = true;
			}else
			{
				setVisibleLabels(false, Namedetail, HPdetail, MPdetail, AGIdetail, SIGHTdetail, SummonCountdetail, Skills[0], Skills[1]);
				setVisibleLabels(true, Name, HP, MP);
				if(SE != null){
					for(int i = 0; i < SE.length; i++)
						if(SE[i] != null)
							SE[i].setVisible(true);
				}
				Detail = false;
			}
		}
	}
	
	public Card getCard(){
		return card;
	}
	
	public void copy(ConcreteCard inputcard){
		this.card = inputcard.card;
		reset(inputcard.card);
	}
	
	public void copy(Card inputcard){
		this.card = inputcard;
		reset(inputcard);
	}
	
	public void reload(){
		reset(card);
	}
	
	public void remove(){
		if(Name != null)
			this.remove(Name);
		if(Main != null)
			this.remove(Main);
		if(HP != null)
			this.remove(HP);
		if(MP != null)
			this.remove(MP);
		
		if(SE != null){
			for(int i = 0; i < SE.length; i++)
				if(SE[i] != null)
					this.remove(SE[i]);
		}
		
		
		if(Namedetail != null)
			this.remove(Namedetail);
		if(HPdetail != null)
			this.remove(HPdetail);
		if(MPdetail != null)
			this.remove(MPdetail);
		if(AGIdetail != null)
			this.remove(AGIdetail);
		if(SIGHTdetail != null)
			this.remove(SIGHTdetail);
		if(SummonCountdetail != null)
			this.remove(SummonCountdetail);
		
		if(Skills != null){
			for(int i = 0; i < Skills.length; i++)
				if(Skills[i] != null)
					this.remove(Skills[i]);
		}
		
		card = null;
		Main = null;
		Name = null;
		HP = null;
		MP = null;
		SE = null;
		Namedetail = null;
		HPdetail = null;
		MPdetail = null;
		AGIdetail = null;
		SIGHTdetail = null;
		SummonCountdetail = null;
		Skills = null;
		Detail = false;
	}
	
	public void setSkillLabel(SkillLabel skill, int id){
		if(Skills != null && id >= 0 && id <= 1){
			Skills[id] = skill;
		}
	}
}
