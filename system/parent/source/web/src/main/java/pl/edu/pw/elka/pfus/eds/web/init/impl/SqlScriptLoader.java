package pl.edu.pw.elka.pfus.eds.web.init.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.web.init.ScriptLoader;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlScriptLoader implements ScriptLoader {
    private static final Logger logger = Logger.getLogger(SqlScriptLoader.class);

    private static final String SQL_COMMENT = "--";
    private static final String UTF_8_BOM = "\uFEFF";

    private String scriptUri;

    public SqlScriptLoader(String scriptUri) {
        this.scriptUri = scriptUri;
    }

    @Override
    public void execute(Connection connection) throws SQLException {
        String wholeQuery = getWholeQuery();

        Iterable<String> queries = getSplittedQueries(wholeQuery);
        for(String query : queries) {
            executeQuery(connection, query);
        }
    }

    private Iterable<String> getSplittedQueries(String wholeQuery) {
        return Splitter.on(";").trimResults().split(wholeQuery);
    }

    private void executeQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        logger.info("executing startup query: " + query);
        if(!Strings.isNullOrEmpty(query.trim()))
            statement.execute(query);
        statement.close();
    }

    private String getWholeQuery() {
        InputStream sqlStream = SqlScriptLoader.class.getResourceAsStream(scriptUri);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sqlStream));

        String wholeQuery = getWholeQueryFromReader(bufferedReader);
        return wholeQuery;
    }

    private String getWholeQueryFromReader(BufferedReader bufferedReader) {
        String wholeQuery = "";
        try {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                line = line.trim().replace(UTF_8_BOM, "");
                if(isApplicableLine(line))
                    wholeQuery += line + "\n";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wholeQuery;
    }

    private boolean isApplicableLine(String line) {
        return !Strings.isNullOrEmpty(line) && !line.startsWith(SQL_COMMENT);
    }

    private String stripUnreadableFirstChar(String line) {
        char firstChar = line.charAt(0);
        if(!Character.isLetterOrDigit(firstChar) && firstChar != SQL_COMMENT.charAt(0))
            return line.substring(1);
        return line;
    }
}
