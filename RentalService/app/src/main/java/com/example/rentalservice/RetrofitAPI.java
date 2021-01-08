package com.example.rentalservice;

import com.example.rentalservice.models.Institution;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {
        @GET("/institution")
        Call<List<Institution>> getInstitution();
        @POST("/institution")
        Call<Institution> createInstitution(@Body Institution institution);
        @PUT("/institution/{id}")
        Call<Institution> putInstitution(@Path("id") String id, @Body Institution institution);

}
