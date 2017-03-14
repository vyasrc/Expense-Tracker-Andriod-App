package com.example.vyas.myapplication;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

        import android.database.sqlite.SQLiteOpenHelper;



        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Locale;


//Created by VYAS on 27/01/2017.

class MyDB extends SQLiteOpenHelper {
            private static final String DBNAME="mydb.db";
            private static final int VERSION=1;


            private static final String TABLE_NAME="account_book";
            public static final String DATE="date";
            public static final String ACTIVITY="activity";
            public static final String AMOUNT="amount";
            private SQLiteDatabase MyD;


            public MyDB(Context context) {

                super(context, DBNAME, null, VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {


                String queryTable="CREATE TABLE "+TABLE_NAME+"("
                        +ACTIVITY+" TEXT NOT NULL,"
                        +DATE+" TEXT NOT NULL,"
                        +AMOUNT+" REAL NOT NULL"+")";

                db.execSQL(queryTable);

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }

            public void openDB(){
                MyD=getWritableDatabase();
            }

            public void closeDB() {
                if (MyD != null && MyD.isOpen()) MyD.close();
            }

            public long insert(String activity, Double amount){
                ContentValues values = new ContentValues();



                Calendar c = Calendar.getInstance();
                SimpleDateFormat sf= new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

                //System.out.println(c.getTime());
                //System.out.println("#####################");

                // fees , 3000
                //date will be 07-Feb-2017
                String queryDate=sf.format(c.getTime());

                String substr=queryDate.substring(3);
                //System.out.println(substr);
                //  String date= String.valueOf(MyD.rawQuery(queryDate,null));
                Cursor count= MyD.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE DATE LIKE " +
                        "'%-"+substr+"' AND " + ACTIVITY+"='"+activity+"'", null, null);
                //System.out.println(count.isAfterLast()+"aaak");
                if(count.isAfterLast()) {
                    values.put(DATE, queryDate);
                    values.put(ACTIVITY, activity);
                    values.put(AMOUNT, amount);
                }
                count.close();
         return MyD.insert(TABLE_NAME, null, values);
    }

    public long update(String activity,String month, Double amount){
         ContentValues values = new ContentValues();
         values.put(ACTIVITY,activity);
         values.put(AMOUNT,amount);

        String where = ACTIVITY + "='"+ activity+"' AND DATE LIKE " + "'%-"+month+"'";
          //  MyD.update()
         return MyD.update(TABLE_NAME, values, where, null);
    }

    public long  delete(String activity , String month){

        String where = ACTIVITY + "='"+ activity+"' AND DATE LIKE " + "'%-"+month+"'";

         return MyD.delete(TABLE_NAME, where, null);
    }

    public Cursor getAllRecords(String searchString){


        return MyD.rawQuery("SELECT * FROM "+TABLE_NAME
                +" WHERE "+DATE+" LIKE '%"+searchString+"'"
                ,null,null);
        //for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
          //  System.out.println(cursor.getString(cursor.getColumnIndex(MyDB.ACTIVITY))+"");

    }

    public Cursor getTotalSum(String searchString){

        return MyD.rawQuery("SELECT SUM(AMOUNT) AS TOTAL FROM "+TABLE_NAME+" WHERE "+DATE+" LIKE '%"+searchString+"'",null,null);
        //for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
        //  System.out.println(cursor.getString(cursor.getColumnIndex(MyDB.ACTIVITY))+"");

    }

    public ArrayList<String> getAllLabels(String searchString){
        ArrayList<String> labels = new ArrayList<>();
        System.out.println(searchString);

        // Select All Query
        //String selectQuery = "SELECT  "+ACTIVITY+" FROM " + TABLE_NAME+" WHERE "+DATE+" LIKE '%"+searchString+"'";

       // SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = MyD.rawQuery("SELECT  "+ACTIVITY+" FROM " + TABLE_NAME+" WHERE "
                +DATE+" LIKE '%"+searchString+"'", null,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());

        }
            cursor.close();
        return labels;
    }

    public Cursor getCurrentAmount(String activity){

        return MyD.rawQuery("SELECT AMOUNT FROM "+TABLE_NAME+" " +
                "WHERE "+ACTIVITY+"='"+activity+"'",null,null);

    }
}
