package academy.devdojo.springboot2.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import academy.devdojo.springboot2.domain.DevDojoUser;
import academy.devdojo.springboot2.util.DevDojoUserCreator;
import academy.devdojo.springboot2.util.TestRestHelper;

@TestConfiguration
@Lazy
public class ITConfiguration {

    @Bean(name = "testRestHelperRoleUser")
    public TestRestHelper testRestHelperRoleUserCreator(@Value("${local.server.port}") int port) {
        DevDojoUser userWithRoleUser = DevDojoUserCreator.createUserWithRoleUser();
        return createTestRestHelper(port, userWithRoleUser);
    }

    @Bean(name = "testRestHelperRoleAdmin")
    public TestRestHelper testRestHelperRoleAdminCreator(@Value("${local.server.port}") int port) {
        DevDojoUser userWithRoleAdmin = DevDojoUserCreator.createUserWithRoleAdmin();
        return createTestRestHelper(port, userWithRoleAdmin);
    }

    private TestRestHelper createTestRestHelper(int port, DevDojoUser user) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .basicAuthentication(user.getUsername(),
                        DevDojoUserCreator.DECRYPTED_PASSWORD);
        TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        return new TestRestHelper(testRestTemplate);
    }

}
