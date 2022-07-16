package my.edu.utar.mobileappassignment2.fyp1.QuizStatistic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import my.edu.utar.mobileappassignment2.fyp1.R;

public class HygieneStatisticActivity extends AppCompatActivity {
    private static final int MAX_X_VALUE = 7;
    private static final String SET_LABEL = " ";
    private static final String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

    private BarChart chart;
    int y1,y2,y3,y4,y5,y6,y7;
    int[] yAll = new int[7];

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hygiene_statistic);

        //toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chart = findViewById(R.id.fragment_hygienebar);

        Bundle exBundle= getIntent().getExtras();
        y1 = exBundle.getInt("y1");
        y2 = exBundle.getInt("y2");
        y3 = exBundle.getInt("y3");
        y4 = exBundle.getInt("y4");
        y5 = exBundle.getInt("y5");
        y6 = exBundle.getInt("y6");
        y7 = exBundle.getInt("y7");

        yAll[0] = y1;
        yAll[1] = y2;
        yAll[2] = y3;
        yAll[3] = y4;
        yAll[4] = y5;
        yAll[5] = y6;
        yAll[6] = y7;

        BarData data = createChartData(yAll);
        configureChartAppearance();
        prepareChartData(data);


    }

    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);
        axisLeft.setAxisMaximum(100);


        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
        axisRight.setAxisMaximum(100);
    }

    private BarData createChartData(int[] yAll) {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < MAX_X_VALUE; i++) {
            int x = i;
            int y = yAll[i];
            values.add(new BarEntry(x, y));
        }

        BarDataSet set1 = new BarDataSet(values, SET_LABEL);

        set1.setColor(ColorTemplate.JOYFUL_COLORS[3]);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

}