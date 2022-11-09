package engine.controllers;

import engine.models.Answer;
import engine.models.Quiz;
import engine.models.Result;
import engine.models.UserWebQuiz;
import engine.repository.UserWebQuizRepository;
import engine.services.QuizService;
import engine.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    UserWebQuizRepository userWebQuizRepository;

    @Autowired
    ResultService resultService;

    /**
     * Stage 1; add quiz
     * @return
     */
    @GetMapping(path = "/api/quiz")
    private ResponseEntity<Quiz> sendQuiz(){
        Quiz quiz = new Quiz();
        quiz.setTitle("The Java Logo");
        quiz.setText("What is depicted on the Java logo?");
        List<String> options = new ArrayList<>();
        options.addAll(List.of("Robot","Tea leaf","Cup of coffee","Bug"));
        quiz.setOptions(options);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    //get answer from above quiz
    @PostMapping("/api/quiz")
    private ResponseEntity solveTheQuiz(@RequestParam String answer){
        Answer a = new Answer();
        if(answer.equals("2")){
            a.setSuccess(true);
        } else {
            a.setSuccess(false);
        }
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    //add quiz, quiz in request body
    //using QuizService
    @PostMapping("/api/quizzes")
    private ResponseEntity createANewQuiz(@Valid @RequestBody Quiz quiz,
                                            Authentication auth){
        Quiz quizToSave = new Quiz();
        quizToSave.setText(quiz.getText());
        quizToSave.setTitle(quiz.getTitle());
        quizToSave.setOptions(quiz.getOptions());
        quizToSave.setAnswer(quiz.getAnswer());
        quizToSave.setCreateByUser(userWebQuizRepository.findByEmail(auth.getName()));

        return new ResponseEntity<>(quizService.save(quizToSave), HttpStatus.OK);
    }

    //get quiz by id
    @GetMapping("/api/quizzes/{id}")
    private ResponseEntity getQuiz(@PathVariable int id){
            Quiz quizToReturn = quizService.findQuizById(id);
            if(quizToReturn != null){
                return new ResponseEntity(quizToReturn, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("",HttpStatus.NOT_FOUND);
            }
    }

    //get all quiz
    @GetMapping("/api/quizzes")
    private ResponseEntity getAllQuiz(@RequestParam(defaultValue ="0") Integer page){
        Page<Quiz> quizPage = quizService.findAll(page, 10);
        if(quizPage == null){
            return new ResponseEntity("[]", HttpStatus.OK);
        } else {
            return new ResponseEntity(quizPage, HttpStatus.OK);
        }
    }

//    @PostMapping("/api/quizzes/{id}/solve")
//    private ResponseEntity solveTheQuizStage2(@PathVariable int id,
//            @RequestParam String answer) {
//
//        if (id > 0 && id <= quizList.size()) {
//            Quiz q = quizList.get(id - 1);
//            Answer a = new Answer();
//
//            if (answer.equals(q.getAnswer())) {
//                a.setSuccess(true);
//            } else {
//                a.setSuccess(false);
//            }
//            return new ResponseEntity<>(a, HttpStatus.OK);
//
//        } else {
//            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
//        }
//    }

    //solve quiz(id), answer in requestbody
    @PostMapping("/api/quizzes/{id}/solve")
    private ResponseEntity solveTheQuizStage3(@PathVariable int id,
                                              @RequestBody Answer answer,
                                              Authentication auth) {
        UserWebQuiz user = userWebQuizRepository.findByEmail(auth.getName());
        Quiz quizToSolve = quizService.findQuizById(id);
        if (quizToSolve != null) {
            List<String> answerOfQuiz = quizToSolve.getAnswer();
            Answer a = new Answer();
            a.setSuccess(true);
            if(answer.getAnswer().isEmpty() && answerOfQuiz.isEmpty()){
                a.setSuccess(true);
            } else if (answer.getAnswer().size() == answerOfQuiz.size()) {
                for(String i : answer.getAnswer()){
                    if(!answerOfQuiz.contains(i)) {
                        a.setSuccess(false);
                    }
                }
            } else {
                a.setSuccess(false);
            }

            if(a.getSuccess()) {
                Result r = new Result();
                r.setId(quizToSolve.getId());
                r.setCompletedAt(LocalDateTime.now().toString());
                r.setUserWebQuiz(user);
                user.addResult(r);
                resultService.save(r);
            }
            System.out.println("your answer is "+a.getSuccess());
            return new ResponseEntity<>(a, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Integer id,
                                     Authentication auth) {
        UserWebQuiz user = userWebQuizRepository.findByEmail(auth.getName());
        Quiz quiz = quizService.findQuizById(id);
        if(quiz != null) {
            if(quiz.getCreateByUser().getEmail().equals(user.getEmail())) {
                resultService.deleteById(id);
                quizService.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
