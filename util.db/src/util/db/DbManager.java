package util.db;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;


public class DbManager {

	
	protected CouchDbInstance dbInst = null;
	protected CouchDbConnector dbCon = null;
	
	public void init(String hostIp, String dbName) throws Exception {
		dbCon = null;
		
		System.getProperties().setProperty("org.ektorp.support.AutoUpdateViewOnChange", "true");
		dbInst = new StdCouchDbInstance(new StdHttpClient.Builder().host(hostIp).build());

		if (!dbInst.checkIfDbExists(dbName)) {
			throw(new Exception("Database '"+dbName+"' does not exist."));
		}
		
		dbCon = new StdCouchDbConnector(dbName, dbInst);
		initRepos();
	}

	public boolean isReady() {
		if (dbInst==null) return false;
		else if (dbCon==null) return false;
		else return true;
	}
	
	public CouchDbConnector getDb() {
		return dbCon;
	}

	
	protected void initRepos() throws Exception {
		
	}
	
}
