package pl.edu.pw.elka.pfus.eds.web.servlet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlHelper {
    private static final String EMPTY_URI = "/";

    private static final String VIEW_PART = "/\\w+/\\w+/([0-9a-zA-Z\\.]*)\\??.*";
    private static final Pattern VIEW_PART_PATTERN = Pattern.compile(VIEW_PART);

    public static String getViewName(String requestUri) {
        Matcher matcher = VIEW_PART_PATTERN.matcher(requestUri);
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
