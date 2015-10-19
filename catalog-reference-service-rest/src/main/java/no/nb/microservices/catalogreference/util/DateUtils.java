package no.nb.microservices.catalogreference.util;

import org.apache.commons.validator.routines.DateValidator;

public class DateUtils {

    private DateUtils() {

    }

    public static boolean isValidDate(String date) {
        DateValidator validator = DateValidator.getInstance();
        return validator.isValid(date,"yyyy") || validator.isValid(date,"yyyy-MM") || validator.isValid(date,"yyyy-MM-dd");
    }

    public static String getRisAndEnwDate(String date) {
        DateValidator validator = DateValidator.getInstance();
        String result = "";
        if (validator.isValid(date,"yyyy")) {
            result = date + "///";
        } else if (validator.isValid(date,"yyyy-MM")) {
            result = date.replaceAll("-","/") + "//";
        } else if (validator.isValid(date,"yyyy-MM-dd")) {
            result = date.replaceAll("-","/") + "/";
        }
        return result;
    }

}