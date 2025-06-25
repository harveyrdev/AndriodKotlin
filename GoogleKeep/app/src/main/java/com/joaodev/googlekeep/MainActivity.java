package com.joaodev.googlekeep;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaodev.googlekeep.Adapters.NoteListAdapter;
import com.joaodev.googlekeep.Models.Note;
import com.joaodev.googlekeep.Models.ResponseNotes;
import com.joaodev.googlekeep.RequestInterface.NotesInterface;
import com.joaodev.googlekeep.Resources.Routes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    List<Note> initialNotes = new ArrayList<>();
    FloatingActionButton fad_add;
    SearchView searhView_home;
    Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.recycler_home);
//        fad_add = findViewById(R.id.fad_add);
        searhView_home = findViewById(R.id.searchView_home);


    }

    private void loadNotes() {
        if (!initialNotes.isEmpty()) {
            return;
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Routes.UrlBase).addConverterFactory(GsonConverterFactory.create()).build();
        NotesInterface notesInterface = retrofit.create(NotesInterface.class);
        Call<ResponseNotes> call = notesInterface.getNotas();
        call.enqueue(new Callback<ResponseNotes>() {
            @Override
            public void onResponse(Call<ResponseNotes> call, Response<ResponseNotes> response) {
                if (response.isSuccessful() && response.body() !=null){
                    List<Note> notes = response.body().getNotas();
                    initialNotes.addAll(notes);
                    noteListAdapter.notifyDataSetChanged();
                }else {
                    Log.d("MainActivity", "onResponse: Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<ResponseNotes> call, Throwable throwable) {
                Log.d("MainActivity", "onFailure: " + throwable.getMessage());
            }
        });
    }


}