package dev.mas.product.filter;

public enum OperatorQuery {
    EQUALS("EQUALS"),
    NOT_EQUALS("NOT_EQUALS"),
    GREATER_THAN("GREATER_THAN"),
    LESS_THAN("LESS_THAN"),
    LIKE("LIKE"),
    IN("IN");

    private final String text;

    OperatorQuery(String text) {
        this.text = text;
    }

    public static OperatorQuery fromString(String text) {
        for (OperatorQuery operatorQuery : OperatorQuery.values()) {
            if (operatorQuery.text.equalsIgnoreCase(text)) {
                return operatorQuery;
            }
        }
        return null;
    }

    public String getText() {
        return this.text;
    }
}
