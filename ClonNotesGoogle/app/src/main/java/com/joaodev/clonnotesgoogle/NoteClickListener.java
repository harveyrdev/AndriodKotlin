package com.joaodev.clonnotesgoogle;

import androidx.cardview.widget.CardView;

import com.joaodev.clonnotesgoogle.Models.Notes;

public interface NoteClickListener {
void onClick(Notes notes);
void  onLoadClick(Notes notes , CardView cardView);

}
