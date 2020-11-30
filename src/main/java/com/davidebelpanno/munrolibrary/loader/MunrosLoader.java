package com.davidebelpanno.munrolibrary.loader;

import static com.davidebelpanno.munrolibrary.utils.Constants.*;

import com.davidebelpanno.munrolibrary.controller.MunroController;
import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.MunroRepository;
import com.davidebelpanno.munrolibrary.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;

@Component
class MunrosLoader implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(MunroController.class);

    @Autowired
    private MunroRepository repository;

    @Value("${app.data.file}")
    private String dataFile;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Setting up database...");
        repository.deleteAll();
        try {
            logger.info("Reading data from file: " + dataFile);
            InputStream inputStream = getClass().getResourceAsStream(dataFile);
            BufferedReader csvReader = new BufferedReader(new InputStreamReader(inputStream));
            csvReader.readLine();
            String line;
            while ((line = csvReader.readLine()) != null && line.split(",").length > 0) {
                String[] data = line.split(",");
                saveMunro(data);
            }
            csvReader.close();
        } catch (Exception e) {
            logger.error("Failed to load the DB: " + e.getMessage());
            throw new Exception("There was a problem while populating the database");
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
        logger.debug("Adding record: " + Arrays.toString(data));
        repository.save(new Munro(getMunroCategory(data), getMunroInfo(data, Constants.NAME_POSITION),
                Double.parseDouble(getMunroInfo(data, HEIGHT_POSITION)), getMunroInfo(data, GRID_REF_POSITION)));
    }
}