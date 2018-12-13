package com.csye6225.demo.bean;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name="UploadFile")
public class UploadFile {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int fileId;


    public UploadFile( ) {


    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public toDoTask getTask() {
        return task;
    }

    public void setTask(toDoTask task) {
        this.task = task;
    }

    @ManyToOne
    @JoinColumn(name="taskID", nullable = false)
    private toDoTask task;



    @Column(name="file", columnDefinition = "BLOB")
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
