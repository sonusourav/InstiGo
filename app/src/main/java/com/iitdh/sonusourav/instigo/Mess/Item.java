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

        sundayMenu.add(new Item(0,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Puri," + "Aalo Chana Gravy," + "Bhaji," + "Coconut Chutney," + "Moong Sprouts," + "Omelette," + "Seasonal Fruit," + "Paneer Bhurji.", null));
        sundayMenu.add(new Item(0,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Schezwan Rice,\n" + "Moong Dal,\n" + "Plain Roti, Butter Roti,\n" + "Malai Kofta,\n" + "Baingan Ka Bharta,\n" + "Rasana.", null));
        sundayMenu.add(new Item(0,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Veg Cutlet,\n" + "Ice Tea,One Seasonal Drink(MilkShake)/Fruit per day, BBJ, Tea, Coffee, Ketchup", null));
        sundayMenu.add(new Item(0,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Veg Pulao,\n" + "Plain Roti, Butter Roti,\n" + "Dal Fry,\n" + "Carrot Beans Dry,\n" + "Shahi Paneer,\n" + "Rasgulla", null));

        mondayMenu.add(new Item(1,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        mondayMenu.add(new Item(1,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Lemon Rice,\n" + "Tur Dal,\n" + "Plain roti, Butter Roti,\n" + "Mutter Paneer,\n" +"Aalo Cabbage,\n" + "Curd,\n" + "Lemonade", null));
        mondayMenu.add(new Item(1,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Bhel Puri,\n" + "Ice Tea", null));
        mondayMenu.add(new Item(1,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Veg Biryani,\n" + "Plain Roti, Butter Roti,\n" + "Dal Kolhapuri,\n" + "Aalo Capsicum,\n" + "Dry Lauki Chana Gravy,\n" + "Vanilla Ice Cream.", null));

        tuesdayMenu.add(new Item(2,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Aalo Paratha,\n" + "Curd,\n" + "Chickpea Sprouts,\n" + "Egg Bhurji,\n" + "Seasonal Fruit,\n" + "Sweet Corn.", null));
        tuesdayMenu.add(new Item(2,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Jeera Rice,\n" + "Palak Dal,\n" + "Plain Roti, Butter Roti,\n" + "Veg Kolhapuri,\n" + "Bhindi Fry,\n" + "ButterMilk", null));
        tuesdayMenu.add(new Item(2,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Pav Bhaji,\n" + "Cold Coffee", null));
        tuesdayMenu.add(new Item(2,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Pudina Rice,\n" + "Plain Roti, Butter Roti,\n" + "Moong Dal,\n" + "Chola Dry,\n" + "Methi Malai Butter Gravy,\n" + "Gulab Jamun-2 pcs", null));


        wednesdayMenu.add(new Item(3,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Upma,\n" + "Sheera,\n" + "Namkeen,\n" + "Coconut Chutney,\n" + "Moong Sprouts,\n" + "Omelette,\n" + "Banana,\n" + "Paneer Bhurji", null));
        wednesdayMenu.add(new Item(3,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Tomato Rice,\n" +"Chana Dal,\n" + "Plain Roti, Butter Roti,\n" + "Aalo Mutter Curry,\n" + "Lauki Ki Sabzi,\n" + "Custard,\n" + "Rasna", null));
        wednesdayMenu.add(new Item(3,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Aalo Masala Sandwhich +\n" + "Cheese,\n" + "Ice Tea", null));
        wednesdayMenu.add(new Item(3,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Veg Fried Rice,\n" + "Plain Roti, Butter Roti,\n" + "Tur Dal,\n" + "Tawa Paneer,\n" + "Sambhar,\n" + "Besan Ladoo" + "Amritsari Bhindi,\n" + "Babycorn Aalo Gravy,\n" + "Cold Rice Kheer", null));

        thursdayMenu.add(new Item(4,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Poha,\n" + "Usal Matki,\n" + "Sev/Namkeen,\n" + "Chickpea Sprouts,\n" + "Omelette,\n" + "Banana,\n" + "Sweet Corn", null));
        thursdayMenu.add(new Item(4,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Ghee Rice,\n" + "Dal Tadka,\n" + "Plain Roti, Butter Roti,\n" + "Paneer Butter Masala,\n" + "Aalo Gobhi,\n" + "Lassi", null));
        thursdayMenu.add(new Item(4,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Maggi,\n" + "Cold Coffee", null));
        thursdayMenu.add(new Item(4,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Puliyogara Rice,\n" + "Plain Roti, Butter Roti,\n" + "Maasoor Dal,\n" + "Amritsari Bhindi,\n" + "Babycorn Aalo Gravy,\n" + "Cold Rice Kheer", null));

        fridayMenu.add(new Item(5,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Masala Dosa,\n" + "Coconut Chutney,\n" + "Sambhar,\n" + "Moong Sprouts,\n" + "Egg Bhurji,\n" + "Seasonal Fruit,\n" + "Paneer Bhurji", null));
        fridayMenu.add(new Item(5,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Onion Rice,\n" + "Dal Makhni,\n" + "Plain Roti, Butter Roti,\n" + "Chole Bhature (2 Pcs),\n" + "Pumpkin Dry Curry,\n" + "Curd,\n" + "Jal Jeera", null));
        fridayMenu.add(new Item(5,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Bread Pakoda,\n" + "Ice Tea.", null));
        fridayMenu.add(new Item(5,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Tomato Peas Masala Rice,\n" + "Plain Roti, Butter Roti,\n" + "Dal Tadka,\n" + "SoyaBean Masala,\n" + "Gulab Jamun-2 pcs", null));

        saturdayMenu.add(new Item(6,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Methi Thepla,\n" + "Curd,\n" + "Chickpea Sprouts,\n" + "Boiled Egg,\n" + "Banana,\n" + "Sweet Corn", null));
        saturdayMenu.add(new Item(6,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Curd Rice,\n" + "Dal Kolhapuri,\n" + "Plain Roti, Butter Roti,\n" + "Rajma,\n" + "Mix Veg,\n" + "Curd,\n" + "Tang", null));
        saturdayMenu.add(new Item(6,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Samosa,\n" + "Cold Coffee", null));
        saturdayMenu.add(new Item(6,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Carrot Masala Rice,\n" + "Plain Roti, Butter Roti,\n" + "Chana Dal,\n" + "Corn Palak,\n" + "Veg Makhanwala,\n" + "Butter Scotch Ice Cream", null));

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

        sunday.add(new Item(0,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Puri,\n" + "Aalo Chana Gravy,\n" + "Bhaji,\n" + "Coconut Chutney,\n" + "Moong Sprouts,\n" + "Omelette,\n" + "Seasonal Fruit,\n" + "Paneer Bhurji.", null));
        sunday.add(new Item(0,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Schezwan Rice,\n" + "Moong Dal,\n" + "Plain Roti, Butter Roti,\n" + "Malai Kofta,\n" + "Baingan Ka Bharta,\n" + "Rasana.", null));
        sunday.add(new Item(0,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Veg Cutlet,\n" + "Ice Tea,One Seasonal Drink(MilkShake)/Fruit per day, BBJ, Tea, Coffee, Ketchup", null));
        sunday.add(new Item(0,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Veg Pulao,\n" + "Plain Roti, Butter Roti,\n" + "Dal Fry,\n" + "Carrot Beans Dry,\n" + "Shahi Paneer,\n" + "Rasgulla", null));

        monday.add(new Item(1,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Idli , Medu Vada ,\nCoconut Chutney , Sambhar ,\n Moong Sprouts , Boiled egg ,\nBanana ,Paneer Bhurji", null));
        monday.add(new Item(1,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice," + "Lemon Rice,\n" + "Tur Dal," + "Plain & Butter Roti,\n" + "Mutter Paneer," +"Aalo Cabbage,\n" + "Curd," + "Lemonade", null));
        monday.add(new Item(1,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Bhel Puri," + "Ice Tea", null));
        monday.add(new Item(1,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice," + "Veg Biryani,\n" + "Plain Roti, Butter Roti,\n" + "Dal Kolhapuri," + "Aalo Capsicum,\n" + "Dry Lauki Chana Gravy,\n" + "Vanilla Ice Cream.", null));

        tuesday.add(new Item(2,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Aalo Paratha,\n" + "Curd,\n" + "Chickpea Sprouts,\n" + "Egg Bhurji,\n" + "Seasonal Fruit,\n" + "Sweet Corn.", null));
        tuesday.add(new Item(2,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Jeera Rice,\n" + "Palak Dal,\n" + "Plain Roti, Butter Roti,\n" + "Veg Kolhapuri,\n" + "Bhindi Fry,\n" + "ButterMilk", null));
        tuesday.add(new Item(2,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Pav Bhaji,\n" + "Cold Coffee", null));
        tuesday.add(new Item(2,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Pudina Rice,\n" + "Plain Roti, Butter Roti,\n" + "Moong Dal,\n" + "Chola Dry,\n" + "Methi Malai Butter Gravy,\n" + "Gulab Jamun-2 pcs", null));


        wednesday.add(new Item(3,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Upma,\n" + "Sheera,\n" + "Namkeen,\n" + "Coconut Chutney,\n" + "Moong Sprouts,\n" + "Omelette,\n" + "Banana,\n" + "Paneer Bhurji", null));
        wednesday.add(new Item(3,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Tomato Rice,\n" +"Chana Dal,\n" + "Plain Roti, Butter Roti,\n" + "Aalo Mutter Curry,\n" + "Lauki Ki Sabzi,\n" + "Custard,\n" + "Rasna", null));
        wednesday.add(new Item(3,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Aalo Masala Sandwhich +\n" + "Cheese,\n" + "Ice Tea", null));
        wednesday.add(new Item(3,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Veg Fried Rice,\n" + "Plain Roti, Butter Roti,\n" + "Tur Dal,\n" + "Tawa Paneer,\n" + "Sambhar,\n" + "Besan Ladoo" + "Amritsari Bhindi,\n" + "Babycorn Aalo Gravy,\n" + "Cold Rice Kheer", null));

        thursday.add(new Item(4,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Poha,\n" + "Usal Matki,\n" + "Sev/Namkeen,\n" + "Chickpea Sprouts,\n" + "Omelette,\n" + "Banana,\n" + "Sweet Corn", null));
        thursday.add(new Item(4,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Ghee Rice,\n" + "Dal Tadka,\n" + "Plain Roti, Butter Roti,\n" + "Paneer Butter Masala,\n" + "Aalo Gobhi,\n" + "Lassi", null));
        thursday.add(new Item(4,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Maggi,\n" + "Cold Coffee", null));
        thursday.add(new Item(4,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Puliyogara Rice,\n" + "Plain Roti, Butter Roti,\n" + "Maasoor Dal,\n" + "Amritsari Bhindi,\n" + "Babycorn Aalo Gravy,\n" + "Cold Rice Kheer", null));

        friday.add(new Item(5,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Masala Dosa,\n" + "Coconut Chutney,\n" + "Sambhar,\n" + "Moong Sprouts,\n" + "Egg Bhurji,\n" + "Seasonal Fruit,\n" + "Paneer Bhurji", null));
        friday.add(new Item(5,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Onion Rice,\n" + "Dal Makhni,\n" + "Plain Roti, Butter Roti,\n" + "Chole Bhature (2 Pcs),\n" + "Pumpkin Dry Curry,\n" + "Curd,\n" + "Jal Jeera", null));
        friday.add(new Item(5,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Bread Pakoda,\n" + "Ice Tea.", null));
        friday.add(new Item(5,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Tomato Peas Masala Rice,\n" + "Plain Roti, Butter Roti,\n" + "Dal Tadka,\n" + "SoyaBean Masala,\n" + "Gulab Jamun-2 pcs", null));

        saturday.add(new Item(6,"B\nR\nE\nA\nK\nF\nA\nS\nT", "7:30 - 9:30 am", "" + "Methi Thepla,\n" + "Curd,\n" + "Chickpea Sprouts,\n" + "Boiled Egg,\n" + "Banana,\n" + "Sweet Corn", null));
        saturday.add(new Item(6,"L\nU\nN\nC\nH\n", "12:00 - 2:15 pm", "" + "Steamed Rice,\n" + "Curd Rice,\n" + "Dal Kolhapuri,\n" + "Plain Roti, Butter Roti,\n" + "Rajma,\n" + "Mix Veg,\n" + "Curd,\n" + "Tang", null));
        saturday.add(new Item(6,"S\nN\nA\nC\nK\nS", "4:45 - 6:15 pm", "" + "Samosa,\n" + "Cold Coffee", null));
        saturday.add(new Item(6,"D\nI\nN\nN\nE\nR", "7:30 - 9:45 pm", "" + "Steamed Rice,\n" + "Carrot Masala Rice,\n" + "Plain Roti, Butter Roti,\n" + "Chana Dal,\n" + "Corn Palak,\n" + "Veg Makhanwala,\n" + "Butter Scotch Ice Cream", null));

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
