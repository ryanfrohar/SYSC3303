package assignment1;

public class Main {

	public static void main(String args[]) {
	//create all objects
		Table table = new Table();
		Thread agent = new Thread(new AgentThread(table));
		Thread peanutButter = new Thread(new ChefThread(Ingredient.PEANUT_BUTTER, table));
		Thread bread = new Thread(new ChefThread(Ingredient.BREAD, table));
		Thread jelly = new Thread(new ChefThread(Ingredient.JAM, table));
		//invoke .start on Thread objects
		agent.start();
		peanutButter.start();
		bread.start();
		jelly.start();
	}
}
