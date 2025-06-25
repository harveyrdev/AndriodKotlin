package com.joaodev.clonnotesgoogle.Models;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class NotesDiffCallback extends DiffUtil.Callback {
    private final List<Notes> oldList;
    private final List<Notes> newList;

    public NotesDiffCallback(List<Notes> oldList, List<Notes> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {

        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Notes oldNote = oldList.get(oldItemPosition);
        Notes newNote = newList.get(newItemPosition);
        return oldNote.getTitle().equals(newNote.getTitle()) &&
                oldNote.getContent().equals(newNote.getContent()) &&
                oldNote.getCreationDate().equals(newNote.getCreationDate());
    }
}
