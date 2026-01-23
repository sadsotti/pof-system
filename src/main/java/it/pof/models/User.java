package it.pof.models;

public class User {
    private int id;
    private String name;
    private String lastName;
    private String birthDate;
    private String address;
    private String documentId;

    public User(int id, String name, String lastName, String birthDate, String address, String documentId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.documentId = documentId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getDocumentId() { return documentId; }
}
