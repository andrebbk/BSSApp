package com.example.bssapp.ui.statistic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassStudentItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.UtilsClass;
import com.example.bssapp.commons.DaysOfWeekValues;
import com.example.bssapp.databinding.FragmentStatisticBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.ClassStudentItem;
import com.example.bssapp.db.models.StudentItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticFragment extends Fragment {

    private FragmentStatisticBinding binding;

    private BarChart chart;

    private DaoSession daoSession;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();

        LoadControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View view){
        chart = view.findViewById(R.id.chartAdults);

        chart.getDescription().setEnabled(false);

        chart.setScaleEnabled(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setTextColor(R.color.purple_700);

        // add a nice and smooth animation
        chart.animateY(1500);
        chart.setTouchEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setTextColor(R.color.purple_700);

        chart.setSelected(false);
        chart.setClickable(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter();
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        LoadGraphData();
    }

    private void LoadGraphData(){
        ArrayList<BarEntry> values = new ArrayList<>();

        int dayCounter = 0;
        for (DaysOfWeekValues day : DaysOfWeekValues.values()) {
            values.add(new BarEntry(dayCounter, getStudentsCount(day, true)));
            dayCounter++;
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.rgb("#FF3700B3"));
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chart.setData(data);
            chart.setFitBars(true);
        }

        chart.invalidate();
    }

    private Long getStudentsCount(DaysOfWeekValues dayOfWeek, boolean isAdult){
        QueryBuilder<StudentItem> qb = daoSession.getStudentItemDao().queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false), StudentItemDao.Properties.IsAdult.eq(isAdult));
        Join cSJoin = qb.join(ClassStudentItem.class, ClassStudentItemDao.Properties.StudentId);
        Join cJoin = qb.join(cSJoin, ClassStudentItemDao.Properties.ClassId, ClassItem.class, ClassItemDao.Properties.ClassId)
                .where(ClassItemDao.Properties.Deleted.eq(false),ClassItemDao.Properties.ClassDayOfWeek.eq(UtilsClass.GetDayOfWeekValue(dayOfWeek)));

        return qb.count();
    }

    public static class DayAxisValueFormatter extends ValueFormatter {
        public DayAxisValueFormatter() {
        }
        @Override
        public String getFormattedValue(float value) {
            if (value == 0.0) {
                return "Segunda";
            }
            else  if (value == 1.0) {
                return "Terça";
            }
            else  if (value == 2.0) {
                return "Quarta";
            }
            else  if (value == 3.0) {
                return "Quinta";
            }
            else  if (value == 4.0) {
                return "Sexta";
            }
            else  if (value == 5.0) {
                return "Sábado";
            }
            else  if (value == 6.0) {
                return "Domingo";
            }

            return "";
        }
    }
}

