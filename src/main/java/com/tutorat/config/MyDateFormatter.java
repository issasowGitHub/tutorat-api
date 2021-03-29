package com.tutorat.config;

import com.tutorat.exception.BusinessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyDateFormatter {
    public Date dateFormat(String dateToFomrat, String format) throws ParseException, BusinessResourceException {
        try {
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formattter = new SimpleDateFormat(format);
            String dateString = formattter.format(new Date());
            Date date = formattter.parse(dateToFomrat);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BusinessResourceException("IncorrectValue", "Une date incorrecte est passée en paramétre. <dateFormat>.", HttpStatus.BAD_REQUEST);
        }
    }
}
