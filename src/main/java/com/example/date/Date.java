package com.example.date;

import javaslang.control.Either;
import javaslang.control.Try;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Date {

  private int day;
  private int month;
  private int year;

  public int getDay() {
    return day;
  }

  public int getMonth() {
    return month;
  }

  public int getYear() {
    return year;
  }

  private Date() {
  }

  public static Builder builder() {
    return new Date.Builder();
  }

  public static class Builder {
    private final static int START_YEAR = 1900;
    private final static int END_YEAR = 2010;
    private final static int START_MONTH = 1;
    private final static int END_MONTH = 12;
    private final static List<Integer> MONTHS_31_DAY = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    private final static List<Integer> MONTHS_30_DAY = Arrays.asList(4, 6, 9, 11);

    private final Date instance = new Date();

    public Builder() {
    }

    public Builder day(final int day) {
      instance.day = day;
      return this;
    }

    public Builder month(final int month) {
      instance.month = month;
      return this;
    }

    public Builder year(final int year) {
      instance.year = year;
      return this;
    }

    // smart constructor
    public Either<Throwable, Date> build() {
      return Try.of(() -> validateYear()
          .andThen(validateMonth())
          .andThen(validateDay())
          .apply(instance)
      ).toEither();
    }

    private Function<Date, Date> validateYear() {
      return date -> {
        int year = date.year;
        if (year < START_YEAR || year > END_YEAR) {
          throw new IllegalArgumentException("Year " + year + " is out of range [" + START_YEAR  + " - " + END_YEAR + "]");
        }
        return date;
      };
    }

    private Function<Date, Date> validateMonth() {
      return date -> {
        int month = date.month;
        if (month < START_MONTH || month > END_MONTH) {
          throw new IllegalArgumentException("Month " + month + " is out of range [" + START_MONTH  + " - " + END_MONTH + "]");
        }
        return date;
      };
    }

    private Function<Date, Date> validateDay() {
      return date -> {
        int day = date.day;
        if (MONTHS_31_DAY.contains(date.month)) {
          if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day " + day + " is out of range [1 - 31]");
          }
        } else if (MONTHS_30_DAY.contains(date.month)) {
          if (day < 1 || day > 30) {
            throw new IllegalArgumentException("Day " + day + " is out of range [1 - 30]");
          }
        } else {
          final boolean isLeapYear = date.year % 4 == 0;
          if (isLeapYear && (day < 1 || day > 29)) {
            throw new IllegalArgumentException("Day " + day + " is out of range [1 - 29]");
          } else if (!isLeapYear && (day < 1 || day > 28)) {
            throw new IllegalArgumentException("Day " + day + " is out of range [1 - 28]");
          }
        }
        return date;
      };
    }

  }

}
