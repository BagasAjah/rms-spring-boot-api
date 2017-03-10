package com.rms.rms_api.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlDateTimeAdapter extends XmlAdapter<String, Date> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(Date date) throws Exception {
        return sdf.format(date);
    }

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return sdf.parse(dateStr);
    }

}
