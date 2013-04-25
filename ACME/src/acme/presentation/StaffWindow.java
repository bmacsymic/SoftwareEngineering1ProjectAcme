package acme.presentation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import acme.business.AccessCategories;
import acme.business.AccessItemCategory;
import acme.business.AccessItems;
import acme.business.FileCopier;
import acme.objects.Category;
import acme.objects.Item;
import acme.objects.ItemCategory;

import ctunit.Register;

public class StaffWindow {
	private Display display;
	private Shell shell;
	private Table productsTable;
	private Text txtName;
	private Text txtDescription;
	private Text txtPrice;
	private Text txtQuantity;
	private Text txtSearch;
	private Item currItem;
	private Item defaultProduct;
	private Image currImage;
	private String currImagePath;
	private ArrayList<ItemCategory> currItemCategories;
	private Composite cmpCategories;
	private Composite cmpCurrCategories;
	private Composite cmpDetails;
	private Button btnRemove;
	private Button btnUpdate;
	private Button btnAdd;
	private Button btnExit;
	private Button btnUncheckAll;
	private Button btnSearch;

	public StaffWindow() {
		Register.newWindow(this);
		
		defaultProduct = new Item();
		defaultProduct.setFileName("images/defaultPicture.jpg");
		display = Display.getDefault();
		currImage = new Image(display, defaultProduct.getFileName());
		currImagePath = defaultProduct.getFileName();
		currItemCategories = new ArrayList<ItemCategory>();

		createWindow();
		populateInfo();
		
		shell.setRedraw(false);
		shell.layout(true);
		shell.setRedraw(true);
	}

