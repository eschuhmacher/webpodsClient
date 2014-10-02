package webpods.client.client.webpods.widgets;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.visualization.client.*;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;

/**
 * Created by eschuhmacher on 6/13/14.
 */
public class ChartPie extends HorizontalPanel {

    private PV pv;
    private PieChart pie;
    private Options options;

    public ChartPie(PV pv) {
        addCallBack(pv);
        this.pv = pv;
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                // Create a pie chart visualization.
                pie = new PieChart(createTable(), createOptions());

                pie.addSelectHandler(createSelectHandler(pie));
                add(pie);
            }
        };
        VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);

    }

    private PieChart.Options createOptions() {
        options = Options.create();
        options.setWidth(400);
        options.setHeight(240);
        options.set3D(true);
        options.setTitle("pv");
        return options;
    }

    private SelectHandler createSelectHandler(final PieChart chart) {
        return new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                String message = "";

                // May be multiple selections.
                JsArray<Selection> selections = chart.getSelections();

                for (int i = 0; i < selections.length(); i++) {
                    // add a new line for each selection
                    message += i == 0 ? "" : "\n";

                    Selection selection = selections.get(i);

                    if (selection.isCell()) {
                        // isCell() returns true if a cell has been selected.

                        // getRow() returns the row number of the selected cell.
                        int row = selection.getRow();
                        // getColumn() returns the column number of the selected cell.
                        int column = selection.getColumn();
                        message += "cell " + row + ":" + column + " selected";
                    } else if (selection.isRow()) {
                        // isRow() returns true if an entire row has been selected.

                        // getRow() returns the row number of the selected row.
                        int row = selection.getRow();
                        message += "row " + row + " selected";
                    } else {
                        // unreachable
                        message += "Pie chart selections should be either row selections or cell selections.";
                        message += "  Other visualizations support column selections as well.";
                    }
                }

                Window.alert(message);
            }
        };
    }

    private AbstractDataTable createTable() {
        DataTable data = DataTable.create();
        data.addColumn(ColumnType.STRING, "PV");
        data.addColumn(ColumnType.NUMBER, "Value");
        data.addRows(2);
        data.setValue(0, 0, pv.getName());
        data.setValue(0, 1, 0);
        data.setValue(1, 0, "Rest");
        data.setValue(1, 1, 0);
        return data;
    }

    private AbstractDataTable updateTable(PvValue value) {
        DataTable data = DataTable.create();
        data.addColumn(ColumnType.STRING, "PV");
        data.addColumn(ColumnType.NUMBER, "Value");
        data.addRows(2);
        data.setValue(0, 0, pv.getName());
        data.setValue(0, 1, Integer.parseInt(value.getValue()));
        data.setValue(1, 0, "Rest");
        data.setValue(1, 1, 100 - Integer.parseInt(value.getValue()));
        return data;
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
                instance.@webpods.client.client.webpods.widgets.ChartPie::update(Lwebpods/client/client/webpods/PvValue;)(pvValue);
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
        pie.draw(updateTable(value), options);
        updateStype(value.getAlarm(), value.getDisplay());

    }

    private void updateStype(Alarm alarm, PvDisplay display) {

        if(alarm.getSeverity() == "MAJOR") {
            pie.setStyleName("input-text-fatal");
        } else if (alarm.getSeverity() == "MINOR"){
            pie.setStyleName("input-text-warning");
        } else {
            pie.setStyleName("input-text-normal");
        }

    }


}
