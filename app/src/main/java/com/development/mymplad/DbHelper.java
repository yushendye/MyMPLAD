package com.development.mymplad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "MyMPLad.db";


    private final static String TBL_LOGIN_TABLE = "tbl_User";
    private final static String COL_LOGIN_ID = "id";
    private final static String COL_FNAME = "fname";
    private final static String COL_LNAME = "lname";
    private final static String COL_MNAME = "mname";
    private final static String COL_USERNAME = "username";
    private final static String COL_STATE = "state";
    private final static String COL_DISTRIACT = "district";
    private final static String COL_ADDRESS = "address";
    private final static String COL_AADHAR = "addhar";
    private final static String COL_CONTACT = "contact";
    private final static String COL_PASSWORD = "password";
    private final static String COL_GEN = "gender";

    private final static String TBL_MP_LOGIN = "tbl_MpLogin";
    private final static String COL_MP__ID = "id";
    private final static String COL_MP_USERNAME = "username";
    private final static String COL_MP_PWD = "password";
    private final static String COL_MP_NAME = "name";
    private final static String COL_MP_STATE = "state";

    private final static String TBL_MP_SUGGESTION = "tbl_Suggestion";
    private final static String SUGG_COL_SUGG_ID = "suggestion_id";
    private final static String SUGG_COL_MP_ID = "id_mp_assigned";
    private final static String SUGG_COL_WORK_SEC = "work_sector";
    private final static String SUGG_COL_DIS = "work_district";
    private final static String SUGG_COL_ST = "work_state";
    private final static String SUGG_COL_TITLE = "work_name";
    private final static String SUGG_COL_DESC = "work_desc";
    private final static String SUGG_COL_CT_NAME = "ct_name";
    private final static String SUGG_COL_CT_MOBILE = "ct_mobile";
    private final static String SUGG_COL_CT_MAIL = "ct_email";
    private final static String SUGG_COL_CT_ADD = "ct_address";
    private final static String SUGG_COL_CT_AREA = "area";
    private final static String SUGG_COL_CT_STATUS = "status";

    String STRING = " string, ";
    String INTEGER = " integer";
    String PRIMARY = " primary key, ";

    public DbHelper(Context context){

        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN = "CREATE TABLE " + TBL_LOGIN_TABLE + "(" + COL_LOGIN_ID + " integer primary key, " + COL_FNAME + " string,"
                + COL_LNAME + " string, " + COL_MNAME + " string, " + COL_USERNAME + " string, " + COL_STATE + " string, " + COL_GEN + " string, " +
                COL_DISTRIACT + " string, " + COL_ADDRESS + " string, " + COL_AADHAR + " string, " + COL_CONTACT + " string, " +
                COL_PASSWORD + " string)";

        String CREATE_MP_LOGIN = "create table " + TBL_MP_LOGIN + "(" + COL_MP__ID + " integer, " + COL_MP_USERNAME + " string,"
                + COL_MP_PWD + " string, " + COL_MP_NAME + " string, " + COL_MP_STATE + " string)";
                ;
        String CREATE_SUGGESTION = "create table " + TBL_MP_SUGGESTION + "(" + SUGG_COL_SUGG_ID + INTEGER + PRIMARY + SUGG_COL_MP_ID  + INTEGER + "," + SUGG_COL_WORK_SEC + STRING +
                SUGG_COL_DIS + STRING  + SUGG_COL_ST + STRING + SUGG_COL_TITLE + STRING  +  SUGG_COL_DESC + STRING  + SUGG_COL_CT_NAME + STRING  + SUGG_COL_CT_MOBILE + STRING  +  SUGG_COL_CT_MAIL + STRING +
                SUGG_COL_CT_ADD + STRING  + SUGG_COL_CT_AREA + STRING  + SUGG_COL_CT_STATUS + "STRING" + ")";

        db.execSQL(CREATE_LOGIN);
        db.execSQL(CREATE_MP_LOGIN);
        db.execSQL(CREATE_SUGGESTION);
    }

    public void  user_register(int id, String fname, String lname, String mname, String username, String state, String district, String address, String gender, String contact, String aadhar, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_LOGIN_ID, id);
        values.put(COL_FNAME, fname);
        values.put(COL_LNAME, lname);
        values.put(COL_MNAME, mname);
        values.put(COL_USERNAME, username);
        values.put(COL_STATE, state);
        values.put(COL_DISTRIACT, district);
        values.put(COL_ADDRESS, address);
        values.put(COL_CONTACT, contact);
        values.put(COL_AADHAR, aadhar);
        values.put(COL_USERNAME, username);
        values.put(COL_GEN, gender);
        values.put(COL_PASSWORD, password);

        db.insert(TBL_LOGIN_TABLE, null, values);
        db.close();
    }
    public List<User> login(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        List<User> user_details = new ArrayList<>();
        String qry = "select * from tbl_user where " + COL_MP_USERNAME + "= '" + username + "' and " + COL_PASSWORD + "= '" + password + "'";
        Cursor result = db.rawQuery(qry, null);
        result.moveToFirst();

        while (result.isAfterLast() == false){
            User user = new User();
            user.setId(result.getInt(0));
            user.setFname(result.getString(1));
            user.setLname(result.getString(2));
            user.setMname(result.getString(3));
            user.setUsername(result.getString(4));
            user.setState(result.getString(5));
            user.setGender(result.getString(6));
            user.setDistrict(result.getString(7));
            user.setAddress(result.getString(9));
            user.setAadhar(result.getString(9));
            user.setContact(result.getString(10));
            user.setPassword(result.getString(11));
            user_details.add(user);
            result.moveToNext();
        }
        return user_details;
    }

    public List<Complaint> getComplaints(int mp_id){
        String qry = "select * from tbl_Suggestion where " + SUGG_COL_MP_ID + "=" + mp_id;
        return new ArrayList<>();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TBL_MP_SUGGESTION);
        db.execSQL("drop table if exists " + TBL_MP_LOGIN);
        db.execSQL("drop table if exists " + TBL_LOGIN_TABLE);

        onCreate(db);
    }
}
