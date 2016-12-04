package comdailyweightfatcontrol.httpsgithub.dailyweightfatcontrol;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import static android.R.attr.duration;
import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySQLiteHelper sql = new MySQLiteHelper(this);
        List<Point> p = sql.getFatLastMonth();

        String abc = p.get(0).toString();
        Toast.makeText(this, abc, LENGTH_LONG).show();



        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);

//        int[][] dataObjects = {
//            {0, 5},
//            {1, 12},
//            {2, 10},
//            {3, 4},
//            {4, 7}
//        };

        ArrayList<ArrayList<Integer>> dataObjects;
        dataObjects = new ArrayList<ArrayList<Integer>>();
        dataObjects.add(new ArrayList<Integer>());
        dataObjects.add(new ArrayList<Integer>());
        dataObjects.add(new ArrayList<Integer>());
        dataObjects.add(new ArrayList<Integer>());
        dataObjects.get(0).add(1);
        dataObjects.get(0).add(10);
        dataObjects.get(1).add(2);
        dataObjects.get(1).add(12);
        dataObjects.get(2).add(3);
        dataObjects.get(2).add(6);
        dataObjects.get(3).add(4);
        dataObjects.get(3).add(18);

        List<Entry> entries = new ArrayList<Entry>();

        for (ArrayList<Integer> i : dataObjects) {

            // turn your data into Entry objects
            entries.add(new Entry(i.get(0),i.get(1)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label xxx"); // add entries to dataset
        dataSet.setColor(Color.rgb(240, 238, 70));
        //dataSet.setValueTextColor(...); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

        finish();
    }
}
