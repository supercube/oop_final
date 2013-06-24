package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class ButtonMouseAdapter extends MouseAdapter
{
	private JButton btn;
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public ButtonMouseAdapter( JButton _btn )
	{
		btn = _btn;
	}
	
		
	public void mouseEntered( MouseEvent e )
	{
		btn.setFont( largeFont );
	}
	
	public void mouseExited( MouseEvent e )
	{
		btn.setFont( smallFont );
	}
}