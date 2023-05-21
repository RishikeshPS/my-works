package com.example.sara;

public class users {
    private String username;
    private String password;
    private String id;
    private String dep;

    public users(){
        //this constructor is required
    }

    public users(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getid() {
        return id;
    }
}
