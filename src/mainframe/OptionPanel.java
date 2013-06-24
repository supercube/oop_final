package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class OptionPanel extends ImagePanel
{
	private MainFrame parentFrame;
	private MainPanel m;
	private JButton b1, b2;
	private JPanel p;
	private BGMPlayer p2;
	
	public OptionPanel( MainFrame _f,  BGMPlayer _p2 )
	{
		parentFrame = _f;
		//p = _p;
		p2 = _p2;
	}
	
	public void buildPanel( MainPanel _m )
	{
		m = _m;
		this.addButton();
	}
	
	public void addButton()
	{
		b1 = new BackToMainButton( parentFrame, m, this );
		b1.setBounds( 500, 600, 300, 50 );
		
		b2 = new SoundButton( p2 );
		b2.setBounds( 500, 300, 300, 50 );
		
		add( b1 );
		add( b2 );
	}
}