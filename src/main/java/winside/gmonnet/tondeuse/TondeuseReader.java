package winside.gmonnet.tondeuse;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import winside.gmonnet.tondeuse.entites.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TondeuseReader implements ItemReader<Route> {
    private final int tailleX;
    private final int tailleY;
    private final List<String> lignes;
    private int index;

    public TondeuseReader(@Value("${tondeuse.inputFile}") String fichier) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource(fichier).toURI());
        List<String> lignes = new ArrayList<>(Files.readAllLines(path));
        if (lignes.isEmpty() || lignes.size() < 3 || lignes.size() % 2 == 0) {
            throw new FichierMalFormateException();
        }
        String configuration = lignes.remove(0);
        if (!configuration.matches("\\d+ \\d+")) {
            throw new FichierMalFormateException();
        }
        String[] tailles = configuration.split(" ");
        this.tailleX = Integer.parseInt(tailles[0]);
        this.tailleY = Integer.parseInt(tailles[1]);
        this.lignes = lignes;
        this.index = 0;
    }

    @Override
    public Route read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (this.index >= this.lignes.size() || this.index + 1 >= this.lignes.size()) {
            return null;
        }
        String configuration = this.lignes.get(this.index);
        if (!configuration.matches("\\d+ \\d+ [NSEW]")) {
            throw new FichierMalFormateException();
        }
        String[] configurationTondeuse = configuration.split(" ");
        Tondeuse tondeuse = new Tondeuse(
                Integer.parseInt(configurationTondeuse[0]),
                Integer.parseInt(configurationTondeuse[1]),
                Orientation.fromString(configurationTondeuse[2]),
                this.tailleX,
                this.tailleY
        );
        String strMouvements = this.lignes.get(this.index + 1);
        if (!strMouvements.matches("[AGD]+")) {
            throw new FichierMalFormateException();
        }
        List<Mouvement> mouvements = Arrays.stream(strMouvements.split("")).map(Mouvement::fromString).toList();

        this.index = this.index + 2;
        return new Route(tondeuse, mouvements);
    }
}
