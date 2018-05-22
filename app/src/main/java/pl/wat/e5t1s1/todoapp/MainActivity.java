package pl.wat.e5t1s1.todoapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;

import pl.wat.e5t1s1.todoapp.R;
import pl.wat.e5t1s1.todoapp.db.TaskContract;
import pl.wat.e5t1s1.todoapp.db.TaskDbHelper;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    ListView mTaskListView;

    private TaskDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new TaskDbHelper(this);
        mTaskListView =  findViewById(R.id.lista);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Utworzenie TAGu dla aktywności MainActivity,
     * w celu umożliwienia filtrowania jej w Logcat.
     */
    private static final String TAG = "MainActivity";

    /**
     * Obsługa przycisku dodaj zadanie
     *
     * @param item
     * @return Logcat entry
     */
    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_add_task:
                    final EditText taskEditText = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Nowe zadanie")
                            .setMessage("Co planujesz?")
                            .setView(taskEditText)
                            .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    SQLiteDatabase db = mHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                    db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                            null,
                                            values,
                                            SQLiteDatabase.CONFLICT_REPLACE);
                                    db.close();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    updateUI();
                    dialog.show();

                    return true;

                default:

                    return super.onOptionsItemSelected(item);
            }
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.position,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }


}
