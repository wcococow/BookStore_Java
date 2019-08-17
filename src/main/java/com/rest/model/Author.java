package com.rest.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

@Entity
@Table(name = "authors")
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
	
	@ManyToMany(mappedBy = "authors",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Publication> publications = new ArrayList<Publication>();
	
	public void setPublication(Publication publication){
		this.publications.add(publication);
	}
	

	
	public List<Publication> getPublication(){
		return publications;
	}
	
	
	
}