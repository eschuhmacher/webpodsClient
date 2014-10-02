package webpods.client.client.webpods.widgets;


import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Gauge;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;


public class GaugeWidget extends HorizontalPanel {

    private Gauge gauge;
    private DataTable data;
    private Gauge.Options options;
    private PV pv;

    public GaugeWidget(PV pv) {
        addCallBack(pv);
        this.pv = pv;
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                // Create a gauge chart visualization.
                gauge = new Gauge(createTable(), createOptions());

                add(gauge);
            }
        };
        VisualizationUtils.loadVisualizationApi(onLoadCallback, Gauge.PACKAGE);

    }

    private Gauge.Options createOptions() {
        options = Gauge.Options.create();
        options.setWidth(400);
        options.setHeight(240);
        options.setGaugeRange(0, 100);
        options.setGreenRange(20, 80);
        options.setRedRange(0,10);
        options.setRedRange(90,100);
        options.setYellowRange(10, 20);
        return options;
    }


    private AbstractDataTable createTable() {
        data = DataTable.create();
        data.addColumn(AbstractDataTable.ColumnType.NUMBER, pv.getName());
        data.addRows(1);
        data.setValue(0, 0, 0);
        return data;
    }

    private void changeValue(int value) {
        data.setValue(0,0, value);
        gauge.draw(data, options);
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
                instance.@webpods.client.client.webpods.widgets.GaugeWidget::update(Lwebpods/client/client/webpods/PvValue;)(pvValue);
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
        updateStype(value.getAlarm(), value.getDisplay());
        changeValue(Integer.parseInt((String) value.getValue()));
    }

    private void updateStype(Alarm alarm, PvDisplay display) {

        if(alarm.getSeverity() == "MAJOR") {
            gauge.setStyleName("input-text-fatal");
        } else if (alarm.getSeverity() == "MINOR"){
            gauge.setStyleName("input-text-warning");
        } else {
            gauge.setStyleName("input-text-normal");
        }
        if(display != null) {
            options.setGaugeRange(display.getLowDisplay(), display.getHighDisplay());
        }
    }

}
