package mainframe;

public class BackGroundMusic implements Runnable
{
	// by default, bgm always play repeatedly.
	private Play player;
	private Key soundKey = new Key();
	private Key external;
	private String[] songList = { "Music/forest.wav", "Music/giant.wav" };
	
	public BackGroundMusic( Key _k )
	{
		external = _k;
		player = new Play( songList[ 0 ], soundKey );
		soundKey.setSoundFlag( true );
	}
	
	public void playBGM()
	{
		System.out.printf( "playing bgm\n" );
		soundKey.setSoundFlag( true );
		player = new Play( songList[ 0 ], soundKey );
		Thread t1 = new Thread( player );
		t1.start();
	}
	
	public void playBGM( int index )
	{
		System.out.printf( "playing bgm\n" );
		player = new Play( songList[ index ], soundKey );
		soundKey.setSoundFlag( true );
		Thread t1 = new Thread( player );
		t1.start();
	}
	
	public void stopBGM()
	{
		// without sychronization, player can't do something like pause music then play music...
		System.out.printf( "trying to stop bgm\n" );
		synchronized( soundKey ){
			soundKey.setSoundFlag( false );
		}
		while( true ){
			synchronized( soundKey ){
				if( soundKey.getStopFlag() != true ){
					soundKey.notifyAll();
					try{
						soundKey.wait();
					}
					catch( Exception e ){
						e.printStackTrace();
					}
				}
				else{
					if( soundKey.getStopFlag() == true ){
						System.out.println( "flag is set true" );
					}
					break;
				}
			}
		}
		System.out.printf( "bgm stopped\n" );
	}
	
	public void run()
	{
		playBGM();
		/*
		try{
			Thread.sleep( 10000 );
			external.setNewSongFlag( true );
		}
		catch( Exception e ){
			e.printStackTrace();
			
		}
		*/
		int cnt = 0;
		while( true ){
			if( external.getStopFlag() == true ){
				System.out.println( "exteranl stop" );
			}
			/*
			else{
				System.out.println( "external not stop" );
			}
			*/
			synchronized( external ){
				if( external.getNewSongFlag() == true ){
					stopBGM();
					cnt = ( cnt + 1 ) % songList.length;
					playBGM( cnt );
					//external.setNewSongFlag( false );
				}
				if( external.getStopFlag() == true ){
					stopBGM();
					break;
				}
			}
			synchronized( soundKey ){
				/*
				System.out.println( "i am here" );
				if( soundKey.getStopFlag() == true ){
					System.out.println( "flag is set true" );
				}
				else{
					System.out.println( "flag is set false" );
				}
				*/
				//System.out.println( "hello" );
				if( external.getNewSongFlag() == true ){
					soundKey.setStopFlag( false );
					external.setNewSongFlag( false );
				}
				
				
				
				if( soundKey.getStopFlag() == true ){
					stopBGM();
					break;
				}
				
				soundKey.notifyAll();
				/*
				try{
					soundKey.wait();
				}
				catch( Exception e ){
					e.printStackTrace();
				}
				*/
				//System.out.println( "bye" );
			}
			
			try{
				Thread.sleep( 100 );
			}
			catch( Exception e2 ){
				e2.printStackTrace();
			}
		}
		System.out.println( "leave the loop" );
	}
	
	
	public static void main( String[] args )
	{
		Key k2 = new Key();
		Thread t = new Thread( new BackGroundMusic( k2 ) );
		t.start();
		
	}
}
