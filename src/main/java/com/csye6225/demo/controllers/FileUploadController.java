package com.csye6225.demo.controllers;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.csye6225.demo.bean.UploadFile;
import com.csye6225.demo.bean.User;
import com.csye6225.demo.bean.toDoTask;
import com.csye6225.demo.repository.TaskRepository;
import com.csye6225.demo.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpRequest;
import com.csye6225.demo.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
public class FileUploadController {
    private static String UPLOADED_FOLDER = "//home//eljoba//upload//";
    @Autowired
    private UploadRepository uploadRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    

//     String bucketName = System.getProperty("bucket.name");
//     AmazonS3 s3client= AmazonS3ClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false)).build();
    

//     AWSCredentials credentials = new BasicAWSCredentials("AKIAJT7VDDAUIZ5IKLMQ", "PrRAsUlaM+7r4gHCqOHkhaarwW7nYHeQn4w9dCnT");
//     AmazonS3 s3client = new AmazonS3Client(credentials);


    //String bucketName = "code-deploy.csye6225-fall2017-patelnisar.me.com";
    String bucketName = System.getProperty("bucket.name");
/*
    AWSCredentials credentials = new BasicAWSCredentials("AKIAJVIBPMD6HW7WRUYA", "fWSiZnFnwAvwgO7aYPRu2TWduG9Sq44lJVWcS7jx");
    AmazonS3 s3client = new AmazonS3Client(credentials);*/

   AmazonS3 s3client= AmazonS3ClientBuilder.standard().withCredentials(new InstanceProfileCredentialsProvider(false)).build();


    @RequestMapping(value="/task/upload/{id}", method=RequestMethod.POST,headers=("content-type=multipart/*"))
    // If not @RestController, uncomment this
    public ResponseEntity<?> uploadFile(@PathVariable("id") String Id,
                                        @RequestParam("file") MultipartFile uploadfile, HttpServletRequest request, HttpServletResponse response) {

        String UID = Id;
        final String auth = request.getHeader("Authorization");
        System.out.println(auth);
        String email = null;
        String password = null;
        if (auth != null && auth.startsWith("Basic")) {
            String credentials = auth.substring("Basic".length()).trim();
            String splitCredentials = new String(java.util.Base64.getDecoder().decode(credentials), Charset.forName("UTF-8"));
            System.out.println(splitCredentials);
            final String values[] = splitCredentials.split(":", 2);
            //int i=0;

            email = values[0];
            password = values[1];

            List<User> li;
            li = (List<User>) userRepository.findByEmail(email);
            boolean flag = false;
            for (User uu : li) {
                System.out.println(uu.getEmail());
                // User uu = (User) itr.next();
                // String ux=uu.setEmail(uu.getEmail());
                System.out.println(uu.getEmail().toString());
                if (uu.getEmail().equalsIgnoreCase(email)) {
                    flag = true;
                    if (BCrypt.checkpw(password, uu.getPassword())) {


                        Iterable<User> lu = userRepository.findAll();

                        Iterator itr1 = lu.iterator();
                        while (itr1.hasNext()) {
                            User u = (User) itr1.next();

                            if (u.getEmail().equalsIgnoreCase(email)) {
                                Iterable<toDoTask> tasks = u.getTasks();

                                Iterator itr = tasks.iterator();


                                while (itr.hasNext()) {

                                    toDoTask task1 = (toDoTask) itr.next();
                                    String sid = task1.getId();
                                    if (sid.equalsIgnoreCase(UID)) {

                                        if (uploadfile.isEmpty()) {
                                            return new ResponseEntity("please select a file!", HttpStatus.OK);
                                        }

                                        try {

                                            UploadFile uff=new UploadFile();
                                            uff.setTask(task1);

                                            uff.setFile(uploadfile.toString());

                                            task1.getFiles().add(uff);
                                            uploadRepository.save(uff);
                                            
                                             String fileName = uploadfile.getOriginalFilename();


                                            byte[] bytes = uploadfile.getBytes();
                                            Path path = Paths.get(fileName);
                                            Files.write(path,bytes);

                                            File f= new File(fileName);
                                            uploadfile.transferTo(f);
                                            s3client.putObject(new PutObjectRequest(bucketName, fileName,f));

/*                                            File path = new File(File.separator+"tmp"+File.separator+"cloud"+File.separator);
                                            File local=new File(path.getAbsoluteFile(),uploadfile.getOriginalFilename().split("\\.")[0]+"."+uploadfile.getOriginalFilename().split("\\.")[1]);
                                            uploadfile.transferTo(local);*/

          }  catch (Exception e) {

                                            System.out.println(e);
                                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                                        }


                                    }
//                                    else {
//                                        response.setStatus(400);
//
//                                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//                                    }
                                }

                            }
                        }
                    }
                }

            }
        }


        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();

            //UploadFile uf=new UploadFile();

            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
//            String dbs=file.getOriginalFilename();
//            //save task id of correspondent user
//            uf.setFile(dbs);
//            uploadRepository.save(uf);


        }

    }

//attachment delete

    @RequestMapping(value = "/task/delete/{id}", method = RequestMethod.DELETE)
    public String deleteFile(@PathVariable("id") String Id,HttpServletRequest request, HttpServletResponse response) {

        String UID = Id;
        // String description = jsonObject.get("Description").toString();

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {

            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));

            final String[] values = credentials.split(":", 2);
//test
            String email = values[0];
            String password = values[1];

            System.out.println(email);
            System.out.println(password);

            Iterable<User> lu = userRepository.findAll();

            Iterator itr1 = lu.iterator();

            while (itr1.hasNext()) {
                User u = (User) itr1.next();

                if(u.getEmail().equalsIgnoreCase(email)){
                    Iterable<toDoTask> tasks = u.getTasks();

                    Iterator itr = tasks.iterator();


                    while (itr.hasNext()) {

                        toDoTask task1 = (toDoTask) itr.next();
                        String sid=task1.getId();

                        if (task1.getId().equalsIgnoreCase(UID)) {
                            Iterable<UploadFile> u1 =task1.getFiles();




                            Iterator itrr = u1.iterator();
                            while (itrr.hasNext()) {
                                //task1.setDescription(description);
                                UploadFile uff=(UploadFile)itrr.next();


                                uploadRepository.delete(uff);
                                task1.getFiles().remove(uff);

                                response.setStatus(200);

                                return "file Id: " + uff.getTask().getId() + " deleted";
                            }
                        }
                    }
                    response.setStatus(400);

                    return "User Not Authorized";

                }
            }
            response.setStatus(400);

            return "No User Found";



        }
        response.setStatus(400);

        return "No User Found";

    }






}
