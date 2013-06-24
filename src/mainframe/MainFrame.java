package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


public class MainFrame extends JFrame
{
	//private Key soundKey = new Key();
	BGMPlayer p = new BGMPlayer();
	
	public MainFrame()
	{
		super( "Java Hacker" );
		
		this.setResizable( false );
		this.setSize( 1396, 798 );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setLayout( null );
		
		
		MainPanel m = new MainPanel( this );
		
		StartPanel s = new StartPanel( this, p );
		AuthorsPanel a = new AuthorsPanel( this );
		OptionPanel o = new OptionPanel( this, p );
		
		m.buildPanel( s, a, o );
		s.buildPanel( m );
		a.buildPanel( m );
		// todo: OptionPanel
		o.buildPanel( m );
		add( m );
		//add( o );
		
		setVisible( true );
	}
	public static void main( String[] args )
	{
		MainFrame m = new MainFrame();
	}
}
