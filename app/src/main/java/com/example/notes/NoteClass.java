package com.example.notes;

public class NoteClass {
    public String noteId;
    public String note;
    public String createdDate;
    public String shortDescription;

    public NoteClass(){

    }

    public NoteClass(String noteId,String note, String createdDate) {
        this.noteId = noteId;
        this.note = note;
        this.createdDate = createdDate;
    }
    public NoteClass(String noteId,String note) {
        this.noteId = noteId;
        this.note = note;

    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
