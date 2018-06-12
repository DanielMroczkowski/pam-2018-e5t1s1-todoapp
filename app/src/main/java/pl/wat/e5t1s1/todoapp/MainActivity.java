package pl.wat.e5t1s1.todoapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.wat.e5t1s1.todoapp.db.TaskContract;
import pl.wat.e5t1s1.todoapp.db.TaskDbHelper;


public class MainActivity extends AppCompatActivity {

    private ToDoAdapter mAdapter;
    RecyclerView mTaskListView;

    ItemTouchHelper itemTouchHelper;
    private GestureDetectorCompat gestureObject;

    private TaskDbHelper mHelper;

    /**
     * Utworzenie TAGu dla aktywności MainActivity,
     * w celu umożliwienia filtrowania jej w Logcat.
     */
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHelper = new TaskDbHelper(MainActivity.this);
        mTaskListView = findViewById(R.id.lista);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        updateUI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                /**
                 * Obsługa przycisku dodaj zadanie
                 *
                 * @param item
                 * @return boolean
                 */
                final EditText taskEditText = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_dialog)
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
                                updateUI();
                            }
                        })
                        .setNegativeButton("Anuluj", null)
                        .create();
                dialog.show();
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                //Toast.makeText(MainActivity.this,"swipeniete"+direction,Toast.LENGTH_SHORT).show();
                if(direction==8){
                    TextView taskIdHolder = viewHolder.itemView.findViewById(R.id.task_id);

                    Intent intent = new Intent(MainActivity.this, PositionEditActivity.class);
                    int taskId = Integer.parseInt(taskIdHolder.getText().toString());
                    intent.putExtra("taskId", taskId);
                    startActivity(intent);
                }else if(direction==4){
                    deleteTask(viewHolder.itemView);
                }

                updateUI();

            }
        };


        itemTouchHelper = new ItemTouchHelper(simpleCallBack);
        itemTouchHelper.attachToRecyclerView(mTaskListView);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX() > e1.getX()){
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            }else if(e1.getX()>e2.getX()){

            }
            //return super.onFling(e1, e2, velocityX, velocityY);
            return true;
        }
    }


    private void updateUI() {

        ArrayList<Task> taskList = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            //int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            //int idtext = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(new Task(cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID)),
                                  cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)),
                                  ""));
        }

        if (mAdapter == null) {
            mAdapter = new ToDoAdapter(taskList);
            mTaskListView.setAdapter(mAdapter);

            mTaskListView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        //View parent = (View) view.getParent();
        //TextView taskTextView = parent.findViewById(R.id.task_title);
        TextView taskTextView = view.findViewById(R.id.task_id);
        int task_id = Integer.parseInt(taskTextView.getText().toString());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry._ID + "=" + task_id,
                null);
        db.close();
        updateUI();
    }

}
