package pl.edu.vistula.homebudget.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.vistula.homebudget.shared.api.response.ErrorMessageResponse;
import pl.edu.vistula.homebudget.support.exception.CategoryNotFoundException;

public class CategoryExceptionAdvisor {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryExceptionAdvisor.class);
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse categoryNotFound(Exception e){
        LOG.error(e.getMessage(),e);
        return new ErrorMessageResponse(e.getMessage());
    }
}
