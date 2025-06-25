package com.joaodev.clonnotesgoogle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.joaodev.clonnotesgoogle.Models.Notes;
import com.joaodev.clonnotesgoogle.Models.NotesDiffCallback;
import com.joaodev.clonnotesgoogle.NoteClickListener;
import com.joaodev.clonnotesgoogle.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Locale;

public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private Context context;
    private List<Notes> list;
    private NoteClickListener listener;

    public NoteListAdapter(Context context, List<Notes> list, NoteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Notes note = list.get(position);
        holder.textView_title.setText(note.getTitle());
        holder.textView_title.setSelected(true);
        holder.textView_content.setText(note.getContent());

        String creationDate = note.getCreationDate();
        String formattedDate = formatCreationDate(creationDate);
        holder.textView_date.setText(formattedDate);
        holder.textView_date.setSelected(true);


        int color_code = getRandomColor();
        holder.notes_container.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), color_code));

        holder.notes_container.setOnClickListener(view -> listener.onClick(list.get(holder.getAdapterPosition())));

        holder.notes_container.setOnLongClickListener(view -> {
            listener.onLoadClick(list.get(holder.getAdapterPosition()), holder.notes_container);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList) {
        list = filteredList;
        notifyDataSetChanged();


    }


    private String formatCreationDate(String dateStr) {
        try {
            // Parsear la fecha del formato original
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
            Date date = originalFormat.parse(dateStr);

            // Formatear la fecha al nuevo formato
            SimpleDateFormat newFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault());
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;  // En caso de error, retornar la fecha sin formatear
        }
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.Chocolate);
        colorCode.add(R.color.CadetBlue);
        colorCode.add(R.color.Purple);
        colorCode.add(R.color.GreenYellow);
        colorCode.add(R.color.MediumBlue);
        colorCode.add(R.color.Yellow);
        colorCode.add(R.color.Red);

        Random random = new Random();
        int randomIndex = random.nextInt(colorCode.size());
        return colorCode.get(randomIndex);
    }
}


class NoteViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView textView_title, textView_content, textView_date;
    ImageView imageView_pin;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_content = itemView.findViewById(R.id.textView_content);
        textView_date = itemView.findViewById(R.id.textView_date);
//        imageView_pin = itemView.findViewById(R.id.imageView_pin);
    }
}
