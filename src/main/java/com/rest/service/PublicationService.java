package com.rest.service;

import com.rest.model.Publication;
import com.rest.model.Author;
import java.util.List;

public abstract class PublicationService implements BaseService<Publication>{
	//public abstract List<Publication> searchPublicationsByAuthorAndYear();
	public abstract List<Publication> search(int id,String catagory,String title,int year,String type,String hero,String genre);
}