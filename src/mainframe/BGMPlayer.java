package mainframe;

public class BGMPlayer
{
	private Key control = new Key();
	
	public BGMPlayer()
	{
		Thread t = new Thread( new BackGroundMusic( control ) );
		t.start();
	}
	
	public void next()
	{
		control.setNewSongFlag( true );
	}
	
	public void stop()
	{
		control.setStopFlag( true );
	}
	
	public void restart()
	{
		control = new Key();
		Thread t = new Thread( new BackGroundMusic( control ) );
		t.start();
	}
	
	public boolean getState()
	{
		return control.getNewSongFlag();
	}
	
	public static void main( String[] args )
	{
		BGMPlayer b = new BGMPlayer();
		
		try{
			Thread.sleep( 5000 );
			b.next();
			Thread.sleep( 5000 );
			b.next();
			Thread.sleep( 5000 );
			b.next();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}