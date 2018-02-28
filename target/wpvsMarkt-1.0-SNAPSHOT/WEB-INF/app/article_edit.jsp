<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Artikel editieren
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/article_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/articles/"/>">Artikelliste &#128458;</a>
        </div>
    </jsp:attribute>
        
       

    <jsp:attribute name="content">
        <form method="post" class="stacked myForm">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
               
                <input type="hidden" name="article_username" value="${article_username}">
              
                <label for="article_category">Kategorie:</label>
                <div class="side-by-side">
     
                    <select name="article_category" ${readonly ? 'disabled="true"' : ''}>
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${article_form.values["article_category"][0] == category.name ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <label for="article_status">
                    Art des Angebots:
                </label>
                <div class="side-by-side margin">
                    <select name="article_status" ${readonly ? 'disabled="true"' : ''}>
                        <c:forEach items="${statuses}" var="artStatus">
                            <option value="${artStatus}" ${article_form.values["article_status"][0] == artStatus ? 'selected' : ''}>
                                <c:out value="${artStatus.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="article_title">
                    Titel
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input placeholder="Mein Titel" ${readonly ? 'disabled="true"' : ''} type="text" name="article_title" value="${article_form.values["article_title"][0]}">
                </div>
                
                <label for="article_long_text">
                    Beschreibung
                </label>
                <div class="side-by-side">
                    <textarea placeholder="Das Produkt ist x Breit und y hoch. Ein echtes Muss!" ${readonly ? 'disabled="true"' : ''} name="article_long_text"><c:out value="${article_form.values['article_long_text'][0]}"/></textarea>
                </div>
                
                <label>
                    Preis
                </label>
                <div class="side-by-side margin">
                    <input placeholder="123.45" ${readonly ? 'disabled="true"' : ''} type="text" name="article_price" value="${article_form.values["article_price"][0]}">
                    <select name="article_artPrice" ${readonly ? 'disabled="true"' : ''}>
                        <c:forEach items="${artPrices}" var="artPrice">
                            
                            <option value="${artPrice}" ${article_form.values["article_artPrice"][0] == artPrice ? 'selected' : ''}>
                                <c:out value="${artPrice.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                        <div id="myInfoDiv">
                            <strong>Eingestellt am:</strong> ${article_form.values["article_due_date"][0]}<br>
                            <br> <strong>Verkäufer</strong><br>
                            ${article_complete_user.name}<br>
                            ${article_complete_user.street} <br>
                            ${article_complete_user.plz} ${article_complete_user.place}<br>
                            ${article_complete_user.phone}<br>
                            ${article_complete_user.mail}            
                        </div>
               
                

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save" ${readonly ? 'disabled="true"' : ''}>
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete" ${readonly ? 'disabled="true"' : ''}>
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty article_form.errors}">
                <ul class="errors">
                    <c:forEach items="${article_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>