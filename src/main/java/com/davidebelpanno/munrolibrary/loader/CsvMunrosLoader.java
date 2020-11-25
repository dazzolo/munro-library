package com.davidebelpanno.munrolibrary.loader;

import com.davidebelpanno.munrolibrary.model.Munro;
import com.davidebelpanno.munrolibrary.model.Munros;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

public class CsvMunrosLoader implements MunrosLoader {

    @Autowired
    private Munros munros;

    private final static int NAME_POSITION = 6;
    private final static int CATEGORY_POSITION = 27;
    private final static int HEIGHT_POSITION = 10;
    private final static int GRID_REF_POSITION = 14;
    private final static String TOP_CATEGORY = "TOP";
    private final static String MUNRO_CATEGORY = "MUN";

    @Override public Munros loadMunros() {
        try {
            munros = new Munros();
            URL url = getClass().getClassLoader().getResource("./munros.csv");
            String line;
            BufferedReader csvReader = new BufferedReader(new FileReader(url.getFile()));
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null && line.split(",").length > 0) {
                String[] data = line.split(",");

                Munro munro = new Munro(getMunroCategory(data), getMunroInfo(data, NAME_POSITION),
                        Double.parseDouble(getMunroInfo(data, HEIGHT_POSITION)), getMunroInfo(data, GRID_REF_POSITION));

                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(munro);

                System.out.println(json);

                munros.addMunro(munro);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getMunroInfo(String[] munro, int position) {
        if (munro[position] != null && !munro[position].isBlank()) {
            return munro[position];
        } else {
            throw new IllegalArgumentException("CSV file is malformed");
        }
    }

    private String getMunroCategory(String[] munro) {
        try {
            if (MUNRO_CATEGORY.equalsIgnoreCase(munro[CATEGORY_POSITION]) || TOP_CATEGORY.equalsIgnoreCase(munro[CATEGORY_POSITION])) {
                return munro[CATEGORY_POSITION];
            } else {
                return "";
            }
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
