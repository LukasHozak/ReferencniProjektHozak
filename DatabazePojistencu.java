package cz.itnetwork;

import java.util.ArrayList;

/**
 * Třída reprezentuje databázi pojištěnců v kolekci ArrayList
 */
public class DatabazePojistencu {
    /**
     * Seznam pojištěnců
     */
    private ArrayList <Pojistenec> pojistenci;

    /**
     * Vytvoří instanci databáze pojištěnců, která v sobě současně vytvoří instanci prázdné kolekce seznamu pojištěnců
     */
    public DatabazePojistencu() {
        this.pojistenci = new ArrayList<>();
    }

    /**
     * Vytvoří instanci databáze pojištěnců, která do seznamu pojištěnců přiřadí seznam pojištěnců z parametru
     */
    public DatabazePojistencu(ArrayList<Pojistenec> pojistenci) {
        this.pojistenci = pojistenci;
    }


    /**
     * Přidá nového pojištěnce
     * @param jmeno Jméno nového uživatele
     * @param prijmeni Příjmení nového uživatele
     * @param vek Věk nového uživatele
     * @param telefonniCislo Telefonní číslo nového uživatele
     */
    public void pridejPojistence(String jmeno, String prijmeni, int vek, String telefonniCislo) {
        pojistenci.add(new Pojistenec(jmeno, prijmeni, vek, telefonniCislo));
    }

    /**
     * Vyhledá a vrátí seznam pojištěnců dle zadaného jména a příjmení
     * @param jmeno Vybrané jméno
     * @param prijmeni Vybrané příjmení
     * @return nalezeniPojistenci seznam nalezených pojištěnců
     */
    public ArrayList<Pojistenec> vyberPojistenceDleJmena(String jmeno, String prijmeni) {
        ArrayList<Pojistenec> nalezeniPojistenci = new ArrayList<>();
        for (Pojistenec pojistenec : pojistenci) {
            if ((jmeno.equals(pojistenec.getJmeno().toLowerCase()))&&(prijmeni.equals(pojistenec.getPrijmeni().toLowerCase()))) {
                nalezeniPojistenci.add(pojistenec);
            }
        }
        return nalezeniPojistenci;
    }

    /**
     * Vrátí množinu ID všech pojištěnců v databázi
     * @return množina ID pojištěnců v databázi
     */
    public int[] vratIDPojistencuVDatabazi() {
        int[] mnozinaIdShodnychPojistencu = new int[pojistenci.size()];
        int i = 0;
        for (Pojistenec pojistenec : pojistenci) {
            mnozinaIdShodnychPojistencu[i] = pojistenec.getIdPojistence();
            i++;
        }
        return mnozinaIdShodnychPojistencu;
    }

    /**
     * Vyhledá a vrátí konkrétního pojištěnce dle ID
     * @param id ID hledaného zaměstnance
     * @return nalezený zaměstnanec
     */
    public Pojistenec vyberPojistenceDleID(int id) {
        int idPojistence = id;
        Pojistenec nalezenyPojistenec = null;
        for (Pojistenec pojistenec : pojistenci) {
            if (idPojistence == pojistenec.getIdPojistence()) {
                nalezenyPojistenec = pojistenec;
            }
        }
        return nalezenyPojistenec;
    }

    /**
     * Vymaže konkrétního pojištěnce
     * @param pojistenec Pojištěnec potvrzený k vymazání
     */
    public void vymazPojistence(Pojistenec pojistenec) {
        pojistenci.remove(pojistenec);
    }

    /**
     * Předá seznam pojištěnců v této databázi
     * @return pojistenci Seznam všech pojištěnců této databáze
     */
    public ArrayList<Pojistenec> predejVsechnyPojistence() {
        return pojistenci;
    }

}
