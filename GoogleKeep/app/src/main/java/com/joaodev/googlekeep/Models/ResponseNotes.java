package com.joaodev.googlekeep.Models;

import java.util.List;

public  class  ResponseNotes {
    private Response response;
    private List<Note> notas;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Note> getNotas() {
        return notas;
    }

    public void setNotas(List<Note> notas) {
        this.notas = notas;
    }

}
