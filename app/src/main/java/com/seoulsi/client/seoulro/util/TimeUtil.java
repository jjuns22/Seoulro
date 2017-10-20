package com.seoulsi.client.seoulro.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SanJuku on 2017-10-20.
 */

public class TimeUtil {
    static public String getTimeToPastString(long writtenTime) {
        Date date = new Date(writtenTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);
        return getTime;
    }
}
