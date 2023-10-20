package org.todeschini.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.todeschini.bookstore.form.LivroForm;

@SpringBootApplication
public class BookStoreApplication {

	public static void main(String[] args) {

		//SpringApplication.run(BookStoreApplication.class, args);
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(BookStoreApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE) // nao é uma aplicacao web
				.run(args);

		java.awt.EventQueue.invokeLater(() -> {
			// instancia do objeto swing
			// obtem uma instancia de JFrame atraves de @Component do spring
			LivroForm ui = ctx.getBean(LivroForm.class);
			// faz o frame ser visivel
			ui.setVisible(true);
		});
	}

}
