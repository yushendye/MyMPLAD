package com.development.mymplad;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class DbHelper extends SQLiteOpenHelper {
    Context context;

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
    private final static String SUGG_COL_CT_ADD = "ct_address";
    private final static String SUGG_COL_CT_STATUS = "status";

    String STRING = " string, ";
    String INTEGER = " integer";
    String PRIMARY = " primary key, ";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN = "CREATE TABLE " + TBL_LOGIN_TABLE + "(" + COL_LOGIN_ID + " integer primary key autoincrement, " + COL_FNAME + " string,"
                + COL_LNAME + " string, " + COL_MNAME + " string, " + COL_USERNAME + " string unique, " + COL_STATE + " string, " + COL_GEN + " string, " +
                COL_DISTRIACT + " string, " + COL_ADDRESS + " string, " + COL_AADHAR + " string, " + COL_CONTACT + " string, " +
                COL_PASSWORD + " string)";

        String CREATE_MP_LOGIN = "create table " + TBL_MP_LOGIN + "(" + COL_MP__ID + " string, " + COL_MP_USERNAME + " string unique,"
                + COL_MP_PWD + " string, " + COL_MP_NAME + " string)";
                ;
        /*String CREATE_SUGGESTION = "create table " + TBL_MP_SUGGESTION + "(" +
                SUGG_COL_SUGG_ID + INTEGER + "autoincrement" + PRIMARY +
                SUGG_COL_MP_ID  + INTEGER + "," +
                SUGG_COL_WORK_SEC + STRING +
                SUGG_COL_DIS + STRING  +
                SUGG_COL_ST + STRING +
                SUGG_COL_TITLE + STRING  +
                SUGG_COL_DESC + STRING  +
                SUGG_COL_CT_NAME + STRING  +
                SUGG_COL_CT_MOBILE + STRING +
                SUGG_COL_CT_ADD + STRING +
                SUGG_COL_CT_STATUS + "STRING" +
         ")";*/

        String CREATE_SUGGESTION = "create table tbl_Suggestion(" +
                "suggestion_id integer primary key autoincrement, " +
                "id_mp_assigned string" +
                ", work_sector string, " +
                "work_name string, " +
                "work_desc string, " +
                "ct_name string, "
                + "ct_mobile string, "
                + "ct_address string, " +
                "status int" + ", work_district string" +
                ")";
        db.execSQL(CREATE_LOGIN);
        Log.d("Login Table", "created successfully!!");
        db.execSQL(CREATE_MP_LOGIN);
        Log.d("MP Login Table", "created successfully!!");
        db.execSQL(CREATE_SUGGESTION);
        Log.d("Suggestion Table", "created successfully!!");
    }

    public long  user_register(String fname, String lname, String mname, String username, String state, String district, String address, String gender, String contact, String aadhar, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

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

        long inserted = db.insert(TBL_LOGIN_TABLE, null, values);
        db.close();
        return inserted;
    }
    public List<User> login(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        List<User> user_details = new ArrayList<>();
        String qry = "select * from tbl_user where " + COL_MP_USERNAME + "= '" + username + "' and " + COL_PASSWORD + "= '" + password + "'";
        Cursor result = db.rawQuery(qry, null);
        result.moveToFirst();

        while (!result.isAfterLast()){
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
        db.close();
        return user_details;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TBL_MP_SUGGESTION);
        db.execSQL("drop table if exists " + TBL_MP_LOGIN);
        db.execSQL("drop table if exists " + TBL_LOGIN_TABLE);

        onCreate(db);
    }

    public long insert_complaint(String mp_id, String sector, String dist, String state, String title, String description, String cust_name, String ct_mobile, String ct_address){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUGG_COL_MP_ID, mp_id);
        values.put(SUGG_COL_WORK_SEC, sector);
        values.put(SUGG_COL_TITLE, title);
        values.put(SUGG_COL_DIS, dist);
        values.put(SUGG_COL_DESC, description);
        values.put(SUGG_COL_CT_NAME, cust_name);
        values.put(SUGG_COL_CT_MOBILE, ct_mobile);
        values.put(SUGG_COL_CT_ADD, ct_address);
        values.put(SUGG_COL_CT_STATUS, 0);
        long inserted = database.insert(TBL_MP_SUGGESTION, null, values);
        database.close();
        return inserted;
    }

    public List<Complaint> get_complaints(String mp_name){
        List<Complaint> complaints = new ArrayList<>();
        String qry = "select * from " + TBL_MP_SUGGESTION + " where " + SUGG_COL_MP_ID + " like '%" +mp_name + "%' and status = 0";

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = database.rawQuery(qry, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Complaint complaint = new Complaint();
            complaint.setId(cursor.getInt(0));
            complaint.setMp_id(cursor.getString(1));
            complaint.setSector(cursor.getString(2));
            complaint.setTitle(cursor.getString(3));
            complaint.setDescription(cursor.getString(4));
            complaint.setCustomer_name(cursor.getString(5));
            complaint.setContact(cursor.getString(6));
            complaint.setLocation(cursor.getString(8) + ", " + cursor.getString(7));
            complaint.setStatus(cursor.getInt(7));
            String url = "";
            switch (cursor.getString(2)){
                case "Electricity Issue":
                    url = "https://image.freepik.com/free-photo/sun-setting-silhouette-electricity-pylons_1127-3239.jpg";
                    break;
                case "Water Issue":
                    url = "https://image.freepik.com/free-photo/close-up-water-splashes_23-2147608472.jpg";
                    break;
                case "Garbage Issue":
                    url = "https://image.freepik.com/free-vector/garbage-dump-with-rubbish-recycling-park-different-types-waste-environmental-conservation-infographics_256892-9.jpg";
                    break;
                case "Transport Issue":
                    url = "https://image.freepik.com/free-vector/passengers-waiting-public-transport-bus-stop_74855-5282.jpg";
                    break;
            }
            complaint.setUrl(url);
            complaints.add(complaint);
            cursor.moveToNext();
        }

        while (!cursor.isAfterLast())
        cursor.close();
        database.close();
        return  complaints;
    }

    public String mp_login(String username, String password){
        String qry = "select " + COL_MP_NAME + " from " + TBL_MP_LOGIN + " where " + COL_MP_USERNAME + " = '" + username + "' and " + COL_MP_PWD + "= '" + password + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(qry, null);
        cursor.moveToFirst();
        String mp_name = "";
        while (!cursor.isAfterLast()){
            mp_name = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return mp_name;
    }

    public void load_mp_data(){
        List<MpPojo> mp_list = new ArrayList<>();
        String mps_data_json = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.mps)));
        try {
            mps_data_json = reader.readLine();
            reader.close();
        }catch (Exception e){}

        try {
            JSONObject object = new JSONObject(mps_data_json);
            JSONArray data = object.getJSONArray("data");

            for(int i = 0; i < data.length(); i++){
                int id = (i + 1);
                JSONArray array = data.getJSONArray(i);
                MpPojo mpPojo = new MpPojo();

                String name = array.getString(0);
                String username = name.toLowerCase();
                username = username.replace(".", "");
                username = username.replace(" ", "_");
                String password = username + "123";

                mpPojo.setId(id);
                mpPojo.setName(name);
                mpPojo.setUsername(username);
                mpPojo.setPassword(password);

                mp_list.add(mpPojo);
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        int times = 0;
        for(int i = 0; i < mp_list.size(); i++){
            MpPojo current = mp_list.get(i);
            values.put(COL_MP__ID, current.getId());
            values.put(COL_MP_NAME, current.getName());
            values.put(COL_MP_USERNAME, current.getUsername());
            values.put(COL_MP_PWD, current.getPassword());
            database.insert(TBL_MP_LOGIN, null, values);
            times++;
        }
        database.close();
        Toast.makeText(context, "Got " + times + " entries!!", Toast.LENGTH_LONG).show();
    }

    public Complaint get_complaint(int id) {
        List<Complaint> complaints = new ArrayList<>();
        String qry = "select * from " + TBL_MP_SUGGESTION + " where " + SUGG_COL_SUGG_ID + " = " + id;

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = database.rawQuery(qry, null);
        cursor.moveToFirst();

        Complaint complaint = new Complaint();
        while (!cursor.isAfterLast()) {
            complaint.setId(cursor.getInt(0));
            complaint.setMp_id(cursor.getString(1));
            complaint.setSector(cursor.getString(2));
            complaint.setTitle(cursor.getString(3));
            complaint.setDescription(cursor.getString(4));
            complaint.setCustomer_name(cursor.getString(5));
            complaint.setContact(cursor.getString(6));
            complaint.setLocation(cursor.getString(7));
            complaint.setStatus(cursor.getInt(7));

            String url = "";
            switch (cursor.getString(2)) {
                case "Electricity Issue":
                    url = "https://image.freepik.com/free-photo/sun-setting-silhouette-electricity-pylons_1127-3239.jpg";
                    break;
                case "Water Issue":
                    url = "https://image.freepik.com/free-photo/close-up-water-splashes_23-2147608472.jpg";
                    break;
                case "Garbage Issue":
                    url = "https://image.freepik.com/free-vector/garbage-dump-with-rubbish-recycling-park-different-types-waste-environmental-conservation-infographics_256892-9.jpg";
                    break;
                case "Transport Issue":
                    url = "https://image.freepik.com/free-vector/passengers-waiting-public-transport-bus-stop_74855-5282.jpg";
                    break;
            }
            complaint.setUrl(url);
            cursor.moveToNext();
        }
        return complaint;
    }

    public long resolve(int id){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUGG_COL_CT_STATUS, 1);
        long res = database.update(TBL_MP_SUGGESTION, values, SUGG_COL_SUGG_ID + "=" + id, null);

        return res;
    }

    public long put_on_wait(int id){
        SQLiteDatabase database = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUGG_COL_CT_STATUS, 0);
        long res = database.update(TBL_MP_SUGGESTION, values, SUGG_COL_SUGG_ID + "=" + id, null);

        return res;
    }

}
