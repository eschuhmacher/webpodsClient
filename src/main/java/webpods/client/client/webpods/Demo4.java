package webpods.client.client.webpods;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import webpods.client.client.webpods.widgets.ChartPie;
import webpods.client.client.webpods.widgets.XYChart;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by eschuhmacher on 6/12/14.
 */
public class Demo4 extends FlowPanel {

    private List<PV> pvs = new LinkedList<PV>();
    VerticalPanel panel;

    public Demo4(final WebppodsClient client) {
        final FlowPanel buttons = new FlowPanel() ;
        FlowPanel header = new FlowPanel() ;
        panel = new VerticalPanel();

        final PV pv = client.subscribePV("sim://ramp(0, 100, 1, 1)", true);
        pvs.add(pv);
        final XYChart spinner = new XYChart(pv);
        header.add(spinner);

        final Button pausePV = new Button("pause");
        pausePV.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (pausePV.getText().equals("pause")) {
                    for(PV pv4 : pvs) {
                        pv4.pause();
                    }
                    pausePV.setText("unpause");
                } else {
                    for(PV pv4 : pvs) {
                       // pv4.resume();
                    }
                    pausePV.setText("pause");
                }

            }
        });
        buttons.add(pausePV);

        Button close = new Button("close PV");
        close.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                //Iterables.getLast(pvs).close();
                pvs.get(pvs.size() -1).close();

            }
        });
        buttons.add(close);
        panel.add(header);
        panel.add(buttons);
        add(panel);
    }

    public List<PV> getPvs() {
        return pvs;
    }
}
