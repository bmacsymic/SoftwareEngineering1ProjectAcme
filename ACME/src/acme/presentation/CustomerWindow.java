package acme.presentation;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import acme.business.AccessCategories;
import acme.business.AccessItemCategory;
import acme.business.AccessItems;
import acme.business.AccessShoppingCart;
import acme.objects.Category;
import acme.objects.Item;
import acme.objects.ItemCategory;
import acme.objects.ShoppingCart;
import acme.objects.User;

import ctunit.Register;

public class CustomerWindow {
	private Display display;
	private Shell shell;
	private Label lblNameVal;
	private Label lblDescriptionVal;
	private Label lblPriceVal;
	private Text txtSearch;
	private Image currImage;
	private Button btnViewShoppingCart;
	private Button btnPrevious;
	private Button btnNext;
	private Button btnExit;
	private Button btnAdd;
	private Button btnUncheckAll;
	private Button btnSearch;
	
	private List lstProducts;
	private Composite cmpCategories;
	private Composite cmpDetails;
	private User user;
	private ShoppingCart cart;

	AccessItems AI;
	AccessShoppingCart AS;

	private Item defaultProduct;

	public CustomerWindow(User user) {
		Register.newWindow(this);
		
		this.user = user;
		defaultProduct = new Item();
		defaultProduct.setFileName("images/defaultPicture.jpg");
		display = Display.getDefault();
		createWindow();
		populateInfo();
		
		shell.setRedraw(false);
		shell.layout(true);
		shell.setRedraw(true);
	}

	private void populateInfo() {
		populateAllItems(null);
		populateCategories(cmpCategories, true);
	}
	
	private CustomerWindow returnParentType() {
		return this;
	}
	
	public void updateCustomerWindow(ShoppingCart tempCart)
	{
		cart = tempCart;
	}

