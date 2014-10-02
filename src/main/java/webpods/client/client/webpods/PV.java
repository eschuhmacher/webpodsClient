package webpods.client.client.webpods;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by eschuhmacher on 6/5/14.
 */
public interface PV {

    public String getName();

    public Integer getId();

    public boolean isPaused();

    public PvValue getValue();

    public boolean isConnected();

    public boolean isWriteAllowed();

    public void addCallback(JavaScriptObject callback);

    public void removeCallback(Callback callback);

    public void setValue(String value);

    public void pause();

    public void close();

    public boolean isClose();

}
