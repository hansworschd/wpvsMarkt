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
        User editieren
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
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

                <%-- Eingabefelder --%>
                <label for="edit_username">Username</label>
                <div class="side-by-side">
                    <input type="text" name="edit_username" value="${edit_form.values["edit_username"][0]}" readonly="readonly" disabled="true">
                </div>
                
                <label for="edit_mail">E-Mail
                    <span class="required">*</span>
                </label>
                
                <div class="side-by-side">
                    <input type="text" name="edit_mail" value="${edit_form.values["edit_mail"][0]}" placeholder="bla@blubb.de">
                </div>

                <label for="edit_name">Name</label>
                <div class="side-by-side">
                    <input type="text" name="edit_name" value="${edit_form.values["edit_name"][0]}" placeholder="Vorname Nachname">
                </div>
                
                <label for="edit_street">Strasse</label>
                <div class="side-by-side">
                    <input type="text" name="edit_street" value="${edit_form.values["edit_street"][0]}" placeholder="Meine Str. 10">
                </div>

                <label for="edit_plz">PLZ und Ort</label>
                <div class="side-by-side">
                    <input type="text" name="edit_plz" value="${edit_form.values["edit_plz"][0]}" placeholder="12345">
                    <input type="text" name="edit_place" value="${edit_form.values["edit_place"][0]}" placeholder="Mannheim">
                </div>
                
                 <label for="edit_phone">Telefon</label>
                <div class="side-by-side">
                    <input type="text" name="edit_phone" value="${edit_form.values["edit_phone"][0]}" >
                </div>
                
                
                
                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>   
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty edit_form.errors}">
                <ul class="errors">
                    <c:forEach items="${edit_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>