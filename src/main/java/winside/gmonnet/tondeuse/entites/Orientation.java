package winside.gmonnet.tondeuse.entites;

import java.util.Arrays;

public enum Orientation {
    NORD("N"), EST("E"), OUEST("W"), SUD("S");
    private final String valeur;

    Orientation(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return valeur;
    }

    public static Orientation fromString(String valeur) {
        return Arrays.stream(Orientation.values())
                .filter(orientation -> orientation.valeur.equalsIgnoreCase(valeur))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
