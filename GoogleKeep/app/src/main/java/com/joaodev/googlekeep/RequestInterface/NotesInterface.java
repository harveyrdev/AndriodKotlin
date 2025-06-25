package com.joaodev.googlekeep.RequestInterface;

import com.joaodev.googlekeep.Models.Note;
import com.joaodev.googlekeep.Models.ResponseNotes;
import com.joaodev.googlekeep.Resources.Routes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NotesInterface {

    @GET (Routes.apiNotas)
    Call<ResponseNotes> getNotas();

    @POST (Routes.apiNotas)
    Call<ResponseNotes> insertNota(@Body Note note);



}
