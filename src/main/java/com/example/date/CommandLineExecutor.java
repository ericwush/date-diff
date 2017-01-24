package com.example.date;

import com.example.date.input.InputParser;
import com.example.date.model.Date;
import com.example.date.model.DateCalculator;
import com.example.date.output.Output;
import javaslang.control.Either;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CommandLineExecutor implements Observer {

  private final InputParser inputParser;
  private final DateCalculator dateCalculator;
  private final Output output;

  public CommandLineExecutor(final InputParser inputParser,
                             final DateCalculator dateCalculator,
                             final Output output) {
    this.inputParser = inputParser;
    this.dateCalculator = dateCalculator;
    this.output = output;
  }

  @Override
  public void update(final Observable listener, final Object commandLineString) {
    Either<Throwable, List<Date>> maybeDates = inputParser.parse((String) commandLineString);
    if (maybeDates.isLeft()) {
      output.display(maybeDates.getLeft().getMessage());
      return;
    }
    List<Date> dates = maybeDates.get();
    int diff = dateCalculator.daysBetweenDates().apply(dates.get(0), dates.get(1));
    output.display(((String) commandLineString).trim() + ", " + diff);
  }

}
