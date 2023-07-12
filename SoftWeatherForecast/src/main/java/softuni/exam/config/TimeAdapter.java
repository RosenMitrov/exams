package softuni.exam.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeAdapter extends XmlAdapter<String, Time> {

    @Override
    public Time unmarshal(String time) throws Exception {

        DateFormat format = new SimpleDateFormat("HH:mm:ss");

        return new Time(format.parse(time).getTime());
    }

    @Override
    public String marshal(Time time) throws Exception {
        return time.toString();
    }
}
