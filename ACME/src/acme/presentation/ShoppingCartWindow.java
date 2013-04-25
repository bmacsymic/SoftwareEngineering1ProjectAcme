package acme.presentation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import acme.business.AccessItems;
import acme.business.AccessShoppingCart;
import acme.objects.Item;
import acme.objects.ShoppingCart;
import acme.objects.User;

import ctunit.Register;

public class ShoppingCartWindow {
	private Display display;
	private Shell shell;
	private Label lblNameVal;
	private Label lblDescriptionVal;
	private Label lblPriceVal;
	private Label lblQuantity;
	private Text textQuantity;
	private Image currImage;
	private Button btnCheckOut;
	private Button btnPrevious;
	private Button btnNext;
	private Button btnExit;
	private Composite cmpDetails;

	private User user;
	private ShoppingCart cart;

	private List shoppingCartList;

	private AccessShoppingCart AS;
	private AccessItems AI;

	private Button btnRemove;
	private Button btnEdit;
	private Button btnUpdate;
	
	private CustomerWindow customerWindow;

	public ShoppingCartWindow(User user, ShoppingCart cart2, CustomerWindow customerWindow) {
		Register.newWindow(this);
		
		this.customerWindow = customerWindow;
		
		this.user = user;
		AS = new AccessShoppingCart();
		AI = new AccessItems();
		cart = cart2;

		display = Display.getDefault();
		createWindow();
	}

	private ShoppingCartWindow returnParentType() {
		return this;
	}

	public void updateShoppingCartWindow() {
		// link all the shopping cart item to the user, and add that to
		// the users history (not implementing now)
		HashMap<String, Integer> temp = cart.getItems();
		Set<?> set = temp.entrySet();
		Iterator<?> iterator = set.iterator();
		Item tempItem;
		int newQuantity;
		int oldQuantity;

		while (iterator.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) (iterator.next());
			tempItem = AI.getItem(new Item((String)entry.getKey()));
			newQuantity = (Integer)entry.getValue();
			oldQuantity = tempItem.getQuantity();
			tempItem.setQuantity(oldQuantity - newQuantity);
			AI.updateItem(new Item((String)entry.getKey()), tempItem);
		}
		clearProductDetails();
		
		// need to update customer window shopping cart too
		cmpDetails.setRedraw(false);
		btnRemove.setEnabled(false);
		btnEdit.setEnabled(false);
		currImage = new Image(display, "images/defaultPicture.jpg");
		shoppingCartList.removeAll();
		cart.emptyCart();
		AS.deleteShoppingCart(cart);
		btnCheckOut.setEnabled(false);
		cmpDetails.setRedraw(true);
		cmpDetails.layout(true);
		cmpDetails.redraw();
		
