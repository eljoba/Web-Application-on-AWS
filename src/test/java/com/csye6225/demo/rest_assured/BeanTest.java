/**
* Nisarg, 001233379, patel.nisar@husky.neu.edu
* Ishita, 001284764, babel.i@husky.neu.edu
* Elton, 001239637, barbosa.el@husky.neu.edu
* Payal, 001224158, Dodeja.p@husky.neu.edu
**/

package com.csye6225.demo.rest_assured;

import com.csye6225.demo.bean.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.Assert;

public class BeanTest {

    @Test
    public void checkEmail(){
        User u = new User();
        u.setEmail("xyz@gmail.com");
        u.setPassword("asdfg");

        assertEquals(u.getEmail(), "xyz@gmail.com");
    }
}
