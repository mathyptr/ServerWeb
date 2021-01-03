/**
 * Classe per la gestione di un oggetto person che sara' prelevato dal database
 * @author Patrissi Mathilde
 */
public class Person {
  private String firstName;
  private String lastName;
  private String address;
  private String passport;
  private String info = "Some standard info";

  /**
   * costruttore della classe Person
   */
  public Person() {}

  /**
   * costruttore della classe Person
   * @param firstName String
   * @param lastName String
   */
  public Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

	/**
	 * Metodo per settare l'indirizzo
	 * 
	 * @param address String
	 */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Metodo per ricavare l'indirizzo
   * @return l'indirizzo
   * 
   */
  public String getAddress() {
    return address;
  }

  /**
   * Metodo per settare il nome
   * @param firstName String
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Metodo per ricavare il nome
   * @return il nome
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Metodo per settare il cognome
   * @param lastName String
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Metodo per ricavare il cognome
   * @return il cognome
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Metodo per settare il passaporto
   * @param passport String
   */
  public void setPassport(String passport) {
    this.passport = passport;
  }

  /**
   * Metodo per ricavare il passaporto
   * @return il passaporto
   */
  public String getPassport() {
    return passport;
  }

  /**
   * Metodo per settare informazioni generali sulla persona
   * @param info String
   */
  public void setInfo(String info) {
    this.info = info;
  }

  /**
   * Metodo per ricavare le informazioni generali sulla persona
   * @return le informazioni
   */
  public String getInfo() {
    return info;
  }
}