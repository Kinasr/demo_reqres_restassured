package models;

public enum HttpStatusCodes {
    CONTINUE(100), OK(200), CREATED(201), ACCEPTED(202), NO_CONTENT(204)
    , MULTIPLE_CHOICE(300), MOVED_PERMANENTLY(301), BAD_REQUEST(400), UNAUTHORIZED(401),
    FORBIDDEN(403), NOT_FOUND(404), CONFLICT(409);

    private final int value;

    HttpStatusCodes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
