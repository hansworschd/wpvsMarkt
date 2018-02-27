package dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa;

import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Task;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-27T09:52:47")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile SingularAttribute<Category, String> name;
    public static volatile SingularAttribute<Category, Long> id;
    public static volatile ListAttribute<Category, Task> tasks;

}