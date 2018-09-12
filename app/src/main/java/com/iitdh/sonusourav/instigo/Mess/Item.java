package com.iitdh.sonusourav.instigo.Mess;

import android.view.View;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.Calendar;


@SuppressWarnings({"unused", "WeakerAccess"})
public class Item {

    private String messPart;
    private String messTime;
    private String messItems;
    private String messRatings;
    private int day;

    private View.OnClickListener submitBtnClickListener;
    private RatingBar.OnRatingBarChangeListener ratingBarChangeListener;

    public Item() {
    }

    public Item(int day,String messPart, String messTime, String messItems, String messRatings) {
        this.day=day;
        this.messPart = messPart;
        this.messTime = messTime;
        this.messItems = messItems;
        this.messRatings = messRatings;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMessPart() {
        return messPart;
    }

    public void setMessPart(String messPart) {
        this.messPart = messPart;
    }

    public String getMessTime() {
        return messTime;
    }

    public void setMessTime(String messTime) {
        this.messTime = messTime;
    }

    public String getMessItems() {
        return messItems;
    }

    public void setMessItems(String messItems) {
        this.messItems = messItems;
    }

    public String getMessRatings() {
        return messRatings;
    }

    public void setMessRatings(String messRatings) {
        this.messRatings = messRatings;
    }

    public View.OnClickListener getSubmitBtnClickListener() {
        return submitBtnClickListener;
    }

    public void setSubmitBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.submitBtnClickListener = requestBtnClickListener;
    }

    public RatingBar.OnRatingBarChangeListener getRatingBarClickListener() {

        return ratingBarChangeListener;
    }

    public void setRatingBarClickListener(RatingBar.OnRatingBarChangeListener ratingBarClickListener) {
        this.ratingBarChangeListener = ratingBarClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (messPart != null ? !messPart.equals(item.messPart) : item.messPart != null)
            return false;
        if (messTime != null ? !messTime.equals(item.messTime) : item.messTime != null)
            return false;
        if (messItems != null ? !messItems.equals(item.messItems) : item.messItems != null)
            return false;


        return messRatings != null ? messRatings.equals(item.messRatings) : item.messRatings == null;

    }

    @Override
    public int hashCode() {
        int result = messPart != null ? messPart.hashCode() : 0;
        result = 31 * result + (messTime != null ? messTime.hashCode() : 0);
        result = 31 * result + (messItems != null ? messItems.hashCode() : 0);
        result = 31 * result + (messRatings != null ? messRatings.hashCode() : 0);

        return result;
    }

    public static ArrayList<Item> makeMenu(int index) {

        ArrayList<Item> sundayMenu = new ArrayList<>();
        ArrayList<Item> mondayMenu = new ArrayList<>();
        ArrayList<Item> tuesdayMenu = new ArrayList<>();
        ArrayList<Item> wednesdayMenu = new ArrayList<>();
        ArrayList<Item> thursdayMenu = new ArrayList<>();
        ArrayList<Item> fridayMenu = new ArrayList<>();
        ArrayList<Item> saturdayMenu = new ArrayList<>();

        sundayMenu.add(new Item(0,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "1Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        sundayMenu.add(new Item(0,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        sundayMenu.add(new Item(0,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        sundayMenu.add(new Item(0,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        mondayMenu.add(new Item(1,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "2Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        mondayMenu.add(new Item(1,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        mondayMenu.add(new Item(1,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        mondayMenu.add(new Item(1,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        tuesdayMenu.add(new Item(2,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "3Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        tuesdayMenu.add(new Item(2,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        tuesdayMenu.add(new Item(2,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        tuesdayMenu.add(new Item(2,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));


        wednesdayMenu.add(new Item(3,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "4Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        wednesdayMenu.add(new Item(3,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        wednesdayMenu.add(new Item(3,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        wednesdayMenu.add(new Item(3,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        thursdayMenu.add(new Item(4,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "5Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        thursdayMenu.add(new Item(4,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        thursdayMenu.add(new Item(4,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        thursdayMenu.add(new Item(4,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        fridayMenu.add(new Item(5,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "6Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        fridayMenu.add(new Item(5,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        fridayMenu.add(new Item(5,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        fridayMenu.add(new Item(5,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        saturdayMenu.add(new Item(6,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "7Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        saturdayMenu.add(new Item(6,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        saturdayMenu.add(new Item(6,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        saturdayMenu.add(new Item(6,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        switch (index) {
            case 0:
                return sundayMenu;

            case 1:
                return mondayMenu;

            case 2:

                return tuesdayMenu;

            case 3:
                return wednesdayMenu;

            case 4:
                return thursdayMenu;

            case 5:
                return fridayMenu;

            case 6: {
                return saturdayMenu;
            }

            default:
                return sundayMenu;


        }
    }
    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList() {
        ArrayList<Item>sunday = new ArrayList<>();
        ArrayList<Item>monday = new ArrayList<>();
        ArrayList<Item>tuesday = new ArrayList<>();
        ArrayList<Item>wednesday = new ArrayList<>();
        ArrayList<Item>thursday = new ArrayList<>();
        ArrayList<Item>friday = new ArrayList<>();
        ArrayList<Item>saturday = new ArrayList<>();

        sunday.add(new Item(0,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "1Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        sunday.add(new Item(0,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        sunday.add(new Item(0,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        sunday.add(new Item(0,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        monday.add(new Item(1,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "2Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        monday.add(new Item(1,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        monday.add(new Item(1,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        monday.add(new Item(1,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        tuesday.add(new Item(2,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "3Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        tuesday.add(new Item(2,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        tuesday.add(new Item(2,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        tuesday.add(new Item(2,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));


        wednesday.add(new Item(3,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "4Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        wednesday.add(new Item(3,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        wednesday.add(new Item(3,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        wednesday.add(new Item(3,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        thursday.add(new Item(4,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "5Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        thursday.add(new Item(4,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        thursday.add(new Item(4,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        thursday.add(new Item(4,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        friday.add(new Item(5,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "6Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        friday.add(new Item(5,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        friday.add(new Item(5,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        friday.add(new Item(5,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        saturday.add(new Item(6,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "7Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        saturday.add(new Item(6,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        saturday.add(new Item(6,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        saturday.add(new Item(6,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                return sunday;

            case Calendar.MONDAY:
                // Current day is Monday
                return monday;

            case Calendar.TUESDAY:
                // etc.
                return tuesday;

            case Calendar.WEDNESDAY:
                return wednesday;

            case Calendar.THURSDAY:
                return thursday;

            case Calendar.FRIDAY:
                return friday;

            case Calendar.SATURDAY:{
                return saturday;
            }

                default:return sunday;



        }



    }

}
