package com.folioreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * This data structure holds information about an individual highlight.
 *
 * @author mahavir on 5/12/16.
 */

public class BookmarkImpl implements Parcelable, Bookmark {

    public static final String INTENT = BookmarkImpl.class.getName();
    public static final String BROADCAST_EVENT = "bookmark_broadcast_event";

    /**
     * Database id
     */
    private int id;
    /**
     * <p> Book id, which can be provided to intent to folio reader, if not provided id is
     * used from epub's dc:identifier field in metadata.
     * <p>for reference, look here:
     * <a href="http://www.idpf.org/epub/30/spec/epub30-publications.html#sec-package-metadata-identifiers">IDPF</a>.</p>
     * in case identifier is not found in the epub,
     * <a href="https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#hashCode()">hash code</a>
     * of book title is used also if book title is not found then
     * hash code of the book file name is used.
     * </p>
     */
    private String bookId;
    /**
     * Date time when highlight is created (format:- MMM dd, yyyy | HH:mm).
     */
    private Date date;
    /**
     * Field defines the color of the highlight.
     */
    private String name;
    /**
     * Page index in the book taken from Epub spine reference.
     */
    private String location;
    /**
     * Unique identifier for a highlight for sync across devices.
     * <p>for reference, look here:
     * <a href = "https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html#toString()">UUID</a>.</p>
     */
    private String uuid;

    public BookmarkImpl(int id, String bookId, String name, Date date, String location, String uuid) {
        this.id = id;
        this.bookId = bookId;
        this.date = date;
        this.uuid = uuid;
        this.location = location;
        this.name = name;
    }

    public BookmarkImpl() {
    }

    protected BookmarkImpl(Parcel in) {
        readFromParcel(in);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getDate() {
        return date;
    }

    public String getName() { return  name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookmarkImpl bookmarkImpl = (BookmarkImpl) o;

        return id == bookmarkImpl.id
                && (bookId != null ? bookId.equals(bookmarkImpl.bookId) : bookmarkImpl.bookId == null
                && (name != null ? name.equals(bookmarkImpl.name) : bookmarkImpl.name == null
                && (date != null ? date.equals(bookmarkImpl.date) : bookmarkImpl.date == null
                && (location != null ? location.equals(bookmarkImpl.location) : bookmarkImpl.location == null))));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HighlightImpl{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(bookId);
        dest.writeString(name);
        dest.writeString(location);
        dest.writeSerializable(date);
        dest.writeString(uuid);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        bookId = in.readString();
        name = in.readString();
        location = in.readString();
        date = (Date) in.readSerializable();
        uuid = in.readString();
    }

    public static final Creator<BookmarkImpl> CREATOR = new Creator<BookmarkImpl>() {
        @Override
        public BookmarkImpl createFromParcel(Parcel in) {
            return new BookmarkImpl(in);
        }

        @Override
        public BookmarkImpl[] newArray(int size) {
            return new BookmarkImpl[size];
        }
    };
}
