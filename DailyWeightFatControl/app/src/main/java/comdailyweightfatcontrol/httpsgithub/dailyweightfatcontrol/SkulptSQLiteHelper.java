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
import java.util.Iterator;
import java.util.List;
import java.lang.String;

import static android.widget.Toast.LENGTH_LONG;

public class SkulptSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public SkulptSQLiteHelper(Context context) {
        super(context, null, null, DATABASE_VERSION);

        SkulptPrepareDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SkulptPrepareDatabase() {
        try {
            String[] cmd = {"su", "-c", "cp", "-rf", "/data/data/com.skulpt.aim/databases/Measurements.db", "/sdcard/Download/skulpt-measurements.db"};
            Process process = new ProcessBuilder(cmd).start();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTotalFatLastMonth() {

        ArrayList<String> data = new ArrayList<String>();

        // build the query
        String query = "SELECT datetime, fat FROM measurement WHERE muscle_name = 'total'";

        // open database
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/sdcard/Download/skulpt-measurements.db", null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = db.rawQuery(query, null);

        // read the data from the database
        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(cursor.getColumnIndex("datetime")));
                data.add(cursor.getString(cursor.getColumnIndex("fat")));
            } while (cursor.moveToNext());
        }

        // return data
        return data;
    }
}