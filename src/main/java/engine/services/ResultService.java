package engine.services;

import engine.models.Result;
import engine.models.UserWebQuiz;
import engine.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Page<Result> findAllByUserId(Integer pageNo, UserWebQuiz user) {
        Pageable paging = PageRequest.of(pageNo, 10, Sort.by("completedAt").descending());
        Page<Result> pageResult = resultRepository.findAllByUserWebQuiz(paging, user);
        System.out.println("creating page");
//        if(pageResult.hasContent()) {
            return pageResult;
//        } else {
//            return null;
//        }
    }

    public Result save(Result result) {
        return resultRepository.save(result);
    }

    public void deleteById(Integer quizId) {
        System.out.println("deleting quizId = "+quizId);
        resultRepository.deleteById(quizId);
    }
}
