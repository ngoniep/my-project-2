package com.camunda.demo.enums;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:42
 * @project my-project
 */
public enum Holidays {

    /**
     * See <a href="http://www.wikiwand.com/en/Public_holidays_in_France">http://www.wikiwand.com/en/Public_holidays_in_France</a>.
     */
    ZIMBABWE {

        @Override
        protected void addFixedHolidays(Set<Holiday> holidays) {
            holidays.add(new Holiday(Calendar.JANUARY, 1));
            holidays.add(new Holiday(Calendar.MAY, 1));
            holidays.add(new Holiday(Calendar.DECEMBER, 22));
            holidays.add(new Holiday(Calendar.DECEMBER, 25));
            holidays.add(new Holiday(Calendar.DECEMBER, 26));
        }

        @Override
        protected void addVariableHolidays(int year, Set<Holiday> holidays) {
            Date easterSunday = getEasterSunday(year);
            holidays.add(new Holiday(getEasterMonday(easterSunday)));
            holidays.add(new Holiday(getAscensionThursday(easterSunday)));
            holidays.add(new Holiday(getPentecostMonday(easterSunday)));
        }

    },

    /**
     * See <a href="http://www.wikiwand.com/en/Public_holidays_in_the_United_Kingdom">http://www.wikiwand.com/en/Public_holidays_in_the_United_Kingdom</a>.
     */
    ENGLAND {

        @Override
        protected void addFixedHolidays(Set<Holiday> holidays) {
            holidays.add(new Holiday(Calendar.JANUARY, 1));
            holidays.add(new Holiday(Calendar.DECEMBER, 25));
            holidays.add(new Holiday(Calendar.DECEMBER, 26));
        }

        @Override
        protected void addVariableHolidays(int year, Set<Holiday> holidays) {
            Date easterSunday = getEasterSunday(year);
            holidays.add(new Holiday(getGoodFriday(easterSunday)));
            holidays.add(new Holiday(getEasterMonday(easterSunday)));
            holidays.add(new Holiday(get(WeekdayIndex.FIRST, Calendar.MONDAY, Calendar.MAY, year)));
            holidays.add(new Holiday(get(WeekdayIndex.LAST, Calendar.MONDAY, Calendar.MAY, year)));
            holidays.add(new Holiday(get(WeekdayIndex.LAST, Calendar.MONDAY, Calendar.AUGUST, year)));
            Holiday christmasDay = new Holiday(Calendar.DECEMBER, 25);
            if (christmasDay.isWeekend(year)) {
                holidays.add(new Holiday(Calendar.DECEMBER, 27));
            }
            Holiday boxingDay = new Holiday(Calendar.DECEMBER, 26);
            if (boxingDay.isWeekend(year)) {
                holidays.add(new Holiday(Calendar.DECEMBER, 28));
            }
        }

    };

    public class HolidayException extends Exception {

        private static final long serialVersionUID = 1L;

        private HolidayException(String message) {
            super(message);
        }

    }

    /**
     * A holiday is defined by a {@link Calendar#MONTH} and a {@link Calendar#DAY_OF_MONTH}.
     */
    private class Holiday {

        private final int day;
        private final int month;

        public Holiday(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        public Holiday(int month, int day) {
            this.month = month;
            this.day = day;
        }

        public Date toDate(int year) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            return calendar.getTime();
        }

