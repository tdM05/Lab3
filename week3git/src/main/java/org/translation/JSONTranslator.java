package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements org.translation.Translator {

    // DONE Task: pick appropriate instance variables for this class
    private Map<String, List<String>> countryLanguageMap;
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        countryLanguageMap = new HashMap<>();
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // DONE Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length; i++) {
                JSONObject entry = jsonArray.getJSONObject(i);
                String country = entry.getString("country");
                JSONArray languagesArray = entry.getJSONArray("languages");
                List<String> languages = new ArrayList<>();
                for (int j = 0; j < languagesArray.length(); j++) {
                    languages.add(languagesArray.getString(j));
                }
                countryLanguageMap.put(country, languages);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // DONE Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return countryLanguageMap.getOrDefault(country, new ArrayList<>());
    }

    @Override
    public List<String> getCountries() {
        // DONE Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryLanguageMap.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // DONE Task: complete this method using your instance variables as needed
        List<String> languagesForCountry = countryLanguageMap.get(country);
        if (languagesForCountry != null && languagesForCountry.contains(language)) {
            return "Translation available for " + country + " in " + language;
        } else {
            return "No translation available for " + country + " in " + language;
        }
    }
}