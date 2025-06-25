package com.joaodev.clonnotesgoogle.Models;

import java.util.List;

public class ApiResponse {
    private Response response;
    private List<Notes> notas;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<Notes> getNotas() {
        return notas;
    }

    public void setNotas(List<Notes> notas) {
        this.notas = notas;
    }
}

