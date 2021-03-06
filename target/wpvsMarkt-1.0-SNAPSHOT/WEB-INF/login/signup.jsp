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

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Registrierung
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/logout/"/>">Einloggen</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="signup_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" placeholder="Hansi94" name="signup_username" value="${signup_form.values["signup_username"][0]}">
                    </div>

                    <label for="signup_password1">
                        Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="signup_password1" value="${signup_form.values["signup_password1"][0]}">
                    </div>

                    <label for="signup_password2">
                        Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="signup_password2" value="${signup_form.values["signup_password2"][0]}">
                    </div>
                    
                     <label for="signup_mail">
                        E-Mail
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input placeholder="bla@blub.de" type="text" name="signup_mail" value="${signup_form.values["signup_mail"][0]}">
                    </div>
                    
                    <label for="signup_name">
                        Name
                    </label>
                    <div class="side-by-side">
                        <input placeholder="Harald Maier" type="text" name="signup_name" value="${signup_form.values["signup_name"][0]}">
                    </div>
                    
                    <label for="signup_street">
                        Strasse
                    </label>
                    <div class="side-by-side">
                        <input placeholder="Meine Strasse 12" type="text" name="signup_street" value="${signup_form.values["signup_street"][0]}">
                    </div>
                    
                     <label for="signup_plz">
                        Anschrift
                    </label>
                    <div class="side-by-side">
                        <input placeholder="12345" type="text" name="signup_plz" value="${signup_form.values["signup_plz"][0]}">
                        <input placeholder="Mannheim" type="text" name="signup_place" value="${signup_form.values["signup_place"][0]}">
                 
                    </div>
                    
                    <label for="signup_phone">
                        Telefon
                    </label>
                    <div class="side-by-side">
                        <input placeholder="+49 1234 56789" type="text" name="signup_phone" value="${signup_form.values["signup_phone"][0]}">
                    </div>
                    
                   

                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Registrieren
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>