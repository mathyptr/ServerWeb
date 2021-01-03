/**
 * Classe per i parametri di configurazione
 * 
 * @author Patrissi Mathilde
 */
public class ConfParam {
	private String serverInfo;
	private String srvPORT;
	private String webroot;
	private String redirectTO;
	private String mysqluser;
	private String mysqlpassw;
	private String mysqlhost;
	private String mysqlport;
	private String mysqldb;

	/**
	 * costruttore della classe ConfParam
	 */
	public ConfParam() {
	}

	/**
	 * costruttore della classe ConfParam
	 *
	 * @param serverInfo String
	 * @param srvPORT    String
	 * @param webroot    String
	 * @param redirectTO String
	 */
	public ConfParam(String serverInfo, String srvPORT, String webroot, String redirectTO) {
		this.serverInfo = serverInfo;
		this.srvPORT = srvPORT;
		this.webroot = webroot;
		this.redirectTO = redirectTO;
	}

	/**
	 * Metodo per settare le informazioni del server da fornire nell'header http
	 * 
	 * @param serverInfo String
	 */
	public void setserverInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	/**
	 * Metodo per ricavare le informazioni del server da fornire nell'header http
	 * 
	 * @return le informazioni
	 */
	public String getserverInfo() {
		return serverInfo;
	}

	/**
	 * Metodo per settare la porta su cui è in ascolto il server
	 * 
	 * @param srvPORT String
	 */
	public void setsrvPORT(String srvPORT) {
		this.srvPORT = srvPORT;
	}

	/**
	 * Metodo per ricavare la porta su cui è in ascolto il server
	 * 
	 * @return la porta su cui è in ascolto il server
	 */
	public String getsrvPORT() {
		return srvPORT;
	}

	/**
	 * Metodo per settare il percorso della directory contenente le risorse html e
	 * txt
	 * 
	 * @param webroot String
	 */
	public void setwebroot(String webroot) {
		this.webroot = webroot;
	}

	/**
	 * Metodo per ricavare il percorso della directory contenente le risorse html e
	 * txt
	 * 
	 * @return il percorso della directory
	 */
	public String getwebroot() {
		return webroot;
	}

	/**
	 * Metodo per settare il nuovo url
	 * 
	 * @param redirectTO String
	 */
	public void setredirectTO(String redirectTO) {
		this.redirectTO = redirectTO;
	}

	/**
	 * Metodo per ricavare il nuovo url
	 * 
	 * @return il nuovo url
	 */
	public String getredirectTO() {
		return redirectTO;
	}

	/**
	 * Metodo per settare l'utente di mysql
	 * 
	 * @param mysqluser String
	 */
	public void setmysqluser(String mysqluser) {
		this.mysqluser = mysqluser;
	}

	/**
	 * Metodo per ricavare l'utente di mysqlta
	 * 
	 * @return l'utente
	 */
	public String getmysqluser() {
		return mysqluser;
	}

	/**
	 * Metodo per settare la password di mysql
	 * 
	 * @param mysqlpassw String
	 */
	public void setmysqlpassw(String mysqlpassw) {
		this.mysqlpassw = mysqlpassw;
	}

	/**
	 * Metodo per ricavare la password di mysqlta
	 * 
	 * @return la password
	 */
	public String getmysqlpassw() {
		return mysqlpassw;
	}

	/**
	 * Metodo per settare l'hostname del server su cui si trova mysql
	 * 
	 * @param mysqlhost String
	 */
	public void setmysqlhost(String mysqlhost) {
		this.mysqlhost = mysqlhost;
	}

	/**
	 * Metodo per ricavare l'hostname del server su cui si trova mysql
	 * 
	 * @return l'hostname
	 */
	public String getmysqlhost() {
		return mysqlhost;
	}

	/**
	 * Metodo per settare la porta su cui è in ascolto il servizio mysql
	 * 
	 * @param mysqlport String
	 */
	public void setmysqlport(String mysqlport) {
		this.mysqlport = mysqlport;
	}

	/**
	 * Metodo per ricavare la porta su cui è in ascolto il servizio mysql
	 * 
	 * @return la porta
	 */
	public String getmysqlport() {
		return mysqlport;
	}

	/**
	 * Metodo per settare il nome del database
	 * 
	 * @param mysqldb String
	 */
	public void setmysqldb(String mysqldb) {
		this.mysqldb = mysqldb;
	}

	/**
	 * Metodo per ricavare il nome del database
	 * 
	 * @return il nome del database
	 */
	public String getmysqldb() {
		return mysqldb;
	}

}
