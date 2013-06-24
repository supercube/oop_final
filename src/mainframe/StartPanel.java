package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class StartPanel extends ImagePanel
{
	private MainFrame parentFrame;
	private MainPanel m;
	private JButton b1, b2, b3;
	private BGMPlayer p;
	
	public StartPanel( MainFrame _f, BGMPlayer _p )
	{
		parentFrame = _f;
		p = _p;
	}
	
	public void buildPanel( MainPanel _m )
	{
		m = _m;
		this.addButton();
	}
	
	public void addButton()
	{
		// In New Game and Continue Button, stop current music and play new music.
		b1 = new NewGameButton( parentFrame, this, p, m );
		b1.setBounds( 500, 300, 300, 50 );
		this.add( b1 );
		
		//b2 = new ContinueButton( p );
		//b2.setBounds( 500, 400, 300, 50 );
		//this.add( b2 );
		
		b3 = new BackToMainButton( parentFrame, m, this );
		b3.setBounds( 500, 500, 300, 50 );
		this.add( b3 );
	}
}