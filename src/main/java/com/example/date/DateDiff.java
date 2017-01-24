package com.example.date;

import com.example.date.input.DateParser;
import com.example.date.input.InputParser;
import com.example.date.model.DateCalculator;
import com.example.date.model.DateDefinition;
import com.example.date.output.Output;

public class DateDiff {

  // Main entrance of the program
  public static void main(final String[] args) {
    DateParser dateParser = new DateParser();
    InputParser inputParser = new InputParser(dateParser);
    DateDefinition dateDefinition = DateDefinition.getInstance();
    DateCalculator dateCalculator = new DateCalculator(dateDefinition);
    Output output = new Output();
    CommandLineExecutor commandLineExecutor = new CommandLineExecutor(inputParser, dateCalculator, output);
    CommandLineListener commandLineListener = new CommandLineListener();
    commandLineListener.addObserver(commandLineExecutor);
    new Thread(commandLineListener).start();
  }

}
