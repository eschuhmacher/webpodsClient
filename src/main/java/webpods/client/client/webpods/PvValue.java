package webpods.client.client.webpods;

import com.google.gwt.typedarrays.shared.Float64Array;

import java.util.Date;
import java.util.Objects;

/**
 * Created by eschuhmacher on 6/6/14.
 */
public interface PvValue  {

    public String getType();

    public String getValue();

    public Date getDate();

    public Alarm getAlarm();

    public PvDisplay getDisplay();

}
