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
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Category;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.Article;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.ArtStatus;
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.ArtPrice;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite bzw. jede Seite, die eine Liste der Aufgaben
 * zeigt.
 */
@WebServlet(urlPatterns = {"/app/articles/"})
public class ArticleListServlet extends HttpServlet {

    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private TaskBean taskBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", ArtStatus.values());
        request.setAttribute("artPrices", ArtPrice.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Aufgaben suchen
        Category category = null;
        ArtStatus status = null;

        if (searchCategory != null) {
            try {
                category = this.categoryBean.findById(Long.parseLong(searchCategory));
            } catch (NumberFormatException ex) {
                category = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = ArtStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }
        }
        
       
        List<Article> tasks = this.taskBean.search(searchText, category, status);
        request.setAttribute("tasks", tasks);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/article_list.jsp").forward(request, response);
    }
}
