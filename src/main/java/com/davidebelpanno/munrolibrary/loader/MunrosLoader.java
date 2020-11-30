package com.davidebelpanno.munrolibrary.loader;

import com.davidebelpanno.munrolibrary.controller.MunroController;
import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

@Component
class MunrosLoader implements CommandLineRunner {

    org.slf4j.Logger logger = LoggerFactory.getLogger(MunroController.class);

    @Autowired
    private MunroRepository repository;

    @Value("${app.data.file}")
    private String dataFile;

    final static int NAME_POSITION = 6;
    final static int CATEGORY_POSITION = 27;
    final static int HEIGHT_POSITION = 10;
    final static int GRID_REF_POSITION = 14;
    final static String TOP_CATEGORY = "TOP";
    final static String MUNRO_CATEGORY = "MUN";

    @Override
    public void run(String... args) {
        logger.info("Setting up database...");
        repository.deleteAll();
        try {
            URL url = getClass().getClassLoader().getResource(dataFile);
            logger.info("Reading data from file: " + url.toString());
            BufferedReader csvReader = new BufferedReader(new FileReader(url.getFile()));
            csvReader.readLine();
            String line;
            while ((line = csvReader.readLine()) != null && line.split(",").length > 0) {
                String[] data = line.split(",");
                saveMunro(data);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getMunroInfo(String[] munros, int position) {
        if (munros[position] != null && !munros[position].isBlank()) {
            return munros[position];
        } else {
            logger.error("ERROR: CSV file is malformed");
            throw new IllegalArgumentException("CSV file is malformed");
        }
    }

    String getMunroCategory(String[] munro) {
        try {
            if (MUNRO_CATEGORY.equalsIgnoreCase(munro[CATEGORY_POSITION]) || TOP_CATEGORY
                    .equalsIgnoreCase(munro[CATEGORY_POSITION])) {
                return munro[CATEGORY_POSITION].toUpperCase();
            } else {
                return "";
            }
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    private void saveMunro(String[] data) {
        logger.debug("Adding record: " + data);
        repository.save(new Munro(getMunroCategory(data), getMunroInfo(data, NAME_POSITION),
                Double.parseDouble(getMunroInfo(data, HEIGHT_POSITION)), getMunroInfo(data, GRID_REF_POSITION)));
    }
}