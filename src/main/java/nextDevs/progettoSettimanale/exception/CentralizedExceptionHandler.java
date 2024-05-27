package nextDevs.progettoSettimanale.exception;


import nextDevs.progettoSettimanale.model.Error;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(DipendenteNonTrovatoException.class)
    public ResponseEntity<Object> DipendenteNonTrovatohandler(DipendenteNonTrovatoException e){
      Error error = new Error();
      error.setMessage(e.getMessage());
      error.setDateErrore(LocalDate.now());
      error.setHttpErrore(HttpStatus.NOT_FOUND);

      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  @ExceptionHandler(DispositivoNonTrovatoException.class)
  public ResponseEntity<Object> DispositivoNonTrovatohandler(DispositivoNonTrovatoException e){
    Error error = new Error();
    error.setMessage(e.getMessage());
    error.setDateErrore(LocalDate.now());
    error.setHttpErrore(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> badRequestHandler(BadRequestException e){
    Error error = new Error();
    error.setMessage(e.getMessage());
    error.setDateErrore(LocalDate.now());
    error.setHttpErrore(HttpStatus.BAD_REQUEST);

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

}
