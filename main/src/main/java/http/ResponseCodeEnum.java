package http;

public enum ResponseCodeEnum {
    success(200);

    private int code;

    ResponseCodeEnum(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

}
