package com.folioreader.model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.folioreader.model.Bookmark;
import com.folioreader.model.BookmarkImpl;
import com.folioreader.model.HighLight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static com.folioreader.Constants.DATE_FORMAT;
import static com.folioreader.model.sqlite.HighLightTable.getDateTimeString;

/**
 * @author syed afshan on 24/12/20.
 */

public class BookmarkTable {

    public static final String TABLE_NAME = "bookmark_table";

    public static final String ID = "_id";
    public static final String bookID = "bookID";
    public static final String uuid = "uuid";
    public static final String date = "date";
    public static final String name = "name";
    public static final String readlocator = "readlocator";

    public static SQLiteDatabase Bookmarkdatabase;

    public BookmarkTable(Context context) {
        FolioDatabaseHelper dbHelper = new FolioDatabaseHelper(context);
        Bookmarkdatabase = dbHelper.getWritableDatabase();
    }

    public static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
            + bookID + " TEXT" + ","
            + date + " TEXT" + ","
            + name + " TEXT" + ","
            + uuid + " TEXT" + ","
            + readlocator + " TEXT" + ")";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static ContentValues getBookmarkContentValues(Bookmark bookmark) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(bookID, bookmark.getBookId());
        contentValues.put(name, bookmark.getName());
        contentValues.put(readlocator, bookmark.getLocation());
        contentValues.put(date, getDateTimeString(bookmark.getDate()));
        contentValues.put(uuid, bookmark.getUUID());
        return contentValues;
    }

    public final long insertBookmark(BookmarkImpl bookmarkImpl) {
        bookmarkImpl.setUUID(UUID.randomUUID().toString());
        return DbAdapter.saveBookmark(getBookmarkContentValues(bookmarkImpl));
    }

    public static final ArrayList<BookmarkImpl> getBookmarksForID(String id, Context context) {
        if(Bookmarkdatabase == null){
            FolioDatabaseHelper dbHelper = new FolioDatabaseHelper(context);
            Bookmarkdatabase = dbHelper.getWritableDatabase();
        }
        ArrayList<BookmarkImpl> bookmarks = new ArrayList<>();
        Cursor c = Bookmarkdatabase.rawQuery("SELECT * FROM "
                + TABLE_NAME + " WHERE " + bookID + " = \"" + id + "\"", null);
        while (c.moveToNext()) {
            DateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            String dateString = c.getString(c.getColumnIndex(BookmarkTable.date));
            long noteId = c.getLong(c.getColumnIndex(BookmarkTable.ID));
            String bookId = c.getString(c.getColumnIndex(BookmarkTable.bookID));
            String uuid = c.getString(c.getColumnIndex(BookmarkTable.uuid));
            String name = c.getString(c.getColumnIndex(BookmarkTable.name));
            String locator = c.getString(c.getColumnIndex(BookmarkTable.readlocator));
            Date date = null;
            try {
                date = simpleDateFormat.parse(dateString);
            } catch (Exception ignored) {}
            final BookmarkImpl bookmark = new BookmarkImpl((int)noteId, bookId, name, date, locator, uuid);
            bookmarks.add(bookmark);
        };
        c.close();
        return bookmarks;
    }

    public static final boolean deleteBookmark(String arg_date, String arg_name, Context context) {
        if(Bookmarkdatabase == null){
            FolioDatabaseHelper dbHelper = new FolioDatabaseHelper(context);
            Bookmarkdatabase = dbHelper.getWritableDatabase();
        }
        String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + date + " = \"" + arg_date + "\"" + "AND " + name + " = \"" + arg_name + "\"";
        Cursor c = Bookmarkdatabase.rawQuery(query, null);

        int id = -1;
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex(BookmarkTable.ID));
        }
        c.close();
        return DbAdapter.deleteById(TABLE_NAME, ID, String.valueOf(id));
    }

    public static final boolean deleteBookmarkById(int id, Context context) {
        if(Bookmarkdatabase == null){
            FolioDatabaseHelper dbHelper = new FolioDatabaseHelper(context);
            Bookmarkdatabase = dbHelper.getWritableDatabase();
        }
        return DbAdapter.deleteById(TABLE_NAME, ID, String.valueOf(id));
    }

    public static void saveBookmarkIfNotExists(Bookmark bookmark) {
        String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + uuid + " = \"" + bookmark.getUUID() + "\"";
        int id = DbAdapter.getIdForQuery(query);
        if (id == -1) {
            DbAdapter.saveBookmark(getBookmarkContentValues(bookmark));
        }
    }
}
