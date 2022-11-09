package engine.repository;

import engine.models.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Integer> {
    Quiz findQuizById(int id);

    Quiz save(Quiz toSave);

    List<Quiz> findAll();

    @Override
    void deleteById(Integer id);

    Iterable<Quiz> findAll(Sort sort);

    Page<Quiz> findAll(Pageable pageable);

}
