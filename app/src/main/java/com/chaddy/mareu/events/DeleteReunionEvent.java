package com.chaddy.mareu.events;

import com.chaddy.mareu.model.Reunion;


/**
 * Event fired when a user deletes a Reunion
 */
public class DeleteReunionEvent {

    /**
     * Reunion to delete
     */
    public Reunion reunion;

    /**
     * Constructor.
     *
     * @param reunion
     */
    public DeleteReunionEvent(Reunion reunion) {
        this.reunion = reunion;
    }
}
