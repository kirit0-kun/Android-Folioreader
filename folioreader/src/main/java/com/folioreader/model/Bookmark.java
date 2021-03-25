package com.folioreader.model;

import java.util.Date;

/**
 * Interface to access Bookmark data.
 *
 * @author gautam chibde on 9/10/17.
 */

public interface Bookmark {

    /**
     * Bookmark action
     */
    enum BookmarkAction {
        NEW, DELETE, MODIFY
    }

    String getBookId();

    /**
     * Returns Date time when highlight is created (format:- MMM dd, yyyy | HH:mm).
     */
    Date getDate();

    /**
     * Returns Field defines the color of the highlight.
     */
    String getName();

    /**
     * Returns href of the page from the Epub spine list.
     */
    String getLocation();

    /**
     * returns Unique identifier.
     */
    String getUUID();
}
