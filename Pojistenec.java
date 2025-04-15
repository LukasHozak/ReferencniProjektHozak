package cz.itnetwork;

/**
 * Třída reprezentuje pojištěnce
 */
public class Pojistenec {
    /**
     * Křestní jméno pojištěnce
     */
    private String jmeno;
    /**
     * Příjmení pojištěnce
     */
    private String prijmeni;
    /**
     * Věk pojištěnce
     */
    private int vek;
    /**
     * Telefonní číslo pojištěnce
     */
    private String telefonniCislo;
    /**
     * Statická proměnná, která se s každou novou instancí pojištěnce inkrementuje
     */
    private static int pristiId = 0;
    /**
     * ID pojištěnce, hodnota je přidělena v konstruktoru
     */
    private int idPojistence;

    /**
     * Vytvoří novou instanci pojištěnce
     * @param jmeno Jméno nového uživatele
     * @param prijmeni Příjmení nového uživatele
     * @param vek Věk nového uživatele
     * @param telefonniCislo Telefonní číslo nového uživatele
     */
    public Pojistenec(String jmeno, String prijmeni, int vek, String telefonniCislo) {
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.vek = vek;
        this.telefonniCislo = telefonniCislo;
        this.idPojistence = pristiId;
        pristiId++;
    }

    /**
     * Vrací ID pojištěnce
     * @return idPojistence Vrácené ID pojištěnce
     */
    public int getIdPojistence() {
        return idPojistence;
    }

    /**
     * Vrací jméno pojištěnce
     * @return jmeno Vrácené jméno
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     * Vrací příjmení pojištěnce
     * @return prijmeni Vrácené příjmení
     */
    public String getPrijmeni() {
        return prijmeni;
    }

    /**
     * Vrací textovou reprezentaci pojištěnce
     * @return text Textová reprezentace pojištěnce
     */
    @Override
    public String toString() {
        int delkaJmena = jmeno.length() + prijmeni.length() + 1;
        int delkaMezery = 20 - delkaJmena;
        String textovaReprezentace = "   ID: " + idPojistence + " - " + jmeno + " " + prijmeni + " ";
        for (int i = 0; i < delkaMezery; i++) {
            textovaReprezentace += "-";
        }
        textovaReprezentace += " věk: " + vek + " tel.č: " + telefonniCislo;
        return textovaReprezentace;
    }
}
