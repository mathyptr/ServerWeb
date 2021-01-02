

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Classe per la gestione del database
 * @author Patrissi Mathilde
 */
public class DBManager {

	private String url = "jdbc:mysql://localhost:3306/TIPSIT";
	private String user;
	private String passw;
	private String host;
	private String port;
	private String db;

	
	DBManager(String user,String passw,String host, String port,String db ){
		this.user=user;
		this.passw=passw;
		this.host=host;
		this.port=port;
		this.db=db;
		url = "jdbc:mysql://"+host+":"+port+"/"+db;
	
	 }
    /**
     * Metodo per la connessione al database
     *
     * @return the Connection object
     */
    private Connection connect() {  	
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,passw);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Metodo per l'inserimento di una riga nel database
     *
     * @param src String
     * @param dest String
     * @param mes String
     */
    public void insert(String src, String dest, String group, String mes) {
        String sql = "INSERT INTO messages(source,dest,groupm,mess) VALUES(?,?,?,?)";

        try {
        	Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, src);
            pstmt.setString(2,dest);
            pstmt.setString(3,group);
            pstmt.setString(4,mes);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metodo per prelevare i messaggi richiesti
     *
     * @param source String
     * @param dest String
     * @param il vettore contenente i messaggi
     */
    public java.util.Vector <Person> readAll() {
    	String sql ="select firstName,lastName,address,passport from person"; 

    	 java.util.Vector <Person> pers=new java.util.Vector <Person>(1,1);
    	 Person p;
    	 try{
     	Connection conn = this.connect();
        Statement stmt  = conn.createStatement();
     	ResultSet rs    = stmt.executeQuery(sql);
             while (rs.next()) {
               p= new Person();
               p.setFirstName(rs.getString("firstName"));     
               p.setLastName(rs.getString("lastName"));
               p.setAddress(rs.getString("address")); 
               p.setPassport(rs.getString("passport"));       
               pers.add(p);
             }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    	 return pers;
    }
  
}