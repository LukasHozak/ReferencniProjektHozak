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
    private DatabazePojistencu databazeVsechPojistencu;

    /**
     * Instance scanneru
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Vytvoří instanci uživatelského rozhranní evidence pojištěnců
     */
    public UzivatelskeRozhraniEvidence() {
        databazeVsechPojistencu = new DatabazePojistencu();
    }

    /**
     * Vrátí databázi pojištěnců
     * @return Kompletní databáze všech pojištěnců
     */
    public DatabazePojistencu getDatabazePojistencu() {
        return this.databazeVsechPojistencu;
    }

    /**
     * Sestaví celou výstražnou hlášku k hodnotě zadané uživatelem, která neuspěla při validaci vstupu
     * @param hlaska Část výstražné hlášky vztahující se ke konkrétnímu validovanému vstupu
     * @return Celá sestavená vrácená výstražná hláška
     */
    public String sestavVystraznouHlasku(String hlaska) {
        return String.format("   Chybně zadáno - %s. Opakujte Znovu.", hlaska);
    }

    /**
     * Obsahuje cyklus, který ošetřuje, aby byla vstupní hodnota typu int
     * @return Parsovaná vstupní hodnota typu int
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
     * @return Vstupní hodnota typu String, která byla úspěšně zvalidována
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
     * Přidá nového pojištěnce. Metoda ošetřuje správnost vstupních záznamů buď přímo, nebo pomocí metod parsujCislo() a zvalidujZadanyText()
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

        //Přidání pojištěnce do databáze
        databazeVsechPojistencu.pridejPojistence(jmeno, prijmeni, vek, telCislo);
        System.out.println("   Nový pojištěnec " + jmeno + " " + prijmeni + " byl přidán");
    }

    /**
     * Vyhledá seznam pojištěnců nalezených podle jména a příjmení a na základě volby uživatele vrátí pouze jednoho pojištěnce vybraného nebo null
     * @return pojistenec jeden konkrétní pojištěnec nebo null
     */
    public Pojistenec vratVyhledanehoPojistence() {
        Pojistenec pojistenec = null;
        System.out.println("   Zadejte jméno a poté zadejte příjmení konkrétního pojištěnce");
        System.out.println("   Jméno: ");
        String jmeno = sc.nextLine().toLowerCase().trim();
        System.out.println("   Příjmení: ");
        String prijmeni = sc.nextLine().toLowerCase().trim();
        System.out.println();
        DatabazePojistencu nalezeniPojistenci = new DatabazePojistencu(databazeVsechPojistencu.vyberPojistenceDleJmena(jmeno, prijmeni));
        if (nalezeniPojistenci.predejVsechnyPojistence().size() > 1) {
            pojistenec = zvolZeShodnychPojistencu(nalezeniPojistenci);
        } else if (nalezeniPojistenci.predejVsechnyPojistence().size() == 1) {
            pojistenec = nalezeniPojistenci.predejVsechnyPojistence().getFirst();
        }

        return pojistenec;
    }

    /**
     * Vyzve k výběru z pojištěnců se stejným jménem a vrátí jednoho zvoleného podle ID
     * @param databazeShodnychPojistencu Předána databáze všech vybraných pojištěnců shodných dle jména a příjmení
     * @return zvolenyPojistenec Jeden zvolený pojištěnec
     */
    public Pojistenec zvolZeShodnychPojistencu(DatabazePojistencu databazeShodnychPojistencu) {
        int[] mnozinaIdShodnychPojistencu = databazeShodnychPojistencu.vratIDPojistencuVDatabazi();
        int vybraneId = -1;
        boolean volbaJeVPoradku = false;
        Pojistenec zvolenyPojistenec = null;
        System.out.println("   Databáze obsahuje více pojištěnců se shodným jménem.");
        vypisVsechnyPojistence(databazeShodnychPojistencu, "shodných");
        System.out.println();

        while (!volbaJeVPoradku) { //Výzva k zadání ID zvoleného pojištěnce a cyklus ke kontrole správné odpovědi dle mnoziny
            System.out.println("   Zadejte ID zvoleného pojištěnce: ");
            vybraneId = Integer.parseInt(sc.nextLine().trim());
            for (int id : mnozinaIdShodnychPojistencu) {
                if (vybraneId == id) {
                    volbaJeVPoradku = true;
                    zvolenyPojistenec = databazeShodnychPojistencu.vyberPojistenceDleID(vybraneId);
                }
            }
            if (!volbaJeVPoradku) {
                System.out.println();
                System.out.println(sestavVystraznouHlasku("neplatné ID"));
            }
        }
        return zvolenyPojistenec;
    }

    /**
     * Na základě parametru vymazatPojistence buď vypíše vyhledaného pojištěnce nebo volá metodu vymazPojistence()
     * @param vymazatPojistence obsahuje informaci, zda je metoda součástí operace vymazPojistence(), nebo pouze vypisuje
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
            databazeVsechPojistencu.vymazPojistence(pojistenecKeSmazani);
        } else {
            System.out.println("   Pojištěnec nebyl vymazán");
        }
    }

    /**
     * Vypíše všechny pojištěnce z vybrané databáze
     * @param vybranaDatabaze Předaná databáze
     * @param popisDatabaze Text popisující předanou databázi
     */
    public void vypisVsechnyPojistence(DatabazePojistencu vybranaDatabaze, String popisDatabaze) {
        ArrayList<Pojistenec> seznamPojistencu = vybranaDatabaze.predejVsechnyPojistence();
        System.out.println("   Výpis " + popisDatabaze + " pojištěnců: ");
        for (Pojistenec pojistenec : seznamPojistencu) {
            System.out.println(pojistenec);
        }
        if (seznamPojistencu.isEmpty()) {
            System.out.println("   Evidence neobsahuje žádné pojištěnce");
        }
    }

    /**
     * Vypíše úvodní obrazovku a vyzve uživatele k volbě operace
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

    /**
     * Přijme od uživatele vstupní data pro výběr požadované operace, na základě vstupu provede požadovanou operaci:
     * 1. operace - vyžádá od uživatele atributy pro pojištěnce, kterého vytvoří a přidá do databáze všech pojištěnců
     * 2. operace - vypíše všechny pojištěnce z databáze všech pojištěnců
     * 3. operace - vypíše pojištěnce dle zadaného jména a příjmení. V případě více instancí se stejným jménem i příjmením
     *              vznikne databáze pojištěnců se shodným jménem, kterou vypíše a vyzve uživatele ke konkrétnímu výběru
     *              pomocí zadání ID
     * 4. operace - vypíše pojištěnce dle zadaného jména a příjmení, případně upřesněného dle ID jako v operaci 3. Poté
     *              vyzve uživatele k potvrzení vymazání záznamu a v kladném případě vymaže záznam
     * 5. operace - ukončí hlavní cyklus programu
     * 6. operace - vypíše chybovou hlášku
     */
    public void vyberOperaci() {
        String volba = "";

        while (!volba.equals("5")) { // Hlavní cyklus programu
            vypisUvodniObrazovku();
            volba = sc.nextLine();
            System.out.println();
            switch (volba) {
                case "1":
                    pridejPojistence();
                    break;
                case "2":
                    vypisVsechnyPojistence(getDatabazePojistencu(), "všech");
                    break;
                case "3":
                    vypisKonkretnihoPojistence(false);
                    break;
                case "4":
                    vypisKonkretnihoPojistence(true);
                    break;
                case "5":
                    System.out.println("   Aplikace byla ukončena");
                    System.out.println("---------------------------------------------------------");
                    break;
                default:
                    System.out.println(sestavVystraznouHlasku("neplatná volba"));
                    break;
            }
            if (!volba.equals("5")) {
                vyzadejEnter();
            }
        }
    }
}
