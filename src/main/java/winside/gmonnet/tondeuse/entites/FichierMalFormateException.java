package winside.gmonnet.tondeuse.entites;

public class FichierMalFormateException extends RuntimeException {
    public static final String MESSAGE = "Fichier d'entrée mal formatée";
    public FichierMalFormateException() {
        super(MESSAGE);
    }
}
