package com.joaodev.clonnotesgoogle.RequestInterface;



import com.joaodev.clonnotesgoogle.Models.ApiResponse;
import com.joaodev.clonnotesgoogle.Models.ApiResponseDelete;
import com.joaodev.clonnotesgoogle.Models.Notes;
import com.joaodev.clonnotesgoogle.Resources.Routes;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface NotasInterface {

    @GET(Routes.apiNotas)
    Call<ApiResponse> getNotas();

    @POST(Routes.apiNotas)
    Call <ApiResponse> insertNota(@Body Notes note);

    @DELETE("api/Notas/{id}")
    Call <ApiResponseDelete> deleteNote(@Path("id") int id);

    @PUT ("api/Notas/{id}")
    Call <ApiResponse> updateNota(@Path("id") int id, @Body Notes note );
}
