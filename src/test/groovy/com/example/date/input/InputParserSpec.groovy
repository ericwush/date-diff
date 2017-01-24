package com.example.date.input

import com.example.date.input.DateParser
import com.example.date.input.InputParser
import com.example.date.model.Date
import javaslang.control.Either
import spock.lang.Specification

class InputParserSpec extends Specification {

  InputParser inputParser
  DateParser dateParser

  def setup() {
    dateParser = Mock()
    inputParser = new InputParser(dateParser)
  }

  def cleanup() {
  }

  def "test more or less than 2 dates input"() {
    when:
    def result = inputParser.parse(input)

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "More or less than 2 dates input"

    where:
    input << ["1 2 2000", "2 3 1999, 5 3 2000, 45"]
  }

  def "test any exception occur in date parser"() {
    when:
    dateParser.parse("date1") >> Either.right(new Date(year: 2000, month: 10, day: 1))
    dateParser.parse("date2") >> Either.left(new IllegalArgumentException("error"))

    def result = inputParser.parse("date1,date2")

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "error"
  }

  def "test no exception occur in date parser"() {
    when:
    dateParser.parse("date1") >> Either.right(new Date(year: 2000, month: 10, day: 1))
    dateParser.parse("date2") >> Either.right(new Date(year: 1999, month: 2, day: 1))

    def result = inputParser.parse("date1,date2")

    then:
    result.isRight()
    result.get().contains(new Date(year: 2000, month: 10, day: 1))
    result.get().contains(new Date(year: 1999, month: 2, day: 1))
  }

}
