package com.example.QuickBuy.models.owner;

public class OwnerDto {
    private String Id;
    private String email;
    private String firstName;
    private String lastName;
    private Sexe sexe;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public OwnerDto(String Id, String email, String firstName, String lastName, Sexe sexe) {
        this.Id = Id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sexe = sexe;
    }


}
