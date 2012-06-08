package com.github.kutschkem.Qgen.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants;

public class WikipediaUtil {

    public static DatabaseConfiguration loadDbConfig() throws IOException, FileNotFoundException {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("conf/connection.properties"));

        DatabaseConfiguration dbConfig = new DatabaseConfiguration();
        dbConfig.setHost(properties.getProperty("wikipedia.host"));
        dbConfig.setDatabase(properties.getProperty("wikipedia.db"));
        dbConfig.setUser(properties.getProperty("wikipedia.user"));
        dbConfig.setPassword(properties.getProperty("wikipedia.password"));
        dbConfig.setLanguage(WikiConstants.Language.valueOf(WikiConstants.Language.english.toString()));
        return dbConfig;
    }

}
