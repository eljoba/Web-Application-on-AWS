/**
* Nisarg, 001233379, patel.nisar@husky.neu.edu
* Ishita, 001284764, babel.i@husky.neu.edu
* Elton, 001239637, barbosa.el@husky.neu.edu
* Payal, 001224158, Dodeja.p@husky.neu.edu
**/

package com.csye6225.demo.repository;


import com.csye6225.demo.bean.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
//    @Override
//    Iterable<User> findAll();

List<User> findByEmail(String email);

}
