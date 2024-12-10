package com.example.rksihackaton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class GraphBuilder {
    public static void BuildBarGraph(BarChart barChart, float groupSpace, float barSpace, float barWidth, BarDataSet... sets ){
        BarData barData = new BarData(sets);
        if(barWidth > 0) {
            barData.setBarWidth(barWidth);
        }
        barChart.setData(barData);

        // Отключение вертикальных линий сетки
        barChart.getXAxis().setDrawGridLines(false); // Горизонтальная ось
        barChart.getXAxis().setDrawLabels(false); // Горизонтальная ось

        barChart.groupBars(-0.5f, groupSpace, barSpace); // perform the "explicit" grouping
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.invalidate(); // Обновление графика
    }

    public static void BuildBarGraph(BarChart barChart, float groupSpace, float barSpace, float barWidth, IBarDataSet[] sets ){
        BarData barData = new BarData(sets);
        if(barWidth > 0) {
            barData.setBarWidth(barWidth);
        }
        barChart.setData(barData);

        // Отключение вертикальных линий сетки
        barChart.getXAxis().setDrawGridLines(false); // Горизонтальная ось
        barChart.getXAxis().setDrawLabels(false); // Горизонтальная ось

        barChart.groupBars(-0.5f, groupSpace, barSpace); // perform the "explicit" grouping
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.invalidate(); // Обновление графика
    }

    public static void BuildLineGraph(LineChart lineChart, LineDataSet... dataSets){
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate(); // Обновление графика
    }
}
