package model;

import model.enums.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HistoricoStatus {
    private final Status status;
    private final String responsavel;
    private final String comentario;
    private final LocalDateTime data;

    public HistoricoStatus(Status status, String responsavel, String comentario) {
        this.status = status;
        this.responsavel = responsavel;
        this.comentario = comentario;
        this.data = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return data.format(fmt) + " | " + status + " | " + responsavel + " | " + comentario;
    }
}