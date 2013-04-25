package acme.business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import acme.objects.Item;
import acme.objects.ShoppingCart;

public class CalculateCosts {
	private static final double GST = 0.05;
	private static final double PST = 0.07;

	// P.S.: this method assumes that the quantity included in the item is the
	// quantity that the
	// customer is going to be purchasing
	public static double getTotalCost(ShoppingCart cart) {
		double total = 0;
		double price = 0;
		int quantity = 0;
		AccessItems AI = new AccessItems();
		Item tempItem;

		if (cart != null) {

			HashMap<String, Integer> temp = cart.getItems();
			Set<?> set = temp.entrySet();
			Iterator<?> iterator = set.iterator();

			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) (iterator.next());
				tempItem = AI.getItem(new Item((String) entry.getKey()));
				price = tempItem.getPrice();
				quantity = (Integer) entry.getValue();
				price = price * quantity;
				total += price;
			}
			total = calculateTaxes(total);
		}

		return roundDouble(total);
	}

	public static double calculateTaxes(double price) {
		double total = price;

		if (price > 0) {
			total += price * GST;
			total += price * PST;
		}
		return roundDouble(total);
	}

	public static double roundDouble(double value) {
		double result = value * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}
}
