package mainframe;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class SoundButton extends MainFrameButton
{
	private Font smallFont = new Font( "Courier", Font.PLAIN, 22 ), largeFont = new Font( "Courier", Font.PLAIN, 44 );
	private Play player;
	private String song = "giant.wav";
	private BGMPlayer p;
	private Thread t1;
	private int soundCnt = 1;
	
	public SoundButton( BGMPlayer _p )
	{
		setText( "Sound On" );
		p = _p;
		//player = new Play( song );
		//t1 = new Thread( new Play( song, k ) );
		//t1.start();
		addActionListener( new SoundButtonListener( this ) );
	}
	
	class SoundButtonListener implements ActionListener
	{
		private SoundButton btn;
		
		public SoundButtonListener( SoundButton _btn )
		{
			btn = _btn;
		}
		
		public void actionPerformed( ActionEvent e )
		{
			btn.setFont( smallFont );
			if( soundCnt == 1 ){
				p.stop();
				btn.setText( "Sound Off" );
				soundCnt = ( soundCnt + 1 ) % 2;
			}
			else{
				btn.setText( "Sound On" );
				synchronized( p ){
					//p.stop();
					p.restart();
				}
				soundCnt = ( soundCnt + 1 ) % 2;
			}
			/*
			if( k.getSoundFlag() == true ){
				btn.setText( "Sound Off" );
				//player = null;
				k.setSoundFlag( false );
				
			}
			else{
				btn.setText( "Sound On" );
				//player = new Play( song );
				k.setSoundFlag( true );
				t1 = new Thread( new Play( song, k ) );
				t1.start();
			}
			*/
			
			System.out.println( "Sound button being pushed." );
		}
	}
}