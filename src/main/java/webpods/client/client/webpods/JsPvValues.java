package webpods.client.client.webpods;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.Date;

/**
 * Created by eschuhmacher on 6/17/14.
 */
public class JsPvValues  extends JavaScriptObject implements PvValues {

    protected JsPvValues() {}

    public final native PvValue[] getBufferValues() /*-{
        return this.type;
    }-*/;

    public final native Date getDate() /*-{
        return this.timestamp;
    }-*/;

    public final native String getSeverity() /*-{
        return this.severity;
    }-*/;


    public final native String getAlarmName() /*-{
        return this.alarmName;
    }-*/;

    public final native String getValue() /*-{
        return this.value;
    }-*/;
}