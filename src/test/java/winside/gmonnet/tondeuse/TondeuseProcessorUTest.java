package winside.gmonnet.tondeuse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import winside.gmonnet.tondeuse.entites.Mouvement;
import winside.gmonnet.tondeuse.entites.Orientation;
import winside.gmonnet.tondeuse.entites.Route;
import winside.gmonnet.tondeuse.entites.Tondeuse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TondeuseProcessorUTest {

    @Test
    void ne_devrait_pas_deplacer_une_tondeuse_deplacer_sans_mouvements() {
        // Given
        int posX = 1;
        int posY = 2;
        Orientation orientation = Orientation.NORD;
        int maxX = 5;
        int maxY = 10;
        Route route = new Route(new Tondeuse(posX, posY, orientation, maxX, maxY), List.of());
        TondeuseProcessor tondeuseProcessor = new TondeuseProcessor();

        // When
        Tondeuse tondeuse = tondeuseProcessor.process(route);

        // Then
        assertThat(tondeuse).isNotNull();
        assertThat(tondeuse.getPositionX()).isEqualTo(posX);
        assertThat(tondeuse.getPositionY()).isEqualTo(posY);
        assertThat(tondeuse.getPositionXMax()).isEqualTo(maxX);
        assertThat(tondeuse.getPositionYMax()).isEqualTo(maxY);
        assertThat(tondeuse.getOrientation()).isEqualTo(orientation);
    }

    @Test
    void devrait_deplacer_une_tondeuse_selon_ses_mouvements() {
        // Given
        int posX = 1;
        int posY = 2;
        Orientation orientation = Orientation.NORD;
        int maxX = 5;
        int maxY = 5;
        List<Mouvement> mouvements = List.of(
                Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.AVANCER
        );
        Route route = new Route(new Tondeuse(posX, posY, orientation, maxX, maxY), mouvements);
        TondeuseProcessor tondeuseProcessor = new TondeuseProcessor();

        // When
        Tondeuse tondeuse = tondeuseProcessor.process(route);

        // Then
        assertThat(tondeuse).isNotNull();
        assertThat(tondeuse.getPositionX()).isEqualTo(1);
        assertThat(tondeuse.getPositionY()).isEqualTo(3);
        assertThat(tondeuse.getPositionXMax()).isEqualTo(maxX);
        assertThat(tondeuse.getPositionYMax()).isEqualTo(maxY);
        assertThat(tondeuse.getOrientation()).isEqualTo(Orientation.NORD);
    }
}