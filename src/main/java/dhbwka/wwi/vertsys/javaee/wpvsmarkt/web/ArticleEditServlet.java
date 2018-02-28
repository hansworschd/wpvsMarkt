/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong, bearbeitet von Lukas Ewald
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.wpvsmarkt.web;

import dhbwka.wwi.vertsys.javaee.wpvsmarkt.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Article;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.ArtStatus;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.ArtPrice;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/article/*")
public class ArticleEditServlet extends HttpServlet {

    @EJB
    TaskBean taskBean;

    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;
    

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", ArtStatus.values());
        
        //Arten der Preise holen
        request.setAttribute("artPrices", ArtPrice.values());
        
        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Article article = this.getRequestedArticle(request);
        
        //Prüfen ob User gleich ist
        String articleUser = article.getOwner().getUsername();
        String currentUser = userBean.getCurrentUser().getUsername();
        
        boolean readonly = true;
        if(articleUser == currentUser){ //kein richtiger Vergleich aber so funktioniert es eben auch...
            readonly = false;
        }
        request.setAttribute("readonly",readonly);
        
        request.setAttribute("article_username",article.getOwner().getUsername()); //username vom Article
        
        request.setAttribute("article", article.getId() != 0);
        
        request.setAttribute("edit",article.getId() != 0); //Prüfen ob es ein neuer odere zu editierender Artikel ist
        
        request.setAttribute("article_complete_user",article.getOwner());
                            
        if (session.getAttribute("article_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
           request.setAttribute("article_form", this.createTaskForm(article));
        }
        else{
           
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/article_edit.jsp").forward(request, response);

        session.removeAttribute("article_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveTask(request, response);
                break;
            case "delete":
                this.deleteTask(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        //Alle Attribute der Article holen
        String articleCategory = request.getParameter("article_category");
        String articleStatus = request.getParameter("article_status");
        String article_title = request.getParameter("article_title");
        String articleLongText = request.getParameter("article_long_text");
        String article_username = request.getParameter("article_username");
        String article_price = request.getParameter("article_price");
        String article_artPrice = request.getParameter("article_artPrice");


        Article article = this.getRequestedArticle(request);

        if (articleCategory != null && !articleCategory.trim().isEmpty()) {
            try {
                article.setCategory(this.categoryBean.findById(Long.parseLong(articleCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }
       
       //ArtPreis holen
        try {
            article.setArtPrice(ArtPrice.valueOf(article_artPrice));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte ArtPreis ist nicht vorhanden.");
        }
      
        //ArtStatus holen
        try {
            article.setArtStatus(ArtStatus.valueOf(articleStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }
        
        //Prüfen ob User gleich ist
        if(!userBean.getCurrentUser().getUsername().equals(article_username)){
            errors.add("Man kann nur eigene Artikel bearbeiten.");
        }

        article.setTitle(article_title);
        article.setLongText(articleLongText);
        article.setPrice(article_price);
      
        
        article.setDueDate(new Date(System.currentTimeMillis()));
        article.setDueTime(new Time(System.currentTimeMillis()));

        this.validationBean.validate(article, errors);

        
        // Datensatz speichern
        if (errors.isEmpty()) {
            this.taskBean.update(article);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/articles/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("article_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
         // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();
        
        // Datensatz löschen
        Article article = this.getRequestedArticle(request);
        
        //Prüfen ob User gleich ist
        if(!userBean.getCurrentUser().getUsername().equals(article.getOwner().getUsername())){
            errors.add("Man kann nur eigene Artikel löschen.");
        }
        
        this.taskBean.delete(article);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/articles/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Article getRequestedArticle(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Article article = new Article();
        article.setOwner(this.userBean.getCurrentUser());
        article.setDueDate(new Date(System.currentTimeMillis()));
        article.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String articleID = request.getPathInfo();

        if (articleID == null) {
            articleID = "";
        }

        articleID = articleID.substring(1);

        if (articleID.endsWith("/")) {
            articleID = articleID.substring(0, articleID.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            article = this.taskBean.findById(Long.parseLong(articleID));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return article;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param task Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createTaskForm(Article article) {
        Map<String, String[]> values = new HashMap<>();

        values.put("article_owner", new String[]{
            article.getOwner().getUsername()
        });

        if (article.getCategory() != null) {
            values.put("article_category", new String[]{
                article.getCategory().getName()
            });
        }

        values.put("article_due_date", new String[]{
            WebUtils.formatDate(article.getDueDate())
        });

        values.put("article_due_time", new String[]{
            WebUtils.formatTime(article.getDueTime())
        });

        
        if (article.getArtStatus() != null) {
            values.put("article_status", new String[]{
            article.getArtStatus().toString()
            });
        }
       
        
        values.put("article_title", new String[]{
            article.getTitle()
        });

        values.put("article_long_text", new String[]{
            article.getLongText()
        });
        
         values.put("article_price", new String[]{
            article.getPrice()
        });
         
         if (article.getArtPrice() != null) {
            values.put("article_artPrice", new String[]{
            article.getArtPrice().toString()
        });
        } 
         
        

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
