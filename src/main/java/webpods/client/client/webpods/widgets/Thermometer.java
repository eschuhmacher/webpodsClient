package webpods.client.client.webpods.widgets;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;

import java.awt.*;
import java.text.NumberFormat;

/**
 * Created by eschuhmacher on 6/10/14.
 */
public class Thermometer {

    private JFreeChart chart;
    private ThermometerPlot plot;

    public Thermometer(PV pv) {
        final DefaultValueDataset dataset = new DefaultValueDataset(Double.parseDouble(pv.getValue().getValue()));
        plot = new ThermometerPlot(dataset);
        chart = new JFreeChart("Thermometer Demo 2",  // chart title
            JFreeChart.DEFAULT_TITLE_FONT,
            plot,                 // plot
            false);               // include legend

        plot.setThermometerStroke(new BasicStroke(2.0f));
        plot.setThermometerPaint(Color.lightGray);


    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setValue(double value) {
        plot.setDataset(new DefaultValueDataset(value));
    }

    public void setOutlinePaint(Paint paint) {
        plot.setOutlinePaint(paint);
    }

    public void setUnits(int units) {
        plot.setUnits(units);
    }

    public void setShowValueLines(boolean show) {
        plot.setShowValueLines(show);
    }

    public void setRange(int low, int upper) {
        plot.setRange(low, upper);
    }

    public void setSubRange(int type, int low, int upper) {
        plot.setSubrange(type, low, upper);
    }

    public void setValueFormat(NumberFormat format) {
        plot.setValueFormat(format);
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
            instance.@webpods.webpods.client.webpods.client.webpods.widgets.Thermometer::update(Lorg/webpods/client/webpods/PvValue;)(pvValue);
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
        setValue(Double.valueOf(value.getValue()));
        updateStype(value.getAlarm(), value.getDisplay());
    }

    private void updateStype(Alarm alarm, PvDisplay display) {
        if (display != null) {
            plot.setRange(display.getLowDisplay(), display.getHighDisplay());
            plot.setSubrange(ThermometerPlot.CRITICAL, display.getLowDisplay(), display.getLowWarning());
            plot.setSubrange(ThermometerPlot.NORMAL, display.getLowWarning(), display.getHighWarning());
            plot.setSubrange(ThermometerPlot.WARNING, display.getHighWarning(), display.getHighAlarm());
            plot.setSubrange(ThermometerPlot.CRITICAL, display.getHighAlarm(), display.getHighDisplay());
        }
        if(alarm.getSeverity() == "MAJOR") {
            plot.setThermometerPaint(Color.red);
        } else if (alarm.getSeverity() == "MINOR"){
            plot.setThermometerPaint(Color.orange);
        } else {
            plot.setThermometerPaint(Color.green);
        }

    }
}
