package com.project.sam.bitservices;

/**
 * Created by samuel.coianiz1 on 19/05/2017.
 */
public class DatabaseUser {

    //region Field Members
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private String suburb;
    private String postcode;
    private String state;
    private int roleId;
    private String username;
    private String password;
    private int status;
//endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public int getStatus() {
        return status;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getState() {
        return state;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getUsername() {
        return username;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//endregion

    //region Constructors
    public DatabaseUser() {

    }

    public DatabaseUser(int id, String firstName, String lastName, String phone, String email, String addressLine1, String addressLine2, String suburb, String postcode, String state, int roleId, String username, String password, int status) {
        this.id = id; // 0 INT
        this.firstName = firstName; // 1 STRING
        this.lastName = lastName;// 2 STRING
        this.phone = phone;// 3 STRING
        this.email = email;// 4 STRING
        this.addressLine1 = addressLine1;// 5 STRING
        this.addressLine2 = addressLine2;// 6 STRING
        this.suburb = suburb;// 7 STRING
        this.postcode = postcode;// 8 STRING
        this.state = state;// 9 STRING
        this.roleId = roleId;// 10 INT
        this.username = username;// 11 STRING
        this.password = password;// 12 STRING
        this.status = status;// 13 INT
    }

    public DatabaseUser(int id, String firstName, String lastName, String phone, String email, String addressLine1, String addressLine2, String suburb, String postcode, String state, int roleId, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.suburb = suburb;
        this.postcode = postcode;
        this.state = state;
        this.roleId = roleId;
        this.username = username;
        this.status = 1;
    }


    public DatabaseUser(String firstName, String lastName, String phone, String email, String addressLine1, String addressLine2, String suburb, String postcode, String state, int roleId, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.suburb = suburb;
        this.postcode = postcode;
        this.state = state;
        this.roleId = roleId;
        this.username = username;
        this.status = 1;
    }

    public DatabaseUser(String firstName, String lastName, String phone, String email, String addressLine1, String addressLine2, String suburb, String postcode, String state, int roleId, String username, int status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.suburb = suburb;
        this.postcode = postcode;
        this.state = state;
        this.roleId = roleId;
        this.username = username;
        this.status = status;
    }

    public DatabaseUser(String user, String pass, int type) {
        username = user;
        password = pass;
        roleId = type;
    }

    public DatabaseUser(int id, int roleId) {
        this.id = id;
        this.roleId = roleId;
    }
    //endregion


}
