package com.example.date.input;

import com.example.date.model.Date;
import javaslang.control.Either;
import javaslang.control.Try;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DateParser {

  public Either<Throwable, Date> parse(final String dateString) {
    Try<List<Integer>> tryDateParts = Try.of(() -> Arrays.stream(dateString.trim().split(" "))
        .map(Integer::valueOf)
        .collect(toList()));
    if (tryDateParts.isFailure()) {
      return Either.left(new IllegalArgumentException("Cannot convert to number"));
    } else if (tryDateParts.get().size() != 3) {
      return Either.left(new IllegalArgumentException("More or less than 3 arguments to form a date"));
    }
    List<Integer> dateParts = tryDateParts.get();
    return Date.builder().day(dateParts.get(0)).month(dateParts.get(1)).year(dateParts.get(2)).build();
  }

}
