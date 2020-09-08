package no.nyseth.fantprosjekt.auth;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import no.nyseth.fantprosjekt.auth.ItemImages;
import no.nyseth.fantprosjekt.auth.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-08T20:27:29")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, User> itemSeller;
    public static volatile SingularAttribute<Item, String> itemTitle;
    public static volatile SingularAttribute<Item, User> itemBuyer;
    public static volatile SingularAttribute<Item, BigDecimal> itemPrice;
    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, String> itemDesc;
    public static volatile ListAttribute<Item, ItemImages> itemImage;

}