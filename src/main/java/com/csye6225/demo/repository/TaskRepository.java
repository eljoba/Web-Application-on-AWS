
/**
 * Nisarg, 001233379, patel.nisar@husky.neu.edu
 * Ishita, 001284764, babel.i@husky.neu.edu
 * Elton, 001239637, barbosa.el@husky.neu.edu
 * Payal, 001224158, Dodeja.p@husky.neu.edu
 **/


package com.csye6225.demo.repository;


import com.csye6225.demo.bean.toDoTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<toDoTask, Long>{




}

