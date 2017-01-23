package com.example.date

import spock.lang.Specification

class DateDefinitionSpec extends Specification {

  DateDefinition dateDefinition

  def setup() {
    dateDefinition = new DateDefinition()
  }

  def cleanup() {
  }

  def "test leap year"() {
    when:
    def date = new Date(year: year)
    def result = dateDefinition.isLeap().apply(date.year)

    then:
    result == isLeap

    where:
    year << [1900, 2000, 1999]
    isLeap << [false, true, false]
  }

  def "test get last day for month"() {
    when:
    def date = new Date(year: year, month: month, day: day)
    def result = dateDefinition.getLastDayForMonth().apply(date)

    then:
    result == lastDay

    where:
    year << [1999, 1999, 2000, 2001]
    month << [1, 2, 2, 4]
    day << [2, 20, 20, 4]
    lastDay << [31, 28, 29, 30]
  }

}
