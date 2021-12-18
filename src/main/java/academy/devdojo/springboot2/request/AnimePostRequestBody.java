package academy.devdojo.springboot2.request;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime name cannot be empty")
    @Schema(description = "The name of the anime", example = "Naruto")
    private String name;
}
