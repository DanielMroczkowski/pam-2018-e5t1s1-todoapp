package pl.wat.e5t1s1.todoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper{

    /**
     * Helper do obługi bazy danych
     * @param context
     */
    public TaskDbHelper(Context context) {
            super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        }

    /**
     * Utworzenie tabeli, jeżeli nie istnieje
     * @param db
     */
    @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                    TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, "+
                    TaskContract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL, "+
                    TaskContract.TaskEntry.COL_TASK_TIME + " TEXT NOT NULL, "+
                    TaskContract.TaskEntry.COL_TASK_ALARM + " INTEGER NOT NULL, "+
                    TaskContract.TaskEntry.COL_TASK_TEXT + " TEXT);";

            db.execSQL(createTable);
        }

    /**
     * Upgrade bazy danych, jeżeli wystąpiła zmiana wersji
     * @param db
     * @param oldVersion
     * @param newVersion
     */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
            onCreate(db);
        }
    }

