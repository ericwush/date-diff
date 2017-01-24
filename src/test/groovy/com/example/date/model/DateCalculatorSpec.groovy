package com.example.date.model

import spock.lang.Specification

class DateCalculatorSpec extends Specification {

  DateCalculator calculator

  def setup() {
    calculator = new DateCalculator(DateDefinition.getInstance())
  }

  def cleanup() {
  }

  def "test day of year"() {
    expect:
    days == calculator.dayOfYear().apply(new Date(year: year, month: month, day: day))

    where:
    year << [1999, 1999, 2000, 2001]
    month << [1, 3, 3, 6]
    day << [30, 10, 10, 30]
    days << [30, 69, 70, 181]
  }

  def "test days between years"() {
    expect:
    days == calculator.daysBetweenYears().apply(startYear, endYear)

    where:
    startYear << [2000, 1999, 1999]
    endYear << [2000, 2000, 2001]
    days << [0, 365, 731]
  }

  def "test days between dates"() {
    expect:
    def startDate = new Date(year: startYear, month: startMonth, day: startDay)
    def endDate = new Date(year: endYear, month: endMonth, day: endDay)
    days == calculator.daysBetweenDates().apply(startDate, endDate)

    where:
    startYear << [2000, 1999, 1900, 2000]
    startMonth << [2, 5, 1, 5]
    startDay << [1, 13, 2, 30]
    endYear << [2000, 2001, 2010, 2000]
    endMonth << [5, 11, 12, 2]
    endDay << [30, 11, 30, 1]
    days << [119, 913, 40539, -119]
  }

}
