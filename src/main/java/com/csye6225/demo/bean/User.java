/**
 * Nisarg, 001233379, patel.nisar@husky.neu.edu
 * Ishita, 001284764, babel.i@husky.neu.edu
 * Elton, 001239637, barbosa.el@husky.neu.edu
 * Payal, 001224158, Dodeja.p@husky.neu.edu
 **/

package com.csye6225.demo.bean;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int userId;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    public User() {
    }




    @OneToMany(mappedBy = "user")
    private List<toDoTask> tasks = new ArrayList<toDoTask>();


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<toDoTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<toDoTask> tasks) {
        this.tasks = tasks;
    }

}

