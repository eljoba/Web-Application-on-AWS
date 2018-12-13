/**
* Nisarg, 001233379, patel.nisar@husky.neu.edu
* Ishita, 001284764, babel.i@husky.neu.edu
* Elton, 001239637, barbosa.el@husky.neu.edu
* Payal, 001224158, Dodeja.p@husky.neu.edu
**/

package com.csye6225.demo.controllers;


import com.csye6225.demo.bean.User;
import com.csye6225.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {

    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String welcome(HttpServletRequest request) {
        final String auth = request.getHeader("Authorization");
        System.out.println(auth);
        String email=null;
        String password=null;
        if (auth != null && auth.startsWith("Basic")) {
            String credentials = auth.substring("Basic".length()).trim();
            String splitCredentials = new String(Base64.getDecoder().decode(credentials), Charset.forName("UTF-8"));
            System.out.println(splitCredentials);
            final String values[] = splitCredentials.split(":", 2);

             email = values[0];
            password = values[1];

            System.out.println("email**************" + email);
            System.out.println("password**************" + password);

            List<User> li;
            li = (List<User>) userRepository.findByEmail(email);
//testing

            //Iterable<User> u = userRepository.findAll();
//test application
            //Iterator itr = u.iterator();

            for (User uu : li) {
                System.out.println(uu.getEmail());
                // User uu = (User) itr.next();
                // String ux=uu.setEmail(uu.getEmail());
                System.out.println(uu.getEmail().toString());
                if (uu.getEmail().equalsIgnoreCase(email)) {
                    if (BCrypt.checkpw(password, uu.getPassword())) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("Hello" + uu.getPassword(), "you are logged in ...");
                        jsonObject.addProperty("log in time", new Date().toString());
                        System.out.println(jsonObject.toString());
                        return jsonObject.toString();

                    } else {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("invalid", "password typed incorrectly");
                        return jsonObject.toString();
                    }
                } else {

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("invalid", "email typed incorrectly");
                    return jsonObject.toString();


                }
            }
        }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error"+" "+email, "user does not exist");
            return jsonObject.toString();

    }
}


//
//    JsonObject jsonObject = new JsonObject();
//    jsonObject.addProperty("message", "Hello CSYE 6225!!!");
//    jsonObject.addProperty("Registration","/user/register/{emailID}/{password}");

// return jsonObject.toString();





