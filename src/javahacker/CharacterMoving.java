package javahacker;
import javax.swing.*;

public class CharacterMoving extends Thread{

	private ImageIcon[] icon;
	private JButton Character;
	private static String[] imgPath = {"Images/Other/left1.png","Images/Other/left2.png","Images/Other/right1.png","Images/Other/right2.png"};
	private boolean dir;	// true:right, false:left
	private boolean flag;
	public CharacterMoving(JButton Character)
	{
		dir = true;
		icon = new ImageIcon[4];
		for(int i=0; i<4; i++)
			icon[i] = new ImageIcon(imgPath[i]);
		this.Character = Character;
		flag = false;
	}
	public void setDir(boolean b)
	{
		dir = b;
	}
	public boolean getDir()
	{
		return dir;
	}
	public void setStopFlag()
	{
		flag = true;
	}
	public void run()
	{
		flag = true;
		while(flag)
		{
			if(dir)
				for(int i=2; i<4; i++)
				{
					if(!flag)
						break;
					Character.setIcon(icon[i]);
					try{
						Thread.sleep(500);
					}catch(Exception e){}
				}
			else
				for(int i=0; i<2; i++)
				{
					if(!flag)
						break;
					Character.setIcon(icon[i]);
					try{
						Thread.sleep(500);
					}catch(Exception e){}
				}
		}
	}
}