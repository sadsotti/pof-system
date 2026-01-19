package it.pof.service;

import it.pof.models.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestionaleService {
    private List<Utente> utenti = new ArrayList<>();
    private List<Prodotto> prodotti = new ArrayList<>();
    private List<Vendita> vendite = new ArrayList<>();

    public void startup() {
        System.out.println("🌿 Benvenuto in Planty of Food. Inizializzazione in corso...");
        
        caricaCSV("prodotti.csv", v -> prodotti.add(new Prodotto(
            Integer.parseInt(v[0]), v[2], v[1], v[4], v[3], v[5].equalsIgnoreCase("SI")
        )));

        caricaCSV("utenti.csv", v -> utenti.add(new Utente(
            Integer.parseInt(v[0]), v[1], v[2], v[3], v[4], v[5]
        )));

        caricaCSV("vendite.csv", v -> vendite.add(new Vendita(
            Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2])
        )));
        
        System.out.println("✅ Database sincronizzato. Pronto per l'uso.");
    }

    private void caricaCSV(String path, java.util.function.Consumer<String[]> parser) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {

                if (line.isBlank() || line.trim().startsWith(";;")) continue;
                
                String[] valori = line.split(";", -1);

                for (int i = 0; i < valori.length; i++) valori[i] = valori[i].trim();
                
                if (!valori[0].isEmpty()) parser.accept(valori);
            }
        } catch (IOException e) {
            System.err.println("⚠️ Nota: File " + path + " non trovato o vuoto.");
        }
    }

    public void visualizzaProdotti() {
        System.out.println("\n--- 🛒 DISPONIBILITÀ MAGAZZINO ---");
        if (prodotti.isEmpty()) System.out.println("Nessun prodotto a catalogo.");
        else prodotti.forEach(System.out::println);
    }

    public void compraProdotto(int idProd, int idUtente) {
        Prodotto p = prodotti.stream()
                .filter(prod -> prod.getId() == idProd && prod.isDisponibile())
                .findFirst()
                .orElse(null);

        boolean utenteEsiste = utenti.stream().anyMatch(u -> u.getId() == idUtente);

        if (p == null) {
            System.out.println("❌ Errore: Prodotto non disponibile o ID inesistente.");
            return;
        }

        if (!utenteEsiste) {
            System.out.println("❌ Errore: Utente ID " + idUtente + " non trovato. Registralo prima!");
            return;
        }

        p.setDisponibile(false);
        int nuovoIdVendita = vendite.stream().mapToInt(Vendita::getId).max().orElse(0) + 1;
        vendite.add(new Vendita(nuovoIdVendita, idProd, idUtente));
        
        sincronizzaTutto();
        System.out.println("🎉 Acquisto registrato! ID Vendita: " + nuovoIdVendita);
    }

    public void restituisciProdotto(int idVendita) {
        Vendita v = vendite.stream().filter(vend -> vend.getId() == idVendita).findFirst().orElse(null);

        if (v != null) {
            prodotti.stream().filter(p -> p.getId() == v.getIdProdotto()).forEach(p -> p.setDisponibile(true));
            vendite.remove(v);
            sincronizzaTutto();
            System.out.println("🔄 Reso completato. Il prodotto è tornato in magazzino.");
        } else {
            System.out.println("❌ ID Vendita non trovato.");
        }
    }

    public void aggiungiUtente(int id, String n, String c, String d, String i, String doc) {
        if (utenti.stream().anyMatch(u -> u.getId() == id)) {
            System.out.println("⚠️ Errore: Questo ID Utente è già occupato.");
            return;
        }

        utenti.add(new Utente(id, n, c, d, i, doc));
        sincronizzaTutto();
        System.out.println("👤 Benvenuto " + n + ", profilo creato con successo.");
    }

    private void sincronizzaTutto() {
        salvaCSV("prodotti.csv", "ID;Nome;Data di inserimento;Prezzo;Marca;Disponibile", 
            prodotti.stream().map(p -> p.getId() + ";" + p.getNome() + ";" + p.getDataInserimento() + ";" + p.getPrezzo() + ";" + p.getMarca() + ";" + (p.isDisponibile() ? "SI" : "NO")).toList());
        
        salvaCSV("utenti.csv", "ID;Nome;Cognome;Data di nascita;Indirizzo;Documento ID", 
            utenti.stream().map(u -> u.getId() + ";" + u.getNome() + ";" + u.getCognome() + ";" + u.getDataNascita() + ";" + u.getIndirizzo() + ";" + u.getDocumentoId()).toList());
        
        salvaCSV("vendite.csv", "ID;ID Prodotto;ID Utente", 
            vendite.stream().map(v -> v.getId() + ";" + v.getIdProdotto() + ";" + v.getIdUtente()).toList());
    }

    private void salvaCSV(String path, String header, List<String> righe) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            pw.println(header);
            righe.forEach(pw::println);
        } catch (IOException e) {
            System.err.println("🚨 Errore di scrittura su " + path);
        }
    }

    public void esportaDisponibili() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String filename = "prodotti_disponibili_" + data + ".csv";
        
        try (PrintWriter pw = new PrintWriter(new File(filename))) {
            pw.println("ID;Data inserimento;Nome;Marca;Prezzo");
            prodotti.stream()
                .filter(Prodotto::isDisponibile)
                .forEach(p -> pw.println(p.getId() + ";" + p.getDataInserimento() + ";" + p.getNome() + ";" + p.getMarca() + ";" + p.getPrezzo()));
            System.out.println("📂 Report generato: " + filename);
        } catch (Exception e) {
            System.err.println("❌ Errore durante l'esportazione.");
        }
    }
}