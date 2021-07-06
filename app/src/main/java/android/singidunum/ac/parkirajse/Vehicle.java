package android.singidunum.ac.parkirajse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vehicle {

    @SerializedName("vehicle_id")
    public int  vehicle_id;
    @SerializedName("vehicle_name")
    public String vehicle_name;
    @SerializedName("license_plate_number")
    public String license_plate_number;
    @SerializedName("users")
    public List<User> users = null;

    public Vehicle(String vehicle_name, String license_plate_number, List<User> users) {
        this.vehicle_name = vehicle_name;
        this.license_plate_number = license_plate_number;
        this.users = users;
    }
}
