package winside.gmonnet.tondeuse.entites;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Tondeuse {
    private int positionX;
    private int positionY;
    private Orientation orientation;
    private final int positionXMax;
    private final int positionYMax;

    public void tournerAGauche() {
        switch (orientation) {
            case NORD -> orientation = Orientation.OUEST;
            case OUEST -> orientation = Orientation.SUD;
            case SUD -> orientation = Orientation.EST;
            case EST -> orientation = Orientation.NORD;
        }
    }

    public void tournerADroite() {
        switch (orientation) {
            case NORD -> orientation = Orientation.EST;
            case OUEST -> orientation = Orientation.NORD;
            case SUD -> orientation = Orientation.OUEST;
            case EST -> orientation = Orientation.SUD;
        }
    }

    public void avancer() {
        int nouvellePositionX = this.positionX;
        int nouvellePositionY = this.positionY;
        switch (orientation) {
            case NORD -> nouvellePositionY++;
            case OUEST -> nouvellePositionX--;
            case SUD -> nouvellePositionY--;
            case EST -> nouvellePositionX++;
        }
        if (nouvellePositionX >= 0 && nouvellePositionX <= this.positionXMax && nouvellePositionY >= 0 && nouvellePositionY <= this.positionYMax) {
            this.positionX = nouvellePositionX;
            this.positionY = nouvellePositionY;
        }
    }

    @Override
    public String toString() {
        return positionX + " " + positionY + " " + orientation;
    }
}
