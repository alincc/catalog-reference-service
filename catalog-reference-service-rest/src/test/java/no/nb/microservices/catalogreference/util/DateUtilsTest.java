package no.nb.microservices.catalogreference.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class DateUtilsTest {

    @Test
    public void whenInvalidDateResultShouldBeFalse() {
        boolean valid = DateUtils.isValidDate("[1970]");
        assertEquals("Date should not be valid", false, valid);
    }

    @Test
    public void whenValidYearMonthDayResultShouldBeTrue() {
        boolean result = DateUtils.isValidDate("1970-01-01");
        assertTrue(result);
    }

    @Test
    public void whenValidYearMonthResultShouldBeTrue() {
        boolean result = DateUtils.isValidDate("1970-01");
        assertTrue(result);
    }

    @Test
    public void whenValidYearResultShouldBeTrue() {
        boolean result = DateUtils.isValidDate("1970");
        assertTrue(result);
    }
}
