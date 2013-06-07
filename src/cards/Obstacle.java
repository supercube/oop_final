package cards;

public abstract class Obstacle implements DImage{
	private int HP;
    private String name;
    
    protected int _img_id;
    
    static protected final boolean checkHP(int _HP){
        return (_HP >= 0);
    }

    
    protected final boolean setHP(int HP){
    	this.HP = HP;
    	if (checkHP(HP))
            return true;
        else
            return false;
    }
    
    protected final boolean setName(String name){
        if (name != null){
            this.name = name;
            return true;
        }
        else{
            return false;
        }
    }
    
    protected final String getName(){
        return name;
    }
    
    protected final int getHP(){
        return HP;
    }
}
