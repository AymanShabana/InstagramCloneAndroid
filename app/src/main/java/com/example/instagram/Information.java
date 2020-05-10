package com.example.instagram;

public class Information {
    private String Email;
    private String Name;

    public Information(){

    }
    public Information(String Email,String Name){
        this.Email=Email;
        this.Name=Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
