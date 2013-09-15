package pl.edu.pw.elka.pfus.eds.web.rest;

import javax.ws.rs.core.Response;

public class Rest {
    public static String rest(String url) {
        return "/rest" + url;
    }

    public static Response responseWithContent(String content) {
        return Response.status(Response.Status.OK).entity(content).build();
    }
}
