package javahacker;

import cards.*;

import javax.swing.*;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.*;

public class ArenaPark extends Arena{
	
	
	/* constructor-decided variables */
	
	
	/* init-decided variables */
	//private Cell[][] _map;						// map of arena
	
	//private Coordinate pos[];				// positions of pets
	//private ArrayList<Cell> _items; 			// cells that will store _items
	
	
	public ArenaPark(Card[] deck, Card[] E_deck, LeadingRoleCard playerCard){
		super(deck, E_deck, playerCard,  "Park", "Images/Other/ArenaBackground.png", "Images/Other/Park.png", "Images/Other/CardPanel.png");
		
	}
	
}

