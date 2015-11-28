package perushinkov.swinglib.model;

import java.text.SimpleDateFormat;

/**
 * Serves as the one singleton model in the MVC pattern. It provides access to data to
 * controller and view, and stands between the web-service api and the controllers which
 * the MVCViews hold.
 *
 * NOTE: MVCModel should know nothing about the view.
 *
 * @author eglavchev
 */
public abstract class RootModel {
    // The default date format used throughout the MVC application.
    public final static SimpleDateFormat getApplicationDateFormat() {
        return new SimpleDateFormat("dd.MM.yyyy");
    }
}
