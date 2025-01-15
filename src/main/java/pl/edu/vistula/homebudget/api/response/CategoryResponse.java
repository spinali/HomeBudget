package pl.edu.vistula.homebudget.api.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
