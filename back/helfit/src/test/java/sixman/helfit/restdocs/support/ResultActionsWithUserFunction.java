package sixman.helfit.restdocs.support;

@FunctionalInterface
public interface ResultActionsWithUserFunction<T, R> {
    R apply(T withUser);
}
