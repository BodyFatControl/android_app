package comdailyweightfatcontrol.httpsgithub.dailyweightfatcontrol;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import static android.widget.Toast.LENGTH_LONG;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "skulptDB";
    private static String DB_PATH = "/sdcard/Download/skulpt-measurements.db";

    public MySQLiteHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, null, null, DATABASE_VERSION);

        try {
            String [] cmd = {"su", "-c", "cp", "-rf", "/data/data/com.skulpt.aim/databases/Measurements.db", "/sdcard/Download/skulpt-measurements.db"};
            Process process = new ProcessBuilder(cmd).start();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //---------------------------------------------------------------------
    // Books table name
    private static final String TABLE_BOOKS = "books";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_AUTHOR};

    public List<Point> getFatLastMonth() {
        List<Point> points = new ArrayList<Point>();

        // 1. build the query
//        String query = "SELECT fat, datetime WHERE datetime BETWEEN 2016-10* AND 2016-11*";
        String query = "SELECT datetime, fat FROM measurement";

        // 2. get reference to writable DB
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("datetime"));
                String fat = cursor.getString(cursor.getColumnIndex("fat"));

                Log.v("date", date);
                Log.v("fat", fat);


            } while (cursor.moveToNext());
        }
        // return books
        return points;
    }
}