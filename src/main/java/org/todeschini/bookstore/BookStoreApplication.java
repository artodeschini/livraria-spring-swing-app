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

		//comentado pois está não é uma app web
		//SpringApplication.run(BookStoreApplication.class, args);
		// permite configurar o spring para que pegar o seu contexto sem ser uma app web
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(BookStoreApplication.class)
				.headless(false)
				.web(WebApplicationType.NONE) // nao é uma aplicacao web
				.run(args);

		// carrega o jframe swing de forma que controle o ciclo de vida da app pelo contexto do spwing
		java.awt.EventQueue.invokeLater(() -> {
			// instancia do objeto swing
			// obtem uma instancia de JFrame atraves de @Component do spring
			LivroForm ui = ctx.getBean(LivroForm.class);
			// faz o frame ser visivel
			ui.setVisible(true);
		});
	}

}
