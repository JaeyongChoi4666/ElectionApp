package com.example.cproviderapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersonProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.cproviderapp";
    private static final String BASE_PATH = "person";
    private static final Uri CONTENT_URI= Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);

    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;

    private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase database;

    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,PERSONS);
        uriMatcher.addURI(AUTHORITY,BASE_PATH+"/#",PERSON_ID);
    }

    public PersonProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count=0;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                count=database.delete(ProviderHelper.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 Uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                return "vnd.android.cursor.dir/persons";
            default:
                throw new IllegalArgumentException("알 수 없는 Uri" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long id=database.insert(ProviderHelper.TABLE_NAME,null,values);
        if(id>0){
            Uri _uri= ContentUris.withAppendedId(CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new SQLException("추가 실패 -> URI:"+uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        ProviderHelper helper=new ProviderHelper(getContext());
        database=helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                cursor=database.query(ProviderHelper.TABLE_NAME,ProviderHelper.ALL_COLUMNS,selection,null,null,null,ProviderHelper.PERSON_NAME+" ASC");
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 Uri"+uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int count=0;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                count=database.update(ProviderHelper.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 Uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}