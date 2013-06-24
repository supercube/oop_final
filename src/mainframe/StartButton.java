package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class StartButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public StartButton( MainFrame _m, MainPanel _p, StartPanel _s )
	{
		setText( "Start" );
		//setContentAreaFilled( false );
		//setFocusPainted( false );
		//setBorder( null );
		//setFont( smallFont );
		//setForeground( Color.white );
		this.addActionListener( new StartButtonListener( _m, _p, _s, this ) );
		//this.addMouseListener( new ButtonMouseAdapter( this ) );
	}
	
	class StartButtonListener implements ActionListener
	{
		private MainFrame m;
		private MainPanel p;
		private StartPanel s;
		private JButton btn;
		
		public StartButtonListener( MainFrame _m, MainPanel _p, StartPanel _s, JButton _btn )
		{
			m = _m;
			p = _p;
			s = _s;
			btn = _btn;
		}
		
		public void actionPerformed( ActionEvent e )
		{
			m.remove( p );
			m.add( s );
			//btn.setFont( new Font( "Courier", Font.PLAIN, 22 ));
			btn.setFont( smallFont );
			m.repaint();
			System.out.println( "Start button being pushed." );
		}
	}
	
}