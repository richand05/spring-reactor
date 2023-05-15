package com.richrb97.springboot.reactor.app;

import com.richrb97.springboot.reactor.app.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringreactorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringreactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringreactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		useFltaMap();
	}

	public void useFltaMap() throws Exception{

		List<String> names = new ArrayList<>();

		names.add("Andres Ramos");
		names.add("Richard Ataya");
		names.add("Andree Jacas");
		names.add("Richard Benites");

		Flux.fromIterable(names)
				.map(name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
				.flatMap(user ->{
					if(user.getName().equalsIgnoreCase("Richard")){
						return Mono.just(user);
					}else{
						return Mono.empty();
					}
				})
				.map(user -> {
					String name = user.getName().toLowerCase();
					user.setName(name);
					return user;
				})
				.subscribe(e->log.info(e.toString()));
	}
	public void firstExample() throws Exception{
		/*Flux<User> names = Flux.just("Andres Ramos", "Richard Benites", "Andree Jacas","Richard Ataya")
				.map(name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().equals("RICHARD"))
				.doOnNext(user -> {
					if(user == null){
						throw new RuntimeException("Nombres no pueden ser vacíos");
					}
					System.out.println(user.getName().concat(" ").concat(user.getLastname()));
				})
				.map(user -> {
					String name = user.getName().toLowerCase();
					user.setName(name);
					return user;
				});


		names.subscribe(e->log.info(e.toString()),
				error -> log.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						log.info("Ha finalizado la ejecución del observable con éxito");
					}
				}
		);*/

		List<String> names = new ArrayList<>();

		names.add("Andres Ramos");
		names.add("Richard Ataya");
		names.add("Andree Jacas");
		names.add("Richard Benites");

		Flux<User> users= Flux.fromIterable(names)
				.map(name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().equals("RICHARD"))
				.doOnNext(user -> {
					if(user == null){
						throw new RuntimeException("Nombres no pueden ser vacíos");
					}
					System.out.println(user.getName().concat(" ").concat(user.getLastname()));
				})
				.map(user -> {
					String name = user.getName().toLowerCase();
					user.setName(name);
					return user;
				});
		users.subscribe(e->log.info(e.toString()),
				error -> log.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						log.info("Ha finalizado la ejecución del observable con éxito");
					}
				}
		);
	}
}
