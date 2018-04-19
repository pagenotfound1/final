import javax.persistence.*;
@Entity
public class Player {
	String name;
	Integer totalmatch;
	Integer run;
	
	public Player(){}
	
	Player(String name,Integer totalmatch,Integer run)
	{
	this.name=name;
	this.totalmatch=totalmatch;
	this.run=run;	
	}
	public String toString() {
		return String.format("(%s, %d,%d)\n", this.name, this.totalmatch, this.run);
	}
	}
