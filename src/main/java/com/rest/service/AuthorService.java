package com.rest.service;

import com.rest.model.Author;
import com.rest.model.Publication;

import java.util.List;

public abstract class AuthorService implements BaseService<Author>{
	public abstract List<Publication> getPublicationsById(int id);
	public abstract List<Publication> search(int id,String catagory,String title,int year,String type,String hero,String genre);

}