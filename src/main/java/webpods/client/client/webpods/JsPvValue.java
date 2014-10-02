package webpods.client.client.webpods;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.typedarrays.shared.Float64Array;

import java.util.Date;

/**
 * Created by eschuhmacher on 6/6/14.
 */
public class JsPvValue  extends JavaScriptObject implements PvValue {

    protected JsPvValue() {}

    public final native String getType() /*-{
        return this.type.name;
    }-*/;

    public final native Date getDate() /*-{
        return this.timestamp;
    }-*/;

    public final native String getValue() /*-{
        return this.value;
    }-*/;

    public final native Integer getDisplayLowAlarm() /*-{
        return this.display.lowAlarm;
    }-*/;

    public final native Integer getDisplayHighAlarm() /*-{
        return this.display.highAlarm;
    }-*/;

    public final native Integer getDisplayLowDisplay() /*-{
        return this.display.lowDisplay;
    }-*/;

    public final native Integer getDisplayHighDisplay() /*-{
        return this.display.highDisplay;
    }-*/;

    public final native Integer getDisplayLowWarning() /*-{
        return this.display.lowWarning;
    }-*/;

    public final native Integer getDisplayHighWarning() /*-{
        return this.display.highWarning;
    }-*/;

    public final PvDisplay getDisplay() {
        if (getDisplayLowAlarm() != null) {
            return new PvDisplay(getDisplayLowAlarm(), getDisplayHighAlarm(), getDisplayLowDisplay(),
            getDisplayHighDisplay(), getDisplayLowWarning(),getDisplayHighWarning());
        } else {
            return null;
        }
    };

    public final native String getAlarmSeverity() /*-{
        return this.alarm.severity;
    }-*/;

    public final native String getAlarmStatus() /*-{
        return this.alarm.status;
    }-*/;

    public final Alarm getAlarm() {
        return new Alarm(getAlarmSeverity(), getAlarmStatus());
    }
}
