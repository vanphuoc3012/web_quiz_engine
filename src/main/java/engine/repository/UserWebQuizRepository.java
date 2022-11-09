package engine.repository;

import engine.models.Result;
import engine.models.UserWebQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserWebQuizRepository extends PagingAndSortingRepository<UserWebQuiz, Integer> {

    UserWebQuiz findByEmail(String email);

    UserWebQuiz save(UserWebQuiz user);

    boolean existsByEmail(String email);

}
