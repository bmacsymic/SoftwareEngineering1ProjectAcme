package acme.presentation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import acme.business.AccessShoppingCart;
import acme.business.CalculateCosts;
import acme.objects.Item;
import acme.objects.ShoppingCart;
import acme.objects.User;

import ctunit.Register;

public class CheckOutWindow {
	private Display display;
	private Shell shell;

	private User user;
	private ShoppingCart cart;

	private AccessShoppingCart AS;
	private Text textConfirmation;
	private Text textNameOnCC;
	private Text textCCNumber;
	private Text textCCIDNumber;
	private Text textExpiryDate;
	private Text txtTopLabel;
	private Button btnSubmit;
	private Button btnCancel;

	private ShoppingCartWindow shoppingCartWindow;
	private Text textGrandTotal;
	private Label lblTotal;

	public CheckOutWindow(User user, ShoppingCart cart2,
			ShoppingCartWindow shoppingCartWindow) {		
		ShoppingCart temp;
		this.shoppingCartWindow = shoppingCartWindow;
		
		Register.newWindow(this);

		this.user = user;
		AS = new AccessShoppingCart();
		temp = new ShoppingCart(user.getUserID());
		cart = cart2;

		display = Display.getDefault();
		createWindow();
	}

	private boolean checkInformation() {
		if (textNameOnCC.getText() != "" && textCCNumber.getText() != ""
				&& textCCIDNumber.getText() != ""
				&& textExpiryDate.getText() != "")
			return true;
		else {
			textConfirmation
					.setText("Please fill in all fields about your Credit Card information.");
			return false;
		}
	}

	private double calculateTotal() {
		double total = 0.00;
		CalculateCosts cc = new CalculateCosts();
		ArrayList<Item> items = null;

		total = CalculateCosts.getTotalCost(cart);

		return total;
	}

	public void createWindow() {
		shell = new Shell();
		shell.setSize(500, 400);
		shell.setText("ACME Corporation Check Out");
		shell.setLayout(new FormLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = shell.getSize().x;
		int windowHeight = shell.getSize().y;
		int xPos = (screenSize.width - windowWidth) / 2;
		int yPos = (screenSize.height - windowHeight) / 2;
		shell.setLocation(xPos, yPos);

		Composite composite = new Composite(shell, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(0, 352);
		fd_composite.right = new FormAttachment(0, 474);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());

		Composite composite_1 = new Composite(composite, SWT.BORDER);
		composite_1.setLayout(new GridLayout(1, false));
		FormData fd_composite_1 = new FormData();
		fd_composite_1.left = new FormAttachment(0, 10);
		fd_composite_1.right = new FormAttachment(100, -10);
		fd_composite_1.bottom = new FormAttachment(0, 42);
		fd_composite_1.top = new FormAttachment(0, 10);
		composite_1.setLayoutData(fd_composite_1);

		txtTopLabel = new Text(composite_1, SWT.BORDER | SWT.CENTER);
		txtTopLabel.setEditable(false);
		txtTopLabel.setText("Please enter your valid credit card information.");
		txtTopLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Composite composite_2 = new Composite(composite, SWT.BORDER);
		FormData fd_composite_2 = new FormData();
		fd_composite_2.left = new FormAttachment(0, 10);
		fd_composite_2.right = new FormAttachment(100, -10);
		fd_composite_2.bottom = new FormAttachment(100, -20);
		composite_2.setLayoutData(fd_composite_2);
		composite_2.setLayout(new GridLayout(10, false));
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		Composite composite_3 = new Composite(composite, SWT.BORDER);
		fd_composite_2.top = new FormAttachment(composite_3, 6);
		composite_3.setLayout(new GridLayout(2, false));
		FormData fd_composite_3 = new FormData();
		fd_composite_3.bottom = new FormAttachment(100, -65);
		fd_composite_3.top = new FormAttachment(composite_1, 6);
		fd_composite_3.right = new FormAttachment(0, 464);
		fd_composite_3.left = new FormAttachment(0, 10);
		composite_3.setLayoutData(fd_composite_3);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);

		Label lblNameOnCC = new Label(composite_3, SWT.NONE);
		lblNameOnCC.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblNameOnCC.setText("Name On Credit Card:");

		textNameOnCC = new Text(composite_3, SWT.BORDER);
		textNameOnCC.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblCCNumber = new Label(composite_3, SWT.NONE);
		lblCCNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCCNumber.setText("Credit Card Number:");

		textCCNumber = new Text(composite_3, SWT.BORDER);
		textCCNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblCCIDNumber = new Label(composite_3, SWT.NONE);
		lblCCIDNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCCIDNumber.setText("CCID:");

		textCCIDNumber = new Text(composite_3, SWT.BORDER);
		textCCIDNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblExpiryDate = new Label(composite_3, SWT.NONE);
		lblExpiryDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblExpiryDate.setText("Expiry Date:");

		textExpiryDate = new Text(composite_3, SWT.BORDER);
		GridData gd_textExpiryDate = new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1);
		gd_textExpiryDate.widthHint = 56;
		textExpiryDate.setLayoutData(gd_textExpiryDate);

		lblTotal = new Label(composite_3, SWT.NONE);
		lblTotal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblTotal.setText("Total After Taxes:");

		textGrandTotal = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY);
		textGrandTotal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		new Label(composite_3, SWT.NONE);
		textGrandTotal.setText("$" + calculateTotal());

		textConfirmation = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY
				| SWT.CENTER);
		textConfirmation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		btnSubmit = new Button(composite_2, SWT.NONE);
		btnSubmit.setText("Submit");
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (checkInformation()) {
					shoppingCartWindow.updateShoppingCartWindow();
					if(Double.parseDouble(textGrandTotal.getText(1, textGrandTotal.getText().length())) > 0)
						textConfirmation.setText("Order has been accepted. Thank You!");
					else
						textConfirmation.setText("Order was $0.00. Nothing Purchased.");
					textNameOnCC.setText("");
					textCCNumber.setText("");
					textCCIDNumber.setText("");
					textExpiryDate.setText("");
					textGrandTotal.setText("");
					btnSubmit.setEnabled(false);
					btnCancel.setText("Exit");
				}
			}
		});
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);

		btnCancel = new Button(composite_2, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});

		shell.open();
	}
}