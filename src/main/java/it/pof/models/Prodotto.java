package it.pof.models;

public class Prodotto {
    private int id;
    private String dataInserimento;
    private String nome;
    private String marca;
    private String prezzo;
    private boolean disponibile;

    public Prodotto(int id, String dataInserimento, String nome, String marca, String prezzo, boolean disponibile) {
        this.id = id;
        this.dataInserimento = dataInserimento;
        this.nome = nome;
        this.marca = marca;
        this.prezzo = prezzo;
        this.disponibile = disponibile;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataInserimento() { return dataInserimento; }
    public String getMarca() { return marca; }
    public String getPrezzo() { return prezzo; }
    public boolean isDisponibile() { return disponibile; }
    public void setDisponibile(boolean disponibile) { this.disponibile = disponibile; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | %s | %s | Prezzo: %s | Disponibile: %s", 
                id, dataInserimento, nome, marca, prezzo, disponibile ? "SI" : "NO");
    }

    public String toCsvRow() {
        return id + ";" + dataInserimento + ";" + nome + ";" + marca + ";" + prezzo;
    }
}