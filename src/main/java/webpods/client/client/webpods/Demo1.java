package webpods.client.client.webpods;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import webpods.client.client.webpods.widgets.TextInput;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eschuhmacher on 6/12/14.
 */
public class Demo1 extends FlowPanel {

    private List<PV> pvs = new LinkedList<PV>();
    VerticalPanel panel;

    public Demo1(WebppodsClient client) {
        panel = new VerticalPanel();
        for (int i = 0 ; i< 1; i++) {
            HorizontalPanel panel1 =  new HorizontalPanel();
            for(int j=0; j<10; j++) {
                final PV pv = client.subscribePV("sim://noise", true);
                TextInput in = new TextInput(pv);
                pvs.add(pv);
                panel1.add(in);
            }
            panel.add(panel1);
        }
        add(panel);
    }

    public VerticalPanel getPanel() {
        return panel;
    }

    public List<PV> getPvs() {
        return pvs;
    }
}
