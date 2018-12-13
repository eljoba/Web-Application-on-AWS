/**
 * Nisarg, 001233379, patel.nisar@husky.neu.edu
 * Ishita, 001284764, babel.i@husky.neu.edu
 * Elton, 001239637, barbosa.el@husky.neu.edu
 * Payal, 001224158, Dodeja.p@husky.neu.edu
 **/

package com.csye6225.demo.rest_assured;

import com.csye6225.demo.bean.User;
import com.csye6225.demo.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

public class RestApiTest {

    @Autowired
    private UserRepository userRepository;

    @Ignore
    public void testGetHomePage() throws URISyntaxException {
        RestAssured.when().get(new URI("http://localhost:8080/")).then().statusCode(200);
    }

 /*   @Test
    public void checkAnyUserAuth(){

*//*        String email;
        String password;
        Iterable<User> users = userRepository.findAll();
        Iterator ir = users.iterator();
        do{
            User usr = (User) ir.next();
             email = usr.getEmail();
            password = usr.getPassword();
        }`
        while (1==0);*//*

        final String uri = "http://localhost:8080/";
        final RequestSpecification basicauth = RestAssured.given().auth().preemptive().basic("elton","elton");
        final Response response = basicauth.accept(ContentType.JSON).get(uri);

        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(200));

    }*/
}
