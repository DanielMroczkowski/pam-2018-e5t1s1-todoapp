package pl.wat.e5t1s1.todoapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import pl.wat.e5t1s1.todoapp.db.TaskContract;
import pl.wat.e5t1s1.todoapp.db.TaskDbHelper;

public class PositionEditActivity extends AppCompatActivity{

    TaskDbHelper mHelper;

    EditText editTitle;
    EditText editText;
    DatePicker editDate;
    TimePicker editTime;
    Switch editAlarm;

    String where;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.position_edit);

        editTitle = findViewById(R.id.editTitle);
        editText = findViewById(R.id.editNote);
        editDate = findViewById(R.id.editDatePicker);
        editTime = findViewById(R.id.editTimePicker);
        editAlarm = findViewById(R.id.swAlarm);

        editTime.setIs24HourView(true);

        mHelper = new TaskDbHelper(PositionEditActivity.this);
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Bundle b = getIntent().getExtras();
        int taskId = -1; // or other values
        if(b != null)
            taskId = b.getInt("taskId");
        //Toast.makeText(this,"swipeniete"+taskId,Toast.LENGTH_SHORT).show();

        String[] columns = {TaskContract.TaskEntry._ID,
                            TaskContract.TaskEntry.COL_TASK_TITLE,
                            TaskContract.TaskEntry.COL_TASK_TEXT,
                            TaskContract.TaskEntry.COL_TASK_DATE,
                            TaskContract.TaskEntry.COL_TASK_TIME,
                            TaskContract.TaskEntry.COL_TASK_ALARM};
//        String[] columns = {TaskContract.TaskEntry._ID,
//                TaskContract.TaskEntry.COL_TASK_TITLE};
        where = TaskContract.TaskEntry._ID + "=" + taskId;
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE, columns, where, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            editTitle.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)));
            editText.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TEXT)));
            String[] data = (cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE))).split("-");
            editDate.updateDate(Integer.parseInt(data[0]),Integer.parseInt(data[1])-1,Integer.parseInt(data[2]));
            String[] time = (cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TIME))).split(":");
            editTime.setHour(Integer.parseInt(time[0]));
            editTime.setMinute(Integer.parseInt(time[1]));
            editAlarm.setChecked(((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_ALARM))) != 0));
        }
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onSaveClick(View view) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, editTitle.getText().toString());
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, editText.getText().toString());
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, editDate.getYear()+"-"+String.format("%02d",editDate.getMonth()+1)+"-"+String.format("%02d",editDate.getDayOfMonth()));
        values.put(TaskContract.TaskEntry.COL_TASK_TIME, String.format("%02d",editTime.getHour())+":"+String.format("%02d",editTime.getMinute()));
        values.put(TaskContract.TaskEntry.COL_TASK_ALARM, (editAlarm.isChecked()) ? 1 : 0);
        db.updateWithOnConflict(TaskContract.TaskEntry.TABLE,
                                values,
                                where,
                                null,
                                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        this.finish();
    }
}
