package javahacker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;


import cards.*;

public abstract class Arena implements ActionListener{
	
	protected Timer timer;
	protected Random rnd;
	protected ArenaFrame window;
	protected Constant.GameStatus game;
	protected int battlefield_todo;
	
	
	/* player's team: 1 */
	protected Card[] deck;
	protected Portal jvm, E_jvm;
	protected LeadingRoleCard playerCard;
	protected LeadingRole player;
	protected int nextToDraw;
	protected PortalCard[] portal_card;
	protected Portal[] portals;
	protected int[] portal_ids;
	protected ArrayList<Monster> monsters;
	protected ArrayList<Integer> monster_ids;
	protected ArrayList<Skill> skills;
	protected ArrayList<Integer> skill_ids;
	protected Card toAdd;
	protected EnemyAI enemy;
	protected ArrayList<Card> reward;
	
	protected int init_count_down;
	protected int draw_count_down;
	protected int ending_count_down;
	protected int no_draw;
	protected ArrayList<Integer> body_ids;
	protected ArrayList<Integer> body_count_down
	;
	public Arena(Card[] deck, Card[] E_deck, LeadingRoleCard playerCard, String Name, String background, String battlefield, String cardpanel){
		try{
			rnd = new Random();
			this.playerCard = playerCard;
			this.deck = new Card[deck.length];
			for(int i = 0; i < deck.length; i++){
				this.deck[i] = deck[i].copy();
			}
			
			
			player = (LeadingRole) playerCard.newMonster(1, 100, 300);
			portal_card = new PortalCard[Constant.MAXPORTAL];
			portals = new Portal[Constant.MAXPORTAL];
			for(int i = 0; i < portals.length; i++)
				portals[i] = null;
			portal_ids = new int[Constant.MAXPORTAL];
			monsters = new ArrayList<Monster>();
			monster_ids = new ArrayList<Integer>();
			skills = new ArrayList<Skill>();
			skill_ids = new ArrayList<Integer>();
			reward = new ArrayList<Card>();
			body_ids = new ArrayList<Integer>();
			body_count_down = new ArrayList<Integer>();
			battlefield_todo = 0;
			
			enemy = new EnemyAI(E_deck, this, 2);
			game = Constant.GameStatus.UNDEFINED;
			window = new ArenaFrame(Name, this, background, battlefield, cardpanel);
			timer = new Timer(8, this);
			
			shuffle(this.deck);
			jvm = new JVMCard().newPortal(1, 75, 450);
			E_jvm = new CastleCard().newPortal(2, 1125, 75);
			
		}catch(Exception e){
			System.out.print("Constructor ArenaPark(): ");
			System.out.println(e);
		}
		
	}
	

	public void init(){
		try{
			nextToDraw = 0;
			draw_count_down = 0;
			ending_count_down = Constant.ENDING_COUNT_DOWN;
			
			/* generate map for arena */
			window.reset();
			
			/* add Obstacle and foreground image to _map in advance */
			
			
			
			/* draw 7 cards */
			no_draw = 7;
			draw(no_draw);
			window.addToBattleField(player.getImage(), player.getX(), player.getY(), player.getImageWidth(), player.getImageHeight(), 0);
			window.addToBackground(jvm.getImage(), jvm.getX(), jvm.getY(), jvm.getImageWidth(), jvm.getImageHeight(), 0);
			window.addToBackground(E_jvm.getImage(), E_jvm.getX(), E_jvm.getY(), E_jvm.getImageWidth(), E_jvm.getImageHeight(), 1);
			
			/* add enemy default portal */
			for(int i = 0; i < enemy.E_portal_card.length; i++){
				if(enemy.E_portal_card[i] == null){
					enemy.E_portal_card[i] = new HellGateCard();
					int x = 1125;
					int y = 220;
					enemy.E_portals[i] = enemy.E_portal_card[i].newPortal(2, x, y);
					if(enemy.E_portals[i] == null){
						break;
					}
					int id = window.addToBackground(enemy.E_portals[i].getImage(), x, y, enemy.E_portals[i].getImageWidth(), enemy.E_portals[i].getImageHeight(), -1);
					enemy.E_portal_ids[i] = id;
					break;
				}
			}
			
			
			/* add Object*/
			
			/* set Image */
			
			init_count_down = 200;
			window.repaint();
		}catch(Exception e){
			System.out.print("init(): ");
			e.printStackTrace();
		}
	}
	
