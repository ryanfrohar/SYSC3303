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
	
	public void clearTable(){
		this.empty=true;
	}

	public Ingredient[] getContents() {
		return ingredients;
	}
	
	public void put(Ingredient[] randIngredients) {
		this.ingredients=randIngredients;
		this.empty=false;
	}
	
	public void incrementSandwichesMade() {
		sandwichesMade++;
	}
	
	public boolean maxReached() {
		return (MAX_SANDWICHES >= this.sandwichesMade);
	}
	public static void main(String args[]) {
		Table table = new Table();
		AgentThread agent = new AgentThread(table);
		ChefThread peanutButter = new ChefThread(Ingredient.PEANUT_BUTTER, table);
		ChefThread bread = new ChefThread(Ingredient.BREAD, table);
		ChefThread jelly = new ChefThread(Ingredient.JAM, table);
		
		agent.start();
		peanutButter.start();
		bread.start();
		jelly.start();
		
	}
}
