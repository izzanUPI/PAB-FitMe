package com.example.fitme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    public static final String  fitMeDB = "fitme.db";

    public DataHelper(@Nullable Context context){
        super(context, "fitme.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL ( "create Table allusers(username VARCHAR(100) UNIQUE," +
                "    email VARCHAR(100) UNIQUE NOT NULL," +
                "    password VARCHAR(100) NOT NULL)" );

        sqLiteDatabase.execSQL ( ("create Table user_notes(username VARCHAR(100), exercise_name VARCHAR(100),note TEXT)") );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL ( "drop table if exists allusers" );

    }

    public Boolean insertData(String email, String password, String username){
        SQLiteDatabase fitMeDB = this.getWritableDatabase ();
        ContentValues contentValues = new ContentValues ();
        contentValues.put ( "username", username );
        contentValues.put ( "email", email );
        contentValues.put ( "password", password );
        long result = fitMeDB.insert ( "allusers", null, contentValues );
        if(result== -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase ();
        Cursor cursor = MyDatabase.rawQuery ( "Select * from allusers where email = ?", new String[]{email} );

        if (cursor.getCount () > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase ();
        Cursor cursor = MyDatabase.rawQuery ( "Select * from allusers where username = ? and password = ?", new String[]{username, password} );

        if (cursor.getCount () > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertNote(String username, String exercise_name, String note){
        SQLiteDatabase fitMeDB = this.getWritableDatabase ();
        ContentValues contentValues = new ContentValues ();
        contentValues.put ( "username", username );
        contentValues.put ( "exercise_name", exercise_name );
        contentValues.put ( "note", note );
        long result = fitMeDB.insert ( "user_notes", null, contentValues );
        if(result== -1){
            return false;
        }else{
            return true;
        }
    }

    public String getNoteByUsername(String username) {
        SQLiteDatabase fitMeDB = this.getReadableDatabase();

        String note = null;

        // Define the columns you want to retrieve
        String[] columnsToRetrieve = {"note"};

        // Define the condition for the query
        String selection = "username=?";
        String[] selectionArgs = {username};

        // Perform the query
        Cursor cursor = fitMeDB.query(
                "user_notes",
                columnsToRetrieve,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if there's data returned
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the value from the cursor
            int noteIndex = cursor.getColumnIndex("note");
            note = cursor.getString(noteIndex);

            // Close the cursor when done
            cursor.close();
        }

        return note;
    }

    public boolean updateNote(String username, String exercise_name, String newNote) {
        SQLiteDatabase fitMeDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("note", newNote);

        // Define the condition for the update query
        String selection = "username=? AND exercise_name=?";
        String[] selectionArgs = {username,exercise_name};

        // Perform the update query
        int rowsAffected = fitMeDB.update(
                "user_notes",
                contentValues,
                selection,
                selectionArgs
        );

        return rowsAffected > 0; // Return true if at least one row was affected
    }

    public boolean deleteNote(String username, String exercise) {
        SQLiteDatabase fitMeDB = this.getWritableDatabase();

        // Define the conditions for the delete query
        String whereClause = "username=? AND exercise_name=?";
        String[] whereArgs = {username, exercise};

        // Perform the delete query
        int rowsDeleted = fitMeDB.delete(
                "user_notes",
                whereClause,
                whereArgs
        );

        return rowsDeleted > 0; // Return true if at least one row was deleted
    }

}
