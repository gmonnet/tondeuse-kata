package winside.gmonnet.tondeuse.entites;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TondeuseUTest {

    @ParameterizedTest
    @CsvSource({
            "N,W",
            "E,N",
            "S,E",
            "W,S"
    })
    void tournerAGauche_devrait_faire_pivoter_une_tondeuse_sur_la_gauche(String initial, String attendu){
        // Given
        Tondeuse tondeuse = new Tondeuse(1, 1, Orientation.fromString(initial), 2, 2);

        // When
        tondeuse.tournerAGauche();

        // Then
        assertThat(tondeuse.getPositionX()).isEqualTo(1);
        assertThat(tondeuse.getPositionY()).isEqualTo(1);
        assertThat(tondeuse.getPositionXMax()).isEqualTo(2);
        assertThat(tondeuse.getPositionYMax()).isEqualTo(2);
        assertThat(tondeuse.getOrientation()).isEqualTo(Orientation.fromString(attendu));
    }

    @ParameterizedTest
    @CsvSource({
            "N,E",
            "E,S",
            "S,W",
            "W,N"
    })
    void tournerADroite_devrait_faire_pivoter_une_tondeuse_sur_la_droite(String initial, String attendu){
        // Given
        Tondeuse tondeuse = new Tondeuse(1, 1, Orientation.fromString(initial), 2, 2);

        // When
        tondeuse.tournerADroite();

        // Then
        assertThat(tondeuse.getPositionX()).isEqualTo(1);
        assertThat(tondeuse.getPositionY()).isEqualTo(1);
        assertThat(tondeuse.getPositionXMax()).isEqualTo(2);
        assertThat(tondeuse.getPositionYMax()).isEqualTo(2);
        assertThat(tondeuse.getOrientation()).isEqualTo(Orientation.fromString(attendu));
    }

    @ParameterizedTest
    @CsvSource({
        "N, 1, 2",
        "W, 0, 1",
        "S, 1, 0",
        "E, 2, 1"
    })
    void avancer_devrait_faire_avancer_une_tondeuse_selon_son_orientation(String orientation, int attenduX, int attenduY) {
        // Given
        Tondeuse tondeuse = new Tondeuse(1, 1, Orientation.fromString(orientation), 2, 2);

        // When
        tondeuse.avancer();

        // Then
        assertThat(tondeuse.getPositionX()).isEqualTo(attenduX);
        assertThat(tondeuse.getPositionY()).isEqualTo(attenduY);
        assertThat(tondeuse.getPositionXMax()).isEqualTo(2);
        assertThat(tondeuse.getPositionYMax()).isEqualTo(2);
        assertThat(tondeuse.getOrientation()).isEqualTo(Orientation.fromString(orientation));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, N",
        "0, 1, W",
        "1, 0, S",
        "2, 1, E"
    })
    void avancer_ne_devrait_pas_faire_avancer_une_tondeuse_en_bordure_de_terrain(int initialX, int initialY, String orientation) {
        // Given
        Tondeuse tondeuse = new Tondeuse(initialX, initialY, Orientation.fromString(orientation), 2, 2);

        // When
        tondeuse.avancer();

        // Then
        assertThat(tondeuse.getPositionX()).isEqualTo(initialX);
        assertThat(tondeuse.getPositionY()).isEqualTo(initialY);
        assertThat(tondeuse.getPositionXMax()).isEqualTo(2);
        assertThat(tondeuse.getPositionYMax()).isEqualTo(2);
        assertThat(tondeuse.getOrientation()).isEqualTo(Orientation.fromString(orientation));
    }
}