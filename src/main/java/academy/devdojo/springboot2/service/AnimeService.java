package academy.devdojo.springboot2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class AnimeService {

    // private final AnimeRepository animeRepository;

    private List<Anime> animes = List.of(
            new Anime(1L, "DBZ"),
            new Anime(2L, "Berserk"),
            new Anime(3L, "Naruto"),
            new Anime(4L, "Boku no hero"));

    public List<Anime> listAll() {
        return animes;
    }

    public Anime findById(long id) {
        return animes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

}