        public boolean isWeekend(int year) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(toDate(year));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (!(obj instanceof Holiday)) {
                return false;
            } else {
                Holiday holiday = (Holiday) obj;
                return holiday.month == month && holiday.day == day;
            }
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[] { month, day });
        }

    }

    /**
     * Use with {@link Holidays#get(WeekdayIndex, int, int, int)}.<br />
     * <br />
     * Example: <code>Holidays.get(WeekdayIndex.FIRST, Calendar.MONDAY, Calendar.MAY, 2000)</code>.
     */
    public enum WeekdayIndex {

        FIRST(1), SECOND(2), THIRD(3), FOURTH(4), LAST(null);

        private final Integer index;

        private WeekdayIndex(Integer index) {
            this.index = index;
        }

        private boolean is(int count) {
            return index != null && index == count;
        }

    }

    private final Set<Holiday> fixedHolidays = new HashSet<Holiday>();

    private final Map<Integer, Set<Holiday>> variableHolidays = new HashMap<Integer, Set<Holiday>>();

    private Holidays() {
        addFixedHolidays(fixedHolidays);
    }

    protected abstract void addFixedHolidays(Set<Holiday> holidays);

    protected abstract void addVariableHolidays(int year, Set<Holiday> holidays);

    /**
     * Returns the number of business days between two dates.
     *
     * @param d1
     *          The first date.
     * @param d2
     *          The second date.
     * @return The number of business days between the two provided dates.
     * @throws HolidayException
     *           If <code>d1</code> or <code>d2</code> is not a business day.
     */
    public int getBusinessDayCount(Date d1, Date d2) throws HolidayException {
        Calendar calendar = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = formatter.parse(formatter.format(d1));
            d2 = formatter.parse(formatter.format(d2));
        } catch (ParseException ignore) {
            // cannot happen
        }
        if (!isBusinessDay(d1) || !isBusinessDay(d2)) {
            throw new HolidayException("Input dates must be business days");
        }
        int businessDayCount = 0;
        Date min = d1.before(d2) ? d1 : d2;
        Date max = min.equals(d2) ? d1 : d2;
        calendar.setTime(min);
        while (calendar.getTime().before(max)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (isBusinessDay(calendar.getTime())) {
                businessDayCount++;
            }
        }
        return businessDayCount;
    }

    /**
     * Returns whether a date is a business day.
     *
     * @param date
     *          The date.
     * @return <code>true</code> if the <code>date</code> is a business day, <code>false</code> otherwise.
     */
    public boolean isBusinessDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return false;
        } else if (isFixedHoliday(date)) {
            return false;
        } else if (isVariableHoliday(date)) {
            return false;
        }
        return true;
    }

    private boolean isFixedHoliday(Date date) {
        return fixedHolidays.contains(new Holiday(date));
    }

    private boolean isVariableHoliday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        Set<Holiday> yearHolidays;
        if (!variableHolidays.containsKey(year)) {
            // variable holidays have not been calculated for this year yet
            yearHolidays = new HashSet<Holiday>();
            addVariableHolidays(year, yearHolidays);
            variableHolidays.put(year, yearHolidays);
        } else {
            yearHolidays = variableHolidays.get(year);
        }
        return yearHolidays.contains(new Holiday(date));
    }

    public static Date getEasterSunday(int year) {
        // credits: https://www.wikiwand.com/en/Computus#/Anonymous_Gregorian_algorithm
        Calendar calendar = Calendar.getInstance();
        int initialYear = year;
        if (year < 1900) {
            year += 1900;
        }
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int j = c % 4;
        int k = (32 + 2 * e + 2 * i - h - j) % 7;
        int l = (a + 11 * h + 22 * k) / 451;
        int m = (h + k - 7 * l + 114) % 31;
        int month = (h + k - 7 * l + 114) / 31 - 1;
        int day = m + 1;
        calendar.set(initialYear, month, day);
        return calendar.getTime();
    }

    public static Date getGoodFriday(Date easterSunday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(easterSunday);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        return calendar.getTime();
    }

    public static Date getEasterMonday(Date easterSunday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(easterSunday);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getAscensionThursday(Date easterSunday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(easterSunday);
        calendar.add(Calendar.DAY_OF_MONTH, 39);
        return calendar.getTime();
    }

    public static Date getPentecostMonday(Date easterSunday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(easterSunday);
        calendar.add(Calendar.DAY_OF_MONTH, 50);
        return calendar.getTime();
    }

    public static Date get(WeekdayIndex weekdayIndex, int dayOfWeek, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        int count = 0;
        Date last = null;
        do {
            if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                count++;
                last = calendar.getTime();
                if (weekdayIndex.is(count)) {
                    return last;
                }
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (calendar.get(Calendar.MONTH) == month);
        if (weekdayIndex.equals(WeekdayIndex.LAST)) {
            return last;
        }
        return null;
    }

}
