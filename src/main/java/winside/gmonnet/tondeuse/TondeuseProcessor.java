package winside.gmonnet.tondeuse;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import winside.gmonnet.tondeuse.entites.Route;
import winside.gmonnet.tondeuse.entites.Tondeuse;

@Component
public class TondeuseProcessor implements ItemProcessor<Route, Tondeuse> {
    @Override
    public Tondeuse process(Route route) {
        Tondeuse tondeuse = route.tondeuse();
        route.mouvements().forEach(mouvement -> {
            switch (mouvement) {
                case DROITE -> tondeuse.tournerADroite();
                case GAUCHE -> tondeuse.tournerAGauche();
                case AVANCER -> tondeuse.avancer();
            }
        });
        return tondeuse;
    }
}
