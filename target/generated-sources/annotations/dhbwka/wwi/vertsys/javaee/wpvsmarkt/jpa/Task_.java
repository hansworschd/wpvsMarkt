package dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa;

import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Category;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.TaskStatus;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.User;
import java.sql.Date;
import java.sql.Time;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-27T09:52:47")
@StaticMetamodel(Task.class)
public class Task_ { 

    public static volatile SingularAttribute<Task, User> owner;
    public static volatile SingularAttribute<Task, String> longText;
    public static volatile SingularAttribute<Task, String> shortText;
    public static volatile SingularAttribute<Task, Date> dueDate;
    public static volatile SingularAttribute<Task, Long> id;
    public static volatile SingularAttribute<Task, Category> category;
    public static volatile SingularAttribute<Task, Time> dueTime;
    public static volatile SingularAttribute<Task, TaskStatus> status;

}