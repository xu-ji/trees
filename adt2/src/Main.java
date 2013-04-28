import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		AVLTree<String, Integer> food = new AVLTree<String, Integer>();
		List<String> values = Arrays.asList("apples", "chicken", "bananas", "beef", "spinach");
		for (String v : values) {
			food.put(v, v.hashCode());
		}
		System.out.println(food);
		food.remove("apples");
		System.out.println(food);
		food.put("apples", 132);
		food.put("chicken", 000);
		System.out.println(food);
	}
	
}
