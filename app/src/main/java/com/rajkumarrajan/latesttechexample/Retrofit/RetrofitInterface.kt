package com.rajkumarrajan.latesttechexample.Retrofit

import com.rajkumarrajan.latesttechexample.RoomDB.Model.MyModel
import io.reactivex.Single
import retrofit2.http.GET


interface RetrofitInterface {


    @GET("albums")
    fun MyPojoCall(): Single<List<MyModel>>

    /*@GET("https://maps.googleapis.com/maps/api/place/autocomplete/json")
    fun placeSearchGoogleAPI(
        @Query("input") input: String,
        @Query("key") key: String
    ): Single<PlaceSearchPojo>*/

    /*@FormUrlEncoded
    @POST("api/user/validate/social")
    fun socialValidateUser(@HeaderMap header: HashMap<String, String>, @FieldMap params: HashMap<String, String>): Single<ValidatePojo>*/


}