package com.chaddy.mareu.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Model object representing a Reunion
 */
public class Reunion implements Serializable {

    /** Identifier */
    private long id;

    /** Sujet */
    private String sujet;

    /** Horaire */
    private String horaire;

    /** Date */
    private String date;

    /** Lieu / Salle */
    private String salle;

    /** Participants */
    private String participants;






    public Reunion(long id, String sujet, String date, String horaire,
                   String salle, String participants) {
        this.id = id;
        this.sujet = sujet;
        this.date = date;
        this.horaire = horaire;
        this.salle = salle;
        this.participants = participants;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String name) {
        this.salle = salle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reunion neighbour = (Reunion) o;
        return Objects.equals(id, neighbour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
