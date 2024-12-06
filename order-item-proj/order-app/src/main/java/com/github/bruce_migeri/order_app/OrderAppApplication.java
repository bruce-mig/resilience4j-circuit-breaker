package com.github.bruce_migeri.order_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@SpringBootApplication
public class OrderAppApplication {

	public static void main(String[] args) {
		// Bulkhead
		/*SpringApplication.run(OrderAppApplication.class, args);
		int i=1;
		IntStream.rangeClosed(i, 10).parallel().forEach(t -> {
			String response = new RestTemplate().getForObject("http://localhost:8080/bulk-head", String.class);
		});*/
	}

}