	private void populateCategories(Composite composite, boolean addEvents) {
		AccessCategories categoryDAO = new AccessCategories();

		while (composite.getChildren().length > 0) {
			composite.getChildren()[0].dispose();
		}
		composite.layout(true);

		Category curr = categoryDAO.getSequential();

		composite.setRedraw(false);
		while (curr != null) {
			Button btn = new Button(composite, SWT.CHECK);
			btn.setText(curr.getName());
			if (addEvents) {
				btn.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						filterProducts();
					}
				});
			}
			curr = categoryDAO.getSequential();
		}
		composite.setRedraw(true);
		composite.layout(true);
	}

	public void updateProductListing() {
		filterProducts();
		clearDetails();
	}
	
	private void filterProducts() {
		AccessCategories categoryDAO = new AccessCategories();
		AccessItems itemDAO = new AccessItems();
		AccessItemCategory itemCategoryDAO = new AccessItemCategory();

		Button btn;
		boolean categoryApplied = false;
		String keyword = txtSearch.getText();

		lstProducts.removeAll();

		lstProducts.setRedraw(false);
		for (int i = 0; i < cmpCategories.getChildren().length; i++) {
			Control ctrl = cmpCategories.getChildren()[i];

			if (ctrl instanceof Button) {
				btn = (Button) cmpCategories.getChildren()[i];
				if (btn.getSelection()) {
					categoryApplied = true;

					Category curr = new Category(btn.getText());
					curr = categoryDAO.getCategory(curr);

					if (curr != null) {
						for (ItemCategory ic : itemCategoryDAO
								.getCategoryItem(new ItemCategory(null, curr.getName()))) {
							addProductToList(
									itemDAO.getItem(new Item(ic.getItemName())),
									keyword);
						}
					}
				}
			}
		}

		if (!categoryApplied) {
			populateAllItems(keyword);
		}

		lstProducts.setRedraw(true);
	}

	private void populateAllItems(String keyword) {
		AccessItems itemDAO = new AccessItems();
		lstProducts.removeAll();

		Item product = itemDAO.getSequential();
		while (product != null) {
			if(product.getQuantity() > 0)
				addProductToList(product, keyword);
			product = itemDAO.getSequential();
		}
		lstProducts.setRedraw(true);
	}

	private void addProductToList(Item product, String keyword) {
		if (keyword != null && !keyword.isEmpty() && product.contains(keyword)
				|| keyword == null || keyword.isEmpty()) {
			boolean duplicateItem = false;

			for (String test : lstProducts.getItems()) {
				if (product.getName().equals(test)) {
					duplicateItem = true;
				}
			}

			if (!duplicateItem && product.getQuantity() > 0) {
				lstProducts.add(product.getName());
			}
		}
	}

	private void clearDetails() {
		cmpDetails.setRedraw(false);
		lblNameVal.setText("");
		lblDescriptionVal.setText("");
		lblPriceVal.setText("");
		currImage = new Image(display, defaultProduct.getFileName());
		cmpDetails.layout(true);
		cmpDetails.setRedraw(true);
	}

	private void fillDetails(Item product) {
		cmpDetails.setRedraw(false);
		lblNameVal.setText(product.getName());
		lblDescriptionVal.setText(product.getDescription());
		lblPriceVal.setText(String.valueOf(product.getPrice()));
		if (product.getFileName() != null && !product.getFileName().isEmpty()) {
			currImage = new Image(display, product.getFileName());
		} else {
			currImage = new Image(display, defaultProduct.getFileName());
		}
		cmpDetails.layout(true);
		cmpDetails.setRedraw(true);
	}

	private void mapListSelectionToDetails() {
		if (lstProducts.getSelectionCount() == 1) {
			String productName = lstProducts.getSelection()[0];
			AccessItems productDAO = new AccessItems();
			Item curr = productDAO.getItem(new Item(productName));
			clearDetails();
			fillDetails(curr);
			btnAdd.setEnabled(true);
		}
	}

	public void createWindow() {
		shell = new Shell();
		shell.setSize(700, 700);

		ShoppingCart tempCart = new ShoppingCart(user.getUserID());

		AI = new AccessItems();
		AS = new AccessShoppingCart();

		cart = AS.getShoppingCart(tempCart);
		if (cart == null) {
			AS.insertShoppingCart(tempCart);
			cart = tempCart;
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = shell.getSize().x;
		int windowHeight = shell.getSize().y;
		int xPos = (screenSize.width - windowWidth) / 2;
		int yPos = (screenSize.height - windowHeight) / 2;
		shell.setLocation(xPos, yPos);

		shell.setText("Welcome to the ACME Corporation");
		shell.setLayout(new GridLayout(4, true));

		final Composite cmpInstructions = new Composite(shell,
				SWT.NONE);
		cmpInstructions.setLayout(new GridLayout(1, false));
		cmpInstructions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 4, 1));

		final Label lblInstructions = new Label(cmpInstructions, SWT.NONE);
		lblInstructions
				.setText("Select a product from the list below to view its details. \nNarrow the results by selecting specific categories or searching by keyword.");

		final Composite cmpSearch = new Composite(shell, SWT.BORDER);
		cmpSearch.setLayout(new GridLayout(3, false));
		cmpSearch.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				4, 1));

		final Label lblSearchByKeyword = new Label(cmpSearch, SWT.NONE);
		lblSearchByKeyword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblSearchByKeyword.setText("Search by keyword:");

		txtSearch = new Text(cmpSearch, SWT.BORDER);
		txtSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				filterProducts();
			}
		});
		txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		btnSearch = new Button(cmpSearch, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				filterProducts();
			}
		});
		btnSearch.setText("Search");

		final Composite cmpFilter = new Composite(shell, SWT.BORDER);
		cmpFilter.setLayout(new GridLayout(3, false));
		cmpFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				4, 1));

		final Label lblFilterByCategory = new Label(cmpFilter, SWT.NONE);
		lblFilterByCategory.setText("Filter by category:");

		cmpCategories = new Composite(cmpFilter, SWT.NONE);
		cmpCategories.setLayout(new RowLayout(SWT.HORIZONTAL));
		cmpCategories.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		btnUncheckAll = new Button(cmpFilter, SWT.NONE);
		btnUncheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				for (int i = 0; i < cmpCategories.getChildren().length; i++) {
					Control item = cmpCategories.getChildren()[i];
					if (item instanceof Button) {
						((Button) item).setSelection(false);
					}
				}

				populateAllItems(txtSearch.getText());
			}
		});
		btnUncheckAll.setText("Uncheck All");

		lstProducts = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		lstProducts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mapListSelectionToDetails();
			}
		});
		lstProducts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1));

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

		final Composite cmpActions = new Composite(shell, SWT.BORDER);
		cmpActions.setLayout(new GridLayout(5, false));
		cmpActions.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true,
				false, 4, 1));

		btnViewShoppingCart = new Button(cmpActions, SWT.NONE);
		btnViewShoppingCart.setText("View Shopping Cart");
		btnViewShoppingCart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new ShoppingCartWindow(user, cart, returnParentType());
			}
		});

		btnAdd = new Button(cmpActions, SWT.NONE);
		btnAdd.setEnabled(false);
		btnAdd.setText("Add to Shopping Cart");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Item tempItem = AI.getItem(new Item(lstProducts.getItem(lstProducts.getSelectionIndex())));
				ShoppingCart tempCart = new ShoppingCart(user.getUserID());
				tempCart.setItems(cart.getItems());

				if (!cart.contains(tempItem.getName()))
				{
					if(tempItem.getQuantity() > 0)
						tempCart.addItem(tempItem.getName(), 1);
					else
						tempCart.addItem(tempItem.getName(), 0);
				}
				AS.updateShoppingCart(cart, tempCart);
				cart = tempCart;
			}
		});

		btnPrevious = new Button(cmpActions, SWT.NONE);
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (lstProducts.getSelectionCount() == 0) {
					lstProducts.select(0);
				} else {
					lstProducts.select((lstProducts.getSelectionIndex() - 1 + lstProducts
							.getItemCount()) % lstProducts.getItemCount());
				}
				mapListSelectionToDetails();
			}
		});
		btnPrevious.setText("Previous");

		btnNext = new Button(cmpActions, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (lstProducts.getSelectionCount() == 0) {
					lstProducts.select(0);
				} else {
					lstProducts.select((lstProducts.getSelectionIndex() + 1 + lstProducts
							.getItemCount()) % lstProducts.getItemCount());
				}
				mapListSelectionToDetails();
			}
		});
		btnNext.setText("Next");

		btnExit = new Button(cmpActions, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnExit.setText("Exit");

		shell.open();
	}
	
	//************************************
	//Methods created for test purposes: (Customer Tests)
	//************************************
	public void selectCategoryOne() {
		Button btn = (Button) cmpCategories.getChildren()[0];
		btn.setSelection(true);
	}
	
	public void unselectCategoryOne() {
		Button btn = (Button) cmpCategories.getChildren()[0];
		btn.setSelection(false);
	}
	
	public void selectCategoryTwo() {
		Button btn = (Button) cmpCategories.getChildren()[1];
		btn.setSelection(true);
	}
	
	public void unselectCategoryTwo() {
		Button btn = (Button) cmpCategories.getChildren()[1];
		btn.setSelection(false);
	}
	
	public void selectCategoryThree() {
		Button btn = (Button) cmpCategories.getChildren()[2];
		btn.setSelection(true);
	}
	
	public void unselectCategoryThree() {
		Button btn = (Button) cmpCategories.getChildren()[2];
		btn.setSelection(false);
	}
	
	public void callFilterProducts()
	{
		filterProducts();
	}
}