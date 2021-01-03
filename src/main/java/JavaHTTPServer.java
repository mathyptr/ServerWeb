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

/**
 * Classe che implementa il web server
 * 
 * @author Patrissi Mathilde
 */

// This software was taken  by the SSaurel's Blog : 
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// Each Client Connection will be managed in a dedicated Thread
public class JavaHTTPServer implements Runnable {

	// static final File WEB_ROOT = new File(".");
	static File WEB_ROOT = new File(".");
	static ConfParam conf;
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	// port to listen connection
	// static int PORT = 8080;

	// verbose mode
	static final boolean verbose = true;
	static final Logger logger = LogManager.getLogger("MyLog");
	MessagesBundle msgB = new MessagesBundle();
	int fileLengthOUT = 0;
	String contentType = "text/html";

	// Client Connection via Socket Class
	private Socket connect;
	ServerSocket serverConnect = null;

	/**
	 * Costruttore della classe JavaHTTPServer
	 * 
	 * @param serverConnect ServerSocket
	 */
	public JavaHTTPServer(ServerSocket serverConnect) {
		msgB.SetLanguage("it", "IT");
		this.serverConnect = serverConnect;
	}

	/**
	 * Metodo per l'accettazione di una connessione
	 */
	public void accept() {
		try {
			connect = serverConnect.accept();
		} catch (IOException e) {
			logger.error(msgB.GetResourceValue("srvAcceptError") + e.getMessage());
		}
		logger.info(msgB.GetResourceValue("srvConnOpened") + new Date());

	}

