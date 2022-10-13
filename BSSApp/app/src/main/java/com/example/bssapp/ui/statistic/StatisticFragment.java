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
import com.example.bssapp.SportItemDao;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.UtilsClass;
import com.example.bssapp.commons.DaysOfWeekValues;
import com.example.bssapp.databinding.FragmentStatisticBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.ClassStudentItem;
import com.example.bssapp.db.models.SportItem;
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
import java.util.Date;
import java.util.List;

public class StatisticFragment extends Fragment {

    private FragmentStatisticBinding binding;

    private BarChart chart;
    private BarChart chartChildren;
    private Date lastTwoMonthsDate;
    private long totalAdultStudents = 0, totalChildrenStudents = 0;
    private DaoSession daoSession;
    private Long surfSportId = Long.valueOf(0);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();

        //Load surfId
        List<SportItem> surfSport = daoSession.getSportItemDao().queryBuilder()
                .where(SportItemDao.Properties.Deleted.eq(false),
                        SportItemDao.Properties.SportName.eq("Surf"))
                .orderAsc(SportItemDao.Properties.CreateDate)
                .limit(1)
                .list();

        if(surfSport != null && !surfSport.isEmpty()){
            SportItem surfItem = surfSport.get(0);
            if(surfItem != null){
                surfSportId = surfItem.getSportId();
            }
        }

        LoadParameters();

        LoadAdultControllers(root);
        LoadChildrenControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadParameters(){
        //until last 2 months
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.MONTH, -2);
        lastTwoMonthsDate = cal.getTime();

        totalAdultStudents = daoSession.getStudentItemDao().queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false), StudentItemDao.Properties.IsAdult.eq(true))
                .count();

        totalChildrenStudents = daoSession.getStudentItemDao().queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false), StudentItemDao.Properties.IsAdult.eq(false))
                .count();
    }

    private void LoadAdultControllers(View view){
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

        //Show 100% value at Y axis
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(100);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter();
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        LoadGraphAdultData();
    }

    private void LoadGraphAdultData(){
        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(0, getStudentsCount(DaysOfWeekValues.MONDAY, true)));
        values.add(new BarEntry(1, getStudentsCount(DaysOfWeekValues.TUESDAY, true)));
        values.add(new BarEntry(2, getStudentsCount(DaysOfWeekValues.WEDNESDAY, true)));
        values.add(new BarEntry(3, getStudentsCount(DaysOfWeekValues.THURSDAY, true)));
        values.add(new BarEntry(4, getStudentsCount(DaysOfWeekValues.FRIDAY, true)));
        values.add(new BarEntry(5, getStudentsCount(DaysOfWeekValues.SATURDAY, true)));
        values.add(new BarEntry(6, getStudentsCount(DaysOfWeekValues.SUNDAY, true)));

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

    private void LoadChildrenControllers(View view){
        chartChildren = view.findViewById(R.id.chartKids);

        chartChildren.getDescription().setEnabled(false);

        chartChildren.setScaleEnabled(false);

        chartChildren.setDrawBarShadow(false);
        chartChildren.setDrawGridBackground(false);

        chartChildren.getAxisLeft().setDrawGridLines(false);
        chartChildren.getAxisLeft().setDrawAxisLine(false);
        chartChildren.getAxisLeft().setTextColor(R.color.purple_700);

        // add a nice and smooth animation
        chartChildren.animateY(1500);
        chartChildren.setTouchEnabled(false);
        chartChildren.getLegend().setEnabled(false);

        chartChildren.getAxisRight().setDrawGridLines(false);
        chartChildren.getAxisRight().setDrawLabels(false);
        chartChildren.getAxisRight().setDrawAxisLine(false);
        chartChildren.getAxisRight().setTextColor(R.color.purple_700);

        chartChildren.setSelected(false);
        chartChildren.setClickable(false);

        //Show 100% value at Y axis
        chartChildren.getAxisLeft().setAxisMinimum(0);
        chartChildren.getAxisLeft().setAxisMaximum(100);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter();
        XAxis xAxis = chartChildren.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        LoadGraphChildrenData();
    }

    private void LoadGraphChildrenData(){
        ArrayList<BarEntry> values = new ArrayList<>();

        values.add(new BarEntry(0, getStudentsCount(DaysOfWeekValues.MONDAY, false)));
        values.add(new BarEntry(1, getStudentsCount(DaysOfWeekValues.TUESDAY, false)));
        values.add(new BarEntry(2, getStudentsCount(DaysOfWeekValues.WEDNESDAY, false)));
        values.add(new BarEntry(3, getStudentsCount(DaysOfWeekValues.THURSDAY, false)));
        values.add(new BarEntry(4, getStudentsCount(DaysOfWeekValues.FRIDAY, false)));
        values.add(new BarEntry(5, getStudentsCount(DaysOfWeekValues.SATURDAY, false)));
        values.add(new BarEntry(6, getStudentsCount(DaysOfWeekValues.SUNDAY, false)));

        BarDataSet set1;

        if (chartChildren.getData() != null &&
                chartChildren.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chartChildren.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chartChildren.getData().notifyDataChanged();
            chartChildren.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.rgb("#FF3700B3"));
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chartChildren.setData(data);
            chartChildren.setFitBars(true);
        }

        chartChildren.invalidate();
    }

    private float getStudentsCount(DaysOfWeekValues dayOfWeek, boolean isAdult){
        QueryBuilder<StudentItem> qb = daoSession.getStudentItemDao().queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false), StudentItemDao.Properties.IsAdult.eq(isAdult));
        Join cSJoin = qb.join(ClassStudentItem.class, ClassStudentItemDao.Properties.StudentId);
        Join cJoin = qb.join(cSJoin, ClassStudentItemDao.Properties.ClassId, ClassItem.class, ClassItemDao.Properties.ClassId)
                .where(ClassItemDao.Properties.Deleted.eq(false),
                        ClassItemDao.Properties.SportId.eq(surfSportId),
                        ClassItemDao.Properties.ClassDayOfWeek.eq(UtilsClass.GetDayOfWeekValue(dayOfWeek)),
                        ClassItemDao.Properties.ClassDateTime.ge(lastTwoMonthsDate));

        if(qb.count() < 1) return qb.count();
        return isAdult ? calculatePercentage(qb.count()) : calculatePercentageKids(qb.count());
    }

    public float calculatePercentage(long obtained) {
        return obtained * 100 / totalAdultStudents;
    }

    public float calculatePercentageKids(long obtained) {
        return obtained * 100 / totalChildrenStudents;
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

