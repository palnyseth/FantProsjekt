/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.nyseth.fantprosjekt.resources;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import java.sql.SQLException;
import no.nyseth.fantprosjekt.resources.DatasourceProducer;

/**
 *
 * @author nyseth
 */
@ApplicationScoped
public class dbCheck {
    
    @Resource(lookup = DatasourceProducer.JNDI_NAME)
    DataSource ds;
    
    @Resource
    DataSource pgDs;
    
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) throws SQLException {
        DatabaseMetaData md = ds.getConnection().getMetaData();
        System.out.println("---------------------------------------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("DB: " + md.getDatabaseProductName() + "." + md.getDatabaseProductVersion());
        System.out.println("Scheme info " + md.getSchemas());
        System.out.println("Table info: " + md.getTables(null, null, "AUSER", null));
        System.out.println("---------------------------------------------------");
        System.out.println("---------------------------------------------------");
        
    }
    
}
