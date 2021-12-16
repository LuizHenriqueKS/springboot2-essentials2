package academy.devdojo.springboot2.util;

import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import academy.devdojo.springboot2.wrapper.PageableResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRestHelper {

    private final TestRestTemplate testRestTemplate;

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {
        return testRestTemplate.getForObject(url, responseType, urlVariables);
    }

    public <T> T postForObject(String url, Object requestBody, Class<T> responseType) {
        return testRestTemplate.postForObject(url, requestBody, responseType);
    }

    public <T> Page<T> getForPage(String url, Class<T> responseType) {
        ParameterizedTypeReference<PageableResponse<T>> responseTypeReference = ParameterizedTypeReference
                .forType(TypeUtils.parameterize(PageableResponse.class, responseType));
        return testRestTemplate
                .exchange(url, HttpMethod.GET, null, responseTypeReference).getBody();
    }

    public <T> List<T> getForList(String url, Class<T> responseType, Object... urlVariables) {
        ParameterizedTypeReference<List<T>> responseTypeReference = ParameterizedTypeReference
                .forType(TypeUtils.parameterize(List.class, responseType));
        return testRestTemplate
                .exchange(url, HttpMethod.GET, null, responseTypeReference, urlVariables).getBody();
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object requestBody, Class<T> responseType) {
        return testRestTemplate.postForEntity(url, requestBody, responseType);
    }

    public <P, T> ResponseEntity<T> putForEntity(String url, P requestBody, Class<T> responseType) {
        ParameterizedTypeReference<T> responseTypeReference = ParameterizedTypeReference
                .forType(TypeUtils.parameterize(responseType));
        HttpEntity<P> requestEntity = requestBody == null ? null : new HttpEntity<P>(requestBody);
        return testRestTemplate
                .exchange(url, HttpMethod.PUT, requestEntity, responseTypeReference);
    }

    public <P> ResponseEntity<Void> putForEntity(String url, P requestBody) {
        return putForEntity(url, requestBody, Void.class);
    }

    public ResponseEntity<Void> deleteForEntity(String url, Object... urlVariables) {
        return testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, urlVariables);
    }

}
