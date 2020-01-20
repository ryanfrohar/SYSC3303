package assignment1;

/**
 * @author Ryan Frohar
 *
 */
public class ChefThread extends Thread{
	private Ingredient ingredient;
	private Table table;

	public ChefThread(Ingredient ingredient, Table table) {
		this.ingredient = ingredient;
		this.table = table;
	}
	
	private synchronized void getFromTable() {
		while(!(table.maxReached())){
			while(table.isEmpty()) {
				try {
					wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
			if(table.isEmpty()) {
				boolean canMakeSandwich = true;
				for(Ingredient i : table.getContents()) {
					if(i == this.ingredient) {
						canMakeSandwich = false;
						break;
					}
				}
				if(canMakeSandwich){
					table.clearTable();
					table.incrementSandwichesMade();
					System.out.println("The " + this.ingredient + " chef has made a sandwich");
				}
			}
		}
	}
	/**
	 * Override run method from Thread class 
	 */
	public void run() {
		getFromTable();
	}

}
