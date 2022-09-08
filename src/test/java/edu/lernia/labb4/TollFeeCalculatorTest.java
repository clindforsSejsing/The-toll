package edu.lernia.labb4;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TollFeeCalculatorTest {


   private void assertArrayEquals(String expectedMessage, String message) {

      if (Objects.equals(expectedMessage, message)) {
         System.out.println("they are the same");
      } else {
         fail("not expected output of totalFee.");
      }
   }

   @Test
   public void getTotalFeeCostOfSaturday() {
      //Test to see that a saturday is free of charge.
      String testFile = "src/test/resources/aSaturday.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 0";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTotalFeeCostOfSunday() {
      //Test to see that a sunday is free of charge.
      String testFile = "src/test/resources/aSunday.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 0";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTotalFeeCostOfHoliday() {
      //Test to see that a holiday is free of charge.
      String testFile = "src/test/resources/aHoliday.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 0";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTotalFeeCostOfDayInJuly() {
      //Test to see that a day in july is free of charge.
      String testFile = "src/test/resources/dayInJuly.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 0";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTotalFeeCostOfaDayThatsNotAHolidayButSameDayOnlyAYearLater() {

      String testFile = "src/test/resources/NotAHoliday.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 55";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTotalFeeCostOfaDayThatsAleapYearAndASaturday() {

      String testFile = "src/test/resources/aSaturdayLeapYear.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 0";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTotalFeeCostOfaDayThatsAleapYearAndAMonday() {

      String testFile = "src/test/resources/leapYear.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 55";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);

   }

   @Test
   public void getTollFeePerPassingMaximumFee() {

      String testFile = "src/test/resources/maximumPayment.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 60";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);
   }

   @Test
   public void getTollFeeOnlyDebOnceIfMultiplePassagesInAnHour() {
      //test to see that fee is correct if multiple tolls is passed during one hour

      String testFile = "src/test/resources/multiplePassingsInAnHour.txt";
      TollFeeCalculator aTest = new TollFeeCalculator(testFile);

      String expectedMessage = "The total fee for the inputfile is 21";
      String message = (aTest.getOutputMessage());
      assertArrayEquals(expectedMessage, message);
   }

   @Test
   public void getTollFeeGetsRightAmountOfFeePerPassage() {
      //test to see that fee returns right amount of fee per passing from file "multiplePassingsInAnHour.txt".

      LocalDateTime testDateTimeOne = LocalDateTime.parse("2022-02-28 06:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultOne = TollFeeCalculator.getTollFeePerPassing(testDateTimeOne);
      assertEquals(8, resultOne);

      LocalDateTime testDateTimeTwo = LocalDateTime.parse("2022-02-28 06:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultTwo = TollFeeCalculator.getTollFeePerPassing(testDateTimeTwo);
      assertEquals(8, resultTwo);

      LocalDateTime testDateTimeThree = LocalDateTime.parse("2022-02-28 06:40", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultThree = TollFeeCalculator.getTollFeePerPassing(testDateTimeThree);
      assertEquals(13, resultThree);

      LocalDateTime testDateTimeFour = LocalDateTime.parse("2022-02-28 14:25", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultFour = TollFeeCalculator.getTollFeePerPassing(testDateTimeFour);
      assertEquals(8, resultFour);

      LocalDateTime testDateTimeFive = LocalDateTime.parse("2022-02-28 14:39", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultFive = TollFeeCalculator.getTollFeePerPassing(testDateTimeFive);
      assertEquals(8, resultFive);

      LocalDateTime testDateTimeSix = LocalDateTime.parse("2022-02-28 14:55", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultSix = TollFeeCalculator.getTollFeePerPassing(testDateTimeSix);
      assertEquals(8, resultSix);

      LocalDateTime testDateTimeSeven = LocalDateTime.parse("2022-02-28 21:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
      int resultSeven = TollFeeCalculator.getTollFeePerPassing(testDateTimeSeven);
      assertEquals(0, resultSeven);

   }

   public String hourOrMinuteToAddUp(int hoursOrMinutes) {
      if (hoursOrMinutes < 10) {
         return "0" + hoursOrMinutes;
      }
      return Integer.toString(hoursOrMinutes);
   }

   @Test
   public void getTollFeePerPassingAfterSixInTheMorning() {
      //test expected toll to be 8 kr between time : 06:00-06:29.


      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      min = 0;
      hr = 6;
      expectedToll = 8;

      while (min < 30) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
      }
   }

   @Test
   public void getTollFeePerPassingForPassingBeforeSeven()
   //test expected toll to be 13kr between time: 06:30-06:59.
   {

      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      min = 30;
      hr = 6;
      expectedToll = 13;

      while (min < 60) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
      }
   }

   @Test
   public void getTollFeePerPassingForPassingBetweenSevenAndEight() {
      //test expected toll to be 18kr between time: 07:00-07:59.

      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      min = 0;
      hr = 7;
      expectedToll = 18;

      while (min < 60) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
      }
   }

   @Test
   public void getTollFeePerPassingForPassingAfterEight() {
      //test expected toll to be 13kr between time: 08:00-08:29.

      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      hr = 8;
      min = 0;
      expectedToll = 13;

      while (min < 30) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
      }
   }

   @Test
   public void getTollFeePerPassingForPassingBetweenNineAnBeforeThree() {
      //test expected toll to be 8kr between time: 8.30-14.30

      int result;
      int min;
      int hr;
      hr = 8;
      min = 30;
      int expectedToll;
      String timeToTest;

      expectedToll = 8;

      while (hr < 14 || (hr == 14 && min <= 30)) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
         if (min == 60) {
            min = 0;
            hr++;
         }

      }
   }

   @Test
   public void getTollFeePerPassingAfterThree() {
      //test expected toll to be 13kr between time: 15:00-15:29.

      int result;
      int min;
      int hr;

      min = 0;
      hr = 15;
      int expectedToll;
      String timeToTest;

      expectedToll = 13;

      while (min < 30) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
      }
   }

   @Test
   public void getTollFeePerPassingHalfPastFour() {
      //test expected toll to be 18kr between time: 15:30-16:59.

      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      min = 30;
      hr = 15;
      expectedToll = 18;

      while (hr < 17) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
         if (min == 60) {
            min = 0;
            hr++;
         }
      }
   }

   @Test
   public void getTollFeePerPassingAfterFive() {
      //test expected toll to be 13kr between time: 17:00-17:59.

      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      min = 0;
      hr = 17;
      expectedToll = 13;

      while (min < 60) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
      }
   }

   @Test
   public void getTollFeePerPassingAfterSixInTheAfterNoon() {
      //test expected toll to be 8kr between time: 18:00-18:29.

      int result;
      int min;
      int hr;
      int expectedToll;
      String timeToTest;

      min = 0;
      hr = 18;
      expectedToll = 8;

      while (min < 30) {
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;

      }
   }

   @Test
   public void getTollFeePerPassingForPassingDuringNightAndEarlyMorning() {
      //test expected toll to be 0kr between time: 18:30-05:59
      int result;
      int min;
      int hr;
      int morningH;

      hr = 18;
      morningH = 0;
      min = 30;
      int expectedToll;
      String timeToTest;
      expectedToll = 0;

      while (hr > 17) { //time between 18.30 and 23.59
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(hr) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
         if (min == 60) {
            min = 0;
            hr++;
         }
         if (hr == 24) {
            hr = 0;
         }
      }
      while (morningH < 5) { //time between 24.00 and 05.59
         timeToTest = "2022-02-28 " + hourOrMinuteToAddUp(morningH) + ":" + hourOrMinuteToAddUp(min);
         LocalDateTime testDateTime = LocalDateTime.parse(timeToTest, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         result = TollFeeCalculator.getTollFeePerPassing(testDateTime);
         assertEquals(expectedToll, result);
         min++;
         if (min == 60) {
            min = 0;
            morningH++;
         }
      }
   }
}
