package com.other.app.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.other.app.producer.Producer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

	private static final String[] WORDS = {"apple", "orange", "lemon", "banana"};
	
	private final Producer firstProducer;
	private final Producer secondProducer;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(() -> {
			TimeUnit.SECONDS.sleep(20);
			while(true) {
				int positionProducer1 = (int) (Math.random() * WORDS.length);
				int positionProducer2 = (int) (Math.random() * WORDS.length);
				firstProducer.send("Hello, " + WORDS[positionProducer1] + " from [First Producer]");
				secondProducer.send("Hello, " + WORDS[positionProducer2] + " from [Second Producer]");
				TimeUnit.MILLISECONDS.sleep(50);
			}
		});
	}
}
