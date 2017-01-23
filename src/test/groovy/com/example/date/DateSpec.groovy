package com.example.date

import spock.lang.Specification

class DateSpec extends Specification {

  def setup() {
  }

  def cleanup() {
  }

  def "test invalid year"() {
    when:
    def result = Date.builder().year(year).month(1).day(1).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Year $year is out of range [1900 - 2010]".toString()

    where:
    year << [1800, 2011]
  }

  def "test year not provided"() {
    when:
    def result = Date.builder().month(1).day(1).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Year 0 is out of range [1900 - 2010]".toString()
  }

  def "test invalid month"() {
    when:
    def result = Date.builder().year(2000).month(month).day(1).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Month $month is out of range [1 - 12]".toString()

    where:
    month << [-1, 13]
  }

  def "test month not provided"() {
    when:
    def result = Date.builder().year(2000).day(1).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Month 0 is out of range [1 - 12]".toString()
  }

  def "test invalid day for 31-day months"() {
    when:
    def result = Date.builder().year(2000).month(month).day(32).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Day 32 is out of range [1 - 31]".toString()

    where:
    month << [1, 3, 5, 7, 8, 10, 12]
  }

  def "test invalid day for 30-day months"() {
    when:
    def result = Date.builder().year(2000).month(month).day(31).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Day 31 is out of range [1 - 30]".toString()

    where:
    month << [4, 6, 9, 11]
  }

  def "test day not provided"() {
    when:
    def result = Date.builder().year(2000).month(1).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Day 0 is out of range [1 - 31]".toString()
  }

  def "test invalid day for 28-day months"() {
    when:
    def result = Date.builder().year(1999).month(2).day(29).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Day 29 is out of range [1 - 28]".toString()
  }

  def "test invalid day for 29-day months"() {
    when:
    def result = Date.builder().year(2000).month(2).day(30).build()

    then:
    result.isLeft()
    result.left.class == IllegalArgumentException
    result.left.message == "Day 30 is out of range [1 - 29]".toString()
  }

  def "test valid date"() {
    when:
    def result = Date.builder().year(year).month(month).day(day).build()

    then:
    result.isRight()
    result.right().get().year == year
    result.right().get().month == month
    result.right().get().day == day

    where:
    year << [1999, 2000, 1999, 1900]
    month << [1, 2, 2, 4]
    day << [31, 29, 28, 30]
  }

}
