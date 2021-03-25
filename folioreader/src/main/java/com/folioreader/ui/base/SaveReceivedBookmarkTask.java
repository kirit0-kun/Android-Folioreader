package com.folioreader.ui.base;

import android.os.AsyncTask;

import com.folioreader.model.Bookmark;
import com.folioreader.model.HighLight;
import com.folioreader.model.sqlite.BookmarkTable;
import com.folioreader.model.sqlite.HighLightTable;

import java.util.List;

/**
 * Background task to save received highlights.
 * <p>
 * Created by gautam on 10/10/17.
 */
public class SaveReceivedBookmarkTask extends AsyncTask<Void, Void, Void> {

    private OnSaveBookmark onSaveBookmark;
    private List<Bookmark> bookmarks;

    public SaveReceivedBookmarkTask(OnSaveBookmark onSaveBookmark,
                                    List<Bookmark> bookmarks) {
        this.onSaveBookmark = onSaveBookmark;
        this.bookmarks = bookmarks;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (Bookmark bookmark : bookmarks) {
            BookmarkTable.saveBookmarkIfNotExists(bookmark);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onSaveBookmark.onFinished();
    }
}
