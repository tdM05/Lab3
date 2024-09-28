package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private final List<String> jArray;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            this.jArray = new ArrayList<>(lines);
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        int x;
        String languageName = "";

        for (x = 1; x < jArray.size(); x = x + 1) {
            String languageInfo = jArray.get(x);
            String[] languageSplit = languageInfo.split("\t");
            String languageCode = languageSplit[1];

            if (languageCode.equalsIgnoreCase(code)) {
                languageName = languageSplit[0];
            }

        }
        return languageName;
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        int x;
        String languageCode = "";

        for (x = 1; x < jArray.size(); x = x + 1) {
            String languageInfo = jArray.get(x);
            String[] languageSplit = languageInfo.split("\t");
            String languageName = languageSplit[0];

            if (languageName.equalsIgnoreCase(language)) {
                languageCode = languageSplit[1];
            }
        }
        return languageCode;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        return jArray.size() - 1;
    }
}
