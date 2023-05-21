package com.example.sara;

public class studentuser {
    private String username;
    private String password;
    private String rollno;
    private String dep;
    private String year;

    public studentuser(String username, String password, String rollno,String dep,String year) {
        this.username = username;
        this.password = password;
        this.rollno = rollno;
        this.dep = dep;
        this.year = year;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getdep() {
        return dep;
    }
    public String getrollno() {
        return rollno;
    }
    public String getyear() {
        return year;
    }

}
