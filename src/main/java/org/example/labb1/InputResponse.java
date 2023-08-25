package org.example.labb1;

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
        return Util.ANSI_RED + message + Util.ANSI_RESET;
    }
}
