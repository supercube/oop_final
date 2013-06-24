package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class ImagePanel extends JPanel
{
	public ImagePanel()
	{
		this.setLayout( null );
		this.setBounds( 0, 0, 1366, 768 );
	}
	public void paintComponent( Graphics g )
	{
		//super.paintComponent( g );
		ImageIcon icon = new ImageIcon( "Images/Other/hackerdesktop3.png" );
		Image img = icon.getImage();
		g.drawImage( img, 0, 0, this );
	}
}