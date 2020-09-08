/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.nyseth.fantprosjekt.auth;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nyseth.fantprosjekt.auth.User;

/**
 *
 * @author nyseth
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String itemTitle;
    private BigDecimal itemPrice;
    private String itemDesc;
    
    @ManyToOne
    private User itemBuyer;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private User itemSeller;
    
    @OneToMany
    private List<ItemImages> itemImage;
    
    
}
