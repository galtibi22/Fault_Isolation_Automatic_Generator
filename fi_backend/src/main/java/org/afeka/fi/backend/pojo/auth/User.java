package org.afeka.fi.backend.pojo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.validator.Email;
import org.afeka.fi.backend.validator.Password;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@Entity
public class User {
    public User(){

    }



    public User(String userName, String password){
        this.userName=userName;
        this.password=password;
    }
    public User(String userName,String password,Role role,String firstName,String lastName){
        this.userName=userName;
        this.password=password;
        this.role=role;
        this.firstName=firstName;
        this.lastName=lastName;
    }

   // @NotNull
    @Expose
    @Password
    @Size(min = 8,max=15,message = "Password must be 8 - 15 characters long")
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @SerializedName("password")
    public String password;
    //@NotNull
    @Expose
    public Role role;
    @Expose
    //@NotNull
    @Size(min = 2,max=48,message = "firstName must be 2 - 48 characters long")
    public String firstName;
    @Expose
    //@NotNull
    @Size(min = 2,max=48,message = "lastName must be 2 - 48 characters long")
    public String lastName;

    @NotNull
    @Id
    @Expose
    public String userName;

    @Transient
    @JsonIgnore
   // @JsonProperty( value = "tres", access = JsonProperty.Access.READ_WRITE)
    @Null
    public List<TRE> tres;

    @Override
    public String toString() {
        return "User{" + "password='" + password + '\'' + ", role=" + role + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", userName='" + userName + '\'' + ", tres=" + tres + '}';
    }
/*    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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


    public List<TRE> getTres() {
        return tres;
    }

    public void setTres(List<TRE> tres) {
        this.tres = tres;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }*/

}
