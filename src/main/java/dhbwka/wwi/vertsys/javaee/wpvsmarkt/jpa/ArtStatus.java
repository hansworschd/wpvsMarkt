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

/**
 * Statuswerte eines Arikels.
 */
public enum ArtStatus {
    SUCHE, BIETE;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case SUCHE:
                return "Suche";
            case BIETE:
                return "Biete";
            default:
                return this.toString();
        }
    }
}
