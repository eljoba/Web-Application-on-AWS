/**
* Nisarg, 001233379, patel.nisar@husky.neu.edu
* Ishita, 001284764, babel.i@husky.neu.edu
* Elton, 001239637, barbosa.el@husky.neu.edu
* Payal, 001224158, Dodeja.p@husky.neu.edu
**/

package com.csye6225.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

}
