package util.db;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;


public class DbManager {

	protected String hostIp = "";
	protected CouchDbInstance dbInst = null;
	protected CouchDbConnector dbCon = null;
	
	public void init(String hostIp, String dbName) throws Exception {
		this.hostIp = hostIp;
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

	public String getUrlForWebView(CouchDbConnector con, String id) {
		StringBuilder url = new StringBuilder();
		url.append("http://");
		url.append(hostIp);
		if (hostIp.equals("192.168.1.3")) url.append(":5984/_utils/document.html?");   // futon
		else url.append(":5984/_utils/#database/");  // fauxton
		url.append(con.getDatabaseName());
		url.append("/");		
		url.append(id);
		return url.toString();
	}
	
	protected void initRepos() throws Exception {
		
	}
	
}
