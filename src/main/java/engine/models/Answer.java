package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Answer {
    private Boolean success;
    private String feedback;

    private List<String> answer;

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    @JsonIgnore
    public List<String> getAnswer() {
        return answer;
    }
    @JsonProperty
    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        if(success){
            feedback = "Congratulations, you're right!";
        } else {
            feedback = "Wrong answer! Please, try again.";
        }
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }
}
