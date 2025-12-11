package com.sonymusic.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@Slf4j
public class MetadataServiceApplication {

	public static void main(String[] args) {
		log.info("Starting Metadata Service");
		SpringApplication.run(MetadataServiceApplication.class, args);
		log.info("Metadata Service started successfully");
	}

}
