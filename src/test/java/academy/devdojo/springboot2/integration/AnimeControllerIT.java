package academy.devdojo.springboot2.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.repository.DevDojoUserRepository;
import academy.devdojo.springboot2.request.AnimePostRequestBody;
import academy.devdojo.springboot2.request.AnimePutRequestBody;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import academy.devdojo.springboot2.util.DevDojoUserCreator;
import academy.devdojo.springboot2.util.TestRestHelper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private DevDojoUserRepository devDojoUserRepository;

    @Autowired
    @Qualifier("testRestHelperRoleUser")
    private TestRestHelper testRestHelperRoleUser;

    @Autowired
    @Qualifier("testRestHelperRoleAdmin")
    private TestRestHelper testRestHelperRoleAdmin;

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();
        Page<Anime> animePage = testRestHelperRoleUser.getForPage("/animes", Anime.class);
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Anime firstAnime = animePage.toList().get(0);
        Assertions.assertThat(firstAnime.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();
        List<Anime> animeList = testRestHelperRoleUser.getForList("/animes/all", Anime.class);
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Anime firstAnime = animeList.get(0);
        Assertions.assertThat(firstAnime.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns an anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();
        Anime anime = testRestHelperRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns an anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();
        List<Anime> animeList = testRestHelperRoleUser.getForList("/animes/find?name={name}", Anime.class,
                expectedName);
        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Anime firstAnime = animeList.get(0);
        Assertions.assertThat(firstAnime.getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = testRestHelperRoleUser.getForList("/animes/find?name={name}", Anime.class,
                expectedName);
        Assertions.assertThat(animeList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        ResponseEntity<Anime> animeResponseEntity = testRestHelperRoleUser.postForEntity("/animes",
                animePostRequestBody,
                Anime.class);
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        AnimePutRequestBody animePutRequestBody = AnimePutRequestBodyCreator.createAnimePutRequestBody();
        animePutRequestBody.setId(savedAnime.getId());
        ResponseEntity<Void> entity = testRestHelperRoleUser.putForEntity("/animes", animePutRequestBody);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleAdmin());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long animeIdToBeDeleted = savedAnime.getId();
        ResponseEntity<Void> entity = testRestHelperRoleAdmin.deleteForEntity("/animes/admin/{id}", animeIdToBeDeleted);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when anime is not admin")
    void delete_Removes403_WhenUserIsNotAdmin() {
        devDojoUserRepository.save(DevDojoUserCreator.createUserWithRoleUser());
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long animeIdToBeDeleted = savedAnime.getId();
        ResponseEntity<Void> entity = testRestHelperRoleUser.deleteForEntity("/animes/admin/{id}", animeIdToBeDeleted);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