	private void populateInfo() {
		populateAllItems(null);
		populateCategories(cmpCategories, true);
		cmpDetails.setRedraw(false);
		populateCategories(cmpCurrCategories, false);
		cmpDetails.setRedraw(true);
		cmpDetails.layout(true);
		cmpDetails.redraw();
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

	private void populateAllItems(String keyword) {
		AccessItems itemDAO = new AccessItems();
		productsTable.removeAll();

		Item product = itemDAO.getSequential();

		productsTable.setRedraw(false);
		while (product != null) {
			addProductToTable(product, keyword);
			product = itemDAO.getSequential();
		}
		productsTable.setRedraw(true);
		productsTable.layout(true);
	}

	private void filterProducts() {
		AccessCategories categoryDAO = new AccessCategories();
		AccessItems itemDAO = new AccessItems();
		AccessItemCategory itemCategoryDAO = new AccessItemCategory();
		Button btn;
		boolean categoryApplied = false;
		String keyword = txtSearch.getText();

		productsTable.removeAll();

		productsTable.setRedraw(false);
		for (int i = 0; i < cmpCategories.getChildren().length; i++) {
			Control ctrl = cmpCategories.getChildren()[i];

			if (ctrl instanceof Button) {
				btn = (Button) cmpCategories.getChildren()[i];
				if (btn.getSelection()) {
					categoryApplied = true;

					Category curr = new Category(btn.getText());
					curr = categoryDAO.getCategory(curr);

					if (curr != null) {
						ArrayList<ItemCategory> itemsOfAKind = itemCategoryDAO.getCategoryItem(new ItemCategory(null, curr.getName()));
						if (itemsOfAKind == null)
							itemsOfAKind = new ArrayList<ItemCategory>();
						
						for (ItemCategory ic : itemsOfAKind) {
							addProductToTable(
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

		productsTable.setRedraw(true);
		productsTable.layout(true);
	}

	private void addProductToTable(Item product, String keyword) {
		if (keyword != null && !keyword.isEmpty() && product.contains(keyword)
				|| keyword == null || keyword.isEmpty()) {
			boolean duplicateItem = false;

			for (TableItem test : productsTable.getItems()) {
				if (product.getName().equals(test.getText())) {
					duplicateItem = true;
				}
			}

			if (!duplicateItem) {
				TableItem item = new TableItem(productsTable, SWT.NONE);

				int col = 0;
				item.setText(col++, product.getName());
				item.setText(col++, String.format("$%.2f", product.getPrice()));
				item.setText(col++, String.valueOf(product.getQuantity()));
			}
		}
	}

	private ArrayList<ItemCategory> createItemCategoriesFromDetails() {
		String name = txtName.getText();
		ArrayList<ItemCategory> results = new ArrayList<ItemCategory>();

		for (Control temp : cmpCurrCategories.getChildren()) {
			if (temp instanceof Button) {
				Button temp2 = (Button) temp;
				if (temp2.getSelection()) {
					results.add(new ItemCategory(name, temp2.getText()));
				}
			}
		}

		return results;
	}

	private Item createItemFromDetails(String imgPath) {
		String name = txtName.getText();
		String desc = txtDescription.getText();
		String price = txtPrice.getText();
		String quantity = txtQuantity.getText();

		double priceVal = 0.0;
		int quantityVal = 0;

		try {
			priceVal = Double.parseDouble(price);
			quantityVal = Integer.parseInt(quantity);
		} catch (NumberFormatException nfe) {
			// values revert to defaults
		}

		Item temp = new Item(name, desc, priceVal, quantityVal, imgPath);

		return temp;
	}
	
	public void mapTableSelectionToDetails(){
		if (productsTable.getSelectionCount() == 1) {	
			TableItem[] selectionArray = productsTable.getSelection();			
			TableItem selection = selectionArray[0];			
			
			if (selection.getText() != null) {
				Item product = new Item(selection.getText());
				AccessItems itemDAO = new AccessItems();
	
				currItem = itemDAO.getItem(product);
				clearDetails();
				fillDetails(currItem);
				btnRemove.setEnabled(true);
				btnUpdate.setEnabled(true);
			}		
		}
	}

	private void fillDetails(Item product) {
		AccessItemCategory accessItemCategory = new AccessItemCategory();
		cmpDetails.setRedraw(false);
		txtName.setText(product.getName());
		txtDescription.setText(product.getDescription());
		txtPrice.setText(String.valueOf(product.getPrice()));
		txtQuantity.setText(String.valueOf(product.getQuantity()));
		if (product.getFileName() != null && !product.getFileName().isEmpty()) {
			currImage = new Image(display, product.getFileName());
			currImagePath = product.getFileName();
		} else {
			currImage = new Image(display, defaultProduct.getFileName());
			currImagePath = defaultProduct.getFileName();
		}
		currItemCategories = accessItemCategory.getItemCategory(new ItemCategory(product
				.getName(), null));
		if (currItemCategories == null)
			currItemCategories = new ArrayList<ItemCategory>();
		
		for (Control temp : cmpCurrCategories.getChildren()) {
			if (temp instanceof Button) {
				Button category = (Button) temp;
				if (currItemCategories.contains(new ItemCategory(product
						.getName(), category.getText()))) {
					category.setSelection(true);
				}
			}
		}
		cmpDetails.setRedraw(true);
		cmpDetails.redraw();
		cmpDetails.layout(true);
	}

	private void clearDetails() {
		cmpDetails.setRedraw(false);
		txtName.setText("");
		txtDescription.setText("");
		txtPrice.setText("");
		txtQuantity.setText("");
		currImage = new Image(display, defaultProduct.getFileName());
		currImagePath = defaultProduct.getFileName();
		cmpDetails.setRedraw(true);
		cmpDetails.redraw();
		cmpDetails.layout(true);
		for (Control temp : cmpCurrCategories.getChildren()) {
			if (temp instanceof Button) {
				((Button) temp).setSelection(false);
			}
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

		shell.setText("ACME Corporation Staff Interface");
		shell.setLayout(new GridLayout(4, true));

		final Composite cmpInstructions = new Composite(shell, SWT.NONE);
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

		productsTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		productsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		productsTable.setHeaderVisible(true);
		productsTable.setLinesVisible(true);
		productsTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (arg0.item instanceof TableItem) {
					mapTableSelectionToDetails();
				}
			}
		});

		final TableColumn tblclmnProductName = new TableColumn(productsTable,
				SWT.NONE);
		tblclmnProductName.setWidth(99);
		tblclmnProductName.setText("Product Name");

		final TableColumn tblclmnPrice = new TableColumn(productsTable,
				SWT.NONE);
		tblclmnPrice.setWidth(55);
		tblclmnPrice.setText("Price");

		final TableColumn tblclmnQuantity = new TableColumn(productsTable,
				SWT.NONE);
		tblclmnQuantity.setWidth(77);
		tblclmnQuantity.setText("Quantity");

		cmpDetails = new Composite(shell, SWT.BORDER);
		cmpDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1));
		cmpDetails.setLayout(new GridLayout(2, false));

		final Label lblPhotoDoubleClick = new Label(cmpDetails, SWT.NONE);
		lblPhotoDoubleClick.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 2, 1));
		lblPhotoDoubleClick.setText("Photo: Double-click to select image");

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
		cnvPicture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Select a picture file");
				fd.setFilterExtensions(new String[] { "*.bmp", "*.gif",
						"*.jpg", "*.jpeg", "*.png" });

				String imgPath = fd.open();
				if (imgPath != null) {
					File imgFile = new File(imgPath);
					if (!FileCopier.copyBinaryFile(imgPath,
							"images/" + imgFile.getName())) {
						imgPath = defaultProduct.getFileName();
					} else {
						imgPath = "images/" + imgFile.getName();
					}
				} else if (productsTable.getSelectionIndex() > 0)
				{
					AccessItems AI = new AccessItems();
					Item tempItem;
					tempItem = AI.getItem(new Item(productsTable.getItem(productsTable.getSelectionIndex()).getText()));
					
					imgPath = tempItem.getFileName();					
				}
				else 
				{
					imgPath = defaultProduct.getFileName();
				}
				Item tempItem = createItemFromDetails(imgPath);
				currImage = new Image(display, tempItem.getFileName());
				currImagePath = imgPath;
				cnvPicture.redraw();
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

		txtName = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1,
				1));

