package winside.gmonnet.tondeuse.entites;

import java.util.Arrays;

public enum Mouvement {
    GAUCHE("G"), DROITE("D"), AVANCER("A");
    private final String valeur;

    Mouvement(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return valeur;
    }

    public static Mouvement fromString(String valeur) {
        return Arrays.stream(Mouvement.values())
                .filter(mouvement -> mouvement.valeur.equalsIgnoreCase(valeur))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
