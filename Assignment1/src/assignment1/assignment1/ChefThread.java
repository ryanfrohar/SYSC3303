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
		//while the sandwiches made is less than 20, loop
		while(!(table.maxReached())) {
			//only loop is the table is NOT empty
			if(!(table.isEmpty())) {
				boolean canMakeSandwich = true;
				//iterate through the array of Ingredients on table and see if respective chef is capable of invoking get()
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
					System.out.println("The " + this.ingredient + " chef has made a sandwich for a total of " + table.numberSandwiches());
				}
				try {
					Thread.sleep(1000);
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
