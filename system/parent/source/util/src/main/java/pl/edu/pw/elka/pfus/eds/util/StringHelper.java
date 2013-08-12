package pl.edu.pw.elka.pfus.eds.util;

import java.io.File;

public class StringHelper {
    private StringHelper() {

    }

    public static String decorateWithLeadingSlash(String str) {
        if(str.endsWith(File.separator))
            return str;
        return str + File.separator;
    }
}
