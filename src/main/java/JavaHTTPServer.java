import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.StringTokenizer;
import org.apache.logging.log4j.Logger;



import org.apache.logging.log4j.LogManager;


// This software was taken  by the SSaurel's Blog : 
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// Each Client Connection will be managed in a dedicated Thread
public class JavaHTTPServer implements Runnable{ 
	
//	static final File WEB_ROOT = new File(".");
	static File WEB_ROOT = new File(".");
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	// port to listen connection
//	static  int PORT = 8080;
	
	// verbose mode
	static final boolean verbose = true;
	static final Logger logger= LogManager.getLogger("MyLog");
    MessagesBundle msgB= new MessagesBundle();	
	int fileLengthOUT = 0;
	String contentType = "text/html";	
	
	// Client Connection via Socket Class
	private Socket connect;
	ServerSocket serverConnect =null;
	
	public JavaHTTPServer(ServerSocket serverConnect) {
	    msgB.SetLanguage("it", "IT");
	    this.serverConnect= serverConnect;
	}
	
	public void accept() {
		try {	
			connect = serverConnect.accept();
		} catch (IOException e) {
			logger.error(msgB.GetResourceValue("srvAcceptError") + e.getMessage());
		}		
		logger.info(msgB.GetResourceValue("srvConnOpened") + new Date()); 

	}
	public static void main(String[] args) {
		
			URL p=JavaHTTPServer.class.getResource(".");
			WEB_ROOT=new File(p.getPath());
			Socket connect;
			
			MessagesBundle msgB= new MessagesBundle();	
			msgB.SetLanguage("it", "IT");
			logger.info( msgB.GetResourceValue("srvStarted") + msgB.GetResourceValue("srvPORT") + " ...\n"); 
			ServerSocket serverConnect =null;
		
			
			try {
					serverConnect = new ServerSocket(Integer.valueOf(msgB.GetResourceValue("srvPORT")).intValue());
			} 
			catch (IOException e) {
				logger.error(msgB.GetResourceValue("srvConnError") + e.getMessage());
			}
				
			// we listen until user halts server execution
			while (true) {
				JavaHTTPServer myServer = new JavaHTTPServer(serverConnect);
				myServer.accept();			
				// create dedicated thread to manage the client connection
				Thread thread = new Thread(myServer);
				thread.start();
			}		
	}

//	@Override
	public void run() {
		// we manage our particular client connection
		BufferedReader in = null; PrintWriter out = null; BufferedOutputStream dataOut = null;
		String fileRequested = null;
		
		try {
			// we read characters from the client via input stream on the socket
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			// we get character output stream to client (for headers)
			out = new PrintWriter(connect.getOutputStream());
			// get binary output stream to client (for requested data)
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			
			// get first line of the request from the client
			String input = in.readLine();
			// we parse the request with a string tokenizer
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
			// we get file requested
			fileRequested = parse.nextToken().toLowerCase();
			logger.debug(input);
			String lang="en";
			String row=null;
			while(parse.hasMoreTokens()) {
				row=parse.nextToken();
				if(row.equals("Accept-Language"))
					lang=row.substring(row.indexOf(":")+1, row.length());
			}
			if(row==null)
				lang="en";
				
			// we support only GET and HEAD methods, we check
			if (!method.equals("GET")  &&  !method.equals("HEAD")) {
				logger.info(msgB.GetResourceValue("srvMethodNotImplemented"));;
				// we return the not supported file to the client
				File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
				int fileLength = (int) file.length();
				String contentMimeType = "text/html";
				//read content to return to client
				byte[] fileData = readFileData(file, fileLength);
									
				// we send HTTP Headers with data to client
				printHeader(out,msgB.GetResourceValue("NotImplemented"), msgB.GetResourceValue("serverInfo"),fileLength);
				// file
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
				
			} else {
				// GET or HEAD method
				
				
				if (fileRequested.endsWith("/")) {
					fileRequested += DEFAULT_FILE;
				}
				
				
				File file = new File(WEB_ROOT, fileRequested);		
				contentType = getContentType(fileRequested);
				
				if(!file.exists() && fileRequested.indexOf(".")==-1)  {
						
//						printHeader(out,msgB.GetResourceValue("respRedirect"), msgB.GetResourceValue("serverInfo"),0);	
						out.println(msgB.GetResourceValue("respRedirect"));
						out.println("Location: " + msgB.GetResourceValue("redirectTO")+fileRequested+"/");
//					out.println("HTTP/1.1 301 Moved Permanently");
//							out.println("Location: http://127.0.0.1:8080");
						logger.debug("Location: " + msgB.GetResourceValue("redirectTO")+fileRequested+"/");
						out.println(); // blank line between headers and content, very important !
						out.flush();
				}
				else
				{	
					if (method.equals("GET")) { // GET method so we return content
					
					if(fileRequested.indexOf(".xml")!=-1)
					{
						DesAndSer d= new DesAndSer();
						try {
							String fileJSON = fileRequested;	
							fileJSON=fileJSON.replace(".xml",".json");					
							d.jsonRead(fileJSON);	
							
							File fileXML = new File(WEB_ROOT, fileRequested);
							d.xmlWrite(fileXML);	
				
							contentType= getContentType(fileRequested);
		
							int fileLength = (int) fileXML.length();
							byte[] fileData = readFileData(fileXML, fileLength);	
							printHeader(out,msgB.GetResourceValue("respOK"), msgB.GetResourceValue("serverInfo"),fileLength);											
							dataOut.write(fileData, 0, fileLength);
					
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							logger.error(msgB.GetResourceValue("ErrorReadJSON") + e.getMessage());
						}
					}	
					else {						
						int fileLength = (int) file.length();
			
						byte[] fileData = readFileData(file, fileLength);					
					// send HTTP Headers
						printHeader(out,msgB.GetResourceValue("respOK"), msgB.GetResourceValue("serverInfo"),fileLength);				
						dataOut.write(fileData, 0, fileLength);
					}
					dataOut.flush();
				}				
				logger.debug(msgB.GetResourceValue("srvFileTypeReturned") + contentType +":" + fileRequested );
				}
			}
			
		} catch (FileNotFoundException fnfe) {
			try {
				fileNotFound(out, dataOut, fileRequested);
			} catch (IOException ioe) {
				logger.error(msgB.GetResourceValue("fileNotFound") + ioe.getMessage());
			}
			
		} catch (IOException ioe) {
			logger.error(msgB.GetResourceValue("srvError") + ioe);
		} finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				connect.close(); // we close socket connection
			} catch (Exception e) {
				logger.error(msgB.GetResourceValue("srvErrConnClosed") + e.getMessage());
			} 			
			logger.debug(msgB.GetResourceValue("srvConnClosed")+"\n");
		}
		
		
	}
	
	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
	
	// return supported MIME Types
	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
		else if (fileRequested.endsWith(".xml")  ||  fileRequested.endsWith(".XML"))
			return "text/xml";
		else
			return "text/plain";
	}
	
	private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = readFileData(file, fileLength);
		printHeader(out,msgB.GetResourceValue("fileNotFound"), msgB.GetResourceValue("serverInfo"),fileLength);
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		logger.debug(fileRequested+msgB.GetResourceValue("fileNotFound"));
	}
	
	private void printHeader(PrintWriter out,String statusCode, String serverInfo,int fileLengthOUT) throws IOException {

		out.println(statusCode);
		out.println(serverInfo);
		out.println("Date: " + new Date());
		out.println("Content-type: " + contentType);
		out.println("Content-length: " + fileLengthOUT);
		out.println(); // blank line between headers and content, very important !
		out.flush(); // flush character output stream buffer
	
		
	}
	
}
