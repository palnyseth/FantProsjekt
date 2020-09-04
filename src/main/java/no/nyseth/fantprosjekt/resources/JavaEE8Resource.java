package no.nyseth.fantprosjekt.resources;

import javax.enterprise.inject.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("api")
public class JavaEE8Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
    
    @GET
    @Path("pingtest")
    public Response pingtest(){
        return Response
                .ok("pingok")
                .build();
    }
    
    @GET
    @Path("test/{name}/{name2}")
    public String pingtest (@PathParam("name") String name, @PathParam("name2") String name2) {
        if(name.contains("hallo")) {
            return "hallo ja! " + name2;
        } else if(name.contains("hei")) {
            return "hei ja! " + name2;
        } else {
            return "mårn ja! " + name2;
        }
    }
    
    @GET
    @Path("test/db") 
    public String dbtest() {
        return DatasourceProducer.JNDI_NAME;
    }
}
