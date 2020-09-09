/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.nyseth.fantprosjekt.auth;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nyseth
 */
@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class ItemImages implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String imageId;
    
    String name;
    
    @ManyToOne
    Item imageItem;
    String mimeType;
    long filesize;
    
    
    
}
