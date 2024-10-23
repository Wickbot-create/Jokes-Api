package org.com.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "jokes")
public class Joke extends PanacheEntity {
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
	@Override
	public String toString() {
		return "Joke [id=" + id + ", question=" + question + ", answer=" + answer + "]";
	}
	public Joke(Long id, String question, String answer) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
	}
	public Joke() {
		super();
		// TODO Auto-generated constructor stub
	}	
}
