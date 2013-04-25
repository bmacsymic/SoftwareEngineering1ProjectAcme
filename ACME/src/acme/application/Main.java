package acme.application;

import acme.business.FileCopier;
import acme.presentation.MainWindow;

public class Main {
	public static final String dbName = "acme";

	public static void main(String[] args) {
		startUp();
		new MainWindow();
		shutDown();
		System.out.println("All done");
		System.exit(0);
	}

	public static void startUp() {
		Services.closeDataAccess();
		Services.createDataAccess(dbName);
	}

	public static void shutDown() {
		Services.closeDataAccess();
	}
	
	public void resetDatabaseToCTdefault() {
		FileCopier.copyTextFile("database/acme.script", "database/curr.acme.script");
		FileCopier.copyTextFile("database/customer.test.script", "database/acme.script");
	}
	
	public void resetDatabaseToOriginalState() {
		FileCopier.copyTextFile("database/curr.acme.script", "database/acme.script");	
	}
}
