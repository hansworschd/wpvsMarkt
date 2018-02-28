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
 * Statuswerte eines Preises.
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
                return "Festpreis";
            default:
                return this.toString();
        }
    }
}
