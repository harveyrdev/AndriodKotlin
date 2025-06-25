package com.joaodev.clonnotesgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonObject;
import com.joaodev.clonnotesgoogle.Models.ApiResponse;
import com.joaodev.clonnotesgoogle.Models.Notes;
import com.joaodev.clonnotesgoogle.RequestInterface.NotasInterface;
import com.joaodev.clonnotesgoogle.Resources.Routes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_title, editText_content;
    ImageView imageView_save;
    Notes note;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_taker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView_save = findViewById(R.id.imageView_save);
        editText_content = findViewById(R.id.editText_content);
        editText_title = findViewById(R.id.editText_title);
        note = new Notes();
        try {
            note = (Notes) getIntent().getSerializableExtra("oldNotes");
            editText_title.setText(note.getTitle());
            editText_content.setText(note.getContent());
            isOldNote = true;

        } catch (Exception e) {
            e.printStackTrace();

        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString().trim();
                String content = editText_content.getText().toString().trim();

                if (content.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please add some content note!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isOldNote) {
                    note = new Notes();


                }

                note.setTitle(title);
                note.setContent(content);

                Intent intent = new Intent();
                intent.putExtra("note", note);

                setResult(NotesTakerActivity.RESULT_OK, intent);
                finish();


            }
        });


    }
}