	/**
	 * Metodo main
	 * 
	 * @param args String[]
	 */
	public static void main(String[] args) {

		MessagesBundle msgB = new MessagesBundle();
		msgB.SetLanguage("it", "IT");

		File jarPath;
		String propertiesPath;

//	        jarPath=new File( this.getClass().getSimpleName().class.getProtectionDomain().getCodeSource().getLocation().getPath());
		jarPath = new File(JavaHTTPServer.class.getProtectionDomain().getCodeSource().getLocation().getPath());

		conf = new ConfParam();
		DesAndSer d = new DesAndSer();
		try {
			conf = d.jsonReadConf(jarPath.getParentFile().getAbsolutePath() + "/JavaHTTPServer.json");
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// URL p=JavaHTTPServer.class.getResource(".");
		// WEB_ROOT=new File(p.getPath());
		WEB_ROOT = new File(conf.getwebroot());

		logger.info(msgB.GetResourceValue("srvStarted") + conf.getsrvPORT() + " ...\n");

		Socket connect;
		ServerSocket serverConnect = null;

		try {
			serverConnect = new ServerSocket(Integer.valueOf(conf.getsrvPORT()).intValue());
		} catch (IOException e) {
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

	/**
	 * metodo che viene richiamato per l'esecuzione di un thread
	 */
	// @Override
	public void run() {
		// we manage our particular client connection
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedOutputStream dataOut = null;
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
			String lang = "en";
			String row = null;
			while (parse.hasMoreTokens()) {
				row = parse.nextToken();
				if (row.equals("Accept-Language"))
					lang = row.substring(row.indexOf(":") + 1, row.length());
			}
			if (row == null)
				lang = "en";

			// we support only GET and HEAD methods, we check
			if (!method.equals("GET") && !method.equals("HEAD")) {
				logger.info(msgB.GetResourceValue("srvMethodNotImplemented"));
				;
				// we return the not supported file to the client
				File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
				int fileLength = (int) file.length();
				String contentMimeType = "text/html";
				// read content to return to client
				byte[] fileData = readFileData(file, fileLength);

				// we send HTTP Headers with data to client
				printHeader(out, msgB.GetResourceValue("NotImplemented"), fileLength);
				// file
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();

			} else {
				// GET or HEAD method

				if (fileRequested.endsWith("/")) {
					fileRequested += DEFAULT_FILE;
				}

				if (fileRequested.endsWith("/db/person-xml")) {
					DesAndSer d = new DesAndSer();
					try {

						String nomeFileXML = fileRequested.substring(fileRequested.indexOf("/db/") + 4) + ".xml";
						File fileXML = new File(WEB_ROOT, nomeFileXML);
						DBManager dbm = new DBManager(conf.getmysqluser(), conf.getmysqlpassw(), conf.getmysqlhost(),
								conf.getmysqlport(), conf.getmysqldb());
						java.util.Vector<Person> allp = dbm.readAll();
						d.xmlWritePerson(fileXML, allp);
						contentType = getContentType(fileRequested);
						int fileLength = (int) fileXML.length();
						byte[] fileData = readFileData(fileXML, fileLength);
						printHeader(out, msgB.GetResourceValue("respOK"), fileLength);
						dataOut.write(fileData, 0, fileLength);
						dataOut.flush();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						logger.error(msgB.GetResourceValue("ErrorReadJSON") + e.getMessage());
					}

				} else if (fileRequested.endsWith("/db/person-json")) {
					DesAndSer d = new DesAndSer();
					try {
						String nomeFileJSON = fileRequested.substring(fileRequested.indexOf("/db/") + 4) + ".json";
						File fileJSON = new File(WEB_ROOT, nomeFileJSON);
						DBManager dbm = new DBManager(conf.getmysqluser(), conf.getmysqlpassw(), conf.getmysqlhost(),
								conf.getmysqlport(), conf.getmysqldb());
						java.util.Vector<Person> allp = dbm.readAll();
						d.jsonWritePerson(fileJSON, allp);
						contentType = getContentType(fileRequested);
						int fileLength = (int) fileJSON.length();
						byte[] fileData = readFileData(fileJSON, fileLength);
						printHeader(out, msgB.GetResourceValue("respOK"), fileLength);
						dataOut.write(fileData, 0, fileLength);
						dataOut.flush();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						logger.error(msgB.GetResourceValue("ErrorReadJSON") + e.getMessage());
					}

				} else {

					File file = new File(WEB_ROOT, fileRequested);
					contentType = getContentType(fileRequested);

					if (!file.exists() && fileRequested.indexOf(".") == -1) {

						// printHeader(out,msgB.GetResourceValue("respRedirect"),
						// msgB.GetResourceValue("serverInfo"),0);
						out.println(msgB.GetResourceValue("respRedirect"));
						out.println("Location: " + conf.getredirectTO() + fileRequested + "/");
						// out.println("HTTP/1.1 301 Moved Permanently");
						// out.println("Location: http://127.0.0.1:8080");
						logger.debug("Location: " + conf.getredirectTO() + fileRequested + "/");
						out.println(); // blank line between headers and content, very important !
						out.flush();
					} else {
						if (method.equals("GET")) { // GET method so we return content

							if (fileRequested.indexOf(".xml") != -1) {
								DesAndSer d = new DesAndSer();
								try {
									File fileXML = new File(WEB_ROOT, fileRequested);
									String fileJSON = fileRequested;
									fileJSON = fileJSON.replace(".xml", ".json");
									d.jsonRead(fileJSON);
									d.xmlWrite(fileXML);
									contentType = getContentType(fileRequested);

									int fileLength = (int) fileXML.length();
									byte[] fileData = readFileData(fileXML, fileLength);
									printHeader(out, msgB.GetResourceValue("respOK"), fileLength);
									dataOut.write(fileData, 0, fileLength);

								} catch (Throwable e) {
									// TODO Auto-generated catch block
									logger.error(msgB.GetResourceValue("ErrorReadJSON") + e.getMessage());
								}
							} else if (fileRequested.indexOf(".json") != -1) {
								DesAndSer d = new DesAndSer();
								try {
									File fileJSON = new File(WEB_ROOT, fileRequested);
									DBManager dbm = new DBManager(conf.getmysqluser(), conf.getmysqlpassw(),
											conf.getmysqlhost(), conf.getmysqlport(), conf.getmysqldb());
									java.util.Vector<Person> allp = dbm.readAll();
									d.jsonWritePerson(fileJSON, allp);

									contentType = getContentType(fileRequested);

									int fileLength = (int) fileJSON.length();
									byte[] fileData = readFileData(fileJSON, fileLength);
									printHeader(out, msgB.GetResourceValue("respOK"), fileLength);
									dataOut.write(fileData, 0, fileLength);

								} catch (Throwable e) {
									// TODO Auto-generated catch block
									logger.error(msgB.GetResourceValue("ErrorReadJSON") + e.getMessage());
								}
							} else {
								int fileLength = (int) file.length();

								byte[] fileData = readFileData(file, fileLength);
								// send HTTP Headers
								printHeader(out, msgB.GetResourceValue("respOK"), fileLength);
								dataOut.write(fileData, 0, fileLength);
							}
							dataOut.flush();
						}
						logger.debug(msgB.GetResourceValue("srvFileTypeReturned") + contentType + ":" + fileRequested);
					}
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
			logger.debug(msgB.GetResourceValue("srvConnClosed") + "\n");
		}

	}

	/**
	 * metodo per la lettura del file richiesto
	 * 
	 * @param file       File
	 * @param fileLength int
	 * @return fileData
	 */
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

	/**
	 * metodo per ricavare il tipo del file
	 * 
	 * @param fileRequested File
	 */
	// return supported MIME Types
	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm") || fileRequested.endsWith(".html"))
			return "text/html";
		else if (fileRequested.endsWith(".xml") || fileRequested.endsWith(".XML"))
			return "text/xml";
		else
			return "text/plain";
	}

	/**
	 * metodo per la gestione dell'errore 404
	 * 
	 * @param out           PrintWriter
	 * @param dataOut       OutputStream
	 * @param fileRequested String
	 */
	private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = readFileData(file, fileLength);
		printHeader(out, msgB.GetResourceValue("fileNotFound"), fileLength);
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		logger.debug(fileRequested + msgB.GetResourceValue("fileNotFound"));
	}

	/**
	 * metodo per settare gli header http
	 * 
	 * @param out           PrintWriter
	 * @param statusCode    String
	 * @param fileLengthOUT int
	 */
	private void printHeader(PrintWriter out, String statusCode, int fileLengthOUT) throws IOException {

		out.println(statusCode);
		out.println(conf.getserverInfo());
		out.println("Date: " + new Date());
		out.println("Content-type: " + contentType);
		out.println("Content-length: " + fileLengthOUT);
		out.println(); // blank line between headers and content, very important !
		out.flush(); // flush character output stream buffer

	}

}
