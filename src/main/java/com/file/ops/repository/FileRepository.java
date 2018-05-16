package com.file.ops.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.file.ops.domain.File;

public interface FileRepository extends CrudRepository<File, Long> {

	@Query("from File f where f.id=:id or lower(f.name) LIKE lower(:name) or lower(f.contentType) LIKE lower(:type)")
	List<File> searchFile(@Param("id") long id, @Param("name") String name, @Param("type") String type);
	
	@Query("from File f where f.createdTime > :date")
	List<File> getLastOneHourFiles(@Param ("date") Date date);
}
