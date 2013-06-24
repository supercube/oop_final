package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class AuthorsPanel extends ImagePanel
{
	private MainFrame parentFrame;
	private MainPanel m;
	private JButton b1;
	private JPanel p;
	
	public AuthorsPanel( MainFrame _f )
	{
		parentFrame = _f;
	}
	
	public void buildPanel( MainPanel _m )
	{
		m = _m;
		this.addLabel();
	}
	
	public void addLabel()
	{
		b1 = new BackToMainButton( parentFrame, m, this );
		b1.setBounds( 500, 600, 300, 50 );
		this.add( b1 );
		
		p = new TextPanel();
		
		JTextArea fld = new JTextArea( );
		fld.setBounds( 600, 300, 100, 50 );
		String newline = "\n";
		fld.setText( "B99902008" + newline + "B99902022" + newline + "B99902072" );
		fld.setBackground( Color.black );
		fld.setForeground( Color.white );
		fld.setEditable( false );
		p.add( fld );
		p.setBounds( 200, 100, 800, 450 );
		this.add( p );
		
	}
	
	class TextPanel extends JPanel
	{
		public TextPanel()
		{
			this.setLayout( null );
			this.setBounds( 0, 0, 800, 450 );
		}
		
		public void paintComponent( Graphics g )
		{
			ImageIcon icon = new ImageIcon( "Images/Other/hacker.png" );
			Image img = icon.getImage();
			g.drawImage( img, 0, 0, this );
		}
	}
}