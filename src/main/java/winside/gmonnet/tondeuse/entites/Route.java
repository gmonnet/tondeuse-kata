package winside.gmonnet.tondeuse.entites;

import java.util.List;

public record Route(Tondeuse tondeuse, List<Mouvement> mouvements) {
}
