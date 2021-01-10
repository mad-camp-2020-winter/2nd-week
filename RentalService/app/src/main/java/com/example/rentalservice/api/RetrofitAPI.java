package com.example.rentalservice.api;

import com.example.rentalservice.models.Institution;
import com.example.rentalservice.models.Item;
import com.example.rentalservice.models.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {
        //INSTITUTION
        @GET("/institution")
        Call<List<Institution>> getInstitution();
        @GET("/institution/name/{id}")
        Call<Institution> getInstitutionById(@Path("id") String id);
        @POST("/institution")
        Call<Institution> createInstitution(@Body Institution institution);
        @PUT("/institution/{id}")
        Call<Institution> putInstitution(@Path("id") String id, @Body Institution institution);
        @DELETE("/institution/{id}")
        Call<Void> deleteInstitution(@Path("id") String id);

        //ITEM
        @GET("/item")
        Call<List<Item>> getItem();
        @GET("/item/institution_id/{institution_id}")
        Call<List<Item>> getItemByInstitution(@Path("institution_id") String institution_id);
        @POST("/item")
        Call<Item> createItem(@Body Item item);
        @PUT("/item/{id}")
        Call<Item> putItem(@Path("id") String id, @Body Item item);
        @DELETE("/item/{id}")
        Call<Void> deleteItem(@Path("id") String id);

        //LOGIN
        @GET("/login")
        Call<Login> getLogin();
        @GET("/login/id/{id}")
        Call<List<Login>> getLoginById(@Path("id") String id);
        @POST("/login")
        Call<Login> createLogin(@Body Login login);
        @PUT("/login/{id}")
        Call<Login> putLogin(@Path("id") String id, @Body Login login);


}
