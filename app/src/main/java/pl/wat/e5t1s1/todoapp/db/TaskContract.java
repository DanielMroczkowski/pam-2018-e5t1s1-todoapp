package pl.wat.e5t1s1.todoapp.db;
import android.provider.BaseColumns;

/**
 * Definicja kolumn w tabeli aplikacji
 */
public class TaskContract {


        public static final String DB_NAME = "pl.wat.e5t1s1.todoapp.db";
        public static final int DB_VERSION = 1;

        public class TaskEntry implements BaseColumns {
            public static final String TABLE = "tasks";

            public static final String COL_TASK_TITLE = "title";
            public static final String COL_TASK_DATE = "date";
            public static final String COL_TASK_TIME = "time";
            public static final String COL_TASK_ALARM = "alarm";
            public static final String COL_TASK_TEXT = "text";
        }
    }

