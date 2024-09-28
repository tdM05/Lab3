package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A minimal example of reading and using the JSON data from resources/sample.json.
 */
public class JSONTranslationExample {

    public static final int CANADA_INDEX = 30;
    private final JSONArray jsonArray;

    // Note: CheckStyle is configured so that we are allowed to omit javadoc for constructors
    public JSONTranslationExample() {
        try {
            // this next line of code reads in a file from the resources folder as a String,
            // which we then create a new JSONArray object from3.
            String jsonString = Files.readString(
                    Paths.get(getClass().getClassLoader().getResource("sample.json").toURI())
            );
            this.jsonArray = new JSONArray(jsonString);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the Spanish translation of Canada.
     * @return the Spanish translation of Canada
     */
    public String getCanadaCountryNameSpanishTranslation() {
        // Instead of using an index (magic number), find Canada by its country code
        return getCountryNameTranslation("CAN", "es");
    }

    /**
     * Returns the name of the country based on the provided country and language codes.
     * @param countryCode the country, as its three-letter code.
     * @param languageCode the language to translate to, as its two-letter code.
     * @return the translation of country to the given language or "Country not found" if there is no translation.
     */
    public String getCountryNameTranslation(String countryCode, String languageCode) {
        // Default message
        String result = "Country not found";

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject country = jsonArray.getJSONObject(i);

            // Check if the "alpha3" field exists and matches the countryCode (case-insensitive)
            if (country.has("alpha3") && country.getString("alpha3").equalsIgnoreCase(countryCode)) {
                // Check if the translation exists for the given language code
                if (country.has(languageCode)) {
                    result = country.getString(languageCode);
                }
                else {
                    result = "Language not found";
                }
                // Exit loop once the country is found
                break;
            }
        }

        // Return the result at the end
        return result;
    }

    /**
     * Prints the Spanish translation of Canada.
     * @param args not used
     */
    public static void main(String[] args) {
        JSONTranslationExample jsonTranslationExample = new JSONTranslationExample();

        System.out.println(jsonTranslationExample.getCanadaCountryNameSpanishTranslation());
        String translation = jsonTranslationExample.getCountryNameTranslation("can", "zh");
        System.out.println(translation);
    }
}
