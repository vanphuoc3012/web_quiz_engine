package engine.repository;

import engine.models.Result;
import engine.models.UserWebQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends PagingAndSortingRepository<Result, Integer> {
//    @Query(value = "SELECT r.RESULT_ID, r.COMPLETED_AT, r.ID FROM result r LEFT JOIN USER_WEB_QUIZ_RESULTS ON USER_WEB_QUIZ_RESULTS.RESULTS_RESULT_ID = r.RESULT_ID WHERE USER_WEB_QUIZ_RESULTS.USER_WEB_QUIZ_ID = :userId ORDER BY RESULT_ID DESC",
//            countQuery = "select count(*) FROM result r LEFT JOIN USER_WEB_QUIZ_RESULTS ON USER_WEB_QUIZ_RESULTS.RESULTS_RESULT_ID = r.RESULT_ID WHERE USER_WEB_QUIZ_RESULTS.USER_WEB_QUIZ_ID = :userId",
//            nativeQuery = true)
    Page<Result> findAllByUserWebQuiz(Pageable pageable, @Param("userId") UserWebQuiz userWebQuiz);

    Result save(Result result);

    @Query(value = "DELETE FROM result r where r.id = ?1",
            nativeQuery = true)
    @Modifying
    void deleteById(Integer id);
}
