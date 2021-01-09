package com.example.rentalservice;

import com.example.rentalservice.models.Institution;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {
        @GET("/institution")
        Call<List<Institution>> getInstitution();
        @GET("/institution/name/{name}")
        Call<List<Institution>> getInstitutionByName(@Path("name") String name);
        @POST("/institution")
        Call<Institution> createInstitution(@Body Institution institution);
        @PUT("/institution/{id}")
        Call<Institution> putInstitution(@Path("id") String id, @Body Institution institution);
        @DELETE("/institution/{id}")
        Call<Void> deleteInstitution(@Path("id") String id);


}
