/**
 * 
 */
package assignment1;

/**
 * @author Ryan Frohar
 *
 */
public class Table {
	public final int MAX_SANDWICHES = 20;
	
	private Ingredient[] ingredients;
	private boolean empty;
	private int sandwichesMade;
	
	/**
	 *Constructor 
	 */
	public Table() {
		ingredients = new Ingredient[2];
		empty = true;
		sandwichesMade=0;
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public synchronized void get() {
	//Synchronized method invoked by ChefThread
		while(this.empty) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				return;
			}
		}
		this.ingredients = null;
		this.empty=true;
		notifyAll();
	}

	public synchronized Ingredient[] getContents() {
	//Returns array of Ingredients CURRENTLY on table so that ChefThread can check and see if they're allowed to make a sandwich
		return ingredients;
	}
	
	public synchronized void put(Ingredient[] randIngredients) {
	//Synchronized method invoked by AgentThread
		while(!(this.empty)) {
			try { 
				wait();
			}
			catch (InterruptedException e) {
				return;
			}
		}
		this.ingredients=randIngredients;
		this.empty=false;
		notifyAll();
	}
	
	public void incrementSandwichesMade() {
	//When ChefThread creates a sandwich, increment variable
		sandwichesMade++;
	}
	
	public boolean maxReached() {
	//Used to determine when ChefThread should stop invoking get() method on table
		return (MAX_SANDWICHES <= this.sandwichesMade);
	}
	
	public int numberSandwiches() {
		return this.sandwichesMade;
	}
}
