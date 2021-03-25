package com.folioreader.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.folioreader.model.Bookmark;
import com.folioreader.model.BookmarkImpl;
import com.folioreader.model.HighLight;
import com.folioreader.model.HighlightImpl;
import com.folioreader.model.sqlite.HighLightTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by priyank on 5/12/16.
 */
public class BookmarkUtil {

    private static final String TAG = "BookmarkUtil";

    public static void sendBookmarkBroadcastEvent(Context context,
                                                   BookmarkImpl bookmarkImpl,
                                                   Bookmark.BookmarkAction action) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                getBookmarkBroadcastIntent(bookmarkImpl, action));
    }

    public static Intent getBookmarkBroadcastIntent(BookmarkImpl bookmarkImpl,
                                                    Bookmark.BookmarkAction modify) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BookmarkImpl.INTENT, bookmarkImpl);
        bundle.putSerializable(Bookmark.BookmarkAction.class.getName(), modify);
        return new Intent(BookmarkImpl.BROADCAST_EVENT).putExtras(bundle);
    }
}
