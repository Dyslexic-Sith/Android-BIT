package com.project.sam.bitservices;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by samuel.coianiz1 on 19/05/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //region Static Field Members
    //Database Version
    private static final int DATABASE_VERSION = 25;

    //Database Name
    private static final String DATABASE_NAME = "BITJobs";

    //Role table name
    private static final String TABLE_ROLE = "role";
    // Role column names
    private static final String KEY_ROLE_ID = "id";
    private static final String KEY_ROLE_TYPE = "role_type";

    // Users table name
    private static final String TABLE_USERS = "users";
    // Users column names
    private static final String KEY_USERS_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_USER_PHONE = "phone_number";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_ADDRESS_LINE1 = "address_line1";
    private static final String KEY_USER_ADDRESS_LINE2 = "address_line2";
    private static final String KEY_USER_ADDRESS_SUBURB = "suburb";
    private static final String KEY_USER_ADDRESS_POSTCODE = "postcode";
    private static final String KEY_USER_ADDRESS_STATE = "state";
    private static final String KEY_USER_ROLE = "role";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_STATUS = "status";

    // JobRequest table name
    private static final String TABLE_JOBREQUEST = "jobrequest";
    //Jobrequest column names
    private static final String KEY_JOB_ID = "id";
    private static final String KEY_JOB_CREATE_DATE = "date_created";
    private static final String KEY_JOB_DUE_DATE = "date_due";
    private static final String KEY_JOB_COMPLETED_DATE = "date_completed";
    private static final String KEY_JOB_SKILL = "skill_id";
    private static final String KEY_JOB_CONTRACTOR = "contractor_id";
    private static final String KEY_JOB_CLIENT = "client_id";
    private static final String KEY_JOB_DESCRIPTION = "job_description";
    private static final String KEY_JOB_KMS = "kms_driven";
    private static final String KEY_JOB_STATUS = "job_status";

    //Skill table name
    private static final String TABLE_SKILL = "skill";
    //Skill column names
    private static final String KEY_SKILL_ID = "id";
    private static final String KEY_SKILL_TITLE = "title";

    // UserSkill table name
    private static final String TABLE_USER_SKILL = "user_skill";
    //UserSkill column names
    private static final String KEY_US_USER_ID = "user_id";
    private static final String KEY_US_SKILL_ID = "skill_id";

    // DatabaseHandler to check if it has been instantiated
    private static DatabaseHandler sInstance;

    //endregion

    // Create a public GetInstance to handle create of the database so we only have one instance running all the time.
    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context so we don't accidentally leak an Activity's context
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    // private constructor so it can't be directly instantiated
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        String CREATE_ROLE_TABLE = "CREATE TABLE " + TABLE_ROLE +
                                    "(" + KEY_ROLE_ID + " INTEGER PRIMARY KEY, " +
                                    KEY_ROLE_TYPE + " TEXT NOT NULL);";

        String CREATE_SKILL_TABLE = "CREATE TABLE " + TABLE_SKILL +
                                    "(" + KEY_SKILL_ID + " INTEGER PRIMARY KEY, " +
                                    KEY_SKILL_TITLE + " TEXT NOT NULL);";

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS +
                                    "(" + KEY_USERS_ID + " INTEGER PRIMARY KEY, " +
                                    KEY_FIRST_NAME + " TEXT NOT NULL, " +
                                    KEY_LAST_NAME + " TEXT NOT NULL, " +
                                    KEY_USER_PHONE + " TEXT NOT NULL, " +
                                    KEY_USER_EMAIL + " TEXT NOT NULL, " +
                                    KEY_USER_ADDRESS_LINE1 + " TEXT NOT NULL, " +
                                    KEY_USER_ADDRESS_LINE2 + " TEXT NULL, " +
                                    KEY_USER_ADDRESS_SUBURB + " TEXT NOT NULL, " +
                                    KEY_USER_ADDRESS_POSTCODE + " TEXT NOT NULL, " +
                                    KEY_USER_ADDRESS_STATE + " TEXT NOT NULL, " +
                                    KEY_USER_ROLE + " INTEGER NOT NULL, " +
                                    KEY_USER_USERNAME + " TEXT NOT NULL, " +
                                    KEY_USER_PASSWORD + " TEXT NOT NULL, " +
                                    KEY_USER_STATUS + " TEXT NOT NULL, " +
                                    "FOREIGN KEY (" + KEY_USER_ROLE + ")" + " REFERENCES " + TABLE_ROLE + " (" + KEY_ROLE_ID + "));";

        String CREATE_USER_SKILL_TABLE =    "CREATE TABLE " + TABLE_USER_SKILL +
                                            "(" + KEY_US_SKILL_ID + " INTEGER NOT NULL, " +
                                            KEY_US_USER_ID + " INTEGER NOT NULL, " +
                                            "PRIMARY KEY (" + KEY_US_SKILL_ID + ", " + KEY_US_USER_ID + "), " +
                                            "FOREIGN KEY (" + KEY_US_SKILL_ID + ")" + " REFERENCES " + TABLE_SKILL + " (" + KEY_SKILL_ID + "), " +
                                            "FOREIGN KEY (" + KEY_US_USER_ID + ")" + " REFERENCES " + TABLE_USERS + " (" + KEY_USERS_ID + "));";

        String CREATE_JOB_TABLE =   "CREATE TABLE " + TABLE_JOBREQUEST +
                                    "(" + KEY_JOB_ID + " INTEGER PRIMARY KEY, " +
                                    KEY_JOB_CREATE_DATE + " TEXT NOT NULL, " +
                                    KEY_JOB_DUE_DATE + " TEXT NOT NULL, " +
                                    KEY_JOB_COMPLETED_DATE + " TEXT NULL, " +
                                    KEY_JOB_SKILL + " INTEGER NOT NULL, " +
                                    KEY_JOB_CONTRACTOR + " INTEGER NULL, " +
                                    KEY_JOB_CLIENT + " INTEGER NOT NULL, " +
                                    KEY_JOB_DESCRIPTION + " TEXT NOT NULL, " +
                                    KEY_JOB_KMS + " INTEGER NULL, " +
                                    KEY_JOB_STATUS + " TEXT NOT NULL, " +
                                    "FOREIGN KEY (" + KEY_JOB_SKILL + ") " + "REFERENCES " + TABLE_SKILL + "(" + KEY_SKILL_ID + "), " +
                                    "FOREIGN KEY (" + KEY_JOB_CONTRACTOR + ")" + " REFERENCES " + TABLE_USERS + " (" + KEY_USERS_ID + "), " +
                                    "FOREIGN KEY (" + KEY_JOB_CLIENT + ")" + " REFERENCES " + TABLE_USERS + " (" + KEY_USERS_ID + "));";

        db.execSQL(CREATE_ROLE_TABLE);
        db.execSQL(CREATE_SKILL_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_SKILL_TABLE);
        db.execSQL(CREATE_JOB_TABLE);

        String insertRoles1 =    "INSERT INTO " + TABLE_ROLE +
                                " (" + KEY_ROLE_TYPE +") VALUES ('Contractor')";
        String insertRoles2 =    "INSERT INTO " + TABLE_ROLE +
                " (" + KEY_ROLE_TYPE +") VALUES ('Client');";
        db.execSQL(insertRoles1);
        db.execSQL(insertRoles2);

        String insertSkill =    "INSERT INTO " + TABLE_SKILL +
                                " (" + KEY_SKILL_TITLE + ") VALUES ('Application Installations'), " +
                "('Application Support'), " +
                "('Data Migration'), " +
                "('Debugging'), " +
                "('Desktop Support'), " +
                "('End User Support'), " +
                "('Hardware Updates'), " +
                "('Software Support'), " +
                "('Software Upgrades'), " +
                "('Testing'), " +
                "('Data Backup'), " +
                "('Repeated Backup'), " +
                "('Phone Support');";
        db.execSQL(insertSkill);

        String insertUsers =    "INSERT INTO " + TABLE_USERS +
                                "(" + KEY_FIRST_NAME + ", " +
                                KEY_LAST_NAME + ", " +
                                KEY_USER_PHONE + ", " +
                                KEY_USER_EMAIL + ", " +
                                KEY_USER_ADDRESS_LINE1 + ", " +
                                KEY_USER_ADDRESS_LINE2 + ", " +
                                KEY_USER_ADDRESS_SUBURB + ", " +
                                KEY_USER_ADDRESS_POSTCODE + ", " +
                                KEY_USER_ADDRESS_STATE + ", " +
                                KEY_USER_ROLE + ", " +
                                KEY_USER_USERNAME + ", " +
                                KEY_USER_PASSWORD + ", " +
                                KEY_USER_STATUS + ") VALUES ('Sam', 'Coianiz', '0422632769', 'coianizs@outlook.com', '191 Prince Alfred Parade', '', 'Newport', '2106', 'NSW', '1', 'scoianiz', 'Coianiz1', '1'), " +
                "('Jessica', 'Tailby', '0404227087', 'jessicatailby@gmail.com', '191 Prince Alfred Parade', '', 'Newport', '2106', 'NSW', '1', 'jtailby', 'Tailby123', '1'), " +
                "('Patrick', 'Dempsey', '0404040404', 'pdempers@mail.com', '279 Hollywood Boulevard', '', 'Surrey Hills', '2020', 'NSW', '1', 'pdempsey', 'Dempers1', '1'), " +
                "('Sally', 'Supervisor', '02 5550 0572', 'supersally@outlook.com', 'Level 3', '100 Pacific Highway', 'North Sydney', '2060', 'NSW', '1', 'ssupervisor', 'Supervisor1', '1'), " +
                "('Connor', 'Kosovich', '0294886371', 'ckosovich@mail.com', '63 Peninsula Drive', '', 'Saratoga', '2251', 'NSW', '1', 'ckosovich', 'Kosovich1', '1'), " +
                "('Christopher', 'Daintree', '0285150354', 'daintree123@gmail.com', '17 Ocean Street', '', 'Sydney South', '2000', 'NSW', '1', 'cdaintree', 'Daintree1', '1'), " +
                "('Mikayla', 'Cussen', '82543044', 'majorcussen@hotmail.com', '32 Boobialla Street', '', 'Corobimilla', '2700', 'NSW', '1', 'mcussen', 'Cussen1', '1');";

        db.execSQL(insertUsers);

        String insertUsers1 =    "INSERT INTO " + TABLE_USERS +
                "(" + KEY_FIRST_NAME + ", " +
                KEY_LAST_NAME + ", " +
                KEY_USER_PHONE + ", " +
                KEY_USER_EMAIL + ", " +
                KEY_USER_ADDRESS_LINE1 + ", " +
                KEY_USER_ADDRESS_LINE2 + ", " +
                KEY_USER_ADDRESS_SUBURB + ", " +
                KEY_USER_ADDRESS_POSTCODE + ", " +
                KEY_USER_ADDRESS_STATE + ", " +
                KEY_USER_ROLE + ", " +
                KEY_USER_USERNAME + ", " +
                KEY_USER_PASSWORD + ", " +
                KEY_USER_STATUS + ") VALUES ('Reece', 'Tailby', '0422632769', 'tailby@mail.com', 'Unit 15', '15 Narabang Way', 'Belrose', '2085', 'NSW', '2', 'rtailby', 'Tailby123', '1'), " +
                "('Bob', 'Builder', '0404227087', 'bobbuilder@timmystools.com', 'Level 2, Suite 24', '14 Polo Avenue', 'Mona Vale', '2103', 'NSW', '2', 'bbuilder', 'Builder1', '1'), " +
                "('Drew', 'Mann', '0298731649', 'mann.d@testies.com', '98 Main Street', '', 'Plane Plains', '2548', 'NSW', '2', 'dmann', 'Dmann123', '1'), " +
                "('Ohm', 'Igosh', '98753214', 'omg@geemail.com', '64', '245 Pitt Street', 'Sydney', '2000', 'NSW', '2', 'oigosh', 'Igsoh123', '1'), " +
                "('Brad', 'Qwerty', '99999999', 'brad@bobby.com', '456 Street Parade', '', 'Suburb', '2251', 'QLD', '2', 'bqwerty', 'Qwerty1', '1'), " +
                "('Crash', 'Bandicoot', '0278945632', 'crash@ctr.com', '1/52', '987 Parade-Circuit Lane', 'Newville', '6000', 'TAS', '2', 'cbandicoot', 'Bandicoot1', '1'), " +
                "('Sandra', 'Cummings', '0299794159', 'info@artformaus.com.au', 'Shop 6', '149 Barrenjoey Road', 'Mona Vale', '2103', 'NSW', '2', 'scummings', 'Cummings1', '1');";

        db.execSQL(insertUsers1);

        String insertUserSkill = "INSERT INTO " + TABLE_USER_SKILL +
                                    "(" + KEY_US_USER_ID + ", " +
                                    KEY_US_SKILL_ID + ") VALUES ('1','1'), " +
                                    "('1','3'), " +
                                    "('1','5'), " +
                                    "('1','7'), " +
                                    "('1','9'), " +
                                    "('1','11'), " +
                                    "('2','2'), " +
                                    "('2','4'), " +
                                    "('2','6'), " +
                                    "('2','8'), " +
                                    "('2','10'), " +
                                    "('3','1'), " +
                                    "('3','2'), " +
                                    "('3','3'), " +
                                    "('3','4'), " +
                                    "('3','5'), " +
                                    "('4','6'), " +
                                    "('4','7'), " +
                                    "('4','8'), " +
                                    "('4','9'), " +
                                    "('4','10'), " +
                                    "('5','3'), " +
                                    "('5','4'), " +
                                    "('5','6'), " +
                                    "('5','7'), " +
                                    "('5','9'), " +
                                    "('6','1'), " +
                                    "('6','2'), " +
                                    "('6','5'), " +
                                    "('6','8'), " +
                                    "('6','10');";

            db.execSQL(insertUserSkill);

        String insertJobs = "INSERT INTO " + TABLE_JOBREQUEST +
                "(" + KEY_JOB_CREATE_DATE + ", " +
                KEY_JOB_DUE_DATE + ", " +
                KEY_JOB_COMPLETED_DATE + ", " +
                KEY_JOB_SKILL + ", " +
                KEY_JOB_CONTRACTOR + ", " +
                KEY_JOB_CLIENT + ", " +
                KEY_JOB_DESCRIPTION + ", " +
                KEY_JOB_KMS + ", " +
                KEY_JOB_STATUS + ") VALUES ('2017-05-25', '2017-05-30', '', '2', '1', '8', 'Please update Adobe Acrobat Reader.', '0', 'Submitted'), " +
                "('2016-05-25', '2016-05-25', '2016-05-25', '2', '4', '8', 'Please update Adobe Acrobat Reader.', '20', 'Completed'), " +
                "('2017-01-25', '2017-01-25', '2017-01-25', '2', '4', '8', 'Please update Adobe Acrobat Reader.', '23', 'Completed'), " +
                "('2017-04-23', '2017-05-30', '', '1', '1', '10', 'Please update Adobe Acrobat Reader.', '0', 'Submitted'), " +
                "('2017-04-23', '2017-05-30', '', '2', '2', '11', 'Can I have a large BBQ Meatlovers and a gluten free Supreme.', '0', 'Submitted'), " +
                "('2017-04-23', '2017-05-30', '', '3', '4', '12', 'My hard drive is on fire.', '0', 'Submitted'), " +
                "('2017-04-23', '2017-05-30', '', '4', '4', '13', 'I seem to have lost Google. Can you please find it for me.', '0', 'Submitted'), " +
                "('2017-04-23', '2017-05-30', '', '5', '4', '14', 'Please fix my monitor.', '0', 'Submitted'), " +
                "('2010-12-12', '2010-12-30', '2010-12-30', '2', '3', '8', 'Please update Adobe Acrobat Reader.', '8', 'Completed');";

        db.execSQL(insertJobs);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBREQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SKILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);

        // Create tables again
        onCreate(db);
    }

    //region DatabaseJob Methods
    public List<DatabaseJob> getJobs() {
        List<DatabaseJob> jobList = new ArrayList<>();

        //Select the jobs
        String selectQuery = "SELECT * FROM " + TABLE_JOBREQUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseJob job = new DatabaseJob();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setDateCreated(cursor.getString(1));
                job.setDateDue(cursor.getString(2));
                job.setDateCompleted(cursor.getString(3));
                job.setSkillId(Integer.parseInt(cursor.getString(4)));
                job.setContractorId(Integer.parseInt(cursor.getString(5)));
                job.setClientId(Integer.parseInt(cursor.getString(6)));
                job.setJobDescription(cursor.getString(7));
                job.setKms(Integer.parseInt(cursor.getString(8)));
                job.setStatus(cursor.getString(9));
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobList;
    }

    public void updateJobs(int id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE " + TABLE_JOBREQUEST + " SET " + KEY_JOB_STATUS + " = 'Completed', " + KEY_JOB_COMPLETED_DATE + " = '" + today + "' WHERE " + KEY_JOB_ID + " = '" + id + "'";
        db.execSQL(update);
    }

    public DatabaseJob getJobSingle(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOBREQUEST, new String[]{KEY_JOB_ID, KEY_JOB_CREATE_DATE, KEY_JOB_DUE_DATE, KEY_JOB_COMPLETED_DATE, KEY_JOB_SKILL, KEY_JOB_CONTRACTOR, KEY_JOB_CLIENT, KEY_JOB_DESCRIPTION, KEY_JOB_KMS, KEY_JOB_STATUS}, KEY_JOB_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DatabaseJob job = new DatabaseJob(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                cursor.getString(7),
                Integer.parseInt(cursor.getString(8)),
                cursor.getString(9));
        cursor.close();
        return job;
    }

    public List<DatabaseJob> getJobsForClient(int id) {
        List<DatabaseJob> jobList = new ArrayList<>();

        //Select the jobs
        String selectQuery = "SELECT * FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_CLIENT + " = '" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseJob job = new DatabaseJob();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setDateCreated(cursor.getString(1));
                job.setDateDue(cursor.getString(2));
                job.setDateCompleted(cursor.getString(3));
                job.setSkillId(Integer.parseInt(cursor.getString(4)));
                job.setContractorId(Integer.parseInt(cursor.getString(5)));
                job.setClientId(Integer.parseInt(cursor.getString(6)));
                job.setJobDescription(cursor.getString(7));
                job.setKms(Integer.parseInt(cursor.getString(8)));
                job.setStatus(cursor.getString(9));
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobList;
    }

    public ArrayList<DatabaseJob> getJobsForClientSorted(int id) {
        ArrayList<DatabaseJob> jobList = new ArrayList<>();

        //Select the jobs
        String selectQuery = "SELECT * FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_CLIENT + " = '" + id + "' ORDER BY " + KEY_JOB_DUE_DATE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseJob job = new DatabaseJob();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setDateCreated(cursor.getString(1));
                job.setDateDue(cursor.getString(2));
                job.setDateCompleted(cursor.getString(3));
                job.setSkillId(Integer.parseInt(cursor.getString(4)));
                job.setContractorId(Integer.parseInt(cursor.getString(5)));
                job.setClientId(Integer.parseInt(cursor.getString(6)));
                job.setJobDescription(cursor.getString(7));
                job.setKms(Integer.parseInt(cursor.getString(8)));
                job.setStatus(cursor.getString(9));
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobList;
    }

    public List<DatabaseJob> getSubmittedJobsForClient(int id) {
        List<DatabaseJob> jobList = new ArrayList<>();

        //Select the jobs
        String selectQuery = "SELECT * FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_CLIENT + " = '" + id + "' AND " + KEY_JOB_STATUS + " = 'Submitted'" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseJob job = new DatabaseJob();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setDateCreated(cursor.getString(1));
                job.setDateDue(cursor.getString(2));
                job.setDateCompleted(cursor.getString(3));
                job.setSkillId(Integer.parseInt(cursor.getString(4)));
                job.setContractorId(Integer.parseInt(cursor.getString(5)));
                job.setClientId(Integer.parseInt(cursor.getString(6)));
                job.setJobDescription(cursor.getString(7));
                job.setKms(Integer.parseInt(cursor.getString(8)));
                job.setStatus(cursor.getString(9));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobList;
    }

    public List<DatabaseJob> getSubmittedJobs() {
        List<DatabaseJob> jobList = new ArrayList<>();

        //Select the jobs
        String selectQuery = "SELECT * FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_STATUS + " = 'Submitted'" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseJob job = new DatabaseJob();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setDateCreated(cursor.getString(1));
                job.setDateDue(cursor.getString(2));
                job.setDateCompleted(cursor.getString(3));
                job.setSkillId(Integer.parseInt(cursor.getString(4)));
                job.setContractorId(Integer.parseInt(cursor.getString(5)));
                job.setClientId(Integer.parseInt(cursor.getString(6)));
                job.setJobDescription(cursor.getString(7));
                job.setKms(Integer.parseInt(cursor.getString(8)));
                job.setStatus(cursor.getString(9));
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobList;
    }

    public List<DatabaseJob> getSubmittedJobs(int id) {
        List<DatabaseJob> jobList = new ArrayList<>();

        //Select the jobs
        String selectQuery = "SELECT * FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_STATUS + " = 'Submitted' AND " + KEY_JOB_CONTRACTOR + " = '" + id + "'" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseJob job = new DatabaseJob();
                job.setId(Integer.parseInt(cursor.getString(0)));
                job.setDateCreated(cursor.getString(1));
                job.setDateDue(cursor.getString(2));
                job.setDateCompleted(cursor.getString(3));
                job.setSkillId(Integer.parseInt(cursor.getString(4)));
                job.setContractorId(Integer.parseInt(cursor.getString(5)));
                job.setClientId(Integer.parseInt(cursor.getString(6)));
                job.setJobDescription(cursor.getString(7));
                if (!cursor.getString(8).isEmpty()){
                job.setKms(Integer.parseInt(cursor.getString(8)));}
                else job.setKms(0);
                job.setStatus(cursor.getString(9));
                jobList.add(job);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return jobList;
    }

    public void addJob(DatabaseJob job) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertJobs = "INSERT INTO " + TABLE_JOBREQUEST +
                "(" + KEY_JOB_CREATE_DATE + ", " +
                KEY_JOB_DUE_DATE + ", " +
                KEY_JOB_COMPLETED_DATE + ", " +
                KEY_JOB_SKILL + ", " +
                KEY_JOB_CONTRACTOR + ", " +
                KEY_JOB_CLIENT + ", " +
                KEY_JOB_DESCRIPTION + ", " +
                KEY_JOB_KMS + ", " +
                KEY_JOB_STATUS + ") VALUES ('" + job.getDateCreated() +
                                        "', '" + job.getDateDue() +
                                        "', '" + job.getDateCompleted() +
                                        "', '" + job.getSkillId() +
                                        "', '" + job.getContractorId() +
                                        "', '" + job.getClientId() +
                                        "', '" + job.getJobDescription() +
                                        "', '" + job.getKms() +
                                        "', '" + job.getStatus() + "');";

        db.execSQL(insertJobs);
        db.close();
    }

    public DatabaseJob selectMaxJobId(){
        SQLiteDatabase db = this.getReadableDatabase();
        DatabaseJob job = new DatabaseJob();;
        String select = "SELECT * FROM " + TABLE_JOBREQUEST + " ORDER BY "+ KEY_JOB_ID +" DESC LIMIT 1";
        Cursor c = db.rawQuery(select, null);
        if (c.moveToFirst()) {
            do {
                job.setId(Integer.parseInt(c.getString(0)));
                job.setDateCreated(c.getString(1));
                job.setDateDue(c.getString(2));
                job.setDateCompleted(c.getString(3));
                job.setSkillId(Integer.parseInt(c.getString(4)));
                job.setContractorId(Integer.parseInt(c.getString(5)));
                job.setClientId(Integer.parseInt(c.getString(6)));
                job.setJobDescription(c.getString(7));
                job.setKms(Integer.parseInt(c.getString(8)));
                job.setStatus(c.getString(9));

            } while (c.moveToNext());
        }
        return job;

    }
    //endregion

    //region DatabaseUser Methods
    public DatabaseUser selectLogin(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_USERS_ID + ", " + KEY_USER_ROLE + " FROM " + TABLE_USERS + " WHERE " + KEY_USER_USERNAME + " = '" + user + "' AND " + KEY_USER_PASSWORD + " = '" + pass + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        DatabaseUser loginCredentials = new DatabaseUser();
        if (cursor.moveToFirst()) {
            do {
                loginCredentials.setId(Integer.parseInt(cursor.getString(0)));
                loginCredentials.setRoleId(Integer.parseInt(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return loginCredentials;
    }

    public List<DatabaseUser> selectContractors(){
        List<DatabaseUser> conList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ROLE + " = '1'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DatabaseUser user = new DatabaseUser();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                user.setAddressLine1(cursor.getString(5));
                user.setAddressLine2(cursor.getString(6));
                user.setSuburb(cursor.getString(7));
                user.setPostcode(cursor.getString(8));
                user.setState(cursor.getString(9));
                user.setRoleId(Integer.parseInt(cursor.getString(10)));
                user.setStatus(Integer.parseInt(cursor.getString(13)));
                conList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return conList;
    }

    public List<DatabaseUser> selectClients(){
        List<DatabaseUser> clientList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER_ROLE + " = '2'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DatabaseUser user = new DatabaseUser();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                user.setAddressLine1(cursor.getString(5));
                user.setAddressLine2(cursor.getString(6));
                user.setSuburb(cursor.getString(7));
                user.setPostcode(cursor.getString(8));
                user.setState(cursor.getString(9));
                user.setRoleId(Integer.parseInt(cursor.getString(10)));
                user.setStatus(Integer.parseInt(cursor.getString(13)));
                clientList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clientList;
    }

    public DatabaseUser selectSingleClient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USERS_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_USER_PHONE, KEY_USER_EMAIL, KEY_USER_ADDRESS_LINE1, KEY_USER_ADDRESS_LINE2, KEY_USER_ADDRESS_SUBURB, KEY_USER_ADDRESS_POSTCODE, KEY_USER_ADDRESS_STATE, KEY_USER_ROLE, KEY_USER_USERNAME, KEY_USER_PASSWORD, KEY_USER_STATUS}, KEY_USERS_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();}
        DatabaseUser user = new DatabaseUser(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    Integer.parseInt(cursor.getString(10)),
                    cursor.getString(11),
                    cursor.getString(12),
                    Integer.parseInt(cursor.getString(13)));

            cursor.close();
            return user;

    }

    public DatabaseUser selectContractorForJob(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_FIRST_NAME + ", " + KEY_LAST_NAME  + ", " + KEY_USER_ADDRESS_LINE1  + ", " + KEY_USER_ADDRESS_SUBURB  + " FROM " + TABLE_USERS + " WHERE " + KEY_USERS_ID + " IN (SELECT " + KEY_JOB_CONTRACTOR + " FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_ID + " = '" + id + "')";
        DatabaseUser contractorForJob = new DatabaseUser();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                contractorForJob.setFirstName(cursor.getString(0));
                contractorForJob.setLastName(cursor.getString(1));
                contractorForJob.setAddressLine1(cursor.getString(2));
                contractorForJob.setSuburb(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contractorForJob;
    }

    public DatabaseUser selectClientForJob(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_FIRST_NAME + ", " + KEY_LAST_NAME + ", " + KEY_USER_ADDRESS_LINE1  + ", " + KEY_USER_ADDRESS_LINE2 + ", " + KEY_USER_ADDRESS_SUBURB + ", " + KEY_USER_ADDRESS_POSTCODE + ", " + KEY_USER_ADDRESS_STATE + ", " + KEY_USER_PHONE  + " FROM " + TABLE_USERS + " WHERE " + KEY_USERS_ID + " IN (SELECT " + KEY_JOB_CLIENT + " FROM " + TABLE_JOBREQUEST + " WHERE " + KEY_JOB_ID + " = '" + id + "')";
        DatabaseUser contractorForJob = new DatabaseUser();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                contractorForJob.setFirstName(cursor.getString(0));
                contractorForJob.setLastName(cursor.getString(1));
                contractorForJob.setAddressLine1(cursor.getString(2));
                contractorForJob.setAddressLine2(cursor.getString(3));
                contractorForJob.setSuburb(cursor.getString(4));
                contractorForJob.setPostcode(cursor.getString(5));
                contractorForJob.setState(cursor.getString(6));
                contractorForJob.setPhone(cursor.getString(7));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contractorForJob;
    }
    //endregion

    //region DatabaseSkill Methods
    public List<DatabaseSkill> getSkills() {
        List<DatabaseSkill> skillList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SKILL;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DatabaseSkill skill = new DatabaseSkill();
                skill.setId(Integer.parseInt(cursor.getString(0)));
                skill.setTitle(cursor.getString(1));
                skillList.add(skill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return skillList;
    }

    //endregion
}
