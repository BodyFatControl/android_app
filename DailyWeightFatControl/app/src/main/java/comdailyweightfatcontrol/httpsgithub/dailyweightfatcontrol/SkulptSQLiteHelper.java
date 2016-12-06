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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    private boolean isSameDay (String dateA, String dateB) {
        String a = dateA.substring(0, 10);
        String b = dateB.substring(0, 10);

        return a.equals(b);
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

        // read the data from the database and average all the values on the same day
        float fat_sum = 0;
        String date = null;
        String newDate = null;
        String last_date = null;
        String fat = null;
        int first_loop = 1;
        int loop_counter = 0;
        int i = 0;
        cursor.moveToFirst();
        int counter = cursor.getCount();

        for ( ; counter > 0; ) {
            if (cursor.isAfterLast()) break;
            date = cursor.getString(cursor.getColumnIndex("datetime"));
            fat = cursor.getString(cursor.getColumnIndex("fat"));
            fat_sum = Float.valueOf(fat);
            loop_counter++;

            while (true) {
                cursor.moveToNext();
                if (cursor.isAfterLast()) break;
                newDate = cursor.getString(cursor.getColumnIndex("datetime"));
                if (isSameDay(date, newDate)) {
                    fat_sum += Float.valueOf(cursor.getString(cursor.getColumnIndex("fat")));
                    loop_counter++;
                    date = newDate;
                }
                else {
                    fat = String.valueOf(fat_sum / loop_counter);
                    loop_counter = 0;
                    break;
                }
            }
            data.add(date);
            data.add(fat);
        }

        // return data
        return data;
    }
}