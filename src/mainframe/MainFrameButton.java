package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
public class MainFrameButton extends JButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	
	public MainFrameButton()
	{
		setForeground( Color.white );
		setFocusPainted( false );
		setContentAreaFilled( false );
		setBorder( null );
		setFont( smallFont );
		setForeground( Color.white );
		addMouseListener( new ButtonMouseAdapter( this ) );
	}
}