import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

public class DesAndSer {

	List<PuntiVendita> listPuntiVendita;
	
	public void DesAndSer(){

	  }
	
	
  public void xmlWrite(File fileXML) throws Throwable{
    
	  XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileXML));
	  encoder.writeObject(listPuntiVendita);
      encoder.close();
 
  }
  
  public void xmlRead() throws Throwable{

	  }
  
	  public void jsonWrite() throws Throwable{
		 
	  }

	  public void jsonRead(String fileRequested) throws Throwable{
		  ObjectMapper objectMapper = new ObjectMapper();
		  InputStream input = getClass().getClassLoader().getResourceAsStream("puntiVendita.json");
		  String jsonPVArray = IOUtils.toString(input, StandardCharsets.UTF_8.name()); 
		
		  listPuntiVendita = objectMapper.readValue(jsonPVArray, new TypeReference<List<PuntiVendita>>(){});
		
	 
	  }	 
}