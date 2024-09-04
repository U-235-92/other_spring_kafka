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

	private final Producer producer;
	private String[] words = {"apple", "orange", "lemon", "banana"};	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(() -> {
			while(true) {
				int position = (int) (Math.random() * words.length);
				producer.send("Hello, " + words[position]);
				TimeUnit.MILLISECONDS.sleep(500);
			}
		});
	}
}
