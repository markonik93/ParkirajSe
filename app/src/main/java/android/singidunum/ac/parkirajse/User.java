package android.singidunum.ac.parkirajse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("user_id")
    public int userId;
    @SerializedName("name")
    public String name;
    @SerializedName("lastname")
    public String lastName;
    @SerializedName("email")
    public String email;
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("phone")
    public String phone;
    @SerializedName("vehicles")
    public List<Vehicle> vehicles;

    public User(int userId,String name, String lastName, String email, String username, String password, String phone) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    public User(String name, String lastName, String email, String username, String password, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

   /* public User(int userId, String name, String lastName, String email, String username, String password, String phone, List<Vehicle> vehicles) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.vehicles = vehicles;
    }*/

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}
