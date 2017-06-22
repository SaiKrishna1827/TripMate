package com.tripmate;

import android.app.models.AddExpenseByPersonModel;
import android.app.models.PersonWiseExpensesSummaryModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.app.models.ExpenseModel;
import android.app.models.PersonModel;
import android.app.models.TripModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sai Krishna on 6/16/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TripExpenseManager";
    private static final int DATABASE_VERSION = 1;

    private static final String TRIPS_TABLE_NAME = "trips";
    private static final String TRIPS_COLUMN_ID = "key_trip_id";
    private static final String TRIPS_COLUMN_TRIP_NAME = "key_trip_name";
    private static final String TRIPS_COLUMN_TRIP_DESC = "key_trip_desc";
    private static final String TRIPS_COLUMN_TRIP_DATE = "key_trip_date";
    private static final String TRIPS_COLUMN_TRIP_PLACES = "key_trip_places";
    private static final String TRIPS_COLUMN_TRIP_TOTAL_AMOUNT = "key_trip_total_amt";


    private static final String ITEMS_TABLE_NAME = "items";
    private static final String ITEMS_COLUMN_TRIP_ID = "key_trip_id";
    private static final String ITEMS_COLUMN_ITEM_NAME = "key_item_name";
    private static final String ITEMS_COLUMN_AMOUNT_TYPE = "key_amount_type";
    private static final String ITEMS_COLUMN_ITEM_AMOUNT = "key_item_amt";
    private static final String ITEMS_COLUMN_ITEM_EXP_BY = "key_item_exp_by";
    private static final String ITEMS_COLUMN_ITEM_CAT = "key_item_cat";
    private static final String ITEMS_COLUMN_ITEM_DATE = "key_item_date";
    private static final String ITEMS_COLUMN_ITEM_SHARE_BY_TYPE = "key_item_share_by_type";
    private static final String ITEMS_COLUMN_ITEM_SHARE_BY = "key_item_share_by";
    private static final String ITEMS_COLUMN_ITEM_DATE_VALUE = "key_item_date_value";
    private static final String ITEMS_COLUMN_ITEM_ID = "key_item_id";


    private static final String PERSONS_TABLE_NAME = "persons";
    private static final String PERSONS_COLUMN_TRIP_ID = "key_trip_id";
    private static final String PERSONS_COLUMN_PERSON_NAME = "key_person_name";
    private static final String PERSONS_COLUMN_PERSON_MOBILE = "key_person_mobile";
    private static final String PERSONS_COLUMN_PERSON_EMAIL = "key_person_email";
    private static final String PERSONS_COLUMN_PERSON_DEPOSIT = "key_person_deposit";
    private static final String PERSONS_COLUMN_PERSON_ADMIN = "key_person_admin";


    private static final String NOTES_TABLE_NAME = "notes";
    private static final String NOTES_COLUMN_NOTE_ID = "key_note_id";
    private static final String NOTES_COLUMN_TRIP_ID = "key_trip_id";
    private static final String NOTES_COLUMN_NOTE_TITLE = "key_note_title";
    private static final String NOTES_COLUMN_NOTE_CONTENT_TYPE = "key_note_content_type";
    private static final String NOTES_COLUMN_NOTE_CONTENT = "key_note_content";
    private static final String NOTES_COLUMN_NOTE_CONTENT_STATUS = "key_note_content_status";

    private static final String CATEGORIES_TABLE_NAME = "categories";
    private static final String CATEGORIES_COLUMN_CAT_NAME = "key_cat_name";




    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TRIPS_TABLE = "CREATE TABLE " + TRIPS_TABLE_NAME + " ( "+ TRIPS_COLUMN_ID + " TEXT PRIMARY KEY, " + TRIPS_COLUMN_TRIP_NAME + " TEXT, "+ TRIPS_COLUMN_TRIP_DESC + " TEXT, "+ TRIPS_COLUMN_TRIP_DATE+" TEXT, "+TRIPS_COLUMN_TRIP_PLACES+" TEXT, "+TRIPS_COLUMN_TRIP_TOTAL_AMOUNT+" REAL )";
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + ITEMS_TABLE_NAME + "("+ ITEMS_COLUMN_ITEM_ID + " TEXT PRIMARY KEY," + ITEMS_COLUMN_TRIP_ID + " TEXT,"+ ITEMS_COLUMN_ITEM_NAME + " TEXT, "+ITEMS_COLUMN_AMOUNT_TYPE+" INTEGER, "+ ITEMS_COLUMN_ITEM_AMOUNT+ " REAL, "+ITEMS_COLUMN_ITEM_EXP_BY+" TEXT, "+ITEMS_COLUMN_ITEM_CAT+" TEXT, "+ITEMS_COLUMN_ITEM_DATE+" TEXT, "+ITEMS_COLUMN_ITEM_SHARE_BY_TYPE+" INTEGER, "+ITEMS_COLUMN_ITEM_SHARE_BY+" TEXT, "+ITEMS_COLUMN_ITEM_DATE_VALUE+" TEXT )";
        String CREATE_PERSONS_TABLE = "CREATE TABLE " + PERSONS_TABLE_NAME + "("+ PERSONS_COLUMN_TRIP_ID + " TEXT," + PERSONS_COLUMN_PERSON_NAME + " TEXT,"+ PERSONS_COLUMN_PERSON_MOBILE + " TEXT, "+PERSONS_COLUMN_PERSON_EMAIL+" TEXT,"+ PERSONS_COLUMN_PERSON_DEPOSIT+" REAL, "+PERSONS_COLUMN_PERSON_ADMIN+" INTEGER )";
        String CREATE_NOTES_TABLE = "CREATE TABLE " + NOTES_TABLE_NAME + "("+ NOTES_COLUMN_NOTE_ID + " TEXT PRIMARY KEY," + NOTES_COLUMN_TRIP_ID + " TEXT,"+ NOTES_COLUMN_NOTE_TITLE + " TEXT, " +NOTES_COLUMN_NOTE_CONTENT_TYPE+" INTEGER, "+NOTES_COLUMN_NOTE_CONTENT+" TEXT, "+ NOTES_COLUMN_NOTE_CONTENT_STATUS + " TEXT )";
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE "+CATEGORIES_TABLE_NAME+" ( "+CATEGORIES_COLUMN_CAT_NAME+" TEXT)";

        db.execSQL(CREATE_TRIPS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_PERSONS_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);

        String cats[] = {"Drink","Entertainment","Food","Hotel","Medical","Miscellaneous","Parking","Shopping","Toll","Travel"};

        for(int i=0;i<cats.length;i++){
            ContentValues values = new ContentValues();
            values.put(CATEGORIES_COLUMN_CAT_NAME,cats[i]);
            db.insert(CATEGORIES_TABLE_NAME,null,values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TRIPS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ITEMS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PERSONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+NOTES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CATEGORIES_TABLE_NAME);
        onCreate(db);
    }

    public boolean addTrip(TripModel trip){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRIPS_COLUMN_ID,trip.getTrip_id());
        values.put(TRIPS_COLUMN_TRIP_NAME,trip.getTrip_name());
        values.put(TRIPS_COLUMN_TRIP_PLACES,trip.getTrip_places());
        values.put(TRIPS_COLUMN_TRIP_DESC,trip.getTrip_desc());
        values.put(TRIPS_COLUMN_TRIP_DATE,trip.getTrip_date());
        values.put(TRIPS_COLUMN_TRIP_TOTAL_AMOUNT,trip.getTrip_amount());

        db.insert(TRIPS_TABLE_NAME,null,values);
        return true;
    }

    public String[] getCategories(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CATEGORIES_TABLE_NAME,null,null,null,null,null,null);
        String[] catList = new String[cursor.getCount()];
        if(cursor!=null && cursor.moveToFirst()){
            int i=0;
            do{
                catList[i] = cursor.getString(cursor.getColumnIndex(CATEGORIES_COLUMN_CAT_NAME));
                i++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  catList;
    }

    public String[] getPersonsListAsString(String trip_id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(PERSONS_TABLE_NAME,new String[]{PERSONS_COLUMN_PERSON_NAME},PERSONS_COLUMN_TRIP_ID+"=?",new String[]{trip_id},null,null,null);
        String[] personsList = new String[cursor.getCount()];
        if(cursor!=null && cursor.moveToFirst()){
            int i=0;
            do{
                personsList[i] = cursor.getString(0);
                i++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  personsList;
    }




    public void addPersons(String trip_id , ArrayList<PersonModel> personsList){
        SQLiteDatabase db = getWritableDatabase();
        for (PersonModel personModel : personsList) {
            ContentValues values = new ContentValues();
            values.put(PERSONS_COLUMN_TRIP_ID,trip_id);
            values.put(PERSONS_COLUMN_PERSON_NAME, personModel.getName());
            values.put(PERSONS_COLUMN_PERSON_MOBILE, personModel.getMobile());
            values.put(PERSONS_COLUMN_PERSON_EMAIL, personModel.getEmail());
            values.put(PERSONS_COLUMN_PERSON_DEPOSIT, personModel.getDeposit());
            values.put(PERSONS_COLUMN_PERSON_ADMIN, personModel.getAdmin());
            db.insert(PERSONS_TABLE_NAME,null,values);
        }
    }

    public ArrayList<PersonModel> getPersons(String trip_id){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PersonModel> personsList = new ArrayList<>();

        Cursor cursor = db.query(PERSONS_TABLE_NAME,null,PERSONS_COLUMN_TRIP_ID+"=?",new String[]{trip_id},
                null,null,null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                PersonModel personModel = new PersonModel();
                personModel.setName(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_NAME)));
                personModel.setMobile(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_MOBILE)));
                personModel.setEmail(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_EMAIL)));
                personModel.setDeposit(cursor.getDouble(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_DEPOSIT)));
                personModel.setAdmin(cursor.getInt(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_ADMIN)));

                personsList.add(personModel);

            }while(cursor.moveToNext());
        }
        cursor.close();

        return  personsList;
    }


    public boolean addExpense(String trip_id,String description,String category,String date,int amount_share_by_type,String expShareByPersonsSelected,int amount_type,ArrayList<AddExpenseByPersonModel> expenseByPersonList,Double fromDepositExpense,Long date_value){
        SQLiteDatabase db = getWritableDatabase();

        if(amount_type == 1){

            ContentValues values = new ContentValues();
            values.put(ITEMS_COLUMN_TRIP_ID,trip_id);
            values.put(ITEMS_COLUMN_ITEM_NAME,description);
            values.put(ITEMS_COLUMN_AMOUNT_TYPE,amount_type);
            values.put(ITEMS_COLUMN_ITEM_AMOUNT,fromDepositExpense);
            values.put(ITEMS_COLUMN_ITEM_CAT,category);
            values.put(ITEMS_COLUMN_ITEM_EXP_BY,"Deposit Money");
            values.put(ITEMS_COLUMN_ITEM_SHARE_BY_TYPE,amount_share_by_type);
            values.put(ITEMS_COLUMN_ITEM_SHARE_BY,expShareByPersonsSelected);
            values.put(ITEMS_COLUMN_ITEM_DATE,date);
            values.put(ITEMS_COLUMN_ITEM_DATE_VALUE,date_value);
            values.put(ITEMS_COLUMN_ITEM_ID,"ITEM"+ UUID.randomUUID().toString());

            db.insert(ITEMS_TABLE_NAME,null,values);

        }
        else if(amount_type == 2){

            for(int i=0;i<expenseByPersonList.size();i++){
                ContentValues values = new ContentValues();
                values.put(ITEMS_COLUMN_TRIP_ID,trip_id);
                values.put(ITEMS_COLUMN_ITEM_NAME,description);
                values.put(ITEMS_COLUMN_AMOUNT_TYPE,amount_type);
                values.put(ITEMS_COLUMN_ITEM_CAT,category);
                values.put(ITEMS_COLUMN_ITEM_SHARE_BY_TYPE,amount_share_by_type);
                values.put(ITEMS_COLUMN_ITEM_SHARE_BY,expShareByPersonsSelected);
                values.put(ITEMS_COLUMN_ITEM_DATE,date);
                values.put(ITEMS_COLUMN_ITEM_DATE_VALUE,date_value);
                values.put(ITEMS_COLUMN_ITEM_ID,"ITEM"+ UUID.randomUUID().toString());

                values.put(ITEMS_COLUMN_ITEM_AMOUNT,expenseByPersonList.get(i).getAmount());
                values.put(ITEMS_COLUMN_ITEM_EXP_BY,expenseByPersonList.get(i).getName());

                db.insert(ITEMS_TABLE_NAME,null,values);
            }

        }

        return true;
    }


    public ArrayList<TripModel> getTripsData() {
        ArrayList<TripModel> trip_array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+TRIPS_TABLE_NAME, null );

        if (cursor.moveToFirst()) {
            do {
                TripModel model = new TripModel();
                model.setTrip_name(cursor.getString(cursor.getColumnIndex(TRIPS_COLUMN_TRIP_NAME)));
                model.setTrip_date(cursor.getString(cursor.getColumnIndex(TRIPS_COLUMN_TRIP_DATE)));
                model.setTrip_amount(cursor.getDouble(cursor.getColumnIndex(TRIPS_COLUMN_TRIP_TOTAL_AMOUNT)));
                model.setTrip_desc(cursor.getString(cursor.getColumnIndex(TRIPS_COLUMN_TRIP_DESC)));
                model.setTrip_id(cursor.getString(cursor.getColumnIndex(TRIPS_COLUMN_ID)));
                model.setTrip_places(cursor.getString(cursor.getColumnIndex(TRIPS_COLUMN_TRIP_PLACES)));

                trip_array_list.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return trip_array_list;
    }



    public ArrayList<ExpenseModel> getAllExpenses(String trip_id){
        ArrayList<ExpenseModel> expenseModelArrayList = new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor = db.query(ITEMS_TABLE_NAME,null,ITEMS_COLUMN_TRIP_ID + "=?",new String[]{trip_id},null,null,null);

        if(cursor!=null && cursor.moveToFirst()){
            do {
                ExpenseModel expenseModel = new ExpenseModel();
                expenseModel.setTripId(trip_id);
                expenseModel.setItemName(cursor.getString(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_NAME)));
                expenseModel.setAmount(cursor.getDouble(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_AMOUNT)));
                expenseModel.setAmountType(cursor.getInt(cursor.getColumnIndex(ITEMS_COLUMN_AMOUNT_TYPE)));
                expenseModel.setExpBy(cursor.getString(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_EXP_BY)));
                expenseModel.setCategory(cursor.getString(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_CAT)));
                expenseModel.setDate(cursor.getString(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_DATE)));
                expenseModel.setShareByType(cursor.getInt(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_SHARE_BY_TYPE)));
                expenseModel.setShareBy(cursor.getString(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_SHARE_BY)));
                expenseModel.setItemId(cursor.getString(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_ID)));
                expenseModel.setDateValue(cursor.getLong(cursor.getColumnIndex(ITEMS_COLUMN_ITEM_DATE_VALUE)));

                expenseModelArrayList.add(expenseModel);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return expenseModelArrayList;

    }


    public Double getAmountOfPerson(String trip_id,String personName){
        Double amount = 0.0;
        ArrayList<ExpenseModel> expenseModelArrayList = getAllExpenses(trip_id);

        for(ExpenseModel e : expenseModelArrayList){
            if(e.getExpBy().equalsIgnoreCase(personName))
                amount+=e.getAmount();
        }

        return  amount;
    }

    public Double getAmountOfCategory(String trip_id,String category){
        Double amount = 0.0;
        ArrayList<ExpenseModel> expenseModelArrayList = getAllExpenses(trip_id);

        for(ExpenseModel e : expenseModelArrayList){
            if(e.getCategory().equalsIgnoreCase(category))
                amount+=e.getAmount();
        }

        return  amount;
    }

    public Double getAmountOfDate(String trip_id,String date){
        Double amount = 0.0;
        ArrayList<ExpenseModel> expenseModelArrayList = getAllExpenses(trip_id);

        for(ExpenseModel e : expenseModelArrayList){
            if(e.getDate().equalsIgnoreCase(date))
                amount+=e.getAmount();
        }

        return  amount;
    }

    public Double getAmountShared(String trip_id,String personName){
        Double amount = 0.0;
        int noOfPersons = getPersons(trip_id).size();
        ArrayList<ExpenseModel> expenseModelArrayList = getAllExpenses(trip_id);

        for(ExpenseModel e : expenseModelArrayList){
            if(e.getShareBy().equalsIgnoreCase("all")){
                amount+=e.getAmount()/noOfPersons;
            }else if(e.getShareBy().toLowerCase().contains(personName.toLowerCase())){
                int noOfSharedPersons = Arrays.asList(e.getShareBy().split(",")).size();
                amount+=e.getAmount()/noOfSharedPersons;
            }
        }

        return  amount;
    }




    public ArrayList<PersonWiseExpensesSummaryModel> getPersonWiseExpensesSummary(String trip_id){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<PersonWiseExpensesSummaryModel> result = new ArrayList<>();

        HashMap<String,Double> depositAmountShareByPerson = new HashMap<>();
        HashMap<String,Double> personalAmountShareByPerson = new HashMap<>();
        HashMap<String,Double> personalAmountGiven = new HashMap<>();

        Cursor cursor = db.query(PERSONS_TABLE_NAME,null,PERSONS_COLUMN_TRIP_ID+"=?",new String[]{trip_id},
                null,null,null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                PersonWiseExpensesSummaryModel model = new PersonWiseExpensesSummaryModel();
                model.setName(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_NAME)));
                model.setMobile(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_MOBILE)));
                model.setEmail(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_EMAIL)));
                model.setDepositAmountGiven(cursor.getDouble(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_DEPOSIT)));
                model.setAdmin(cursor.getInt(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_ADMIN)));

                depositAmountShareByPerson.put(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_NAME)),0.0);
                personalAmountShareByPerson.put(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_NAME)),0.0);
                personalAmountGiven.put(cursor.getString(cursor.getColumnIndex(PERSONS_COLUMN_PERSON_NAME)),0.0);


                result.add(model);

            }while(cursor.moveToNext());
        }
        cursor.close();

        int no_of_persons = result.size();

        ArrayList<ExpenseModel> allExpensesList = getAllExpenses(trip_id);

        Double totalDepositShare = 0.0,totalPersonalShare = 0.0;


        for(int i=0;i<allExpensesList.size();i++){
            ExpenseModel expenseModel = allExpensesList.get(i);

            if(expenseModel.getAmountType() == 1){

                if(expenseModel.getShareByType() == 1){
                    totalDepositShare += (expenseModel.getAmount()/no_of_persons);
                }else{

                    String persons[] = expenseModel.getShareBy().split(",");
                    int no_of_share_by_deposit = persons.length;
                    Double sharedDepositAmount = expenseModel.getAmount()/no_of_share_by_deposit;

                    for(int j=0;j<no_of_share_by_deposit;j++){
                        Double itemToBeAdded = depositAmountShareByPerson.get(persons[j].trim())+ sharedDepositAmount;
                        depositAmountShareByPerson.put(persons[j].trim(),itemToBeAdded);
                    }

                }

            }else{

                Double itemToBeAddedPersonal = personalAmountGiven.get(expenseModel.getExpBy()) + expenseModel.getAmount();
                personalAmountGiven.put(expenseModel.getExpBy(),itemToBeAddedPersonal);

                if(expenseModel.getShareByType() == 1){
                    totalPersonalShare += (expenseModel.getAmount()/no_of_persons);
                }else{

                    String persons[] = expenseModel.getShareBy().split(",");
                    int no_of_share_by_personal_amount = persons.length;
                    Double sharedPersonalAmount = expenseModel.getAmount()/no_of_share_by_personal_amount;

                    for(int j=0;j<no_of_share_by_personal_amount;j++){
                        Double itemToBeAdded = personalAmountShareByPerson.get(persons[j].trim())+ sharedPersonalAmount;
                        personalAmountShareByPerson.put(persons[j].trim(),itemToBeAdded);
                    }


                }

            }
        }

        for(int i=0;i<result.size();i++){

            result.get(i).setDepositAmountSpent(depositAmountShareByPerson.get(result.get(i).getName()));
            result.get(i).setPersonalAmountSpent(personalAmountShareByPerson.get(result.get(i).getName()));
            result.get(i).setPersonalAmountGiven(personalAmountGiven.get(result.get(i).getName()));

            result.get(i).setDepositAmountSpent(result.get(i).getDepositAmountSpent()+totalDepositShare);
            result.get(i).setDepositAmountRemaining(result.get(i).getDepositAmountGiven()-result.get(i).getDepositAmountSpent());

            result.get(i).setPersonalAmountSpent(result.get(i).getPersonalAmountSpent()+totalPersonalShare);
            result.get(i).setPersonalAmountRemaining(result.get(i).getPersonalAmountGiven()-result.get(i).getPersonalAmountSpent());

            result.get(i).setTotalAmountGiven(result.get(i).getDepositAmountGiven()+result.get(i).getPersonalAmountGiven());
            result.get(i).setTotalAmountSpent(result.get(i).getDepositAmountSpent() + result.get(i).getPersonalAmountSpent());
            result.get(i).setTotalAmountRemaining(result.get(i).getDepositAmountRemaining()+result.get(i).getPersonalAmountRemaining());


            result.get(i).setDepositAmountGiven(RoundOff(result.get(i).getDepositAmountGiven()));
            result.get(i).setDepositAmountSpent(RoundOff(result.get(i).getDepositAmountSpent()));
            result.get(i).setDepositAmountRemaining(RoundOff(result.get(i).getDepositAmountRemaining()));
            result.get(i).setPersonalAmountGiven(RoundOff(result.get(i).getPersonalAmountGiven()));
            result.get(i).setPersonalAmountSpent(RoundOff(result.get(i).getPersonalAmountSpent()));
            result.get(i).setPersonalAmountRemaining(RoundOff(result.get(i).getPersonalAmountRemaining()));
            result.get(i).setTotalAmountGiven(RoundOff(result.get(i).getTotalAmountGiven()));
            result.get(i).setTotalAmountSpent(RoundOff(result.get(i).getTotalAmountSpent()));
            result.get(i).setTotalAmountRemaining(RoundOff(result.get(i).getTotalAmountRemaining()));

        }

        Collections.sort(result, new Comparator<PersonWiseExpensesSummaryModel>() {
            @Override
            public int compare(PersonWiseExpensesSummaryModel o1, PersonWiseExpensesSummaryModel o2) {
                return o2.getTotalAmountGiven().compareTo(o1.getTotalAmountGiven());

            }
        });

        return  result;
    }


    void editPerson(String trip_id,PersonModel model){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PERSONS_COLUMN_PERSON_MOBILE,model.getMobile());
        cv.put(PERSONS_COLUMN_PERSON_EMAIL,model.getEmail());
        cv.put(PERSONS_COLUMN_PERSON_DEPOSIT,model.getDeposit());

        db.update(PERSONS_TABLE_NAME, cv,PERSONS_COLUMN_TRIP_ID + " = \"" + trip_id+"\" AND "+PERSONS_COLUMN_PERSON_NAME+" = \""+model.getName()+"\"", null);
    }

    void addAsAdmin(String trip_id,String name,String pastAdmin){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PERSONS_COLUMN_PERSON_ADMIN,1);
        db.update(PERSONS_TABLE_NAME, cv,PERSONS_COLUMN_TRIP_ID + " = \"" + trip_id+"\" AND "+PERSONS_COLUMN_PERSON_NAME+" = \""+name+"\"", null);


        ContentValues cv1 = new ContentValues();
        cv1.put(PERSONS_COLUMN_PERSON_ADMIN,0);
        db.update(PERSONS_TABLE_NAME, cv1,PERSONS_COLUMN_TRIP_ID + " = \"" + trip_id+"\" AND "+PERSONS_COLUMN_PERSON_NAME+" = \""+pastAdmin+"\"", null);

    }


    public Double RoundOff(Double d){
        return Math.round(d * 100.0) / 100.0;
    }



}
