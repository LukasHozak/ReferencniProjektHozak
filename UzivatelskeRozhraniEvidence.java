package cz.itnetwork;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Třída slouží k vykreslení evidence a ke komunikaci s uživatelem
 */
public class UzivatelskeRozhraniEvidence {

    /**
     * Databáze pojištěnců
     */
    private DatabazePojistencu databaze;

    /**
     * Instance scanneru
     */
    private Scanner sc = new Scanner(System.in);

    public UzivatelskeRozhraniEvidence() {
        databaze = new DatabazePojistencu();
    }

    /**
     * Sestaví celou výstražnou hlášku k neúspěšné validaci vstupní hodnoty
     * @param hlaska konkrétní část výstražné hlášky vztahující se k validované proměnné
     * @return
     */
    public String sestavVystraznouHlasku(String hlaska) {
        return String.format("   Chybně zadáno - %s. Opakujte Znovu.", hlaska);
    }

    /**
     * Obsahuje cyklus, který ošetřuje, aby byla vstupní hodnota typu int
     * @return čiselný vstup
     */
    public int parsujCislo() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception ex) {
                System.out.println(sestavVystraznouHlasku("nebyla zadána číselná hodnota"));
            }
        }
    }

    /**
     * Zvaliduje zadaný text
     * @param hlaska parametr, který bude doplněn do výstražné hlášky podle druhu validovaného textu
     * @return
     */
    public String zvalidujZadanyText(String hlaska) {
        boolean spravneZadano = false;
        String vstupniText = "";
        while (!spravneZadano) { //Validace zadání vstupního textu - musí obsahovat alespoň jeden znak
            vstupniText = sc.nextLine().trim();
            if (vstupniText.isEmpty()) {
                System.out.println(sestavVystraznouHlasku(hlaska + " neobsahuje žádnou hodnotu"));
            } else spravneZadano = true;
        }
        return vstupniText;
    }

    /**
     * Přidá nového pojištěnce. Metoda ošetřuje správnost vstupních záznamů
     */
    public void pridejPojistence() {
        boolean spravne = false;
        String jmeno = "";
        String prijmeni = "";
        int vek = 0;
        String telCislo = "";
        String overovaciRetezec = "0123456789";
        boolean telCisloObsahujeZnak = false;

        System.out.println("   Zadejte jméno pojištěného: "); //Zadání křestního jména pojištěnce
        jmeno = zvalidujZadanyText("jméno");

        System.out.println("   Zadejte příjmení pojištěného: "); //Zadání příjmení pojištěnce
        prijmeni = zvalidujZadanyText("příjmení");

        System.out.println("   Zadejte věk pojištěného: ");
        while (!spravne) { //Validace zadání hodnoty - věk nesmí být záporný, ani delší než 120 let
            vek = parsujCislo();
            if ((vek < 0)||(vek > 120)) {
                System.out.println(sestavVystraznouHlasku("zadaná hodnota není reálná"));
            } else spravne = true;
        }

        spravne = false;
        System.out.println("   Zadejte telefonní číslo pojištěného bez předvolby: ");
        while (!spravne) {
            telCislo = sc.nextLine().trim();
            for (char cislo : telCislo.toCharArray()) {
                if (!(overovaciRetezec.contains(Character.toString(cislo)))) { //Podmínka porovnává, že telCislo obsahuje pouze znaky z proměnné overovaciRetezec (čísla)
                    telCisloObsahujeZnak = true;
                }
            }
            if (telCisloObsahujeZnak) { //Číslo nesmí obsahovat nečíselný znak
                System.out.println(sestavVystraznouHlasku("zadané tel. číslo obsahuje nečíselnou hodnotu"));
                telCisloObsahujeZnak = false;
            } else if (telCislo.length() != 9) { //Číslo musí být dlouhé přesně 9 znaků
                System.out.println(sestavVystraznouHlasku("zadané tel. číslo nemá správnou délku"));
            } else spravne = true;
        }

        spravne = false;
        //Přidání pojištěnce do databáze
        databaze.pridejPojistence(jmeno, prijmeni, vek, telCislo);
        System.out.println("   Nový pojištěnec " + jmeno + " " + prijmeni + " byl přidán");
    }

    /**
     * Vyhledá a vrátí pojištěnce vybraného podle jména a příjmení nebo null
     */
    public Pojistenec vratVyhledanehoPojistence() {
        System.out.println("   Zadejte jméno a poté zadejte příjmení konkrétního pojištěnce");
        System.out.println("   Jméno: ");
        String jmeno = sc.nextLine().toLowerCase().trim();
        System.out.println("   Příjmení: ");
        String prijmeni = sc.nextLine().toLowerCase().trim();
        System.out.println();
        Pojistenec pojistenec = databaze.vyberPojistenceDleJmena(jmeno, prijmeni);
        return pojistenec;
    }

    /**
     * Na základě parametru vymazatPojistence buď vypíše vyhledaného pojištěnce nebo volá metodu vymazPojistence()
     * @param vymazatPojistence
     */
    public void vypisKonkretnihoPojistence(boolean vymazatPojistence) {
        Pojistenec vyhledanyPojistenec = vratVyhledanehoPojistence();
        if (vyhledanyPojistenec != null) {
            if (!vymazatPojistence) {
                System.out.println("   Vyhledaný záznam: ");
                System.out.println(vyhledanyPojistenec);
            } else {
                vymazPojistence(vyhledanyPojistenec);
            }

        } else {
            System.out.println("   Zvolený pojištěnec se v evidenci nenachází");
        }
    }

    /**
     * Vymaže pojištěnce zvoleného podle jména
     * @param pojistenec Vyhledaný pojištěnec, který se nerovná null
     */
    private void vymazPojistence(Pojistenec pojistenec) {
        String volba = "";
        Pojistenec pojistenecKeSmazani = pojistenec;

        System.out.println("   Záznam, který bude vymazán: ");
        System.out.println(pojistenecKeSmazani);
        System.out.println();

        while ((!volba.equals("Y"))&&(!volba.equals("N"))&&(!volba.equals("YES"))&&(!volba.equals("NO"))) { //Vyzva k potvrzení vymazání zvoleného pojištěnce a cyklus ke kontrole správné odpovědi
            System.out.println("   Opravdu si přejete pojištěnce vymazat? Y/N");
            volba = sc.nextLine().trim().toUpperCase();
            if ((!volba.equals("Y"))&&(!volba.equals("N"))&&(!volba.equals("YES"))&&(!volba.equals("NO"))) {
                System.out.println();
                System.out.println(sestavVystraznouHlasku("neplatná volba"));
            }
        }

        if ((volba.equals("Y"))||(volba.equals("YES"))) {
            System.out.printf("   Pojištěnec %s %s byl vymazán %n", pojistenecKeSmazani.getJmeno(), pojistenecKeSmazani.getPrijmeni());
            databaze.vymazPojistence(pojistenecKeSmazani);
        } else {
            System.out.println("   Pojištěnec nebyl vymazán");
        }
    }

    /**
     * Vypíše všechny pojištěnce
     */
    public void vypisVsechnyPojistence() {
        ArrayList<Pojistenec> seznamPojistencu = databaze.predejVsechnyPojistence();
        System.out.println("   Výpis seznamu všech pojištěnců: ");
        for (Pojistenec pojistenec : seznamPojistencu) {
            System.out.println(pojistenec);
        }
        if (seznamPojistencu.isEmpty()) {
            System.out.println("   Evidence neobsahuje žádné pojištěnce");
        }
    }

    /**
     * Vypíše úvodní obrazovku
     */
    public void vypisUvodniObrazovku() {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("   Evidence pojištěnců");
        System.out.println("=========================================================");
        System.out.println();
        System.out.println("   Zvolte požadovanou operaci:");
        System.out.println("   1 - Přidat nového pojištěnce");
        System.out.println("   2 - Vypsat všechny pojištěnce");
        System.out.println("   3 - Vyhledat konkrétního pojištěnce");
        System.out.println("   4 - Vymazat konkrétního pojištěnce");
        System.out.println("   5 - Ukončit aplikaci");
        System.out.println();
    }

    /**
     * Vizuálně oddělí jakoukoliv operaci od úvodní obrazovky a vyzve uživatele k pokračování pomocí enteru
     */
    public void vyzadejEnter() {
        System.out.println("   Pokračujte stisknutím klávesy enter");
        System.out.println("---------------------------------------------------------");
        sc.nextLine();
    }
}
