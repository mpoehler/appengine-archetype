#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marco on 13.10.14.
 */
public class PageListEntry {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String url;

    private String state;

    private Date lastChanged;

    private ChangeIntervall intervall;

    // 0.0 - 1.0, default is 0.5
    private double priority = 0.5;

    /**
     * expect line in format: "RELATIVEURL,STATE,yyyy-MM-dd,INTERVAL(always,hourly,daily,weekly,monthly,yearly,never),priority(0.0-1.0)"
     * examples are:
     * "/contact.html,,2014-10-23,weekly,0.7"
     * or
     * "/,imprint,2014-07-30,daily,0.5"
     *
     * @param line
     * @return
     */
    public static synchronized PageListEntry parse(String line) throws ParseException {
        String[] fields = line.split(",");
        if (fields.length == 5) {
            PageListEntry ple = new PageListEntry();
            ple.setUrl(fields[0].trim());
            ple.setState(fields[1].trim());
            ple.setLastChanged(sdf.parse(fields[2].trim()));
            ple.setIntervall(ChangeIntervall.valueOf(fields[3].trim()));
            ple.setPriority(Double.parseDouble(fields[4].trim()));
            return ple;
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

    public ChangeIntervall getIntervall() {
        return intervall;
    }

    public void setIntervall(ChangeIntervall intervall) {
        this.intervall = intervall;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PageListEntry{" +
                "url='" + url + '\'' +
                ", state='" + state + '\'' +
                ", lastChanged=" + lastChanged +
                ", intervall=" + intervall +
                ", priority=" + priority +
                '}';
    }
}
