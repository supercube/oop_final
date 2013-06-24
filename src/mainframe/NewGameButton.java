package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

import javahacker.*;

public class NewGameButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	private BGMPlayer p;
	private MainFrame parentFrame;
	private StartPanel s;
	private MainPanel m;
	
	public NewGameButton( MainFrame _parentFrame, StartPanel _s, BGMPlayer _p, MainPanel _m )
	{
		setText( "New Game" );
		addActionListener( new NewGameListener( this ) );
		s = _s;
		p = _p;
		m = _m;
		parentFrame = _parentFrame;
	}
	
	class NewGameListener implements ActionListener
	{
		private JButton btn;
		
		public NewGameListener( JButton _btn )
		{
			btn = _btn;
		}
		public void actionPerformed( ActionEvent e )
		{
			btn.setFont( smallFont );
			System.out.println( "New Game Button being pushed" );
			//parentFrame.setVisible( false );
			Profile profile = new Profile( parentFrame, p, s, m );
			s.setVisible( false );
			parentFrame.add( profile );
			profile.repaint();
			synchronized( p ){
				p.next();
				if( p.getState() == true )
					System.out.println( "new song" );
				else
					System.out.println( "no new song." );
			}
			// i am not sure if this works, since main frame haven't combined with game program;
			//soundKey.setSoundFlag( false );
			//soundKey.setSoundFlag( true );
		}
	}
	
}