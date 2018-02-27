package dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa;

import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.ArtPrice;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.ArtStatus;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Category;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.User;
import java.sql.Date;
import java.sql.Time;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-27T14:38:15")
@StaticMetamodel(Article.class)
public class Article_ { 

    public static volatile SingularAttribute<Article, User> owner;
    public static volatile SingularAttribute<Article, ArtPrice> artPrice;
    public static volatile SingularAttribute<Article, String> longText;
    public static volatile SingularAttribute<Article, String> price;
    public static volatile SingularAttribute<Article, Date> dueDate;
    public static volatile SingularAttribute<Article, Long> id;
    public static volatile SingularAttribute<Article, Category> category;
    public static volatile SingularAttribute<Article, ArtStatus> artStatus;
    public static volatile SingularAttribute<Article, String> title;
    public static volatile SingularAttribute<Article, Time> dueTime;

}