package com.repella.groupproject;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
    public class UnitTests {

    private Pattern pattern = Pattern.compile("[A-Za-z0-9_]+"); //Pattern which identifies nondigit and nonletter characters.
    private Pattern patternTwo = Pattern.compile("[A-Za-z0-9 ]+"); //Same as above but allows white spaces (For Tasks Location/descriptions)
    @Test
    public void testCases () {
        assertTrue(isValidUsername("jomama had a big soup tomale bonjourno"));
        assertFalse(isValidUsername("jackbrown*(<"));
        assertFalse(isValidUsername("select from + *\n")); // don't forget returns and line feeds
        assertFalse(isValidUsername("1 or 1=1;--"));
    }

    //Function to test input validation for usernames, task locations, and task descriptions.
    private boolean isValidUsername(String username) {
        if(username.length() < 51 && username.length() > 0) {       //LENGTH CONSTRAINTS, Comment out if you wish to remove them.
            boolean valid = patternTwo.matcher(username).matches();
            return valid;
        }
        return false;
    }
}
