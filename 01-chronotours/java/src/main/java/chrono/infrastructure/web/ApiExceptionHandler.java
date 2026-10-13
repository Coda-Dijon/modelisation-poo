package chrono.infrastructure.web;

import chrono.application.TravellerNotFound;
import chrono.domain.JumpRefused;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(JumpRefused.class)
    ProblemDetail jumpRefused(JumpRefused refusal) {
        return problem(HttpStatus.UNPROCESSABLE_CONTENT, "Saut refusé", refusal);
    }

    @ExceptionHandler(TravellerNotFound.class)
    ProblemDetail travellerNotFound(TravellerNotFound notFound) {
        return problem(HttpStatus.NOT_FOUND, "Voyageur inconnu", notFound);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalidArgument(IllegalArgumentException invalid) {
        return problem(HttpStatus.BAD_REQUEST, "Demande invalide", invalid);
    }

    private static ProblemDetail problem(HttpStatus status, String title, RuntimeException exception) {
        var problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problem.setTitle(title);
        return problem;
    }
}
