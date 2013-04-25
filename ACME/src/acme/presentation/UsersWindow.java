package acme.presentation;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import acme.business.AccessUsers;
import acme.objects.User;

import ctunit.Register;

public class UsersWindow {
	private Shell shell;
	private Button btnPrevious;
	private Button btnNext;
	private Button btnExit;
	private Button btnUpdate;
	private Button btnAdd;
	private Button btnEdit;
	private List lstUsers;
	private Composite cmpDetails;

	private Text textPostalCode;
	private Text textCity;
	private Text textCountry;
	private Text textStreetAddress;
	private Text textLastName;
	private Text textFirstName;
	private Text textUserID;

	AccessUsers accessUsers;
	private List listHistory;
	private Label lblType;
	private Text textProvince;
	private Label lblProvince;
	private Label lblBirthdate;
	private Text textBirthdate;
	private Text textPhoneNumber;
	private Text textEmail;
	private Label lblPhoneNumber;
	private Label lblEmail;
	private Combo comboType;

	private User currentUser;
	private Button btnRemove;
	private Composite composite;
	private MainWindow mainWindow;

	public UsersWindow(MainWindow mainWindow) {
		Register.newWindow(this);
		this.mainWindow = mainWindow;
		createWindow();
	}

	private void clearUserDetails() {
		textUserID.setText("");
		comboType.select(0);
		textFirstName.setText("");
		textLastName.setText("");
		textBirthdate.setText("");
		textStreetAddress.setText("");
		textPostalCode.setText("");
		textCity.setText("");
		textProvince.setText("");
		textCountry.setText("");
		textPhoneNumber.setText("");
		textEmail.setText("");
	}

	private void fillDetails() {
		cmpDetails.setRedraw(false);
		// if(currentUser.getUserID() != null || currentUser.getUserID != "")
		textUserID.setText(Integer.toString(currentUser.getUserID()));

		if (currentUser.getType().equals("Staff"))
			comboType.select(1);
		else
			comboType.select(0);
		comboType.select(2);
		textFirstName.setText(currentUser.getFirstname());
		textLastName.setText(currentUser.getLastname());
		textBirthdate.setText(currentUser.getBirthdate());
		textStreetAddress.setText(currentUser.getStreetAddress());
		textPostalCode.setText(currentUser.getPostalCode());
		textCity.setText(currentUser.getCity());
		textProvince.setText(currentUser.getProvince());
		textCountry.setText(currentUser.getCountry());
		textPhoneNumber.setText(currentUser.getPhoneNumber());
		textEmail.setText(currentUser.getEmail());

		btnEdit.setEnabled(true);
		cmpDetails.setRedraw(true);
		cmpDetails.redraw();
		cmpDetails.layout(true);
	}

	private void mapListSelectionToDetails() {
		if (lstUsers.getSelectionCount() == 1) {
			int userID = Integer.parseInt(lstUsers.getSelection()[0]);
			AccessUsers usersDAO = new AccessUsers();
			currentUser = usersDAO.getUser(new User(userID));
			clearUserDetails();
			fillDetails();
		}
	}

	private void createUser() {
		String firstname = textFirstName.getText();
		String lastname = textLastName.getText();
		String birthdate = textBirthdate.getText();
		String streetAddress = textStreetAddress.getText();
		String city = textCity.getText();
		String province = textProvince.getText();
		String postalCode = textPostalCode.getText();
		String country = textCountry.getText();
		String phoneNumber = textPhoneNumber.getText();
		String email = textEmail.getText();
		String type = comboType.getText();

		if (firstname == "")
			firstname = "Empty Value";
		if (lastname == "")
			lastname = "Empty Value";
		if (birthdate == "")
			birthdate = "Empty Value";
		if (streetAddress == "")
			streetAddress = "Empty Value";
		if (city == "")
			city = "Empty Value";
		if (province == "")
			province = "Empty Value";
		if (postalCode == "")
			postalCode = "Empty Value";
		if (country == "")
			country = "Empty Value";
		if (phoneNumber == "")
			phoneNumber = "Empty Value";
		if (email == "")
			email = "Empty Value";
		if (type == "")
			type = "Empty Value";

		currentUser = new User(firstname, lastname, birthdate, streetAddress,
				city, province, postalCode, country, phoneNumber, email, type);

		boolean success = accessUsers.insertUser(currentUser);
		while (success == false) {
			currentUser = new User(firstname, lastname, birthdate,
					streetAddress, city, province, postalCode, country,
					phoneNumber, email, type);
			success = accessUsers.insertUser(currentUser);
		}
	}

	private void deleteUser() {

		accessUsers.deleteUser(currentUser);
		lstUsers.remove(lstUsers.getSelectionIndex());
		currentUser = null;
	}

