package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class BackToMainButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public BackToMainButton( MainFrame _f, MainPanel _p1, JPanel _p2 )
	{
		setText( "Back" );
		//setContentAreaFilled( false );
		//setBorder( null );
		//setFont( smallFont );
		//setForeground( Color.white );
		addActionListener( new BackToMainListener( _f, _p1, _p2, this ) );
		//addMouseListener( new ButtonMouseAdapter( this ) );
	}

	class BackToMainListener implements ActionListener
	{
		private MainFrame m;
		private MainPanel p1;
		private JPanel p2;
		private JButton btn;
		
		public BackToMainListener( MainFrame _m, MainPanel _p1, JPanel _p2, JButton _btn )
		{
			m = _m;
			p1 = _p1;
			p2 = _p2;
			btn = _btn;
		}
		
		public void actionPerformed( ActionEvent e )
		{
			m.remove( p2 );
			m.add( p1 );
			btn.setFont( smallFont );
			m.repaint();
			System.out.println( "BackToMain button being pushed" );
		}
	}
	
}