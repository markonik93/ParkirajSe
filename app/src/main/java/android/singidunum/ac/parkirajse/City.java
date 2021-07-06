package android.singidunum.ac.parkirajse;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("city_id")
    public int city_id;

    @SerializedName("city_name")
    public String city_name;

    public City(int city_id,String city_name){
        this.city_id = city_id;
        this.city_name = city_name;
    }
}
