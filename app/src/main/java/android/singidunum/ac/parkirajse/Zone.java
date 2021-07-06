package android.singidunum.ac.parkirajse;

import com.google.gson.annotations.SerializedName;

public class Zone {

    @SerializedName("zone_id")
    public int zone_id;
    @SerializedName("zone_name")
    public String zone_name;
    @SerializedName("zone_number")
    public String zone_number;

    public Zone(int zone_id, String zone_name, String zone_number) {
        this.zone_id = zone_id;
        this.zone_name = zone_name;
        this.zone_number = zone_number;
    }
}
