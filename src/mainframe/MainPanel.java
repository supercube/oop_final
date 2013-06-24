package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class MainPanel extends ImagePanel
{
	private MainFrame parentFrame;
	private StartPanel s;
	private AuthorsPanel a;
	private OptionPanel o;
	private JButton b1, b2, b3, b4;
	
	public MainPanel( MainFrame _f )
	{
		parentFrame = _f;
	}
	
	public void buildPanel( StartPanel _s, AuthorsPanel _a, OptionPanel _o )
	{
		s = _s;
		a = _a;
		o = _o;
		this.addButton();
	}
	
	public void addButton()
	{
		b1 = new StartButton( parentFrame, this, s );
		b1.setBounds( 500, 200, 300, 50 );
		//b1.setFont( new Font( "Courier", Font.PLAIN, 22 ) );
		//b1.setForeground( Color.white );
		this.add( b1 );
		
		b2 = new OptionButton( parentFrame, this, o );
		b2.setBounds( 500, 300, 300, 50 );
		this.add( b2 );
		
		b3 = new AuthorsButton( parentFrame, this, a );
		b3.setBounds( 500, 400, 300, 50 );
		this.add( b3 );
		
		b4 = new QuitButton();
		b4.setBounds( 500, 500, 300, 50 );
		this.add( b4 );
	}
}