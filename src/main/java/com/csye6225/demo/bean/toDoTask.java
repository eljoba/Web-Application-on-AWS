package com.csye6225.demo.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="ToDoTask")
public class toDoTask {

    @Id
    @Column(name="taskID")
    private String id;

    @OneToMany
    private List<UploadFile> files=new ArrayList<UploadFile>();

    public List<UploadFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadFile> files) {
        this.files = files;
    }

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="userID", nullable = false)
    private User user;

    public toDoTask(){

    }
    public toDoTask(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
