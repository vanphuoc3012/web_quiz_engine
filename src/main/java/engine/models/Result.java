package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long resultId;

    private int id;

    private String completedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userwebquiz_id")
    private UserWebQuiz userWebQuiz;

    public UserWebQuiz getUserWebQuiz() {
        return userWebQuiz;
    }

    public void setUserWebQuiz(UserWebQuiz userWebQuiz) {
        this.userWebQuiz = userWebQuiz;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
