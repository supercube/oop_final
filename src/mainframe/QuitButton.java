package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class QuitButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public QuitButton()
	{
		setText( "Quit" );
		//setContentAreaFilled( false );
		//setBorder( null );
		//setFont( smallFont );
		//setForeground( Color.white );
		addActionListener( new QuitListener( this ) );
		//addMouseListener( new ButtonMouseAdapter( this ) );
	}
	
	class QuitListener implements ActionListener
	{
		private JButton btn;
		
		public QuitListener( JButton _btn )
		{
			btn = _btn;
		}
		
		public void actionPerformed( ActionEvent e )
		{
			btn.setFont( smallFont );
			System.out.println( "Quit Button being pushed" );
			System.exit( 0 );
		}
	}
	
}