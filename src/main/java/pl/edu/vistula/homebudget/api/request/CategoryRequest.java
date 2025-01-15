package pl.edu.vistula.homebudget.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CategoryRequest {
    private String name;
    @JsonCreator
    public CategoryRequest(String name) {
        this.name = name;
    }
}
