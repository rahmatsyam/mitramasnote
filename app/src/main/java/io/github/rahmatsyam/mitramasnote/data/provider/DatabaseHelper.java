package io.github.rahmatsyam.mitramasnote.data.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.github.rahmatsyam.mitramasnote.data.model.Note;
import io.github.rahmatsyam.mitramasnote.data.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DBNAME = "db_user";
    private static final String TABLENAME = "user";
    private static final String TABLENOTE = "note";
    private static final String colNama = "nama";
    private static final String colAktivitas = "aktivitas";
    private static final String colDate = "date";


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("CREATE TABLE if not exists user(id INTEGER NOT NULL PRIMARY KEY," +
                "nama Text);"
        );

        db.execSQL("DROP TABLE IF EXISTS note");
        db.execSQL("CREATE TABLE if not exists note(id INTEGER NOT NULL PRIMARY KEY," +
                "aktivitas Text, date Text);"
        );
    }

    public void addNama(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(colNama, user.getNama());

        db.insert(TABLENAME,
                null,
                cv);
        db.close();

    }

    public void addAktivitas(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(colAktivitas, note.getAktivitas());
        cv.put(colDate, note.getDate());

        db.insert(TABLENOTE,
                null,
                cv);
        db.close();

    }

    public List<Note> getAll() {

        List<Note> model = new ArrayList<>();
        model.clear();
        String selectData = "SELECT * FROM note ORDER BY date DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(selectData, null);


        if (data.moveToFirst()) {
            do {
                model.add(new Note(Integer.parseInt(data.getString(data.getColumnIndex("id"))),
                        data.getString(data.getColumnIndex("aktivitas")),
                        data.getString(data.getColumnIndex("date"))));
            } while (data.moveToNext());


            data.close();

        }
        return model;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createTable(db);
    }
}
