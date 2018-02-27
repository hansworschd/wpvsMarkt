/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.wpvsmarkt.jpa;

/**
 * Statuswerte einer Aufgabe.
 */
public enum ArtPrice {
    VHB, FEST;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case VHB:
                return "VHB";
            case FEST:
                return "fest";
            default:
                return this.toString();
        }
    }
}
