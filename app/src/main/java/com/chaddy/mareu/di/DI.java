package com.chaddy.mareu.di;

import com.chaddy.mareu.model.Reunion;
import com.chaddy.mareu.service.ReunionApiService;
import com.chaddy.mareu.service.ReunionApiServiceInterface;

import java.util.List;


/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static ReunionApiService service = new ReunionApiService();



    /**
     * Get an instance on @{@link com.chaddy.mareu.service.ReunionApiServiceInterface}
     * @return
     */
    public static ReunionApiService getReunionApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link ReunionApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static ReunionApiService getNewInstanceApiService() {
        return new ReunionApiService();
    }
}
