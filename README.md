# 🥗 Planty of Food (POF) - Software Gestionale E-commerce

**Planty of Food** è un'applicazione Java progettata per gestire i flussi operativi di un e-commerce alimentare sostenibile. Il sistema permette la gestione centralizzata di prodotti biologici, l'anagrafica dei clienti e il tracciamento delle vendite/restituzioni, riflettendo l'impegno dell'azienda verso una filiera trasparente.

## 🌱 Vision & Mission
* **Vision:** Rendere l'alimentazione plant-based accessibile, etica e biologica.
* **Mission:** Supportare i produttori italiani e ridurre l'impatto ambientale attraverso packaging compostabili e una gestione digitale efficiente.

---

## 📂 Struttura del Progetto (Tree)
Il progetto segue le convenzioni Maven e una chiara separazione dei package:

```text
.
├── pom.xml                     # Configurazione Maven e dipendenze
├── README.md                   # Questo file
├── prodotti.csv                # Database prodotti
├── utenti.csv                  # Anagrafica utenti
├── vendite.csv                 # Registro transazioni
└── src
    └── main
        └── java
            └── it
                └── pof
                    ├── Main.java              # Interfaccia Utente (CLI)
                    ├── models                 # Package: Entità del dominio
                    │   ├── Utente.java        
                    │   ├── Prodotto.java      
                    │   └── Vendita.java       
                    └── service                # Package: Logica applicativa
                        └── GestionaleService.java # Business Logic e I/O
```

📊 Organizzazione dei File CSV (Database)
I dati sono persistiti in formato CSV utilizzando il punto e virgola (`;`) come delimitatore.

### 1. `prodotti.csv` (Catalogo alimenti)
* **Struttura:** `ID;Nome;Data di inserimento;Prezzo;Marca;Disponibile`
* **Esempio:** `1;Latte di mandorla;22/07/2021;2,15 €;Bjorg;SI`

### 2. `utenti.csv` (Anagrafica clienti POF)
* **Struttura:** `ID;Nome;Cognome;Data di nascita;Indirizzo;Documento ID`
* **Esempio:** `1;Mario;Rossi;23/02/1990;Via Roma 15;AS348945`

### 3. `vendite.csv` (Registro transazioni)
* **Struttura:** `ID;ID Prodotto;ID Utente`
* **Esempio:** `1;3;1`

## 🏗️ Architettura e Implementazione Tecnica

### 1. Programmazione ad Oggetti (OOP)
Il software è strutturato per massimizzare l'**Incapsulamento**. Le classi nel package `models` proteggono lo stato degli oggetti tramite campi privati e forniscono l'accesso tramite metodi **Getter** e **Setter**. La logica di visualizzazione (`Main`) è rigorosamente separata dalla logica di business (`GestionaleService`), facilitando la manutenzione del codice.

### 2. Focus Tecnico: Programmazione Funzionale (Java 17)
Il sistema utilizza paradigmi moderni per la manipolazione dei dati:
* **Interfacce Funzionali:** Il metodo `caricaCSV` accetta un `Consumer<String[]>` come parametro, rendendo il motore di parsing generico e riutilizzabile per diversi modelli (Utenti, Prodotti, Vendite).
* **Lambda Expressions:** Utilizzate per mappare velocemente le righe dei file CSV in oggetti Java durante la fase di `startup`, riducendo la verbosità del codice.
* **Stream API:** Implementate nel metodo `esportaDisponibili()` per filtrare i prodotti in stock e generare report dinamici, includendo la data corrente nel nome del file di output.

### 3. Robustezza e Validazione
* **Gestione Eccezioni:** Implementazione di blocchi `try-catch` nel `Main` per prevenire crash da `NumberFormatException` (input utente non validi) e nel `Service` per gestire `IOException` durante l'accesso ai file di persistenza.
* **Sincronizzazione:** Ogni operazione di vendita o restituzione aggiorna dinamicamente lo stato di disponibilità (`SI`/`NO`) degli oggetti e sincronizza immediatamente i file fisici per prevenire la perdita di dati.

## 📥 Clonazione del Repository
Per scaricare il progetto in locale sul tuo PC, apri il terminale ed esegui:

```bash
git clone https://github.com/sadsotti/progetto-pof.git
```

Dopo la clonazione, entra nella cartella del progetto:
```bash
cd progetto-pof
```

## 🛠️ Istruzioni per la Compilazione ed Esecuzione

### Compilazione
Il progetto utilizza **Apache Maven** per la gestione della build. Per compilare il codice sorgente e generare il file JAR eseguibile, lancia il seguente comando dalla root del progetto:

```bash
mvn clean package
```

Al termine del processo, troverai il file generato nel percorso:
`target/progetto-pof-0.0.1.jar`

### Esecuzione
Per avviare correttamente l'applicazione, assicurati che i file di persistenza (`prodotti.csv`, `utenti.csv`, `vendite.csv`) siano presenti nella stessa directory da cui lanci il comando. Esegui quindi:

```bash
java -jar target/progetto-pof-0.0.1.jar
```

**Requisiti di sistema:** È necessario Java 17 o superiore installato, come specificato nella configurazione del compilatore Maven.

## 🔗 Link Utili

- https://www.start2impact.it/  
- https://linkedin.com/in/lorenzo-sottile  

---
