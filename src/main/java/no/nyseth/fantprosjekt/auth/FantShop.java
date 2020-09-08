/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.nyseth.fantprosjekt.auth;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;
import no.nyseth.fantprosjekt.auth.AuthenticationService;
import no.nyseth.fantprosjekt.auth.Group;
import no.nyseth.fantprosjekt.auth.User;
import org.glassfish.jersey.media.multipart.FormDataParam;
import no.nyseth.fantprosjekt.auth.Item;
import no.nyseth.fantprosjekt.auth.MailService;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author nyseth
 */
@Path("shop")
@Stateless
/*@DeclareRoles({Group.USER})*/
@Log
public class FantShop {
    @PersistenceContext
    EntityManager em;
    
    @Inject
    AuthenticationService as;
    
    @GET
    @Path("pingtest")
    public Response shoptest() {
        return Response.ok("sdf").build();
    }
    
    @PUT
    @Path("buy")
    @RolesAllowed({Group.USER})
    public Response buyItem(@QueryParam("itemId") long itemId) {
        Item item =  em.find(Item.class, itemId);
        
        if (item != null) {
            User itemBuyer = as.getCurrentUser();
                   return Response
                    .ok().build();
        }
        return Response
                .ok().build();
    }
    
    @Inject
    JsonWebToken principal;
    
    private User getCurrentUser() {
        return em.find(User.class, principal.getName());
    }
    
    @POST
    @Path("additem")
    @RolesAllowed({Group.USER})
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    public Response addItem(@FormDataParam("itemTitle") String itemTitle, @FormDataParam("itemPrice") BigDecimal itemPrice, @FormDataParam("itemDesc") String itemDesc) {
        log.log(Level.INFO, "attempting to add item", itemTitle);
        User itemSeller = this.getCurrentUser();
        Item itemtbs = new Item();
        itemtbs.setItemTitle(itemTitle);
        itemtbs.setItemPrice(itemPrice);
        itemtbs.setItemDesc(itemDesc);
        itemtbs.setItemSeller(itemSeller);
        em.persist(itemtbs);
        log.log(Level.INFO, "adding items", itemTitle);
        return Response
                .ok()
                .build();
    }
    
    @GET
    @Path("getitems")
    @RolesAllowed({Group.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItem() {
        return em.createNativeQuery("SELECT * FROM ITEM", Item.class).getResultList();
    }
    
    @PUT
    @Path("buyitem")
    @RolesAllowed({Group.USER})
    public Response buyItem(@QueryParam("itemid") Long itemid) {
        log.log(Level.INFO, "checking if item exists", itemid);
        Item itemtbb = em.find(Item.class, itemid);
        if (itemtbb != null) {
            log.log(Level.INFO, "item found, attempting to find buyer", itemid);
            if (itemtbb.getItemBuyer() == null) {
                User itembuyer = this.getCurrentUser();
                itemtbb.setItemBuyer(itembuyer);
                log.log(Level.INFO, "buyer found, sending purchase info to seller", itemid);
                MailService mail = new MailService();
                mail.sendMail(itemtbb.getItemSeller().getEmail(), "your stuff be sold", "your item is b sold");
                return Response
                        .ok().build();
            }
        }
        log.log(Level.INFO, "item not found, try again", itemid);
        return Response
                .notModified().build();
    }
    
    @DELETE
    @Path("removeitem")
    @RolesAllowed({Group.USER})
    public Response removeItem(@QueryParam("itemid") Long itemid) {
        log.log(Level.INFO, "checking if item exists", itemid);
        Item itemtbd = em.find(Item.class, itemid);
        if (itemtbd != null) {
            log.log(Level.INFO, "checking if deleter is item seller", itemid);
            User itemDeleter = this.getCurrentUser();
            /*System.out.println("itemDeleter: " + itemDeleter);
            System.out.println("itemseller: " + itemtbd.getItemSeller());*/
            if (itemtbd.getItemSeller().equals(itemDeleter)) {
                log.log(Level.INFO, "user verified, deleting.", itemid);
                em.remove(itemtbd);
                return Response
                        .ok().build();
            }
        }
        log.log(Level.INFO, "item doesn´t exist", itemid);
        return Response
                .notModified().build();
    }
}
