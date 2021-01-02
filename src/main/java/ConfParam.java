
public class ConfParam {
		  private String serverInfo;
		  private String srvPORT;
		  private String webroot ;
		  private String redirectTO ;

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

		}