	protected boolean draw(int number){
		for(int i = 0; i < number; i++){
			if(nextToDraw >= deck.length){
				return false;
			}
			try{
				Card card = deck[nextToDraw].copy();
				if(no_draw > 0 && window.addToHand(new ConcreteCard(card))){
					nextToDraw++;
					no_draw--;
				}else{
					break;
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e){
		switch(game){
			case INIT:
				
				init_count_down--;
				if(init_count_down <= 0){
					game = Constant.GameStatus.INGAME;
					init_count_down = 1000;
				}
				
				break;
			case GAMEOVER:
			case WIN:
				ending_count_down--;
				if(ending_count_down <= 0){
					game = Constant.GameStatus.END;
				}
			case INGAME:
				inGameAction(e);
				break;
			case UNDEFINED:
				game = Constant.GameStatus.INIT;
				init();
				timer.start();
				break;
			case PAUSE:
				break;
			default:;
		}
		window.redraw();
	}

	public void inGameAction(ActionEvent e){
		
		/* dead body count down */
		for(int id = 0; id < body_ids.size(); id++){
			int prev = body_count_down.get(id);
			prev--;
			if(prev <= 0){
				window.removeFromBackground(body_ids.get(id));
				body_ids.remove(id);
				body_count_down.remove(id);
				id--;
			}else{
				body_count_down.set(id, prev);
			}
		}
		
		
		/* oneTimeStep for Player */
		ArrayList<Skill> tmp_skills;
		tmp_skills = player.oneTimeStep(this);
		if(tmp_skills == null){
			window.removeFromBattleField(0);
			window.addToBackground(player.getImage(), player.getX(), player.getY(), player.getImageWidth(), player.getImageHeight(), -1);
			if(game == Constant.GameStatus.INGAME){
				game = Constant.GameStatus.GAMEOVER;
				reward.add(enemy.E_deck[rnd.nextInt(enemy.E_deck.length)]);
			}
		}else{
			window.addToBattleField(player.getImage(), player.getX(), player.getY(), player.getImageWidth(), player.getImageHeight(), 0);
		}
		
		/* oneTimeStep for Skills */
		for(int id = 0; id < skills.size(); id++){
			Skill s = skills.get(id);
			if(s.oneTimeStep(this)){
				window.removeFromBattleField(skill_ids.get(id));
				skills.remove(id);
				skill_ids.remove(id);
				id--;
			}else{
				window.addToBattleField(s.getImage(), s.getX(), s.getY(), s.getImageWidth(), s.getImageHeight(), skill_ids.get(id));
			}
		}
		
		
		
		
		/* oneTimeStep for Monsters */
		for(int id = 0; id < monsters.size(); id++){
			Monster m = monsters.get(id);
			tmp_skills = m.oneTimeStep(this);
			
			// is dead
			if(tmp_skills == null){
				window.removeFromBattleField(monster_ids.get(id));
				monsters.remove(id);
				monster_ids.remove(id);
				int idtmp = window.addToBackground(m.getImage(), m.getX(), m.getY(), m.getImageWidth(), m.getImageHeight(), -1);
				body_ids.add(idtmp);
				body_count_down.add(Constant.BODY_COUNT_DOWN);
				id--;
			}else{
				for(Skill s : tmp_skills){
					addSkill(s);
				}
				window.addToBattleField(m.getImage(), m.getX(), m.getY(), m.getImageWidth(), m.getImageHeight(), monster_ids.get(id));
			}
		}
		
		/* oneTimeStep for Portals */
		for(int i = 0; i < portal_card.length; i++){
			if(portals[i] != null && portals[i] != null){
				ArrayList<Monster> tmp_monsters = portals[i].oneTimeStep(this);
				/* portal is broken */
				if(tmp_monsters == null){
					window.removeFromBackground(portal_ids[i]);
					window.removePortalCard(i);
					int idtmp = window.addToBackground(portals[i].getImage(), portals[i].getX(), portals[i].getY(), portals[i].getImageWidth(), portals[i].getImageHeight(), -1);
					body_ids.add(idtmp);
					body_count_down.add(Constant.BODY_COUNT_DOWN);
					portals[i] = null;
					portal_card[i] = null;
				}else{
					for(Monster m: tmp_monsters){
						addMonster(m);
					}
					
				}
			}
		}
		
		/* oneTimeStep for Enemy Portals */
		for(int i = 0; i < enemy.E_portal_card.length; i++){
			if(enemy.E_portals[i] != null && enemy.E_portals[i] != null){
				ArrayList<Monster> tmp_monsters = enemy.E_portals[i].oneTimeStep(this);
				/* portal is broken */
				if(tmp_monsters == null){
					window.removeFromBackground(enemy.E_portal_ids[i]);
					int idtmp = window.addToBackground(enemy.E_portals[i].getImage(), enemy.E_portals[i].getX(), enemy.E_portals[i].getY(), enemy.E_portals[i].getImageWidth(), enemy.E_portals[i].getImageHeight(), -1);
					body_ids.add(idtmp);
					body_count_down.add(Constant.BODY_COUNT_DOWN);
					enemy.E_portals[i] = null;
					enemy.E_portal_card[i] = null;
				}else{
					for(Monster m: tmp_monsters){
						monsters.add(m);
						int id = window.addToBattleField(m.getImage(), m.getX(), m.getY(), m.getImageWidth(), m.getImageHeight(), -1);
						monster_ids.add(id);
					}
				}
			}
		}
		
		/* add enemy portals */
		for(int i = 0; i < enemy.E_portal_card.length; i++){
			if(enemy.E_portal_card[i] != null && enemy.E_portals[i] == null){
				int x = 600 + rnd.nextInt(500);
				int y = 100 + rnd.nextInt(300);
				enemy.E_portals[i] = enemy.E_portal_card[i].newPortal(2, x, y);
				if(enemy.E_portals[i] == null){
					break;
				}
				int id = window.addToBackground(enemy.E_portals[i].getImage(), x, y, enemy.E_portals[i].getImageWidth(), enemy.E_portals[i].getImageHeight(), -1);
				enemy.E_portal_ids[i] = id;
			}
		}
		
		/* oneTimeStep for JVM */
		ArrayList<Monster>  tmp_monsters = jvm.oneTimeStep(this);
		if(tmp_monsters == null){
			window.removeFromBackground(0);
			window.addToBackground(jvm.getImage(), jvm.getX(), jvm.getY(), jvm.getImageWidth(), jvm.getImageHeight(), -1);
			if(game == Constant.GameStatus.INGAME){
				game = Constant.GameStatus.GAMEOVER;
				reward.add(enemy.E_deck[rnd.nextInt(enemy.E_deck.length)]);
			}
		}else{
			window.addToBackground(jvm.getImage(), jvm.getX(), jvm.getY(), jvm.getImageWidth(), jvm.getImageHeight(), 0);
		}
		/* oneTimeStep for enemy JVM */
		tmp_monsters = E_jvm.oneTimeStep(this);
		if(tmp_monsters == null){
			window.removeFromBackground(1);
			window.addToBackground(E_jvm.getImage(), E_jvm.getX(), E_jvm.getY(), E_jvm.getImageWidth(), E_jvm.getImageHeight(), -1);
			if(game == Constant.GameStatus.INGAME){
				game = Constant.GameStatus.WIN;
				int r1 = rnd.nextInt(enemy.E_deck.length);
				int r2 = rnd.nextInt(enemy.E_deck.length);
				int r3 = rnd.nextInt(enemy.E_deck.length);
				while(r2 == r1){
					r2 = rnd.nextInt(enemy.E_deck.length);
				}
				while(r3 == r1 || r3 == r2){
					r3 = rnd.nextInt(enemy.E_deck.length);
				}
				reward.add(enemy.E_deck[r1]);
				reward.add(enemy.E_deck[r2]);
				reward.add(enemy.E_deck[r3]);
			}
		}else{
			window.addToBackground(E_jvm.getImage(), E_jvm.getX(), E_jvm.getY(), E_jvm.getImageWidth(), E_jvm.getImageHeight(), 1);
		}
		
		draw_count_down--;
		if(draw_count_down <= 0){
			draw_count_down = Constant.DRAW_COUNT_DOWN;
			if(nextToDraw + no_draw < deck.length)
				no_draw++;
		}
	}
	
	
	public boolean addMonster(Monster m){
		monsters.add(m);
		int id = window.addToBattleField(m.getImage(), m.getX(), m.getY(), m.getImageWidth(), m.getImageHeight(), -1);
		monster_ids.add(id);
		return true;
	}
	
	public boolean addPortal(PortalCard pcard, int team, int x, int y){
		Portal p = pcard.newPortal(team, x, y);
		if(p == null){
			return false;
		}
		for(int i = 0; i < portal_card.length; i++){
			if(portal_card[i] == pcard){
				portals[i] = p;
				int id = window.addToBackground(p.getImage(), x, y, p.getImageWidth(), p.getImageHeight(), -1);
				portal_ids[i] = id;
				return true;
			}
		}
		return false;
	}
	
	public boolean addSkill(Skill skill){
		if(skill == null)
			return false;
		skills.add(skill);
		int id = window.addToBattleField(skill.getImage(), skill.getX(), skill.getY(), skill.getImageWidth(), skill.getImageHeight(), -1);
		skill_ids.add(id);
		return true;
	}
	
	public ArrayList<Cell> getSight(Monster monster){
		return getSight(monster.getX(), monster.getY(), monster.getSightRange());
	}
	
	public ArrayList<Cell> getSight(Skill skill){
		return getSight(skill.getX(), skill.getY(), skill.getSightRange());
	}
	
	public ArrayList<Cell> getSight(int x, int y, int range){
		int diffx, diffy;
		int rad;
		ArrayList<Cell> sight = new ArrayList<Cell>();
		for(Monster m: monsters){
			diffx = x - m.getX();
			diffy = y - m.getY();
			rad = Math.min(m.getImageHeight(), m.getImageWidth())/2;
			if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
				Cell c = new Cell(Constant.CardType.MONSTER, m, m.getTeam());
				sight.add(c);
			}
		}
		
		for(Portal p: portals){
			if(p == null)
				continue;
			diffx = x - p.getX();
			diffy = y - p.getY();
			rad = Math.min(p.getImageHeight(), p.getImageWidth())/2;
			if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
				Cell c = new Cell(Constant.CardType.PORTAL, p, p.getTeam());
				sight.add(c);
			}
		}
		
		for(Portal p: enemy.E_portals){
			if(p == null)
				continue;
			diffx = x - p.getX();
			diffy = y - p.getY();
			rad = Math.min(p.getImageHeight(), p.getImageWidth())/2;
			if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
				Cell c = new Cell(Constant.CardType.PORTAL, p, p.getTeam());
				sight.add(c);
			}
		}
		
		for(Skill s: skills){
			diffx = x - s.getX();
			diffy = y - s.getY();
			rad = Math.min(s.getImageHeight(), s.getImageWidth())/2;
			if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
				Cell c = new Cell(Constant.CardType.SKILL, s, s.getTeam());
				sight.add(c);
			}
		}
		
