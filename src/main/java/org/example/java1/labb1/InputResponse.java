package org.example.java1.labb1;

public class InputResponse {
    private final Status status;
    private final String message;

    public InputResponse(Status status) {
        this.status = status;
        message = "";
    }

    public InputResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return Utils.ANSI_RED + message + Utils.ANSI_RESET;
    }
}
