package acme.business;

import java.util.ArrayList;

import acme.application.Main;
import acme.application.Services;
import acme.objects.ShoppingCart;
import acme.persistence.DataAccess;

public class AccessShoppingCart {
	private DataAccess dataAccess;
	private ArrayList<ShoppingCart> shoppingCartList;
	private ShoppingCart shoppingCart;
	private int currentShoppingCart;

	public AccessShoppingCart() {
		dataAccess = Services.getDataAccess(Main.dbName);
		shoppingCartList = null;
		shoppingCart = null;
		currentShoppingCart = 0;
	}

	public ShoppingCart getSequential() {
		if (shoppingCartList == null) {
			shoppingCartList = dataAccess.getShoppingCarts(null);
			currentShoppingCart = 0;
		}
		if (currentShoppingCart < shoppingCartList.size()) {
			shoppingCart = shoppingCartList
					.get(currentShoppingCart);
			currentShoppingCart++;
		} else {
			shoppingCartList = null;
			shoppingCart = null;
			currentShoppingCart = 0;
		}
		return shoppingCart;
	}

	public boolean insertShoppingCart(ShoppingCart currentShoppingCart) {
		return dataAccess.insertShoppingCart(currentShoppingCart);
	}

	public ShoppingCart getShoppingCart(ShoppingCart shoppingCart) {
		return dataAccess.getShoppingCart(shoppingCart);
	}

	public boolean deleteShoppingCart(ShoppingCart currentShoppingCart) {
		return dataAccess.deleteShoppingCart(currentShoppingCart);
	}

	public boolean updateShoppingCart(ShoppingCart currentShoppingCart,
			ShoppingCart newShoppingCart) {
		return dataAccess.updateShoppingCart(currentShoppingCart,
				newShoppingCart);
	}

}
