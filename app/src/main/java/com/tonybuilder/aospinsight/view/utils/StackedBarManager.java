package com.tonybuilder.aospinsight.view.utils;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StackedBarManager {
    /**
     * create a bar data
     * @param barName   line name
     * @param xValues    x values showing on the axis
     * @param yFirst    y1 values showing on the axis
     * @param ySecond    y1 values showing on the axis
     * @param labelFirst y1 label showing on the axis
     * @param labelSecond y2 label showing on the axis
     * @return BarData
     */
    public static BarData creatDoubleStackChart(String barName, List<String> xValues, List<Float> yFirst, List<Float> ySecond, String labelFirst, String labelSecond) {
        int count = xValues.size();
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            barEntries.add(new BarEntry(new float[]{yFirst.get(i), ySecond.get(i)}, i));
        }

        BarDataSet set1 = new BarDataSet(barEntries, barName);
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{labelFirst, labelSecond});

        List<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xValues, dataSets);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextColor(Color.WHITE);
        return data;
    }

    static private int[] getColors() {
        // have as many colors as stack-values per entry
        int[] colors = new int[2];
        System.arraycopy(ColorTemplate.MATERIAL_COLORS, 0, colors, 0, colors.length);
        return colors;
    }

    public static void initDataStyle(BarChart barChart, BarData barData) {
        barChart.setMaxVisibleValueCount(40);
        barChart.setPinchZoom(true);

        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);

        barChart.setDrawValueAboveBar(true);
        barChart.setHighlightFullBarEnabled(true);
        barChart.setData(barData);

        // change the position of the y-labels
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setAxisMinValue(0);
        barChart.getAxisRight().setEnabled(false);

        XAxis xLabels = barChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);

        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setFormToTextSpace(4f);
        legend.setXEntrySpace(6f);

        barChart.animateY(2000, Easing.EasingOption.Linear);
        barChart.animateX(2000, Easing.EasingOption.Linear);
        barChart.invalidate();
    }
}
