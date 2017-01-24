package com.example.date.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * I know about how to define a valid date.
 */
public class DateDefinition {

  // Below can be externalized to property configs
  private final static List<Integer> MONTHS_31_DAY = Collections.unmodifiableList(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
  private final static List<Integer> MONTHS_30_DAY = Collections.unmodifiableList(Arrays.asList(4, 6, 9, 11));
  private final static int MIN_YEAR = 1900;
  private final static int MAX_YEAR = 2010;
  private final static int MIN_MONTH = 1;
  private final static int MAX_MONTH = 12;
  private final static int MIN_DAY = 1;

  private static DateDefinition instance;

  private DateDefinition() {
  }

  public static synchronized DateDefinition getInstance() {
    if(instance == null){
      instance = new DateDefinition();
    }
    return instance;
  }

  public Function<Integer, Boolean> isLeap() {
    return year -> year % 4 == 0 && !(year % 100 == 0 && year % 400 != 0);
  }

  public Function<Date, Date> validateYear() {
    return date -> {
      int year = date.getYear();
      if (year < MIN_YEAR || year > MAX_YEAR) {
        throw new IllegalArgumentException("Year " + year + " is out of range [" + MIN_YEAR + " - " + MAX_YEAR + "]");
      }
      return date;
    };
  }

  public Function<Date, Date> validateMonth() {
    return date -> {
      int month = date.getMonth();
      if (month < MIN_MONTH || month > MAX_MONTH) {
        throw new IllegalArgumentException("Month " + month + " is out of range [" + MIN_MONTH + " - " + MAX_MONTH + "]");
      }
      return date;
    };
  }

  public Function<Date, Date> validateDay() {
    return date -> {
      int day = date.getDay();
      int maxDay = getLastDayForMonth().apply(date);
      if (day < MIN_DAY || day > maxDay) {
        throw new IllegalArgumentException("Day " + day + " is out of range [" + MIN_DAY+ " - " + maxDay + "]");
      }
      return date;
    };
  }

  public Function<Date, Integer> getLastDayForMonth() {
    return date -> {
      if (MONTHS_31_DAY.contains(date.getMonth())) {
        return 31;
      } else if (MONTHS_30_DAY.contains(date.getMonth())) {
        return 30;
      } else {
        return isLeap().apply(date.getYear()) ? 29 : 28;
      }
    };
  }

  public IntFunction<Integer> daysInYear() {
    return year -> isLeap().apply(year) ? 366 : 365;
  }

  public int getMinMonth() {
    return MIN_MONTH;
  }

  public int getMinDay() {
    return MIN_DAY;
  }

}
