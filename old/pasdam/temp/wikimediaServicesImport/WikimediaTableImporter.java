package com.pasdam.temp.wikimediaServicesImport;

import java.sql.SQLException;
import java.util.StringTokenizer;

import org.jsoup.select.Elements;

import com.miraiCreative.database.DatabaseWrapper;
import com.miraiCreative.html.HtmlTableImport;

public class WikimediaTableImporter extends HtmlTableImport {
	
	// db infos
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_NAME = "wikimedia";
	private static final String DB_HOST = "localhost";
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	
	@Override
	public void exportToDB() throws ClassNotFoundException, SQLException {
		DatabaseWrapper dbWrapper = new DatabaseWrapper(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
		Elements tables = this.document.getElementsByTag("table");
		Elements rows = null, columns = null;
		dbWrapper.execSQL("DROP TABLE IF EXISTS wikimedia;");
		dbWrapper.execSQL("CREATE TABLE wikimedia ("
						+ "wiki VARCHAR(20) PRIMARY KEY, "
				   		+ "lang VARCHAR(30) UNIQUE, "
				   		+ "wikipedia TINYINT(1), "
				   		+ "wiktionary TINYINT(1), "
				   		+ "wikiquote TINYINT(1), "
				   		+ "wikinews TINYINT(1), "
				   		+ "wikibooks TINYINT(1));");
		StringTokenizer tokenizer = null;
		String wiki = null, project = null;
		for (int i = 0; i < tables.size(); i++) {
			rows = tables.get(i).getElementsByTag("tr");
			for (int j = 0; j < rows.size(); j++) {
				columns = rows.get(j).getElementsByTag("td");
				if (!columns.isEmpty()) {
					tokenizer = new StringTokenizer(columns.get(2).getElementsByTag("a").get(0).ownText(), ".");
					if (tokenizer.countTokens() == 2) {
						wiki = tokenizer.nextToken();
						project = tokenizer.nextToken();
						if(columns.get(3).html().isEmpty() 
								|| !(project.equals("wikipedia") 
										|| project.equals("wikiquote") 
										|| project.equals("wiktionary") 
										|| project.equals("wikinews") 
										|| project.equals("wikibooks"))){
							continue;
						}
						dbWrapper.execSQL("INSERT INTO wikimedia (wiki, lang, " + project + ") VALUES ("
				   				+ "'" + wiki + "',"
				   				+ "'" + columns.get(3).getElementsByTag("a").get(0).ownText() + "',"
				   				+ "1) ON DUPLICATE KEY UPDATE " + project + " = 1;");
					}
				}
			}
		}
	}
}
