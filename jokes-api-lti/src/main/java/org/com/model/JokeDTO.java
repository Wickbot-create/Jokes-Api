package org.com.model;

public class JokeDTO {
    public Long id;
    public String question;
    public String answer;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public JokeDTO() {}

    public JokeDTO(Long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public static JokeDTO notFound() {
        return new JokeDTO(null, "Joke not found", null);
    }

	@Override
	public String toString() {
		return "JokeDTO [id=" + id + ", question=" + question + ", answer=" + answer + "]";
	}
}
