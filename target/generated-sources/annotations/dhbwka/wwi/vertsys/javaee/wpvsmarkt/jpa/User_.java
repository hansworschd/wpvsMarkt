package dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa;

import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Article;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-27T14:38:15")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> mail;
    public static volatile SingularAttribute<User, String> phone;
    public static volatile SingularAttribute<User, String> street;
    public static volatile SingularAttribute<User, String> name;
    public static volatile ListAttribute<User, String> groups;
    public static volatile SingularAttribute<User, String> place;
    public static volatile SingularAttribute<User, String> passwordHash;
    public static volatile ListAttribute<User, Article> tasks;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> plz;

}