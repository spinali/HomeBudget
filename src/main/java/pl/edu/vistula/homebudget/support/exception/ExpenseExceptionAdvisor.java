package pl.edu.vistula.homebudget.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.vistula.homebudget.shared.api.response.ErrorMessageResponse;

public class ExpenseExceptionAdvisor {
    private static final Logger LOG = LoggerFactory.getLogger(ExpenseExceptionAdvisor.class);
    @ExceptionHandler(ExpenseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageResponse expenseNotFound(Exception e){
        LOG.error(e.getMessage(), e);
        return new ErrorMessageResponse(e.getMessage());
    }
}
