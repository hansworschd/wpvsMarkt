/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
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
import dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/user/edit/")
public class UserEditServlet extends HttpServlet {

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
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp");

        //Aktuellen User laden
        User user = this.userBean.getCurrentUser();
        
        //Aktuellen User in Form laden
        request.setAttribute("edit_form", this.createEditForm(user));
        
        dispatcher.forward(request, response);
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
                this.saveUser(request, response);
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
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

       
        String name = request.getParameter("edit_name");
        String plz = request.getParameter("edit_plz");
        String street = request.getParameter("edit_street");
        String place = request.getParameter("edit_place");
        String phone = request.getParameter("edit_phone");
        String mail = request.getParameter("edit_mail");

        User user = this.userBean.getCurrentUser();
        
        //Prüfen ob Mail leer ist
        if (mail != null) {
            user.setMail(mail);
        } else {
            errors.add("Die Mail darf nicht leer sein");
        }

       
        user.setName(name);
        user.setStreet(street);
        user.setPlace(place);
        user.setPlz(plz);
        user.setPhone(phone);
      
        this.validationBean.validate(user, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.userBean.update(user);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/user/edit/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("edit_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
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
   private FormValues createEditForm(User user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("edit_username", new String[]{
            user.getUsername()
        });

        values.put("edit_name", new String[]{
            user.getName()
        });
        values.put("edit_street", new String[]{
            user.getStreet()
        });
        values.put("edit_plz", new String[]{
            user.getPlz()
        });
        values.put("edit_place", new String[]{
            user.getPlace()
        });
        values.put("edit_phone", new String[]{
            user.getPhone()
        });
        
        values.put("edit_mail", new String[]{
            user.getMail()
        });
       
        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}
