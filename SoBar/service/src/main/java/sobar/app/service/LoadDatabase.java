package sobar.app.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sobar.app.service.model.Business;
import sobar.app.service.model.BusinessAttributes;
import sobar.app.service.repository.BusinessRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BusinessRepository repository) {
        FileInputStream inputStream = null;
        Scanner sc = null;

        Gson gson = new Gson();
        ArrayList<Business> businesses = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();

        try {
        	
        	// Load Categories to match by
        	inputStream = new FileInputStream("data/categories.txt");
            sc = new Scanner(inputStream, "UTF-8");
            
            while (sc.hasNextLine()) {
            	categories.add(sc.nextLine());
            }
            
            sc.close();
        	
            // Load businesses
            inputStream = new FileInputStream("data/business.json");
            sc = new Scanner(inputStream, "UTF-8");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Business b = gson.fromJson(line, Business.class);
                BusinessAttributes attributes = gson.fromJson(line, BusinessAttributes.class);
                b.setAttributes(attributes);
                
                boolean hasAlcohol = false;
                boolean matchesCategory = false;
                
                if (b.getAttribute("Alcohol") != null) {
                	String s = b.getAttribute("Alcohol").getAsString();
                	if (!s.toLowerCase().contains("none")) {
                    	hasAlcohol = true;
                	}
                }
                
                for (String s : categories) {
                	if (b.getCategories() != null) {
                		if (b.getCategories().contains(s)) {
                			matchesCategory = true;
                			break;
                		}
                	}
                }
                
                if (hasAlcohol || matchesCategory) {
                	businesses.add(b);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (sc != null) {
                sc.close();
            }
        }


        return args -> {
            for (Business bar : businesses) {
            	log.info("Preloading " + repository.save(bar));
            }
        };
    }
}