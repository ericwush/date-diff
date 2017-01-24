package com.example.date.input

import spock.lang.Specification

class DateParserSpec extends Specification {

  DateParser parser

  def setup() {
    parser = new DateParser()
  }

  def cleanup() {
  }

  def "test parse date from string that has more or less than 3 args"() {
    when:
    def result = parser.parse(dateString)

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "More or less than 3 arguments to form a date"

    where:
    dateString << ["1 2", "1 2 2010 3"]
  }

  def "test parse date from string that has args cannot be converted to integer"() {
    when:
    def result = parser.parse(dateString)

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Cannot convert to date"

    where:
    dateString << ["1 2 a", "c 2 2010"]
  }

  def "test parse date from string but got invalid date"() {
    when:
    def result = parser.parse(dateString)

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == message

    where:
    dateString << ["2 29 1999", "33 9 2000"]
    message << ["Month 29 is out of range [1 - 12]", "Day 33 is out of range [1 - 30]"]
  }

  def "test parse date from string for a valid date"() {
    when:
    def result = parser.parse("09 03 2000")

    then:
    result.isRight()
    result.get().year == 2000
    result.get().month == 3
    result.get().day == 9
  }

}
