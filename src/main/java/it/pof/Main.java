package it.pof;

import it.pof.service.GestionaleService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionaleService service = new GestionaleService();
        service.startup();
        Scanner sc = new Scanner(System.in);
        String scelta = "";

        while (!scelta.equals("0")) {
            System.out.println("\n--- POF: Planty of Food ---");
            System.out.println("1 = Visualizzare tutti i prodotti");
            System.out.println("2 = Comprare un prodotto");
            System.out.println("3 = Restituire un prodotto");
            System.out.println("4 = Aggiungere nuovo utente");
            System.out.println("5 = Esportare prodotti disponibili");
            System.out.println("0 = Uscire");
            System.out.print("Seleziona operazione: ");
            scelta = sc.nextLine();

            try {
                switch (scelta) {
                    case "1" -> service.visualizzaProdotti();
                    case "2" -> {
                        System.out.print("ID Prodotto: "); int ip = Integer.parseInt(sc.nextLine());
                        System.out.print("ID Utente: "); int iu = Integer.parseInt(sc.nextLine());
                        service.compraProdotto(ip, iu);
                    }
                    case "3" -> {
                        System.out.print("ID Vendita: "); int iv = Integer.parseInt(sc.nextLine());
                        service.restituisciProdotto(iv);
                    }
                    case "4" -> {
                        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nome: "); String n = sc.nextLine();
                        System.out.print("Cognome: "); String c = sc.nextLine();
                        System.out.print("Data Nascita (gg/mm/aaaa): "); String d = sc.nextLine();
                        System.out.print("Indirizzo: "); String ind = sc.nextLine();
                        System.out.print("Documento ID: "); String doc = sc.nextLine();
                        service.aggiungiUtente(id, n, c, d, ind, doc);
                    }
                    case "5" -> service.esportaDisponibili();
                    case "0" -> System.out.println("Arrivederci!");
                    default -> System.out.println("Comando non riconosciuto.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserisci un numero valido.");
            }
        }
        sc.close();
    }
}