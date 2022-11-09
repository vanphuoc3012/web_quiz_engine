package engine.controllers;

import engine.models.Result;
import engine.models.UserWebQuiz;
import engine.repository.UserWebQuizRepository;
import engine.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserWebQuizRepository userWebQuizRepository;

    @Autowired
    ResultService resultService;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public ResponseEntity register(@Validated @RequestBody UserWebQuiz user) {
        if(userWebQuizRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");
            userWebQuizRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity getCompleted(Authentication auth,
                                       @RequestParam(defaultValue = "0") Integer page) {
        UserWebQuiz user = userWebQuizRepository.findByEmail(auth.getName());

        Page<Result> resultPage = resultService.findAllByUserId(page, user);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);

    }
}
