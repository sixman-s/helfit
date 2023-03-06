package sixman.helfit.domain.user.enums;

public enum ActivityLevel {
    SEDENTARY("sedentary", 1.2),
    LIGHTLY_ACTIVE("lightly_active", 1.375),
    MODERATELY_ACTIVE("moderately_active", 1.55),
    VERY_ACTIVE("very_active", 1.725),
    EXTRA_ACTIVE("extra_active", 1.9);

    private final String name;
    private final double value;

    ActivityLevel(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
