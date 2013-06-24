package mainframe;

public class Key
{
	private boolean soundFlag = true;
	private boolean stopFlag = false;
	private boolean finished = false;
	private boolean newSongFlag = false;
	
	public void setSoundFlag( boolean _b )
	{
		soundFlag = _b;
	}
	
	public boolean getSoundFlag()
	{
		return soundFlag;
	}
	
	public void setStopFlag( boolean _b )
	{
		stopFlag = _b;
	}
	
	public boolean getStopFlag()
	{
		return stopFlag;
	}
	
	public void setNewSongFlag( boolean _b )
	{
		newSongFlag = _b;
	}
	
	public boolean getNewSongFlag()
	{
		return newSongFlag;
	}
	
}