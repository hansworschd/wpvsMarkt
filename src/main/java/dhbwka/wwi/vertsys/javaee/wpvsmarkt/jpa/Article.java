/*
 * Copyright © 2018 Lukas Ewald
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Ein Artikel aus dem kleinanzeigensystem
 */
@Entity
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "article_ids")
    @TableGenerator(name = "article_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Category category;

    @Column(name="art")
    private ArtStatus artStatus;
    
    @Column(length = 50)
    @NotNull(message = "Der Titel darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Der Titel muss zwischen ein und 50 Zeichen lang sein.")
    private String title;

    @Lob
    @NotNull
    private String longText;

    @Column
    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date dueDate;

    @Column
    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time dueTime;
    
    @Column
    @NotNull(message="Preis nicht leer")
    private String price;
    
    @Column
    private ArtPrice artPrice;
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Article() {
    }

    public Article(long id, User owner, Category category, String title, String longText, Date dueDate, Time dueTime, String price) {
        this.id = id;
        this.owner = owner;
        this.category = category;
        this.title = title;
        this.longText = longText;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.price = price;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }
    
     public ArtStatus getArtStatus() {
        return artStatus;
    }

    public void setArtStatus(ArtStatus artStatus) {
        this.artStatus = artStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }
    
     public void setPrice(String price) {
        this.price = price;
    }
    
     public ArtPrice getArtPrice() {
        return artPrice;
    }

    public void setArtPrice(ArtPrice artPrice) {
        this.artPrice = artPrice;
    }
     
    //</editor-fold>

    

}
