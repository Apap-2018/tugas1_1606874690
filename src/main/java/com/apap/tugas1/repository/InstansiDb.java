package com.apap.tugas1.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.apap.tugas1.model.InstansiModel;

public interface InstansiDb extends JpaRepository<InstansiModel, Long>{
	
	InstansiModel findById(long id);
	List<InstansiModel> findAll();
}
