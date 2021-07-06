package android.singidunum.ac.parkirajse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    @POST("/users/add")
    Call<User> createUser(@Body User user);

    @POST("/users/isExist")
    Call<User> isLogIn(@Body User user);

    @GET("/cities")
    Call<List<City>> getCities();

    @POST("/zones/city")
    Call<List<Zone>> getZoneByCities(@Body City city);

    @POST("/vehicles/findByUser")
    Call<List<Vehicle>> getVehicleByUser(@Body User user);

    @PUT("/users/update")
    Call<User> updateUser(@Body User user);

    @DELETE("users/deleteById/{id}")
    Call<String> deleteUserById(@Path("id") int id);

    @POST("/vehicles/Add")
    Call<Vehicle> createVehicle(@Body Vehicle vehicle);

}
