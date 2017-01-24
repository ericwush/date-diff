package com.example.date.model;

import javaslang.control.Either;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DateCalculator {

  private final DateDefinition dateDefinition;

  public DateCalculator(final DateDefinition dateDefinition) {
    this.dateDefinition = dateDefinition;
  }

  public Function<Date, Integer> dayOfYear() {
    return date ->
        IntStream.range(dateDefinition.getMinMonth(), date.getMonth())
            .mapToObj(month -> Date.builder().year(date.getYear()).month(month).day(dateDefinition.getMinDay()).build())
            .filter(Either::isRight)
            .map(newDate -> newDate.right().get())
            .map(dateDefinition.getLastDayForMonth())
            .mapToInt(Integer::intValue)
            .sum()
            + date.getDay();
  }

  public BiFunction<Integer, Integer, Integer> daysBetweenYears() {
    return (startYear, endYear) ->
      IntStream.range(startYear, endYear)
          .mapToObj(dateDefinition.daysInYear())
          .mapToInt(Integer::intValue)
          .sum();
  }

  public BiFunction<Date, Date, Integer> daysBetweenDates() {
    return (startDate, endDate) ->
      dayOfYear().apply(endDate) - dayOfYear().apply(startDate)
          + daysBetweenYears().apply(startDate.getYear(), endDate.getYear());

  }

}
