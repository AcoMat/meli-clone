package unq.pdes._5.g1.segui_tus_compras.util;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Object metadata;

    public ApiResponse(boolean success, String message, T data, Object metadata) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.metadata = metadata;
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