package com.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
;
import com.rest.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication,Integer>,
														JpaSpecificationExecutor {

}