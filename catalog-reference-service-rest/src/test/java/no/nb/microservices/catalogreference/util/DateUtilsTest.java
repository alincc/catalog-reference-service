package no.nb.microservices.catalogreference.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class DateUtilsTest {

    @Test
    public void whenInvalidDateResultShouldBeEmpty() {
        String result = DateUtils.getRisAndEnwDate("[1970]");
        assertTrue("Result should be empty", result.isEmpty());
    }

    @Test
    public void whenDateIsValidResultShouldBeTrue() {
        boolean result = DateUtils.isValidDate("1970-01-01");
        assertTrue(result);

        result = DateUtils.isValidDate("1970-01");
        assertTrue(result);

        result = DateUtils.isValidDate("1970");
        assertTrue(result);

    }

    @Test
    public void whenValidDateResultShouldBeFormattedCorrectly() {
        String result = DateUtils.getRisAndEnwDate("1970");
        assertEquals("1970///",result);

        result = DateUtils.getRisAndEnwDate("1970-01-01");
        assertEquals("1970/01/01/",result);

        result = DateUtils.getRisAndEnwDate("1970-01");
        assertEquals("1970/01//",result);
    }
}
