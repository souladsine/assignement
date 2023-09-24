package ma.octo.assignement.domain.util;


public enum TransactionStatus {
    VALID("etat valide", Type.VALID),
    MISSING_DETAILS("you are missing a few details", Type.ACCOUNT),
    EXCEEDED_MAX_AMOUNT("max transaction amount exceeded", Type.TRANSACTION),
    MIN_AMOUNT_NOT_REACHED("minimum transaction amount not reached", Type.TRANSACTION),
    RECIPIENT_NOT_FOUND("the recipient was not found", Type.ACCOUNT),
    SENDER_NOT_FOUND("the sender was not found", Type.ACCOUNT),
    NOT_ENOUGH_CREDIT("you don't have the required amount to be sent", Type.CREDIT),
    MISSING_REASON("reason missing from transaction", Type.TRANSACTION);


    TransactionStatus(String message, Type type) {
        this.message = message;
    }

    private String message;
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid(){
        return this == TransactionStatus.VALID;
    }

    @Override
    public String toString() {
        return message;
    }

    public enum Type {
        VALID("valid"),
        ACCOUNT("account"),
        TRANSACTION("transaction"),
        CREDIT("credit");

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        Type(String type) {
            this.type = type;
        }
    }
}