	private void updateUserDetails() {
		User newUser;
		String firstname = textFirstName.getText();
		String lastname = textLastName.getText();
		String birthdate = textBirthdate.getText();
		String streetAddress = textStreetAddress.getText();
		String city = textCity.getText();
		String province = textProvince.getText();
		String postalCode = textPostalCode.getText();
		String country = textCountry.getText();
		String phoneNumber = textPhoneNumber.getText();
		String email = textEmail.getText();
		String type = comboType.getText();

		newUser = new User(firstname, lastname, birthdate, streetAddress, city,
				province, postalCode, country, phoneNumber, email, type);
		try {
			currentUser = new User(Integer.parseInt(textUserID.getText()));
		} catch (NumberFormatException nfe) {
			System.out.println(textUserID.getText()
					+ " cannot be changed to a number.");
		}
		accessUsers.updateUser(currentUser, newUser);
	}

	private void disableUserInfo() {
		comboType.setEnabled(false);
		textFirstName.setEditable(false);
		textLastName.setEditable(false);
		textBirthdate.setEditable(false);
		textStreetAddress.setEditable(false);
		textPostalCode.setEditable(false);
		textCity.setEditable(false);
		textProvince.setEditable(false);
		textCountry.setEditable(false);
		textPhoneNumber.setEditable(false);
		textEmail.setEditable(false);
	}

	private void enableUserInfo() {
		comboType.setEnabled(true);
		textFirstName.setEditable(true);
		textLastName.setEditable(true);
		textBirthdate.setEditable(true);
		textStreetAddress.setEditable(true);
		textPostalCode.setEditable(true);
		textCity.setEditable(true);
		textProvince.setEditable(true);
		textCountry.setEditable(true);
		textPhoneNumber.setEditable(true);
		textEmail.setEditable(true);
	}

