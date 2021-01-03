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

/**
 * Classe per la serializzazione e deserializzazione
 * 
 * @author Patrissi Mathilde
 */
public class DesAndSer {

	List<PuntiVendita> listPuntiVendita;

	/**
	 * costruttore della classe DesAndSer
	 */
	public void DesAndSer() {

	}

	/**
	 * Metodo per serializzare una lista di punti vendita in xml
	 * 
	 * @param fileXML File
	 */
	public void xmlWrite(File fileXML) throws Throwable {

		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileXML));
		encoder.writeObject(listPuntiVendita);
		encoder.close();

	}

	/**
	 * Metodo per deserializzare da file json a classe puntiVendita
	 * 
	 * @param fileRequested File
	 */
	public void jsonRead(String fileRequested) throws Throwable {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream input = getClass().getClassLoader().getResourceAsStream("puntiVendita.json");
		String jsonPVArray = IOUtils.toString(input, StandardCharsets.UTF_8.name());

		listPuntiVendita = objectMapper.readValue(jsonPVArray, new TypeReference<List<PuntiVendita>>() {
		});

	}

	/**
	 * Metodo per leggere le informazioni di configurazione dal file json che le
	 * contiene
	 * 
	 * @param fileRequested File
	 * @return l'oggetto ConfParam contenente tali informazioni
	 */
	public ConfParam jsonReadConf(String fileRequested) throws Throwable {
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream input = new FileInputStream(fileRequested);
		return objectMapper.readValue(input, ConfParam.class);

	}

	/**
	 * Metodo per serializzare una lista di oggetti Person in xml
	 * 
	 * @param fileXML    File
	 * @param listPerson List<Person>
	 */
	public void xmlWritePerson(File fileXML, List<Person> listPerson) throws Throwable {

		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileXML));
		encoder.writeObject(listPerson);
		encoder.close();

	}

	/**
	 * Metodo per serializzare una lista di oggetti Person in json
	 * 
	 * @param fileJSON   File
	 * @param listPerson List<Person>
	 */
	public void jsonWritePerson(File fileJSON, List<Person> listPerson) throws Throwable {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new FileOutputStream(fileJSON), listPerson);
	}
}