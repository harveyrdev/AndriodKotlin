package com.joaodev.googlekeep.Models;

import androidx.cardview.widget.CardView;

public interface NoteClickListener {

    void onClick(Note notes);
    void onLoadClick(Note notes, CardView cardView);
}
