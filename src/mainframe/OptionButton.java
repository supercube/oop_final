package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class OptionButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public OptionButton( MainFrame _m, MainPanel _p, OptionPanel _o )
	{
		setText( "Option" );
		addActionListener( new OptionButtonListener( _m, _p, _o, this ) );
	}
	
	class OptionButtonListener implements ActionListener
	{
		private MainFrame m;
		private MainPanel p;
		private OptionPanel o;
		private JButton btn;
		
		public OptionButtonListener( MainFrame _m, MainPanel _p, OptionPanel _o, JButton _btn )
		{
			m = _m;
			p = _p;
			o = _o;
			btn = _btn;
		}
		
		public void actionPerformed( ActionEvent e )
		{
			m.remove( p );
			m.add( o );
			btn.setFont( smallFont );
			m.repaint();
			System.out.println( "Option button being pushed." );
		}
	}
	
}