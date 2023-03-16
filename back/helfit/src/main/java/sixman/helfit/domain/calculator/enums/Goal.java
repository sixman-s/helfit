package sixman.helfit.domain.calculator.enums;

public enum Goal {
    BULK("bulk"),
    DIET("diet"),
    KEEP("keep");
    private final String name;

    Goal(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
