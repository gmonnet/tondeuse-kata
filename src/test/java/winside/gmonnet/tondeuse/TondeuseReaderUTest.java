package winside.gmonnet.tondeuse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import winside.gmonnet.tondeuse.entites.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TondeuseReaderUTest {

    @ParameterizedTest
    @CsvSource({
            "entetesIncorrects.txt",
            "incomplet.txt",
            "mauvaisNombresLignes.txt",
            "vide.txt"
    })
    void devrait_renvoyer_une_erreur_si_fichier_mal_formate(String fichier) {
        assertThatThrownBy(() -> new TondeuseReader(fichier))
                .isInstanceOf(FichierMalFormateException.class)
                .hasMessage(FichierMalFormateException.MESSAGE);
    }

    @Test
    void devraite_lire_un_fichier() throws Exception {
        // Given
        TondeuseReader tondeuseReader = new TondeuseReader("tondeuses.txt");
        List<Route> routes = new ArrayList<>();

        // When
        Route route = tondeuseReader.read();
        while (route != null) {
            routes.add(route);
            route = tondeuseReader.read();
        }

        // Then
        assertThat(routes).isNotEmpty().hasSize(2);
        assertThat(routes.get(0).tondeuse())
                .isEqualTo(new Tondeuse(1, 2, Orientation.NORD, 5, 5));
        assertThat(routes.get(0).mouvements())
                .isNotEmpty()
                .hasSize(9)
                .containsExactly(Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.GAUCHE, Mouvement.AVANCER, Mouvement.AVANCER);
        assertThat(routes.get(1).tondeuse())
                .isEqualTo(new Tondeuse(3, 3, Orientation.EST, 5, 5));
        assertThat(routes.get(1).mouvements())
                .isNotEmpty()
                .hasSize(10)
                .containsExactly(Mouvement.AVANCER, Mouvement.AVANCER, Mouvement.DROITE, Mouvement.AVANCER, Mouvement.AVANCER, Mouvement.DROITE, Mouvement.AVANCER, Mouvement.DROITE, Mouvement.DROITE, Mouvement.AVANCER);
    }

}