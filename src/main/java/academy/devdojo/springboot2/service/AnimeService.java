package academy.devdojo.springboot2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class AnimeService {

    // private final AnimeRepository animeRepository;

    private List<Anime> animes = new ArrayList<>(List.of(
            new Anime(1L, "DBZ"),
            new Anime(2L, "Berserk"),
            new Anime(3L, "Naruto"),
            new Anime(4L, "Boku no hero")));

    public List<Anime> listAll() {
        return animes;
    }

    public Anime findById(long id) {
        return animes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(Anime anime) {
        anime.setId(getNewId());
        animes.add(anime);
        return anime;
    }

    private long getNewId() {
        long biggestId = animes.stream()
                .mapToLong(Anime::getId)
                .max()
                .orElse(0L);
        return biggestId + 1;
    }

    public void delete(Long id) {
        animes.remove(findById(id));
    }

    public void replace(Anime anime) {
        delete(anime.getId());
        animes.add(anime);
    }

}
