package com.example.date

import com.example.date.input.InputParser
import com.example.date.model.Date
import com.example.date.model.DateCalculator
import com.example.date.output.Output
import javaslang.control.Either
import spock.lang.Specification

class CommandLineExecutorSpec extends Specification {

  CommandLineExecutor executor
  InputParser inputParser
  DateCalculator dateCalculator
  Output output

  def setup() {
    inputParser = Mock()
    dateCalculator = Stub()
    output = Mock()
    executor = new CommandLineExecutor(inputParser, dateCalculator, output)
  }

  def cleanup() {
  }

  def "test if error occurs in parser"() {
    when:
    inputParser.parse(input) >> Either.left(new Exception("some error"))
    executor.update(new Observable(), input)

    then:
    1 * output.display("some error")
    0 * dateCalculator.daysBetweenDates()

    where:
    input = "some input"
  }

  def "test if diff is calculated"() {
    when:
    def date1 = new Date(year: 2000, month: 1, day: 1)
    def date2 = new Date(year: 2001, month: 1, day: 1)
    inputParser.parse(input) >> Either.right([date1, date2])
    executor.update(new Observable(), input)

    then:
    1 * output.display("some input, 0")

    where:
    input = "some input"
  }

}
