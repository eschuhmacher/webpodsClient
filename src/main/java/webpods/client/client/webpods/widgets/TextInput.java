package webpods.client.client.webpods.widgets;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import webpods.client.client.webpods.Alarm;
import webpods.client.client.webpods.PV;
import webpods.client.client.webpods.PvDisplay;
import webpods.client.client.webpods.PvValue;

/**
 * Created by eschuhmacher on 6/11/14.
 */
public class TextInput extends HorizontalPanel {

    private  TextBox box;

    public TextInput(final PV pv) {
        box = new TextBox();
        box.setText("");
        addCallBack(pv);
        add(box);
        box.addKeyboardListener(new KeyboardListenerAdapter() {
            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                // If return then save, if Esc cancel the change, otherwise do nothing
                switch (keyCode) {
                    case 13:
                        updatePV(pv);
                        break;
                    case 27:
                        box.setText(pv.getValue().getValue());
                        break;
                }
            }
        });
    }

    private void updatePV(PV pv) {
        pv.setValue(box.getText());
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
                instance.@webpods.client.client.webpods.widgets.TextInput::update(Lwebpods/client/client/webpods/PvValue;)(pvValue);
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
        box.setValue(value.getValue());
        updateStype(value.getAlarm(), value.getDisplay());
    }

    private void updateStype(Alarm alarm, PvDisplay display) {

        if(alarm.getSeverity() == "MAJOR") {
            box.setStyleName("input-text-fatal");
        } else if (alarm.getSeverity() == "MINOR"){
            box.setStyleName("input-text-warning");
        } else {
            box.setStyleName("input-text-normal");
        }
    }

}
