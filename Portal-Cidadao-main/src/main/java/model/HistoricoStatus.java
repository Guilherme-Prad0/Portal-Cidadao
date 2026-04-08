package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class HistoricoStatus {
    Status status;
    String responsavel;
    String comentario;
    LocalDateTime data;

    public HistoricoStatus(Status status, String responsavel, String comentario) {
        this.status = status;
        this.responsavel = responsavel;
        this.comentario = comentario;
        this.data = LocalDateTime.now();
    }

    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return data.format(fmt) + " | " + status + " | " + responsavel + " | " + comentario;
    }
}