	private void createWindow() {
		shell = new Shell();
		shell.setSize(600, 700);
		User tempUser;
		accessUsers = new AccessUsers();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowWidth = shell.getSize().x;
		int windowHeight = shell.getSize().y;
		int xPos = (screenSize.width - windowWidth) / 2;
		int yPos = (screenSize.height - windowHeight) / 2;
		shell.setLocation(xPos, yPos);

		shell.setText("ACME Corporation User Interface");
		shell.setLayout(new GridLayout(5, true));

		final Composite cmpInstructions = new Composite(shell, SWT.NONE);
		cmpInstructions.setLayout(new GridLayout(1, false));
		cmpInstructions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 5, 1));

		final Label lblInstructions = new Label(cmpInstructions, SWT.NONE);
		lblInstructions
				.setText("Select a user from the list below to view its details. \r\nAdd, Edit, or Update an existing user by clicking on the appropriate button.");

		lstUsers = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

		tempUser = accessUsers.getSequential();
		for (int i = 0; i < accessUsers.getUsersSize(); i++) {
			lstUsers.add(Integer.toString(tempUser.getUserID()));
			tempUser = accessUsers.getSequential();
		}

		GridData gd_lstUsers = new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1);
		gd_lstUsers.widthHint = 90;
		lstUsers.setLayoutData(gd_lstUsers);

		lstUsers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mapListSelectionToDetails();
				btnRemove.setEnabled(true);
				disableUserInfo();
			}
		});

		listHistory = new List(shell, SWT.BORDER);
		listHistory.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		listHistory.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));

		cmpDetails = new Composite(shell, SWT.BORDER);
		cmpDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1));
		cmpDetails.setLayout(new GridLayout(2, false));

		Label lblUserId = new Label(cmpDetails, SWT.NONE);
		lblUserId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUserId.setText("User ID:");

		textUserID = new Text(cmpDetails, SWT.BORDER | SWT.READ_ONLY);
		textUserID.setEditable(false);
		textUserID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblType = new Label(cmpDetails, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblType.setText("Type:");

		comboType = new Combo(cmpDetails, SWT.READ_ONLY);
		comboType.setItems(new String[] { "Customer", "Staff" });
		comboType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		comboType.select(0);

		final Label lblName = new Label(cmpDetails, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblName.setText("First Name:");

		textFirstName = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textFirstName.setEditable(false);
		textFirstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		final Label lblLastName = new Label(cmpDetails, SWT.NONE);
		lblLastName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblLastName.setText("Last Name:");

		textLastName = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textLastName.setEditable(false);
		textLastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblBirthdate = new Label(cmpDetails, SWT.NONE);
		lblBirthdate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblBirthdate.setText("Birthdate:");

		textBirthdate = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textBirthdate.setEditable(false);
		textBirthdate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		final Label lblStreetAddress = new Label(cmpDetails, SWT.NONE);
		lblStreetAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		lblStreetAddress.setText("StreetAddress:");

		textStreetAddress = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textStreetAddress.setEditable(false);
		textStreetAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Label lblPostalCode = new Label(cmpDetails, SWT.NONE);
		lblPostalCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPostalCode.setText("Postal Code:");

		textPostalCode = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textPostalCode.setEditable(false);
		textPostalCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblCity = new Label(cmpDetails, SWT.NONE);
		lblCity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblCity.setText("City:");

		textCity = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textCity.setEditable(false);
		textCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		lblProvince = new Label(cmpDetails, SWT.NONE);
		lblProvince.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblProvince.setText("Province:");

		textProvince = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textProvince.setEditable(false);
		textProvince.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblCountry = new Label(cmpDetails, SWT.NONE);
		lblCountry.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblCountry.setText("Country:");

		textCountry = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textCountry.setEditable(false);
		textCountry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblPhoneNumber = new Label(cmpDetails, SWT.NONE);
		lblPhoneNumber.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPhoneNumber.setText("Phone Number:");

		textPhoneNumber = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textPhoneNumber.setEditable(false);
		textPhoneNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		lblEmail = new Label(cmpDetails, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblEmail.setText("Email:");

		textEmail = new Text(cmpDetails, SWT.BORDER | SWT.WRAP);
		textEmail.setEditable(false);
		textEmail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		composite = new Composite(cmpDetails, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false,
				2, 1);
		gd_composite.heightHint = 100;
		composite.setLayoutData(gd_composite);

		final Composite cmpActions = new Composite(shell, SWT.BORDER);
		cmpActions.setLayout(new GridLayout(7, false));
		cmpActions.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true,
				false, 5, 1));

		btnAdd = new Button(cmpActions, SWT.NONE);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (textFirstName.getEditable() == true) {
					createUser();
					disableUserInfo();
					btnEdit.setEnabled(true);
					btnRemove.setEnabled(true);
					lstUsers.add(Integer.toString(currentUser.getUserID()));
					lstUsers.setSelection(lstUsers.getItemCount() - 1);
					mapListSelectionToDetails();
				} else {
					clearUserDetails();
					enableUserInfo();
					lstUsers.deselectAll();
					btnEdit.setEnabled(false);
					btnRemove.setEnabled(false);
				}
			}
		});

		btnRemove = new Button(cmpActions, SWT.NONE);
		btnRemove.setEnabled(false);
		btnRemove.setText("Remove");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				disableUserInfo();
				clearUserDetails();
				deleteUser();
				lstUsers.select(0);
				if (lstUsers.getItemCount() > 0)
					btnEdit.setEnabled(true);
				else
					btnEdit.setEnabled(false);
				mapListSelectionToDetails();
				if (lstUsers.getSelectionCount() == 0) {
					btnRemove.setEnabled(false);
				}
			}
		});

		btnEdit = new Button(cmpActions, SWT.NONE);
		btnEdit.setEnabled(false);
		btnEdit.setText("Edit");
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				enableUserInfo();
				btnRemove.setEnabled(false);
				btnAdd.setEnabled(false);
				btnEdit.setEnabled(false);
				btnUpdate.setEnabled(true);
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
				disableUserInfo();
				updateUserDetails();
				btnRemove.setEnabled(true);
				btnAdd.setEnabled(true);
				btnEdit.setEnabled(true);
				btnPrevious.setEnabled(true);
				btnNext.setEnabled(true);
				btnUpdate.setEnabled(false);
			}
		});

		btnPrevious = new Button(cmpActions, SWT.NONE);
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnRemove.setEnabled(true);
				btnEdit.setEnabled(true);
				btnUpdate.setEnabled(false);
				if (lstUsers.getSelectionCount() == 0) {
					lstUsers.select(0);
				} else {
					lstUsers.select((lstUsers.getSelectionIndex() - 1 + lstUsers
							.getItemCount()) % lstUsers.getItemCount());
				}
				mapListSelectionToDetails();
				disableUserInfo();
			}
		});
		btnPrevious.setText("Previous");

		btnNext = new Button(cmpActions, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnRemove.setEnabled(true);
				btnEdit.setEnabled(true);
				btnUpdate.setEnabled(false);
				if (lstUsers.getSelectionCount() == 0) {
					lstUsers.select(0);
				} else {
					lstUsers.select((lstUsers.getSelectionIndex() + 1 + lstUsers
							.getItemCount()) % lstUsers.getItemCount());
				}
				mapListSelectionToDetails();
				disableUserInfo();
			}
		});
		btnNext.setText("Next");

		btnExit = new Button(cmpActions, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				mainWindow.updateUsersList();
				shell.close();
			}
		});
		btnExit.setText("Exit");

		shell.open();
	}
}