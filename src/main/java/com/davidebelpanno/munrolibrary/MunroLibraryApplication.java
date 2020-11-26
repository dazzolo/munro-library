package com.davidebelpanno.munrolibrary;

import com.davidebelpanno.munrolibrary.loader.CsvMunrosLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MunroLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MunroLibraryApplication.class, args);
		CsvMunrosLoader loader = new CsvMunrosLoader();
		loader.loadMunros();
	}
}
