package pl.wat.e5t1s1.todoapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pl.wat.e5t1s1.todoapp.db.TaskContract;
import pl.wat.e5t1s1.todoapp.db.TaskDbHelper;

public class PositionEditActivity extends AppCompatActivity{

    TaskDbHelper mHelper;

    EditText editTitle;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.position_edit);

        editTitle = findViewById(R.id.editTitle);
        editText = findViewById(R.id.editNote);

        mHelper = new TaskDbHelper(PositionEditActivity.this);
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Bundle b = getIntent().getExtras();
        int taskId = -1; // or other values
        if(b != null)
            taskId = b.getInt("taskId");
        //Toast.makeText(this,"swipeniete"+taskId,Toast.LENGTH_SHORT).show();

        String[] columns = {TaskContract.TaskEntry._ID,
                            TaskContract.TaskEntry.COL_TASK_TITLE,
                            TaskContract.TaskEntry.COL_TASK_TEXT};
//        String[] columns = {TaskContract.TaskEntry._ID,
//                TaskContract.TaskEntry.COL_TASK_TITLE};
        String where = TaskContract.TaskEntry._ID + "=" + taskId;
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE, columns, where, null, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            editTitle.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)));
            editText.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TEXT)));
        }
        db.close();
    }

    public void onSaveClick(View view) {

    }
}
