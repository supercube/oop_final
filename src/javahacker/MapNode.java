package javahacker;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class MapNode extends JButton{

	private int ID;
	private Icon icon;
	private static final int[][] ImageSize = {{40,40},{60,60},{80,80}};
	private int NodeType;
	
	// position
	private int PositionX, PositionY;
	// border
	private static final Color activeColor = new Color(225,127,39);
	private static final MatteBorder activeBorder = new MatteBorder(3,3,3,3,activeColor);
	private static final Border defaultBorder = UIManager.getBorder("Button.border");
	// direction
	private MapNode[] Neighbor;	// up down left right
	
	// x: Xposition, y: Yposition, size: 0:small 1: medium 2: large
	// NodeType: 1: county, 2: battlefield
	public MapNode(int x, int y, int size, int ID, MouseAdapter M, int NodeType)
	{
		this.ID = ID;
		this.NodeType = NodeType;
		this.setContentAreaFilled(false);
		this.setBorder(null);
		this.setSize(ImageSize[size][0], ImageSize[size][1]);
		this.setBounds(x-ImageSize[size][0]/2, y-ImageSize[size][1]/2, ImageSize[size][0], ImageSize[size][1]);
		addMouseListener(M);
	}
	public int getNodeType()
	{
		return NodeType;
	}
	public void setNeighbor(MapNode... neighbor)
	{
		Neighbor = neighbor;
	}
	public void Activate()
	{
		this.setBorder(activeBorder);
	}
	public void InActivate()
	{
		this.setBorder(null);
	}
	public boolean IsNeighbor(MapNode node)
	{
		for(int i=0; i<Neighbor.length; i++)
			if(Neighbor[i] == node)
				return true;
		return false;
	}
}
