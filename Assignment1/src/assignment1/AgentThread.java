/**
 * 
 */
package assignment1;

import java.util.Random;
/**
 * @author Ryan Frohar
 *
 */
public class AgentThread extends Thread{
	
	private Table table;
	private Random rand;
	//invoke values() method on enum Ingredient for array of all ingredients
	private final Ingredient[] ingredientArr = Ingredient.values();
	
	public AgentThread(Table table) {
		rand = new Random();
		this.table = table;
	}
	
	private synchronized void putOnTable() {
		while(!(table.maxReached())) {
			while(!(table.isEmpty())) {
				try { 
					wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			Ingredient[] randIngredients = new Ingredient[2];
			//Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive), drawn from this random number generator's sequence.
			randIngredients[0] = ingredientArr[rand.nextInt(ingredientArr.length)];
			
			//pick a second random ingredient which is different from the first
			do {
				randIngredients[1]= ingredientArr[rand.nextInt(ingredientArr.length)];
			} while(randIngredients[0] == randIngredients[1]);
			
			table.put(randIngredients);
			System.out.println("The agent has put " + randIngredients[0] + " and " + randIngredients[1] + " on the table.");
		}
	}
	
	public void run() {
		putOnTable();
	}
	

}
