package pl.edu.vistula.homebudget.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateCategoryRequest extends CategoryRequest {
    private final Long id;
    @JsonCreator
    public UpdateCategoryRequest(String name, Long id){
        super(name);
        this.id = id;
    }
}
