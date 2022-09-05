package edu.lernia.labb4;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class TollFeeCalculator {

    public TollFeeCalculator(String inputFile) {
        try {
            Scanner sc = new Scanner(new File(inputFile));
            String[] dateStrings = sc.nextLine().split(", ");
            LocalDateTime[] dates = new LocalDateTime[dateStrings.length-1];
            for(int i = 0; i < dates.length; i++) {
                dates[i] = LocalDateTime.parse(dateStrings[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }
            System.out.println("The total fee for the inputfile is " + getTotalFeeCost(dates));
        } catch(IOException e) {
            System.err.println("Could not read file " + new File(inputFile).getAbsolutePath());
        }
    }

    public static int getTotalFeeCost(LocalDateTime[] dates) {
        int totalFee = 0;
        LocalDateTime intervalStart = dates[0];
        for(LocalDateTime date: dates) {
            System.out.println(date.toString());
            long diffInMinutes = intervalStart.until(date, ChronoUnit.MINUTES);
            if(diffInMinutes > 60) {
                totalFee += getTollFeePerPassing(date);
                intervalStart = date;
            }
            else {
                //totalFee += Math.max(getTollFeePerPassing(date), getTollFeePerPassing(intervalStart));
                if(getTollFeePerPassing(date) > getTollFeePerPassing(intervalStart))
                {
                    totalFee += getTollFeePerPassing(date);
                    totalFee -= getTollFeePerPassing(intervalStart);
                    intervalStart = date;
                }
            }
        }
        return Math.min(totalFee, 60); //ändrad till math.min ist, annars returnerar bara 60 hela tiden (som är det större talet)
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
       /* System.out.println("this date" + (date.getDayOfWeek().getValue() == 6));*/
        return date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7 || date.getMonth().getValue() == 7;
    }

    public static void main(String[] args) {
        new TollFeeCalculator("src/test/resources/Lab4.txt");

    }
}

//test för
//* rätt deb/ klockslag/ fm
//rätt deb/klockslag em/kväll
//rätt avgiftsfri deb natt
//* rätt deb avgiftsfri dag (även helgdagar)
//* rätt deb om flrea tider passerats inom 1 h och då bed dyraste zonpriset
//* deb stannar vid 60kr maxtak
//*rätt deb i juli
//test att resultat blir mindre än 60 och returnerar rätt tal
//test att resultat blir över 60 och returnerar 60.