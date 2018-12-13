package com.csye6225.demo.repository;


import com.csye6225.demo.bean.UploadFile;
import com.csye6225.demo.bean.toDoTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends CrudRepository<UploadFile, Long> {

    String findByTaskId(String taskID);

}
