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
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(() -> {
			sleep(20);
			System.out.println("[START GENERTING MESSAGES]");
			int count = 0;
			while(count++ < 10) {
				int positionProducer1 = (int) (Math.random() * WORDS.length);
				firstProducer.send("Hello, " + WORDS[positionProducer1] + " from [First Producer]");
			}
			System.out.println("[FINISH GENERTING MESSAGES]");
		});
	}
	
	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
