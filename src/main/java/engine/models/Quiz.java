package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title must not blank")
    @NotNull
    private String title;

    @NotBlank(message = "Text must not blank")
    @NotNull
    private String text;

    @Size(min = 2)
    @NotEmpty
    @ElementCollection
    private List<String> options;

    @ElementCollection
    private List<String> answer;
    @ManyToOne
    private UserWebQuiz createByUser;

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public UserWebQuiz getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(UserWebQuiz createByUser) {
        this.createByUser = createByUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public List<String> getAnswer() {
        return answer;
    }
    @JsonProperty
    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Quiz() {
    }

    public Quiz(String title, String text, List<String> options, List<String> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }
}
