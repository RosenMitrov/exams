package com.example.football.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.football.constants.PrintMessages.*;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String date) throws Exception {


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(LOCAL_DATE_ADAPTER_PATTERN);

        return LocalDate.parse(date, dtf);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.toString();
    }
}
