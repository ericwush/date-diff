package com.example.date;

import javaslang.control.Either;
import javaslang.control.Try;

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
    return new Date.Builder(DateDefinition.getInstance());
  }

  public static class Builder {

    private final Date instance = new Date();
    private final DateDefinition dateDefinition;

    public Builder(final DateDefinition dateDefinition) {
      this.dateDefinition = dateDefinition;
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

    // Smart constructor
    public Either<Throwable, Date> build() {
      return Try.of(() -> dateDefinition.validateYear()
          .andThen(dateDefinition.validateMonth())
          .andThen(dateDefinition.validateDay())
          .apply(instance)
      ).toEither();
    }

  }

}