		diffx = x - player.getX();
		diffy = y - player.getY();
		rad = Math.min(player.getImageHeight(), player.getImageWidth())/2;
		if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
			Cell c = new Cell(Constant.CardType.PLAYER, player, player.getTeam());
			sight.add(c);
			
		}
		diffx = x - jvm.getX();
		diffy = y - jvm.getY();
		rad = Math.min(jvm.getImageHeight(), jvm.getImageWidth())/2;
		if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
			Cell c = new Cell(Constant.CardType.JVM, jvm, jvm.getTeam());
			sight.add(c);
			
		}
		
		diffx = x - E_jvm.getX();
		diffy = y - E_jvm.getY();
		rad = Math.min(E_jvm.getImageHeight(), E_jvm.getImageWidth())/2;
		if(Math.sqrt(diffx*diffx + diffy*diffy) - rad <= range){
			Cell c = new Cell(Constant.CardType.JVM, E_jvm, E_jvm.getTeam());
			sight.add(c);
			
		}
		
		return sight;
	}
	
	/* the entry point */
	public ArrayList<Card> fight(){
			game = Constant.GameStatus.INIT;
			init();
			timer.start();
			while(true){
				try{
					Thread.yield();
					Thread.sleep(100);
				}catch(Exception e){
					System.out.println("wait err" + e);
				}
				if(game == Constant.GameStatus.END){
					timer.stop();
					window.dispose();
					return reward;
				}
			}
			
			
	}
	
	public void show(){
		window.repaint();
	}
	
	public boolean hasNext(){
		if(nextToDraw < deck.length){
			return true;
		}else{
			return false;
		}
	}
	
	public void shuffle(Card[] deck){
		for(int i = 1; i < deck.length; i++){
			int j = rnd.nextInt(deck.length);
			if(i != j && j != 0){
				Card tmp = deck[i];
				deck[i] = deck[j];
				deck[j] = tmp;
			}
		}
		for(int i = 1; i < deck.length; i++){
			int j = rnd.nextInt(deck.length);
			if(i != j && j != 0){
				Card tmp = deck[i];
				deck[i] = deck[j];
				deck[j] = tmp;
			}
		}
	}
	
	public Portal getJVM(int team){
		if(team == 2){
			return jvm;
		}else{
			return E_jvm;
		}
	}
	
}
