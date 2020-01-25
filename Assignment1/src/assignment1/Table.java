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
		return ingredients;
	}
	
	public synchronized void put(Ingredient[] randIngredients) {
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
		sandwichesMade++;
	}
	
	public boolean maxReached() {
		return (MAX_SANDWICHES <= this.sandwichesMade);
	}
	
	public int numberSandwiches() {
		return this.sandwichesMade;
	}
}
