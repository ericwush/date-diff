package com.example.date.input;

import com.example.date.model.Date;
import javaslang.control.Either;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class InputParser {

  private final DateParser dateParser;

  public InputParser(final DateParser dateParser) {
    this.dateParser = dateParser;
  }

  public Either<Throwable, List<Date>> parse(final String input) {
    String[] inputs = input.trim().split(",");
    if (inputs.length != 2) {
      return Either.left(new IllegalArgumentException("More or less than 2 dates input"));
    }
    List<Either<Throwable, Date>> maybeDateList = Arrays.stream(inputs)
        .map(dateParser::parse)
        .collect(toList());
    Optional<Either<Throwable, Date>> maybeException = maybeDateList.stream()
        .filter(Either::isLeft)
        .findFirst();
    if (maybeException.isPresent()) {
      return Either.left(maybeException.get().getLeft());
    } else {
      List<Date> dates = maybeDateList.stream()
          .map(Either::get)
          .collect(toList());
      return Either.right(dates);
    }

  }

}
