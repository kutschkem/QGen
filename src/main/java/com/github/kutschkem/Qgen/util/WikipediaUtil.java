// Copyright (c) 2012 Michael Kutschke. All Rights Reserved.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

package com.github.kutschkem.Qgen.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants;

public class WikipediaUtil {

	public static DatabaseConfiguration loadDbConfig() throws IOException,
			FileNotFoundException {
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
