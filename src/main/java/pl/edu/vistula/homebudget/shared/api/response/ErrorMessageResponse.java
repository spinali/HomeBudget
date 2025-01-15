package pl.edu.vistula.homebudget.shared.api.response;

import lombok.Data;

@Data
public class ErrorMessageResponse {
    private final String error;
    public ErrorMessageResponse(String message) {
        this.error = message;
    }
}
