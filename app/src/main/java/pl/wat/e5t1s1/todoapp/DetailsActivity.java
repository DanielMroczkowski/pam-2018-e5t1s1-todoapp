package pl.wat.e5t1s1.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import pl.wat.e5t1s1.todoapp.db.TaskContract;
import pl.wat.e5t1s1.todoapp.db.TaskDbHelper;

public class DetailsActivity extends AppCompatActivity {

    TaskDbHelper mHelper;

    EditText Title;
    EditText Text;
    DatePicker Date;
    TimePicker Time;
    Switch Alarm;

    String where;

    int taskId = -1; // or other values

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Title = findViewById(R.id.viewTitle);
        Text = findViewById(R.id.viewNote);
        Date = findViewById(R.id.viewDatePicker);
        Time = findViewById(R.id.viewTimePicker);
        Alarm = findViewById(R.id.viewAlarm);

        Title.setKeyListener(null);
        Text.setKeyListener(null);
        Date.setEnabled(false);
        Time.setEnabled(false);
        Alarm.setClickable(false);

        Time.setIs24HourView(true);

        mHelper = new TaskDbHelper(DetailsActivity.this);
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Bundle b = getIntent().getExtras();
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
            Title.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)));
            Text.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TEXT)));
            String[] data = (cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE))).split("-");
            Date.updateDate(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
            String[] time = (cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TIME))).split(":");
            Time.setHour(Integer.parseInt(time[0]));
            Time.setMinute(Integer.parseInt(time[1]));
            Alarm.setChecked(((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_ALARM))) != 0));
        }
        db.close();


    }

    public void onEditClick(View view) {
        Intent intent = new Intent(DetailsActivity.this, PositionEditActivity.class);
        intent.putExtra("taskId", taskId);
        this.finish();
        startActivity(intent);
    }
}
