package com.joaodev.clonnotesgoogle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

//import androidx.appcompat.widget.PopupMenu;

import android.widget.SearchView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaodev.clonnotesgoogle.Adapters.NoteListAdapter;
import com.joaodev.clonnotesgoogle.Models.ApiResponse;
import com.joaodev.clonnotesgoogle.Models.ApiResponseDelete;
import com.joaodev.clonnotesgoogle.Models.Notes;
import com.joaodev.clonnotesgoogle.Models.NotesDiffCallback;
import com.joaodev.clonnotesgoogle.RequestInterface.NotasInterface;
import com.joaodev.clonnotesgoogle.Resources.Routes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    List<Notes> initialNotes = new ArrayList<>();
    FloatingActionButton fad_add;
    SearchView searhView_home;
    Notes selectedNote;

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
        fad_add = findViewById(R.id.fad_add);
        searhView_home = findViewById(R.id.searhView_home);


        updateRecycler(initialNotes);


        agregarNotasDeEjemplo();


        fad_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });


        searhView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });
    }

    private void filter(String s) {

        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : initialNotes) {
            if (singleNote.getTitle().toLowerCase().contains(s.toLowerCase())
                    || singleNote.getContent().toLowerCase().contains(s.toLowerCase())
            ) {
                filteredList.add(singleNote);

            }

        }

        noteListAdapter.filterList(filteredList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {

            Notes newNote = (Notes) data.getSerializableExtra("note");


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Routes.UrlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            NotasInterface notasInterface = retrofit.create(NotasInterface.class);
            Call<ApiResponse> call = notasInterface.insertNota(newNote);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        List<Notes> notas = response.body().getNotas();
//                        Log.d("AMAME", "onActivityResult: "
//                                + notas
//                        );
                        initialNotes.clear();
                        initialNotes.addAll(notas);
                        noteListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                    Log.d("note", "onActivityResult: "
                            + throwable.getMessage()
                    );
                }
            });


        } else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            Notes oldNote = (Notes) data.getSerializableExtra("note");
            Log.d("AMAME", "onActivityResult: "
                    + oldNote.getId()
                    + " "

                    + oldNote.getContent()
            );
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Routes.UrlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            NotasInterface notasInterface = retrofit.create(NotasInterface.class);
            Call<ApiResponse> call = notasInterface.updateNota(oldNote.getId(), oldNote);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        List<Notes> notas = response.body().getNotas();

                        if (notas != null) {
                            initialNotes.clear();
                            initialNotes.addAll(notas);
                            noteListAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("API Response", "La lista de notas es nula.");
                        }
                    } else {
                        // Manejar errores HTTP o respuestas inesperadas
                        Log.e("API Error", "Error en la respuesta: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                    Log.e("Network Error", "Error al realizar la llamada: " + throwable.getMessage());
                    throwable.printStackTrace(); // Imprime la traza completa del error
                }
            });
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        noteListAdapter = new NoteListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(noteListAdapter);
    }

    private void agregarNotasDeEjemplo() {
        if (!initialNotes.isEmpty()) {
            return;  // No cargar si ya hay notas en la lista
        }

        Log.d("MainActivity", "Fetching example notes from API...");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Routes.UrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotasInterface notasInterface = retrofit.create(NotasInterface.class);
        Call<ApiResponse> notasCall = notasInterface.getNotas();

        notasCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notes> notas = response.body().getNotas();
                    Log.d("MainActivity", "Notas recibidas: " + notas.size());
                    if (notas != null && !notas.isEmpty()) {
                        initialNotes.addAll(notas);
                        noteListAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("MainActivity", "onResponse: Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.d("MainActivity", "onFailure: " + throwable.getMessage());
            }
        });
    }

    private final NoteClickListener notesClickListener = new NoteClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("oldNotes", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLoadClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);


        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);


        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.deleteNN) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Routes.UrlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            NotasInterface notasInterface = retrofit.create(NotasInterface.class);
            Log.d("NotaTag", "onMenuItemClick: "+ selectedNote.getId());
            Call<ApiResponseDelete> call = notasInterface.deleteNote(selectedNote.getId());
            call.enqueue(new Callback<ApiResponseDelete>() {
                @Override
                public void onResponse(Call<ApiResponseDelete> call, Response<ApiResponseDelete> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            // Manejar éxito de la respuesta
                            String mensaje = response.body().getMensaje();
                            Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();
                            cargarNotasActualizadas();
                        } else {
                            // Manejar error en la respuesta
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                            Log.e("API Error", "Error en la respuesta: " + errorBody);
                            Toast.makeText(MainActivity.this, "Error en la eliminación: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Manejar cualquier excepción durante el manejo de la respuesta
                        Log.e("Callback Error", "Excepción al manejar la respuesta: ", e);
                        Toast.makeText(MainActivity.this, "Excepción al manejar la respuesta", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseDelete> call, Throwable throwable) {
                    Log.d("MainActivity", "onFailure: " + throwable.getMessage());
                }
            });


            return true;
        } else {
            return false; // Devuelve false si no se manejó el clic
        }

//        switch (item.getItemId()) {
//            case R.id.deleteNN:
//                Log.d("Selected", "onMenuItemClick: " + selectedNote);
//                // Agrega tu lógica para eliminar la nota aquí
//                return true; // Devuelve true si el manejo del clic se completó con éxito
//            case R.id.pp:
//                Log.d("Selected", "No hace nada");
//                // Lógica para el otro caso, si aplica
//                return true;
//            default:
//                return false; // Devuelve false si no se manejó el clic
//        }


    }


    private void cargarNotasActualizadas() {
        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Routes.UrlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotasInterface notasInterface = retrofit.create(NotasInterface.class);
        Call<ApiResponse> notasCall = notasInterface.getNotas();

        notasCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notes> notas = response.body().getNotas();
                    if (notas != null) {
                        initialNotes.clear();
                        initialNotes.addAll(notas);
                        noteListAdapter.notifyDataSetChanged(); // Notificar al adaptador de los cambios
                    } else {
                        Log.e("API Response", "La lista de notas es nula.");
                    }
                } else {
                    Log.d("MainActivity", "onResponse: Error en la respuesta al cargar notas actualizadas");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                Log.e("MainActivity", "Error al cargar notas actualizadas: " + throwable.getMessage());
            }
        });
    }

}
