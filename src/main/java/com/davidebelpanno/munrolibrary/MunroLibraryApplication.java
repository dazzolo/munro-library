package com.davidebelpanno.munrolibrary;

import com.davidebelpanno.munrolibrary.loader.CsvMunrosLoader;
import com.davidebelpanno.munrolibrary.loader.MunrosLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MunroLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MunroLibraryApplication.class, args);
		MunrosLoader loader = new CsvMunrosLoader();
		loader.loadMunros();
	}
}
