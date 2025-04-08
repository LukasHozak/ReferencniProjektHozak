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
     * Vytvoří instanci databáze pojištěnců, která v sobě současně vytvoří instanci kolekce seznamu pojištěnců
     */
    public DatabazePojistencu() {
        pojistenci = new ArrayList<>();
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
     * Vyhledá pojištěnce podle zadaného jména a příjmení
     * @param jmeno Vybrané jméno
     * @param prijmeni Vybrané příjmení
     * @return nalezenyPojistenec
     */
    public Pojistenec vyberPojistenceDleJmena(String jmeno, String prijmeni) {
        Pojistenec nalezenyPojistenec = null;
        for (Pojistenec pojistenec : pojistenci) {
            if ((jmeno.equals(pojistenec.getJmeno().toLowerCase()))&&(prijmeni.equals(pojistenec.getPrijmeni().toLowerCase()))) {
                nalezenyPojistenec = pojistenec;
            }
        }
        return nalezenyPojistenec;
    }

    /**
     * Vymaže konkrétního pojištěnce
     */
    public void vymazPojistence(Pojistenec pojistenec) {
        Pojistenec smazanyPojistenec = vyberPojistenceDleJmena(pojistenec.getJmeno(), pojistenec.getPrijmeni());
        pojistenci.remove(smazanyPojistenec);
    }

    /**
     * Předá seznam evidovaných pojištěnců
     * @return Seznam všech evidovaných pojištěnců
     */
    public ArrayList<Pojistenec> predejVsechnyPojistence() {
        return pojistenci;
    }

}