		final Label lblDescription = new Label(cmpDetails, SWT.NONE);
		lblDescription.setText("Description:");

		txtDescription = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));

		final Label lblPrice = new Label(cmpDetails, SWT.NONE);
		lblPrice.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPrice.setText("Price:");

		txtPrice = new Text(cmpDetails, SWT.BORDER);
		txtPrice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1,
				1));
		txtPrice.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				if (event.text.equals("-")) {
					event.text = "";
				}
			}
		});

		final Label lblQuantity = new Label(cmpDetails, SWT.NONE);
		lblQuantity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblQuantity.setText("Quantity:");

		txtQuantity = new Text(cmpDetails, SWT.BORDER);
		txtQuantity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));
		txtQuantity.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				if (event.text.equals("-")) {
					event.text = "";
				}
			}
		});

		final Label lblCategories = new Label(cmpDetails, SWT.NONE);
		lblCategories.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCategories.setText("Categories:");

		cmpCurrCategories = new Composite(cmpDetails, SWT.NONE);
		cmpCurrCategories.setLayout(new RowLayout(SWT.HORIZONTAL));
		cmpCurrCategories.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));

		final Composite cmpActions = new Composite(shell, SWT.BORDER);
		cmpActions.setLayout(new GridLayout(4, false));
		cmpActions.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true,
				false, 4, 1));

		btnAdd = new Button(cmpActions, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Item tempItem = createItemFromDetails(currImagePath);
				ArrayList<ItemCategory> ics = createItemCategoriesFromDetails();
				
				if(!tempItem.getName().equals(""))
				{
					AccessItems itemDAO = new AccessItems();
					itemDAO.insertItem(tempItem);
					currItem = tempItem;
	
					AccessItemCategory itemCategoryDAO = new AccessItemCategory();
					for (ItemCategory ic : ics) {
						itemCategoryDAO.insertItemCategory(ic);
					}
	
					filterProducts();
				}
			}
		});
		btnAdd.setText("Add Item");

		btnRemove = new Button(cmpActions, SWT.NONE);
		btnRemove.setEnabled(false);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (currItem != null) {
					btnRemove.setEnabled(false);
					btnUpdate.setEnabled(false);

					AccessItems itemDAO = new AccessItems();
					itemDAO.deleteItem(currItem);
					filterProducts();
					currItem = defaultProduct;
					clearDetails();
				}
			}
		});
		btnRemove.setText("Remove Item");

		btnUpdate = new Button(cmpActions, SWT.NONE);
		btnUpdate.setEnabled(false);
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (currItem != null && !(txtName.getText().equals(""))) {
					Item tempItem = createItemFromDetails(currImagePath);

					AccessItems itemDAO = new AccessItems();
					itemDAO.updateItem(currItem, tempItem);
					currItem = tempItem;

					ArrayList<ItemCategory> ics = createItemCategoriesFromDetails();
					AccessItemCategory itemCategoryDAO = new AccessItemCategory();
					currItemCategories = itemCategoryDAO
							.getItemCategory(new ItemCategory(currItem.getName(), null));
					if (currItemCategories == null)
						currItemCategories = new ArrayList<ItemCategory>();
					
					for (int i = 0; i < currItemCategories.size(); ++i) {
						ItemCategory itemCategory = currItemCategories.get(i);

						// remove category if the new list doesn't contain it
						if (!ics.contains(itemCategory)) {
							itemCategoryDAO.deleteItemCategory(itemCategory);
						}
					}
					for (int i = 0; i < ics.size(); ++i) {
						ItemCategory itemCategory = ics.get(i);

						// add category if the old list doesn't contain it
						if (!currItemCategories.contains(itemCategory)) {
							itemCategoryDAO.insertItemCategory(itemCategory);
						}
					}

					filterProducts();
				}
			}
		});
		btnUpdate.setText("Update Item");

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
