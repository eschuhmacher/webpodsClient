package webpods.client.client.webpods;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import webpods.client.client.webpods.widgets.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eschuhmacher on 6/12/14.
 */
public class Demo3 extends FlowPanel {

    private List<PV> pvs = new LinkedList<PV>();
    VerticalPanel panel;

    public Demo3(WebppodsClient client) {
        panel = new VerticalPanel();
        FlowPanel subPanel = new FlowPanel();
        final PV pv = client.subscribePV("loc://test(12)", false);
        pvs.add(pv);
        TextInput input = new TextInput(pv);
        subPanel.add(input);
        final PV pv2 = client.subscribePV("loc://test(12)", false);
        pvs.add(pv2);
        ProgressBar bar = new ProgressBar(0.0, 100.0, pv2);
        subPanel.add(bar);
        final PV pv1 = client.subscribePV("loc://test(12)", false);
        pvs.add(pv1);
        final ValueSpinner spinner = new ValueSpinner(0, -200, 200, pv1);
        subPanel.add(spinner);
        final PV pv3 = client.subscribePV("loc://test(12)", false);
        pvs.add(pv3);
        final SliderBar sliderBar = new SliderBar(0.0, 100.0, pv3);
        sliderBar.setNumLabels(10);
        sliderBar.setNumTicks(10);
        subPanel.add(sliderBar);
        final PV pv4 = client.subscribePV("loc://test(12)", false);
        pvs.add(pv4);
        final GaugeWidget gaugeWidget = new GaugeWidget(pv4);
        subPanel.add(gaugeWidget);
        panel.add(subPanel);
        add(panel);
    }

    public VerticalPanel getPanel() {
        return panel;
    }

    public List<PV> getPvs() {
        return pvs;
    }

}
