package acme.application;

import acme.persistence.DataAccess;
import acme.persistence.DataAccessObject;

public class Services {
	private static DataAccess dataAccessService = null;

	public static DataAccess createDataAccess(DataAccess other) {
		if (dataAccessService == null) {
			dataAccessService = other;
			dataAccessService.open(Main.dbName);
		}
		return dataAccessService;
	}

	public static DataAccess createDataAccess(String dbName) {
		if (dataAccessService == null) {
			dataAccessService = new DataAccessObject(dbName);
			dataAccessService.open(dbName);
		}
		return dataAccessService;
	}

	public static DataAccess getDataAccess(String dbName) {
		if (dataAccessService == null) {
			System.out
					.println("Connection to data access has not been established.");
			System.exit(1);
		}
		return dataAccessService;
	}

	public static void closeDataAccess() {
		if (dataAccessService != null) {
			dataAccessService.close();
		}
		dataAccessService = null;
		System.gc();
	}
}
