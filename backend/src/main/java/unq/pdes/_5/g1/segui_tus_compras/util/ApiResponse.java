package unq.pdes._5.g1.segui_tus_compras.util;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private Object metadata;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, Object metadata, T data) {
        this.success = success;
        this.metadata = metadata;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Object getMetadata() {
        return metadata;
    }
}