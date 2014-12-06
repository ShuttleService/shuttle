package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.jws.soap.SOAPBinding;
import java.util.Collection;
import java.util.Date;

/**
 * Created by zorodzayi on 14/10/04.
 */
@Document
public class User implements UserDetails{
    private ObjectId id;
    @Indexed(unique = true)
    private String username;
    private String password;
    @Indexed
    private String surname;
    @Indexed
    private String firstName;
    @Indexed(unique = true)
    private String email;
    @Indexed
    private String cellNumber;
    private String dateOfBirth;
    private String streetAddress;
    private String suburb;
    private String town;
    private String province;
    private String postalCode;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
    private Collection authorities;


  public void setAccountNonExpired(Boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }


  public void setAccountNonLocked(Boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }
  

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setAuthorities(Collection authorities) {
    this.authorities = authorities;
  }

  public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  @Override
  public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {

        return String.format("{id:%s,username:%s,password%s,firstName:%s,surname:%s,email:%s,cellNumber:%s,dateOfBirth:%s,streetAddress:%s,surburb:%s,town:%s,province:%s,postalCode:%s," +
            " accountNonExpired:%b,accountNonLocked:%b,credentialsNonExpired:%b,enabled:%b,authorities:%s}",id, username, password, firstName,surname, email, cellNumber, dateOfBirth,
          streetAddress, suburb, town, province, postalCode,accountNonExpired,accountNonLocked,credentialsNonExpired,email,authorities);
    }
    @Override
    public boolean equals(Object object){
        if(this == object ){
            return true;
        }
        if(id == null){
            return false;
        }

        if(!(object instanceof  User)){
            return false;
        }

        User user = (User)object;
        return id.equals(user.getId());
    }
}
