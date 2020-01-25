package assignment1;

public class Main {

	public static void main(String args[]) {
		Table table = new Table();
		Thread agent = new Thread(new AgentThread(table));
		Thread peanutButter = new Thread(new ChefThread(Ingredient.PEANUT_BUTTER, table));
		Thread bread = new Thread(new ChefThread(Ingredient.BREAD, table));
		Thread jelly = new Thread(new ChefThread(Ingredient.JAM, table));
		
		agent.start();
		peanutButter.start();
		bread.start();
		jelly.start();
	}
}
