package org.com.model;

public class JokeResponseDTO {
	public Long id;
    public String setup;
    public String punchline;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSetup() {
		return setup;
	}
	public void setSetup(String setup) {
		this.setup = setup;
	}
	public String getPunchline() {
		return punchline;
	}
	public void setPunchline(String punchline) {
		this.punchline = punchline;
	}
	@Override
	public String toString() {
		return "JokeResponseDTO [id=" + id + ", setup=" + setup + ", punchline=" + punchline + "]";
	}
	public JokeResponseDTO(Long id, String setup, String punchline) {
		super();
		this.id = id;
		this.setup = setup;
		this.punchline = punchline;
	}
	public JokeResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}

