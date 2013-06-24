package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class AuthorsButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public AuthorsButton( MainFrame _m, MainPanel _p, AuthorsPanel _s )
	{
		setText( "Authors" );
		//setContentAreaFilled( false );
		//setBorder( null );
		//setFont( smallFont );
		//setForeground( Color.white );
		addActionListener( new AuthorsListener( _m, _p, _s, this ) );
		//addMouseListener( new ButtonMouseAdapter( this ) );
	}
	
	class AuthorsListener implements ActionListener
	{
		private JFrame mainFrame;
		private JPanel mainPanel;
		private JPanel authorsPanel;
		private JButton authorsButton;
		
		public AuthorsListener( MainFrame _m, MainPanel _p, AuthorsPanel _a, JButton _btn )
		{
			mainFrame = _m;
			mainPanel = _p;
			authorsPanel = _a;
			authorsButton = _btn;
		}
		
		public void actionPerformed( ActionEvent e )
		{
			mainFrame.remove( mainPanel );
			mainFrame.add( authorsPanel );
			authorsButton.setFont( smallFont );
			mainFrame.repaint();
			System.out.println( "Authors button being pushed" );
		}
	}
}