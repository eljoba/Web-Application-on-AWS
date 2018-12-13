
/**
 * Nisarg, 001233379, patel.nisar@husky.neu.edu
 * Ishita, 001284764, babel.i@husky.neu.edu
 * Elton, 001239637, barbosa.el@husky.neu.edu
 * Payal, 001224158, Dodeja.p@husky.neu.edu
 **/

package com.csye6225.demo.controllers;

import com.csye6225.demo.bean.User;
import com.csye6225.demo.bean.toDoTask;
import com.csye6225.demo.repository.TaskRepository;
import com.csye6225.demo.repository.UserRepository;
import com.csye6225.demo.services.GenerateID;
import com.google.gson.JsonObject;

//import javafx.beans.property.Property;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.simple.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    HttpServletRequest request;

    HttpServletResponse response;

    @Autowired
    private GenerateID UUID;

    @RequestMapping(value = "/user/login", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String LoginUser(@RequestBody User user) {


        System.out.println(user.getEmail());
        //createHeaders(user.getEmail(),user.getPassword());

        return "user logged in";
    }

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @RequestMapping(value = "/user/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
    @ResponseBody
    public String RegisterUser(@RequestBody User user) {
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());


        Iterable<User> users = userRepository.findAll();
        Iterator ir = users.iterator();


        while (ir.hasNext()) {

            User usr = (User) ir.next();
            if (usr.getEmail().equalsIgnoreCase(user.getEmail())) {
                return "User already exsist";

            }
        }

        User u = new User();
        u.setEmail(user.getEmail());
        System.out.println("password****" + user.getPassword());
        System.out.println("password encrypted****" + bcrypt.encode(user.getPassword()));
        u.setPassword(bcrypt.encode(user.getPassword()));

        userRepository.save(u);

        System.out.println("User Registered !!!!!");
        return "User Registered !!!!!";

    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public Object addTask(@RequestBody JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) {
        final String auth = request.getHeader("Authorization");
        System.out.println(auth);
        String email = null;
        String password = null;
        if (auth != null && auth.startsWith("Basic")) {
            String credentials = auth.substring("Basic".length()).trim();
            String splitCredentials = new String(java.util.Base64.getDecoder().decode(credentials), Charset.forName("UTF-8"));
            System.out.println(splitCredentials);
            final String values[] = splitCredentials.split(":", 2);

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
                        String description = jsonObject.get("Description").toString();

                        if (description.length() < 4097) {

                            String UID = UUID.getID();

                            toDoTask newTask = new toDoTask(UID, description);


                            uu.getTasks().add(newTask);

                            newTask.setUser(uu);

                            taskRepository.save(newTask);

                            response.setStatus(201);


                            return "Task Added " + " Your To Do Task Id is : " + UID;


                        } else {
                            JsonObject jsonObject1 = new JsonObject();
                            jsonObject1.addProperty("Error", "Description should be less than 4097");
                        }

                    } else {
                        JsonObject jo = new JsonObject();
                        jo.addProperty("Error", "Password Incorrect");
                    }
                }
            }
            if (flag = false) {
                JsonObject jo = new JsonObject();
                jo.addProperty("Error", "User does not exist ");

                return jo.toString();
            }

        } else {

            JsonObject jo = new JsonObject();
            jo.addProperty("Error", "You Are Not Logged In");

        }
        System.out.println("FInished");
        return null;
    }

    @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
    public String updateTasks(@RequestBody JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String Id) {

        String UID = Id;
        String description = jsonObject.get("Description").toString();

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {

            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));

            final String[] values = credentials.split(":", 2);

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
//chnage
                    while (itr.hasNext()) {

                        toDoTask task1 = (toDoTask) itr.next();
                        String sid=task1.getId();

                        System.out.println("check id"+sid+" "+task1.getUser().getUserId()+task1.getUser());
                        if (sid.equalsIgnoreCase(UID)) {
                            task1.setDescription(description);

                            taskRepository.save(task1);

                            response.setStatus(200);

                            return "Update to the Task Id: " + task1.getId() + " has been done";
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

    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public String deleteTasks(@PathVariable("id") String Id,HttpServletRequest request, HttpServletResponse response) {

        String UID = Id;
        // String description = jsonObject.get("Description").toString();

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {

            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));

            final String[] values = credentials.split(":", 2);

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
                        if (sid.equalsIgnoreCase(UID)) {

                            //task1.setDescription(description);

                            taskRepository.delete(task1);

                            response.setStatus(200);

                            return "Task Id: " + task1.getId() + " deleted";
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

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)


    @ResponseBody
    public String reset(@RequestBody JSONObject jo, HttpServletResponse response) {


        boolean flag=false;

        System.out.println(jo.toString());
        String userName = jo.get("Username").toString();
        AmazonSNSClient snsClient = new AmazonSNSClient(new DefaultAWSCredentialsProviderChain());
        String top=snsClient.createTopic("csye6225-Topic").getTopicArn();
        snsClient.setRegion(Region.getRegion(Regions.US_EAST_1));

        String topicArn = top;

        Iterable<User> lu = userRepository.findAll();
        Iterator itr = lu.iterator();

        do {

            User u1 = (User) itr.next();

            if (u1.getEmail().equalsIgnoreCase(userName)) {
                flag=true;
                //  return  "Email sent";
            }
        }
        while (itr.hasNext());

        if(flag==true){
            PublishRequest publishRequest = new PublishRequest(topicArn, userName);
            PublishResult publishResult = snsClient.publish(publishRequest);
            System.out.println("MessageId - " + publishResult.getMessageId());}
        response.setStatus(200);
        JsonObject j = new JsonObject();
        j.addProperty("Information", "Reset Link Sent");
        return j.toString();

    }
}
