
public class ConfParam {
		  private String serverInfo;
		  private String srvPORT;
		  private String webroot ;
		  private String redirectTO ;
		  private String mysqluser;
		  private String mysqlpassw;
		  private String mysqlhost;
		  private String mysqlport;
		  private String mysqldb;

		  public ConfParam() {}

		  public ConfParam(String serverInfo, String srvPORT,String webroot,String redirectTO) {
		    this.serverInfo = serverInfo;
		    this.srvPORT=srvPORT;
		    this.webroot = webroot;
		    this.redirectTO = redirectTO;
		    
		  }

		  public void setserverInfo(String serverInfo) {
		    this.serverInfo = serverInfo;
		  }

		  public String getserverInfo() {
		    return serverInfo;
		  }

		  public void setsrvPORT(String srvPORT) {
		    this.srvPORT = srvPORT;
		  }

		  public String getsrvPORT() {
		    return srvPORT;
		  }
		  public void setwebroot(String webroot) {
			    this.webroot = webroot;
		  }

		  public String getwebroot() {
			    return webroot;
		  }
		  public void setredirectTO(String redirectTO) {
		    this.redirectTO = redirectTO;
		  }

		  public String getredirectTO() {
		    return redirectTO;
		  }

		  public void setmysqluser(String mysqluser) {
			    this.mysqluser = mysqluser;
			  }

			  public String getmysqluser() {
			    return mysqluser;
			  }
			  public void setmysqlpassw(String mysqlpassw) {
				    this.mysqlpassw = mysqlpassw;
				  }

				  public String getmysqlpassw() {
				    return mysqlpassw;
				  }
				  public void setmysqlhost(String mysqlhost) {
					    this.mysqlhost = mysqlhost;
					  }

					  public String getmysqlhost() {
					    return mysqlhost;
					  }
					  public void setmysqlport(String mysqlport) {
						    this.mysqlport = mysqlport;
						  }

						  public String getmysqlport() {
						    return mysqlport;
						  }
						  public void setmysqldb(String mysqldb) {
							    this.mysqldb = mysqldb;
							  }

							  public String getmysqldb() {
							    return mysqldb;
							  }
		  
		  
		  
		}

