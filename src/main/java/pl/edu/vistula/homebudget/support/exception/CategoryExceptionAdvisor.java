package pl.edu.vistula.homebudget.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.vistula.homebudget.shared.api.response.ErrorMessageResponse;
@RestControllerAdvice
public class CategoryExceptionAdvisor {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryExceptionAdvisor.class);
//    @ExceptionHandler(CategoryNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ErrorMessageResponse categoryNotFound(Exception e){
//        LOG.error(e.getMessage(),e);
//        return new ErrorMessageResponse(e.getMessage());
//    }
    @ExceptionHandler(CategoryDeletionException.class)
    public ResponseEntity<ErrorMessageResponse> categoryDeletion(CategoryDeletionException e){
        LOG.error(e.getMessage(),e);
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> categoryNotFound(CategoryDeletionException e){
        LOG.error(e.getMessage(),e);
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> categoryAlreadyExists(CategoryDeletionException e){
        LOG.error(e.getMessage(),e);
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
}
