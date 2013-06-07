package javahacker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import cards.Card;
import cards.ConcreteCard;
import cards.LeadingRole;
import cards.LeadingRoleCard;
import cards.Monster;

public abstract class Arena implements ActionListener{
	
	protected Timer timer;
	protected Random rnd;
	protected ArenaFrame window;
	protected Constant.GameStatus game;
	protected int init_count_down;
	protected Card[] deck;
	protected LeadingRoleCard playerCard;
	protected LeadingRole player;
	protected Monster[] monsters;
	protected int nextToDraw;
	protected int nextToPlace;
	public Arena(Card[] deck, LeadingRoleCard playerCard){
		try{
			this.deck = deck;
			this.playerCard = playerCard;
			player = (LeadingRole) playerCard.newMonster(100, 300);
			game = Constant.GameStatus.UNDEFINED;
			window = new ArenaFrame("Park", player, "Images/ArenaBackground.png", "Images/Park.png", "Images/CardPanel.png");
			timer = new Timer(5, this);
			rnd = new Random();
			
		}catch(Exception e){
			System.out.print("Constructor ArenaPark(): ");
			System.out.println(e);
		}
		
	}
	

	public void init(){
		try{
			nextToDraw = 0;
			nextToPlace = 0;
			/* generate map for arena */
			window.reset();
			
			/* add Obstacle and foreground image to _map in advance */
			
			
			/* draw 10 cards */
			draw(10);
			window.addToArenaIOPanel(player.getImage(), player.getX(), player.getY(), player.getImageWidth(), player.getImageHeight(), 0);
			window.repaint();
			/* add Object*/
			
			/* set Image */
			
		}catch(Exception e){
			System.out.print("init(): ");
			e.printStackTrace();
		}
	}
	
	protected boolean draw(int number){
		for(int i = 0; i < number; i++){
			if(nextToPlace >= 10 || nextToDraw >= deck.length){
				return false;
			}
			window.addToHand(new ConcreteCard(deck[nextToDraw]), nextToPlace);
			nextToDraw++;
			nextToPlace++;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e){
		switch(game){
			case INIT:
				init_count_down--;
				if(init_count_down <= 0){
					game = Constant.GameStatus.INGAME;
					init_count_down = 50;
				}
				break;
			case INGAME:
			case GAMEOVER:
			case WIN:
				inGameAction(e);
				break;
			case UNDEFINED:
				game = Constant.GameStatus.INIT;
				init();
				timer.start();
				break;
			
			default:;
		}
	}

	public void inGameAction(ActionEvent e){
		/* reset fog of war*/
		
		/* oneTimeStep for Skills/Obstacles */
		
		/* oneTimeStep for Pets */
		player.oneTimeStep(this);
		System.out.println(player.getImageHeight());
		window.addToArenaIOPanel(player.getImage(), player.getX(), player.getY(), player.getImageWidth(), player.getImageHeight(), 0);
		
			/* adjust fog of war */
		window.repaint();
	}
	
	
	
	/* the entry point */
	public boolean fight(){
			game = Constant.GameStatus.INIT;
			init();
			timer.start();
			try{
				Thread.yield();
			}catch(Exception e){
				System.out.println("wait err" + e);
			}
			return false;
	}
	
	public void show(){
		window.repaint();
	}
	
	/*public POOCoordinate getPosition(POOPet p){
		for(int id = 0; id < _parr.length; id++){
			if(p == _parr[id]){
				return (POOCoordinate) new Coordinate(_pet_pos[id]);
			}
		}
		return null;
	}
	
	public POOCoordinate getSize(){
		return (POOCoordinate) new Coordinate(_no_cell_x, _no_cell_y);
	}
	
	public int getPetId(POOPet pet){
		for(int id = 0; id < _parr.length; id++)
			if(_parr[id] == pet)
				return id;
		return -1;
	}
	
	public Cell[][] getMap(){
		return _map;
	}*/
	
	/* get cells in sight for Pet 
	public ArrayList<Cell> getSight(POOPet pet){
		int sight_range = ((Pet)pet).getSightRange();
		ArrayList<Cell> sight = new ArrayList<Cell>(0);
		POOCoordinate pos = getPosition(pet);
		int x_lower = 0, y_lower = 0, x_upper = _no_cell_x - 1, y_upper = _no_cell_y - 1;
		if(pos.x - sight_range > x_lower)
			x_lower = pos.x - sight_range;
		if(pos.y - sight_range > y_lower)
			y_lower = pos.y - sight_range;
		if(pos.x + sight_range < x_upper)
			x_upper = pos.x + sight_range;
		if(pos.y + sight_range < y_upper)
			y_upper = pos.y + sight_range;
		
		
		for(int i = x_lower; i <= x_upper; i++){
			for(int j = y_lower; j <= y_upper; j++){
				if((i-pos.x)*(i-pos.x)+(j-pos.y)*(j-pos.y) <= sight_range * sight_range){
					sight.add(new Cell(_map[i][j]));
				}
			}
		}
		return sight;
	}*/
	
	/* adjust fog matrix 
	private void setFog(int sight_range, POOCoordinate pos, POOConstant.Fog fog){
		int x_times = POOConstant.CELL_X_SIZE / POOConstant.FOG_X_SIZE;
		int y_times = POOConstant.CELL_Y_SIZE / POOConstant.FOG_Y_SIZE;
		int x_add = x_times/2, y_add = y_times/2;
		sight_range = sight_range * ((x_times + y_times)/2);
		Coordinate new_pos = new Coordinate(pos.x * x_times + x_add, pos.y * y_times + y_add);
		int x_lower = 0, y_lower = 0, x_upper = _no_fog_x - 1, y_upper = _no_fog_y - 1;
		if(new_pos.x - sight_range > x_lower)
			x_lower = new_pos.x - sight_range;
		if(new_pos.y - sight_range > y_lower)
			y_lower = new_pos.y - sight_range;
		if(new_pos.x + sight_range < x_upper)
			x_upper = new_pos.x + sight_range;
		if(new_pos.y + sight_range < y_upper)
			y_upper = new_pos.y + sight_range;
		for(int i = x_lower; i <= x_upper; i++){
			for(int j = y_lower; j <= y_upper; j++){
				if((i-new_pos.x)*(i-new_pos.x)+(j-new_pos.y)*(j-new_pos.y) <= sight_range * sight_range){
					_fog_of_war[i][j] = fog;
				}
			}
		}
	}*/
}
