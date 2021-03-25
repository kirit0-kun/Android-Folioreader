package com.folioreader.util;

import com.folioreader.model.Bookmark;
import com.folioreader.model.HighLight;
import com.folioreader.model.BookmarkImpl;

/**
 * Interface to convey bookmark events.
 *
 * @author gautam chibde on 26/9/17.
 */

public interface OnBookmarkListener {

    /**
     * This method will be invoked when a bookmark is created, deleted or modified.
     *
     * @param bookmark meta-data for created bookmark {@link BookmarkImpl}.
     * @param type      type of event e.g new,edit or delete {@link BookmarkImpl.BookmarkAction}.
     */
    void onBookmark(Bookmark bookmark, Bookmark.BookmarkAction type);
}
