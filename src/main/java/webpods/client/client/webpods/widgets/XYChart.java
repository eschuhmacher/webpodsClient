package webpods.client.client.webpods.widgets;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;

import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.events.ChartSelectionEvent;
import org.moxieapps.gwt.highcharts.client.events.ChartSelectionEventHandler;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;
import org.moxieapps.gwt.highcharts.client.Chart;

/**
 * Created by eschuhmacher on 6/13/14.
 */
public class XYChart extends HorizontalPanel {

    private PV pv;
    private Chart chart;
    private Series series;

    public XYChart(PV pv) {
        addCallBack(pv);
        this.pv = pv;
        chart = new Chart()
                .setType(Series.Type.SPLINE)
                .setChartTitleText("")
                .setMarginRight(10);

        series = chart.createSeries()
                .setName(pv.getName())
                .setPoints(new Number[] { 0 });
        chart.addSeries(series);

        chart.setZoomType(Chart.ZoomType.X);
        chart.setSelectionEventHandler(new ChartSelectionEventHandler() {
            public boolean onSelection(ChartSelectionEvent e) {
                Window.alert("Selected " + e.getXAxisMin() + " to " + e.getXAxisMax());
                return true;
            }
        });
        add(chart);

    }

    public native void addCallBack(PV pv)
    /*-{
        var instance = this;
        pv.addCallback(function(evt, pv) {
            switch (evt.type) {
            case "connection": //connection state changed
                break;
            case "value": //value changed
                //PV's value is usually a data structure that has timestamp, value, alarm and meta information.
                //Here is an example on how to extract the timestamp and
                //numeric value from PV if the PV holds numeric value
                var pvValue = pv.getValue();
                pvValue = evt.value;
                instance.@webpods.client.client.webpods.widgets.XYChart::update(Lwebpods/client/client/webpods/PvValue;)(pvValue);
                break;
            case "error": //error happened
                break;
            case "writePermission":	// write permission changed.
                break;
            case "writeFinished": // write finished.
                break;
            default:
                break;
            }
        });
	}-*/;


    private void update(PvValue value) {
        series.addPoint(Integer.parseInt(value.getValue()));
       // updateStype(value.getAlarm(), value.getDisplay());

    }

    private void updateStype(Alarm alarm, PvDisplay display) {

        if(alarm.getSeverity() == "MAJOR") {
            chart.setStyleName("input-text-fatal");
        } else if (alarm.getSeverity() == "MINOR"){
        } else {
        }

    }



}
