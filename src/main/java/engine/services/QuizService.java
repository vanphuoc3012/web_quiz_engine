package engine.services;

import engine.models.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository){
        this.quizRepository = quizRepository;
    }

    public Quiz findQuizById(Integer id){
        return quizRepository.findQuizById(id);
    }

    public Quiz save(Quiz toSave){
        return quizRepository.save(toSave);
    }

    public List<Quiz> findAll(){
        return quizRepository.findAll();
    }

    public void deleteById(int id) {
        quizRepository.deleteById(id);
    }

    public Page<Quiz> findAll(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Quiz> pageResult = quizRepository.findAll(paging);

        return pageResult;
    }


}
