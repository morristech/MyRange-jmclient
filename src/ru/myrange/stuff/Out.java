package ru.myrange.stuff;

import java.util.Calendar;
import java.util.Date;

/**
 * Out for saves different kinds of information (errors, control information, etc.)
 * @author Oleg Ponfilenok
 */

public class Out
{
	public final int MAX_SIZE = 50000; // Max length of string
	private String out = "";		        // String to save out
	
	public Out() {}
    
    public void erase() {
		out = "";
	}

    /**
     * Print text to beginning
     */
	public void print(String s) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String timeHR = c.get(Calendar.HOUR_OF_DAY) + ":" + (c.get(Calendar.MINUTE) > 9 ? "" : "0") + c.get(Calendar.MINUTE) +
                ":" + (c.get(Calendar.SECOND) > 9 ? "" : "0") + c.get(Calendar.SECOND);

        // print to beginning
        out = timeHR + " " + s + out;
        if ( out.length() > MAX_SIZE ) out = out.substring(out.length() - MAX_SIZE);
	}
	
	public void println(String s) {
        print(s + "\n");
	}
	
	public int numLines() {		// The total namber of saved out (Lines)
		int i = 0;
		int n = 0;
		while (i>=0) {
			i = out.indexOf("\n", i+1);
			n += 1;
		}
		return n;
	}
	
	public String getLines(int startInd, int endInd) {	// Get lines from  startInd to endInd (first line has index 1)
		int i = 0;
		int n = 0;
		int start = 0;
		int end = 0;
		while (i>=0) {
			if (n+1 == startInd)  start = i+1;
			i = out.indexOf("\n", i+1);
			if ((n+1 == endInd) && (i>0))  end = i;
			else if (n+1 == endInd) end = out.length();
			n += 1;
		}
		if ((start<=end) && (end <= out.length())) return out.substring(start, end);
		else return "";
	}
	
	public String getAll() {		// Get all out
		return out;
	}
	
}
