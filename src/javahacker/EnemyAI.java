package javahacker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import cards.*;

public class EnemyAI implements ActionListener{
	protected Card[] E_deck;
	protected int E_nextToDraw;
	protected PortalCard[] E_portal_card;
	protected Portal[] E_portals;
	protected int[] E_portal_ids;
	protected Card[] hands;
	protected Arena arena;
	protected Random rnd;
	protected Timer timer;
	
	public EnemyAI(Card[] E_deck, Arena arena, int team){
		try{
			this.E_deck = new Card[E_deck.length];
			for(int i = 0; i < E_deck.length; i++){
				this.E_deck[i] = E_deck[i].copy();
			}
			this.arena = arena;
			hands = new Card[10];
			for(int i = 0; i < hands.length; i++)
				hands[i] = null;
			E_portal_card = new PortalCard[Constant.MAXPORTAL];
			E_portals = new Portal[Constant.MAXPORTAL];
			for(int i = 0; i < E_portals.length; i++)
				E_portals[i] = null;
			E_portal_ids = new int[Constant.MAXPORTAL];
			E_nextToDraw = 0;
			
			
			arena.shuffle(this.E_deck);
			draw(10);
			rnd = new Random();
			timer = new Timer(8000, this);
			timer.start();
			Thread.yield();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(arena.game == Constant.GameStatus.PAUSE){
			return;
		}
		draw(1);
		for(int i = 0; i < hands.length; i++){
			if(hands[i] != null){
				if(hands[i] instanceof PortalCard){
					for(int j = 0; j < E_portal_card.length; j++){
						if(E_portal_card[j] == null){
							E_portal_card[j] = (PortalCard)hands[i];
							hands[i] = null;
							break;
						}
					}
				}else if(hands[i] instanceof MonsterCard){
					for(int j = 0; j < E_portal_card.length; j++){
						if(E_portal_card[j] != null){
							if(E_portal_card[j].getMonster(0) == null){
								E_portal_card[j].setMonster((MonsterCard)hands[i], 0);
								hands[i] = null;
								break;
							}else if(E_portal_card[j].getMonster(1) == null){
								E_portal_card[j].setMonster((MonsterCard)hands[i], 1);
								hands[i] = null;
								break;
							}
						}
					}
				}else if(hands[i] instanceof SkillCard){
					for(int j = 0; j < E_portal_card.length; j++){
						if(E_portal_card[j] != null){
							MonsterCard mcard;
							if((mcard = E_portal_card[j].getMonster(0)) != null && mcard.getMP() >= ((SkillCard)hands[i]).getMPConsume()){
								if(mcard.getSkill(0) == null){
									mcard.setSkill((SkillCard)hands[i], 0);
									hands[i] = null;
									break;
								}else if(mcard.getSkill(1) == null){
									mcard.setSkill((SkillCard)hands[i], 1);
									hands[i] = null;
									break;
								}
							}else if((mcard = E_portal_card[j].getMonster(1)) != null && mcard.getMP() >= ((SkillCard)hands[i]).getMPConsume()){
								if(mcard.getSkill(0) == null){
									mcard.setSkill((SkillCard)hands[i], 0);
									hands[i] = null;
									break;
								}else if(mcard.getSkill(1) == null){
									mcard.setSkill((SkillCard)hands[i], 1);
									hands[i] = null;
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	protected boolean draw(int number){
		for(int i = 0; i < number; i++){
			if(E_nextToDraw >= E_deck.length){
				return false;
			}
			try{
				Card card = E_deck[E_nextToDraw].copy();
				if(addToHand(card)){
					E_nextToDraw++;
				}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	protected boolean addToHand(Card card){
		for(int i = 0; i < hands.length; i++){
			if(hands[i] == null){
				hands[i] = card;
				return true;
			}
		}
		return false;
	}
}
