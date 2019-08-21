package com.rest.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.*;



@Entity
@Table(name = "publication")
public class Publication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
	@ManyToMany(mappedBy = "publications",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Author> authors = new ArrayList<Author>();
	
	
	public void setAuthor(Author author){
		authors.add(author);
		author.getPublication().add(this);
	}
	
	@JsonBackReference
	public List<Author> getAuthor(){
		return authors;
	}
	
	public Publication(int id,String catagory, String title, int year ,String type, String hero, String genre){
		this.id = id;
		this.title = title;
		this.catagory = catagory;
		this.year = year;
		this.type = type;
		this.hero = hero;
		
		this.genre = genre;
	}
	
	public Publication(){
	}
	
	@Column(name = "catagory")
	private String catagory;
	public String getCatagory() {
        return catagory;
    }
    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
	@Column(name = "title")
	private String title;
	public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
	@Column(name = "year")
	private int year;
	public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
	@Column(name = "type")
	private String type = "NA";
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
	@Column(name = "hero")
	private String hero = "NA";
	public String getHero() {
        return hero;
    }
    public void setHero(String hero) {
        this.hero = hero;
    }
	@Column(name = "genre")
	private String genre = "NA";
	public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
	
	
}