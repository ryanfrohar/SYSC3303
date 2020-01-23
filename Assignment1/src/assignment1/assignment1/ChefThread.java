package assignment1;

/**
 * @author Ryan Frohar
 *
 */
public class ChefThread implements Runnable{
	private Ingredient ingredient;
	private Table table;

	public ChefThread(Ingredient ingredient, Table table) {
		this.ingredient = ingredient;
		this.table = table;
	}
	
	private void getFromTable() {
		while(!(table.maxReached())) {
			if(!(table.isEmpty())) {
				boolean canMakeSandwich = true;
				for(Ingredient i : table.getContents()) {
					if(i == this.ingredient) {
						canMakeSandwich = false;
						break;
					}
				}
				if(canMakeSandwich){
					System.out.println(this.ingredient + " chef is getting");
					this.table.get();
					table.incrementSandwichesMade();
					System.out.println("The " + this.ingredient + " chef has made a sandwich");
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
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
