package it.pof.models;

public class Utente {
    private int id;
    private String nome;
    private String cognome;
    private String dataNascita;
    private String indirizzo;
    private String documentoId;

    public Utente(int id, String nome, String cognome, String dataNascita, String indirizzo, String documentoId) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.indirizzo = indirizzo;
        this.documentoId = documentoId;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getDataNascita() { return dataNascita; }
    public String getIndirizzo() { return indirizzo; }
    public String getDocumentoId() { return documentoId; }
}