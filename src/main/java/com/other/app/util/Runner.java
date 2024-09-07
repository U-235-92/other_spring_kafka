package com.other.app.util;

import java.util.UUID;
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
	
	private final Producer producer;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.submit(() -> {
			sleep(5);
			System.out.println("[START GENERTING MESSAGES]");
			int count = 0;
			while(count++ < 10) {
				producer.send("Hello, " + UUID.randomUUID().toString().substring(0, 5));
			}
			System.out.println("[FINISH GENERTING MESSAGES]");
		});
	}
	
	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
