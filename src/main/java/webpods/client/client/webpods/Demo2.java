package webpods.client.client.webpods;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import webpods.client.client.webpods.widgets.ValueSpinner;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eschuhmacher on 6/12/14.
 */
public class Demo2 extends FlowPanel {

    private List<PV> pvs = new LinkedList<PV>();
    VerticalPanel panel;

    public Demo2(final WebppodsClient client) {
        final FlowPanel buttons = new FlowPanel() ;
        FlowPanel header = new FlowPanel() ;
        panel = new VerticalPanel();

        final PV pv = client.subscribePV("sim://noise", true);
        pvs.add(pv);
        final ValueSpinner spinner = new ValueSpinner(0, -200, 200, pv);
        header.add(spinner);
        final Button pausePV = new Button("pause");
        pausePV.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (pausePV.getText().equals("pause")) {
                    //Iterables.getLast(pvs).pause(true);
                    pvs.get(pvs.size() -1).pause();
                    pausePV.setText("unpause");
                } else {
                    //Iterables.getLast(pvs).pause(false);
                   // pvs.get(pvs.size() -1).resume();
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

        final Button createPV = new Button("create PV");
        createPV.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (pvs.get(pvs.size() -1).isClose()) {
                    PV pv1 = client.subscribePV( "sim://ramp(0, 100, 1, 1)",true);
                    pvs.add(pv1);
                    spinner.refresh(pv1);
                }
            }
        });
        buttons.add(createPV);
        panel.add(header);
        panel.add(buttons);
        add(panel);
    }

    public VerticalPanel getPanel() {
        return panel;
    }

    public List<PV> getPvs() {
        return pvs;
    }
}
