package cz.itnetwork;

import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
        UzivatelskeRozhraniEvidence rozhrani = new UzivatelskeRozhraniEvidence();
        Scanner sc = new Scanner(System.in);
        String volba = "";

        while (!volba.equals("5")) {
            rozhrani.vypisUvodniObrazovku();
            volba = sc.nextLine();
            System.out.println();
            switch (volba) {
                case "1":
                    rozhrani.pridejPojistence();
                    break;
                case "2":
                    rozhrani.vypisVsechnyPojistence();
                    break;
                case "3":
                    rozhrani.vypisKonkretnihoPojistence(false);
                    break;
                case "4":
                    rozhrani.vypisKonkretnihoPojistence(true);
                    break;
                case "5":
                    System.out.println("   Aplikace byla ukončena");
                    System.out.println("---------------------------------------------------------");
                    break;
                default:
                    System.out.println("   Neplatná volba");
                    break;
            }
            if (!volba.equals("5")) {
                rozhrani.vyzadejEnter();
            }
        }
    }
}
