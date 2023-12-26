package gharach_aw.frame_analysis.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gharach_aw.frame_analysis.exception.PacketCaptureNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PacketCaptureNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(PacketCaptureNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        // Log the exception or perform any other necessary actions
        e.printStackTrace();

        // Return a custom error message and 500 Internal Server Error status
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An internal server error occurred while accessing the database. Please try again later.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception or perform any other necessary actions
        e.printStackTrace();

        // Return a generic error message and 500 Internal Server Error status
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An internal server error occurred. Please try again later.");
    }
}