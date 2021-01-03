/**
 * Classe per la gestione dei punti vendita
 * @author Patrissi Mathilde
 */
public class PuntiVendita {

  
  private int idPuntoVendita;
  private String  denominazione;
  private String  indirizzo;
  private String  cap;
  private String  comune;
  private String  codProvincia;
  private String  urlSito;
  private String telefonoPrincipale;
  private String telefonoSecondario;
  private String  email;
  private String latitudine;
  private String longitudine;
  private boolean flagFisicoOnline;
  private int idEsercente;
  private String  ragioneSociale;
  
	/**
	 * costruttore della classe PuntiVendita
	 */
  public PuntiVendita() {}

  /**
   * Metodo per ricavare l'id del PuntoVendita
   * @return l'id
   */
  public int getIdPuntoVendita() {
	return idPuntoVendita;
}

  /**
   * Metodo per settare l'id del PuntoVendita
   * @param idPuntoVendita int
   */
public void setIdPuntoVendita(int idPuntoVendita) {
	this.idPuntoVendita = idPuntoVendita;
}

/**
 * Metodo per ricavare il nome del PuntoVendita
 * @return il nome
 */
public String getDenominazione() {
	return denominazione;
}

/**
 * Metodo per settare il nome del PuntoVendita
 * @param denominazione String
 */
public void setDenominazione(String denominazione) {
	this.denominazione = denominazione;
}

/**
 * Metodo per ricavare l'indirizzo del PuntoVendita
 * @return l'indirizzo
 */
public String getIndirizzo() {
	return indirizzo;
}

/**
 * Metodo per settare l'indirizzo del PuntoVendita
 * @param indrizzo String
 */
public void setIndirizzo(String indirizzo) {
	this.indirizzo = indirizzo;
}

/**
 * Metodo per ricavare il cap del PuntoVendita
 * @return il cap
 */
public String getCap() {
	return cap;
}

/**
 * Metodo per settare il cap del PuntoVendita
 * @param cap String
 */
public void setCap(String cap) {
	this.cap = cap;
}

/**
 * Metodo per ricavare il comune del PuntoVendita
 * @return il comune
 */
public String getComune() {
	return comune;
}

/**
 * Metodo per settare il comune del PuntoVendita
 * @param comune String
 */
public void setComune(String comune) {
	this.comune = comune;
}

/**
 * Metodo per ricavare il codice della provincia del PuntoVendita
 * @return il codice della provincia
 */
public String getCodProvincia() {
	return codProvincia;
}

/**
 * Metodo per settare il codice della provincia del PuntoVendita
 * @param codProvincia String
 */
public void setCodProvincia(String codProvincia) {
	this.codProvincia = codProvincia;
}

/**
 * Metodo per ricavare l'url del sito del PuntoVendita
 * @return l'url
 */
public String getUrlSito() {
	return urlSito;
}

/**
 * Metodo per settare l'url del sito del PuntoVendita
 * @param urlSito String
 */
public void setUrlSito(String urlSito) {
	this.urlSito = urlSito;
}

/**
 * Metodo per ricavare il telefono principale del PuntoVendita
 * @return il telefono principale
 */
public String getTelefonoPrincipale() {
	return telefonoPrincipale;
}

/**
 * Metodo per settare il telefono principale del PuntoVendita
 * @param telefonoPrincipale String
 */
public void setTelefonoPrincipale(String telefonoPrincipale) {
	this.telefonoPrincipale = telefonoPrincipale;
}

/**
 * Metodo per ricavare il telefono secondario del PuntoVendita
 * @return il telefono secondario
 */
public String getTelefonoSecondario() {
	return telefonoSecondario;
}

/**
 * Metodo per settare il telefono secondario del PuntoVendita
 * @param telefonoSecondario String
 */
public void setTelefonoSecondario(String telefonoSecondario) {
	this.telefonoSecondario = telefonoSecondario;
}

/**
 * Metodo per ricavare la mail del PuntoVendita
 * @return la mail
 */
public String getEmail() {
	return email;
}

/**
 * Metodo per settare la mail del PuntoVendita
 * @param email String
 */
public void setEmail(String email) {
	this.email = email;
}

/**
 * Metodo per ricavare la latitudine del PuntoVendita
 * @return la latitudine
 */
public String getLatitudine() {
	return latitudine;
}

/**
 * Metodo per settare la latitudine del PuntoVendita
 * @param latitudine String
 */
public void setLatitudine(String latitudine) {
	this.latitudine = latitudine;
}

/**
 * Metodo per ricavare la longitudine del PuntoVendita
 * @return la longitudine
 */
public String getLongitudine() {
	return longitudine;
}

/**
 * Metodo per settare la longitudine del PuntoVendita
 * @param longitudine String
 */
public void setLongitudine(String longitudine) {
	this.longitudine = longitudine;
}

/**
 * Metodo per conoscere lo stato del punto vendita
 * @return vero o falso
 */
public boolean isFlagFisicoOnline() {
	return flagFisicoOnline;
}

/**
 * Metodo per settare lo stato del PuntoVendita
 * @param flagFisicoOnline boolean
 */
public void setFlagFisicoOnline(boolean flagFisicoOnline) {
	this.flagFisicoOnline = flagFisicoOnline;
}

/**
 * Metodo per ricavare l'id dell'esercente
 * @return l'id dell'esercente
 */
public int getIdEsercente() {
	return idEsercente;
}

/**
 * Metodo per settare l'id dell'esercente
 * @param idEsercente int
 */
public void setIdEsercente(int idEsercente) {
	this.idEsercente = idEsercente;
}

/**
 * Metodo per ricavare la ragione sociale
 * @return la ragione sociale
 */
public String getRagioneSociale() {
	return ragioneSociale;
}

/**
 * Metodo per settare la ragione sociale
 * @param ragioneSociale String
 */
public void setRagioneSociale(String ragioneSociale) {
	this.ragioneSociale = ragioneSociale;
}

 
}