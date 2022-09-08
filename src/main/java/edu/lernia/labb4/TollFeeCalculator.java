package edu.lernia.labb4;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {
   private String outputMessage;

   public String getOutputMessage() {
      return outputMessage;
   }

  /* public void setOutputMessage(String outputMessage) {
      this.outputMessage = outputMessage;
   }*/


   public TollFeeCalculator(String inputFile) {
      try {
         Scanner sc = new Scanner(new File(inputFile));
         String[] dateStrings = sc.nextLine().split(", ");
         LocalDateTime[] dates = new LocalDateTime[dateStrings.length - 1];
         for ( int i = 0; i < dates.length; i++ ) {
            dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
         }
         outputMessage = ("The total fee for the inputfile is " + getTotalFeeCost(dates));
         System.out.println(outputMessage);
      } catch (IOException e) {
         System.err.println("Could not read file " + new File(inputFile).getAbsolutePath());
      }
   }

   public static int getTotalFeeCost(LocalDateTime[] dates) {
      int totalFee = 0;
      LocalDateTime intervalStart = dates[0];
      for ( LocalDateTime date : dates ) {
         /*System.out.println(date.toString());*/
         long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
         if (diffInMinutes > 60 || diffInMinutes == 0) {
            totalFee += getTollFeePerPassing(date);
            intervalStart = date;
         } else {
            if (getTollFeePerPassing(date) > getTollFeePerPassing(intervalStart)) {
               totalFee += getTollFeePerPassing(date);
               totalFee -= getTollFeePerPassing(intervalStart);
               intervalStart = date;
            }
         }
      }
      return Math.min(totalFee, 60);
   }

   public static int getTollFeePerPassing(LocalDateTime date) {
      if (isTollFreeDate(date)) return 0;
      int hour = date.getHour();
      int minute = date.getMinute();

      if (hour == 6 && minute <= 29) return 8;
      else if (hour == 6) return 13;
      else if (hour == 7) return 18;
      else if (hour == 8 && minute <= 29) return 13;
      else if ((hour >= 8 && hour <= 14)) return 8;
      else if (hour == 15 && minute <= 29) return 13;
      else if (hour == 15 || hour == 16) return 18;
      else if (hour == 17) return 13;
      else if (hour == 18 && minute <= 29) return 8;
      else return 0;
   }

   public static boolean isTollFreeDate(LocalDateTime date) {
      return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7 || isDayHoliday(date);
   }

   public static boolean isDayHoliday(LocalDateTime date) {
      int year = date.getYear();
      int month = date.getMonthValue();
      int day = date.getDayOfMonth();

      if (year == 2022) {
         if (month == 1 && day == 1 || month == 1 && day == 6) {
            return true;
         } else if (month == 4 && day == 15 || month == 4 && day == 18) {
            return true;
         } else if (month == 5 && day == 26) {
            return true;
         } else if (month == 6 && day == 6) {
            return true;
         } else return month == 12 && day == 26;
      } else if (year == 2021) {
         if (month == 1 && day == 1 || month == 1 && day == 6) {
            return true;
         } else if (month == 4 && day == 2 || month == 4 && day == 5) {
            return true;
         } else return month == 5 && day == 13;

      } else if (year == 2020) {
         if (month == 1 && day == 1 || month == 1 && day == 6) {
            return true;
         } else if (month == 4 && day == 10 || month == 4 && day == 13) {
            return true;
         } else if (month == 5 && day == 1 || month == 5 && day == 21) {
            return true;
         } else return month == 12 && day == 25;
      } else {
         return false;
      }
   }

   public static void main(String[] args) {
      new TollFeeCalculator("src/test/resources/Lab4.txt");
   }
}

