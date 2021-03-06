package academy.devdojo.springboot2.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import lombok.Setter;

@Setter
public class PageableResponse<T> extends PageImpl<T> {

    boolean first;
    boolean last;
    int totalPages;
    int numberOfElements;

    @JsonCreator
    @JsonIgnoreProperties(ignoreUnknown = true)
    public PageableResponse(@JsonProperty("content") List<T> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") int totalElements,
            @JsonProperty("last") boolean last,
            @JsonProperty("first") boolean first,
            @JsonProperty("totalPages") int totalPages,
            @JsonProperty("numberOfElement") int numberOfElement,
            @JsonProperty("numberOfElements") int numberOfElements,
            @JsonProperty("pageable") JsonNode pageable,
            @JsonProperty("sort") JsonNode sort) {
        super(content, PageRequest.of(number, size), totalElements);

        this.last = last;
        this.first = first;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
    }

}
