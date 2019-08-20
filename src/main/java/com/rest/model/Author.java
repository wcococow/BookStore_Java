package com.rest.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

@Entity
@Table(name = "author")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
	@Column(name = "name",nullable = false)
	private String name;
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
	@Column(name = "email_address", nullable = false, unique=true )
	private String emailId;
	public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
	
	public Author(int id, String name, String emailId){
		this.id = id;
		this.name = name;
		this.emailId = emailId;
	}
	
	public Author(){
	}
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "author_publication",
				joinColumns = @JoinColumn(name="author_id",referencedColumnName="id"),
				inverseJoinColumns = @JoinColumn(name="publication_id",referencedColumnName="id")
				)
	private List<Publication> publications = new ArrayList<Publication>();
	
	public void setPublication(Publication publication){
		publications.add(publication);
		publication.setAuthor(this);
	}
	
	
	public List<Publication> getPublication(){
		return publications;
	}
	
	
}