		customerWindow.updateCustomerWindow(cart);
	}

	private void fillShoppingCartList() {
		if (cart != null) {
			shoppingCartList.removeAll();
			HashMap<String, Integer> temp = cart.getItems();
			Set<?> set = temp.entrySet();
			Iterator<?> iterator = set.iterator();

			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) (iterator.next());
				shoppingCartList.add((String) entry.getKey());
			}
		}
	}

	private void deleteItemFromCart() {
		ShoppingCart tempCart = new ShoppingCart(user.getUserID());
		tempCart.setItems(cart.getItems());
		tempCart.removeItem(shoppingCartList.getItem(shoppingCartList
				.getSelectionIndex()));
		shoppingCartList.remove(shoppingCartList.getSelectionIndex());
		AS.updateShoppingCart(cart, tempCart);
		cart = tempCart;
		customerWindow.updateCustomerWindow(cart);
	}

	private void clearProductDetails() {
		lblNameVal.setText("");
		lblDescriptionVal.setText("");
		lblPriceVal.setText("");
		textQuantity.setText("");
	}

	private void updateProductDetails(String itemName) {
		HashMap<String, Integer> mapTemp = cart.getItems();
		ShoppingCart tempCart = new ShoppingCart(user.getUserID());
		Item tempItem = AI.getItem(new Item(itemName));
		int cartItemQuantity = tempItem.getQuantity();
		if(textQuantity.getText().length()> 9)
			textQuantity.setText(textQuantity.getText(0, 8));
		int requestedQuantity = Integer.parseInt(textQuantity.getText());
		
		if(requestedQuantity <= cartItemQuantity)
		{
			tempItem.setQuantity(requestedQuantity);
			mapTemp.put(itemName, requestedQuantity);
		}
		else
		{
			tempItem.setQuantity(cartItemQuantity);
			textQuantity.setText(Integer.toString(cartItemQuantity));
			mapTemp.put(itemName, cartItemQuantity);
		}

		tempCart.setItems(mapTemp);
		AS.updateShoppingCart(cart, tempCart);
		cart = tempCart;
	}

	private void fillShoppingCartDetails(Item product) {
		cmpDetails.setRedraw(false);
		lblNameVal.setText(product.getName());
		lblDescriptionVal.setText(product.getDescription());
		lblPriceVal.setText(String.valueOf(product.getPrice()));
		textQuantity.setText(Integer.toString((cart.getItems()).get(product
				.getName())));
		if (product.getFileName() != null && !product.getFileName().isEmpty()) {
			currImage = new Image(display, product.getFileName());
		}
		cmpDetails.setRedraw(true);
		cmpDetails.redraw();
		cmpDetails.layout(true);
	}

	private void mapListSelectionToDetails() {
		if (shoppingCartList.getSelectionCount() == 1) {
			String productName = shoppingCartList.getItem(shoppingCartList
					.getSelectionIndex());
			AccessItems productDAO = new AccessItems();
			Item curr = productDAO.getItem(new Item(productName));
			clearProductDetails();
			fillShoppingCartDetails(curr);
		}
	}

	public void createWindow() {
		shell = new Shell();
		shell.setSize(700, 700);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = shell.getSize().x;
		int windowHeight = shell.getSize().y;
		int xPos = (screenSize.width - windowWidth) / 2;
		int yPos = (screenSize.height - windowHeight) / 2;
		shell.setLocation(xPos, yPos);

		shell.setText("ACME Corporation Shopping Cart");
		shell.setLayout(new GridLayout(4, true));

		final Composite cmpInstructions = new Composite(shell,
				SWT.NONE);
		cmpInstructions.setLayout(new GridLayout(1, false));
		cmpInstructions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 4, 1));

		final Label lblInstructions = new Label(cmpInstructions, SWT.NONE);
		lblInstructions
				.setText("Select a product from the list below to view its details. \r\nRemove or change quantities on any item in your shopping cart.\r\nClick on Check Out to purchase the items currently in your shopping cart.");

		shoppingCartList = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		shoppingCartList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnRemove.setEnabled(true);
				btnEdit.setEnabled(true);
				textQuantity.setEditable(false);
				mapListSelectionToDetails();
			}
		});
		shoppingCartList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		fillShoppingCartList();

		cmpDetails = new Composite(shell, SWT.BORDER);
		cmpDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1));
		cmpDetails.setLayout(new GridLayout(2, false));

		currImage = new Image(display, "images/defaultPicture.jpg");
		final Canvas cnvPicture = new Canvas(cmpDetails, SWT.NONE);
		cnvPicture.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent arg0) {
				if (currImage != null) {
					arg0.gc.drawImage(currImage, 0, 0,
							currImage.getBounds().width,
							currImage.getBounds().height, 0, 0,
							cnvPicture.getBounds().width,
							cnvPicture.getBounds().height);
				}
			}
		});

		GridData gd_cnvPicture = new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1);
		gd_cnvPicture.heightHint = 246;
		gd_cnvPicture.widthHint = 74;
		cnvPicture.setLayoutData(gd_cnvPicture);

		final Label lblName = new Label(cmpDetails, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblName.setText("Name:");

		lblNameVal = new Label(cmpDetails, SWT.BORDER);
		lblNameVal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));

		final Label lblDescription = new Label(cmpDetails, SWT.NONE);
		lblDescription.setText("Description:");

		lblDescriptionVal = new Label(cmpDetails, SWT.BORDER | SWT.WRAP);
		lblDescriptionVal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));

		final Label lblPrice = new Label(cmpDetails, SWT.NONE);
		lblPrice.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPrice.setText("Price:");

		lblPriceVal = new Label(cmpDetails, SWT.BORDER);
		lblPriceVal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));

		lblQuantity = new Label(cmpDetails, SWT.NONE);
		lblQuantity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblQuantity.setText("Quantity");

		textQuantity = new Text(cmpDetails, SWT.BORDER);
		textQuantity.setEnabled(false);
		textQuantity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		textQuantity.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				if (event.text.equals("-")) {
					event.text = "";
				}
			}
		});

		final Composite cmpActions = new Composite(shell,
				SWT.BORDER);
		cmpActions.setLayout(new GridLayout(7, false));
		cmpActions.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true,
				false, 4, 1));

		btnCheckOut = new Button(cmpActions, SWT.NONE);
		btnCheckOut.setText("Check Out");
		btnCheckOut.setEnabled(false);
		if (shoppingCartList.getItemCount() > 0) {
			btnCheckOut.setEnabled(true);
		}
		btnCheckOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new CheckOutWindow(user, cart, returnParentType());
			}
		});

		btnRemove = new Button(cmpActions, SWT.NONE);
		btnRemove.setEnabled(false);
		btnRemove.setText("Remove");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				clearProductDetails();
				deleteItemFromCart();
				shoppingCartList.deselectAll();
				btnEdit.setEnabled(false);
				shoppingCartList.select(0);
				if (shoppingCartList.getItemCount() > 0)
					btnEdit.setEnabled(true);
				else
					btnEdit.setEnabled(false);
				if (shoppingCartList.getSelectionCount() == 0) {
					btnRemove.setEnabled(false);
				}
				if (shoppingCartList.getItemCount() == 0) {
					btnCheckOut.setEnabled(false);
				}
			}
		});

		btnEdit = new Button(cmpActions, SWT.NONE);
		btnEdit.setEnabled(false);
		btnEdit.setText("Edit");
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnRemove.setEnabled(false);
				btnEdit.setEnabled(false);
				btnUpdate.setEnabled(true);
				textQuantity.setEnabled(true);
				textQuantity.setEditable(true);
				btnPrevious.setEnabled(false);
				btnNext.setEnabled(false);
			}
		});

		btnUpdate = new Button(cmpActions, SWT.NONE);
		btnUpdate.setEnabled(false);
		btnUpdate.setText("Update");
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//This is really wierd, for some reason shopping cart doesn't update properly if this
				//temp variable isn't here!?!?
				String temp = textQuantity.getText();
				
				
				btnRemove.setEnabled(true);
				btnEdit.setEnabled(true);
				btnPrevious.setEnabled(true);
				btnNext.setEnabled(true);
				btnUpdate.setEnabled(false);
				textQuantity.setEnabled(false);
				updateProductDetails(shoppingCartList.getItem(shoppingCartList
						.getSelectionIndex()));
				mapListSelectionToDetails();
				customerWindow.updateCustomerWindow(cart);
			}
		});

		btnPrevious = new Button(cmpActions, SWT.NONE);
		btnPrevious.setText("Previous");
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (shoppingCartList.getSelectionCount() == 0) {
					shoppingCartList.select(0);
				} else {
					shoppingCartList.select((shoppingCartList
							.getSelectionIndex() - 1 + shoppingCartList
							.getItemCount())
							% shoppingCartList.getItemCount());
				}
				mapListSelectionToDetails();
			}
		});

		btnNext = new Button(cmpActions, SWT.NONE);
		btnNext.setText("Next");
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (shoppingCartList.getSelectionCount() == 0) {
					shoppingCartList.select(0);
				} else {
					shoppingCartList.select((shoppingCartList
							.getSelectionIndex() + 1 + shoppingCartList
							.getItemCount())
							% shoppingCartList.getItemCount());
				}
				mapListSelectionToDetails();
			}
		});

		btnExit = new Button(cmpActions, SWT.NONE);
		btnExit.setText("Exit");
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				customerWindow.updateProductListing();
				shell.close();
			}
		});

		shell.open();
	}
}