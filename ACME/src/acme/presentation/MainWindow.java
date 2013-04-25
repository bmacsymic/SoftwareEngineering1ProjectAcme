package acme.presentation;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import acme.business.AccessUsers;
import acme.objects.User;
import ctunit.EventLoop;
import ctunit.Register;

public class MainWindow {
	private Display display;
	private Shell shell;

	private Button staffButton;
	private Button customerButton;
	private Button exitButton;
	private Button usersButton;
	private Combo usersList;
	private Image wileEImage;

	AccessUsers accessUsers;

	private User user;
	private ArrayList<User> users;

	public MainWindow() {
		Register.newWindow(this);
		display = Display.getDefault();
		createWindow();
		runWindow();
	}

	public void runWindow() {
		shell.open();
		if (EventLoop.isEnabled())
		{
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
			display.dispose();
		}
	}

	public void determineUserType() {

		if (users.size() != 0) {
			user = users.get(usersList.getSelectionIndex());
			if (user.getType().equals("Staff")) {
				customerButton.setEnabled(false);
				staffButton.setEnabled(true);
			}

			else if (user.getType().equals("Customer")) {
				customerButton.setEnabled(true);
				staffButton.setEnabled(false);
			}
		} else {
			customerButton.setEnabled(false);
			staffButton.setEnabled(false);
		}
	}

	public void updateUsersList() {
		usersList.removeAll();
		User tempUser;

		users = new ArrayList<User>();
		tempUser = accessUsers.getSequential();
		for (int i = 0; i < accessUsers.getUsersSize(); i++) {
			users.add(tempUser);
			usersList.add(Integer.toString(tempUser.getUserID()));
			tempUser = accessUsers.getSequential();
		}

		if (usersList.getItems().length > 0) {
			usersList.select(0);
			determineUserType();
		}

		determineUserType();

	}

	private MainWindow returnParentType() {
		return this;
	}

	public void createWindow() {
		accessUsers = new AccessUsers();

		shell = new Shell();
		shell.setSize(500, 200);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = shell.getSize().x;
		int windowHeight = shell.getSize().y;
		int xPos = (screenSize.width - windowWidth) / 2;
		int yPos = (screenSize.height - windowHeight) / 2;
		shell.setLocation(xPos, yPos);
		shell.setText("ACME Corporation");
		shell.setLayout(new GridLayout(3, false));

		usersList = new Combo(shell, SWT.READ_ONLY);
		usersList.setItems(new String[] {});

		usersList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		usersList.select(0);
		usersList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				determineUserType();
			}
		});

		final Label welcomeLabel = new Label(shell, SWT.HORIZONTAL
				| SWT.CENTER);
		welcomeLabel.setSize(221, 19);
		welcomeLabel.setFont(new Font(display, new FontData("Times", 16,
				SWT.BOLD)));
		welcomeLabel.setForeground(new Color(display, 200, 0, 0));
		welcomeLabel.setText("ACME Corporation Storefront");
		welcomeLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 1, 1));

		usersButton = new Button(shell, SWT.NONE);
		usersButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new UsersWindow(returnParentType());
			}
		});
		usersButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		usersButton.setText("Users");

		wileEImage = new Image(display, "images/wile_e_coyote-01.gif");

		Composite composite = new Composite(shell, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1);
		gd_composite.heightHint = 16;
		composite.setLayoutData(gd_composite);
		new Label(shell, SWT.NONE);

		Composite composite_1 = new Composite(shell, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd_composite_1.heightHint = 8;
		composite_1.setLayoutData(gd_composite_1);
		final Canvas wileECanvas = new Canvas(shell, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL, SWT.FILL, true, true);
		gridData.widthHint = 80;
		gridData.heightHint = 80;
		wileECanvas.setLayoutData(gridData);
		wileECanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(final PaintEvent event) {
				if (wileEImage != null) {
					event.gc.drawImage(wileEImage, 0, 0,
							wileEImage.getBounds().width,
							wileEImage.getBounds().height, 0, 0,
							wileECanvas.getBounds().width,
							wileECanvas.getBounds().height);
				}
			}
		});

		final Label instructionLabel = new Label(shell,
				SWT.HORIZONTAL | SWT.CENTER);
		instructionLabel
				.setText("Please choose one of the available options:\n(Images used are all copyrighted by Warner Brothers)");
		instructionLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				true, false, 1, 1));

		Composite optionsComposite = new Composite(shell, SWT.NONE);
		GridData gd_optionsComposite = new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 1, 1);
		gd_optionsComposite.widthHint = 79;
		optionsComposite.setLayoutData(gd_optionsComposite);
		optionsComposite.setLayout(new GridLayout(1, false));

		customerButton = new Button(optionsComposite, SWT.CENTER);
		customerButton.setEnabled(false);
		customerButton.setText("Customers");
		customerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new CustomerWindow(user);
			}
		});

		staffButton = new Button(optionsComposite, SWT.CENTER);
		staffButton.setEnabled(false);
		staffButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		staffButton.setText("Staff");
		staffButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new StaffWindow();
			}
		});

		exitButton = new Button(optionsComposite, SWT.CENTER);
		exitButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		exitButton.setText("Exit");
		exitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});

		updateUsersList();
		shell.open();
	}
}
