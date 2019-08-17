package com.rest.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;




@Entity
@Table(name = "publications")
public class Publication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "publication_author",
				joinColumns = @JoinColumn(name="publication_id",referencedColumnName="id"),
				inverseJoinColumns = @JoinColumn(name="author_id",referencedColumnName="id")
				)
	private List<Author> authors = new ArrayList<Author>();
	
	
	public void setAuthor(List<Author> authors){
		this.authors = authors;
	}
	
	public List<Author> getAuthor(){
		return authors;
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