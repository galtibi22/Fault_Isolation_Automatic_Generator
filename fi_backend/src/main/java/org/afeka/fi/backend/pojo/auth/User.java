package org.afeka.fi.backend.pojo.auth;

import org.afeka.fi.backend.pojo.commonstructure.ND;

import java.util.List;

public class User {
    private String userName;
    private String password;
    private Person person;
    private Title title;
    private List<ND> nds;

    public List<ND> getNds() {
        return nds;
    }

    public void setNds(List<ND> nds) {
        this.nds = nds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
