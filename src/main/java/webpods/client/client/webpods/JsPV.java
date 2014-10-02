package webpods.client.client.webpods;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by eschuhmacher on 6/5/14.
 */
public class JsPV extends JavaScriptObject implements PV {

    protected JsPV() {}


    public final native String  getName()  /*-{
        return this.name;
    }-*/;

    public final native Integer getId()  /*-{
        return this.id;
    }-*/;

    public final native boolean isPaused() /*-{
        return this.paused;
    }-*/;

    public final native PvValue getValue() /*-{
        return this.getValue();
    }-*/;

    public final native boolean isConnected() /*-{
        return this.isConnected ();
    }-*/;

    public final native boolean isWriteAllowed()/*-{
        return this.isWriteAllowed();
    }-*/;

    public final native void addCallback(JavaScriptObject callback) /*-{
        this.addCallback(callback);
    }-*/;

    public final native void removeCallback(Callback callback)/*-{
        this.removeCallback(callback);
    }-*/;

    public final native void setValue(String value)/*-{
        this.setValue(value);
    }-*/;

    public final native void pause()/*-{
        this.pause();
    }-*/;

    public final native void resume()/*-{
        this.resume();
    }-*/;

    public final native void close() /*-{
        this.unsubscribe();
    }-*/;

    public final native boolean isClose() /*-{
        return !this.connected;
    }-*/;
}
