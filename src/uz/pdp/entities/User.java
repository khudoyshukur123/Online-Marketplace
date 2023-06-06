package uz.pdp.entities;

import java.io.Serializable;

public class User implements Serializable {

    private String firstName;
    private String phoneNumber;
    private String password;
    private int id;

    public User(String firstName, String phoneNumber, String password) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        id++;
    }

    public User() {
        id++;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User user){
            return user.phoneNumber.equals(this.phoneNumber) && user.password.equals(this.password);
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
