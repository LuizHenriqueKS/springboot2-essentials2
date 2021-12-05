package academy.devdojo.springboot2.service;

import java.util.List;
import org.springframework.stereotype.Service;
import academy.devdojo.springboot2.domain.Anime;

@Service
public class AnimeService {

    // private final AnimeRepository animeRepository;

    public List<Anime> listAll() {
        return List.of(
                new Anime(1L, "DBZ"),
                new Anime(2L, "Berserk"),
                new Anime(3L, "Naruto"),
                new Anime(4L, "Boku no hero"));
    }

}
