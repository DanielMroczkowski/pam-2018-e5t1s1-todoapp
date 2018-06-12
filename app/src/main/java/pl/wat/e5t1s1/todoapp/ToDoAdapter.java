package pl.wat.e5t1s1.todoapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    protected ArrayList<Task> taskList;

    public ToDoAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 1. utwórz inflater (narzędzie do wczytywania widoków stworzonych w XML
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 2. wczytaj widok jednego wiersza
        View rowView = inflater.inflate(R.layout.position, parent, false);
        // 3. stwórz obiek ViewHolder, który będzie trzymać odwołania
        // do elementów jednego wiersza (np. tytułu)
        ViewHolder viewHolder = new ViewHolder(rowView);
        // 4. zwróć nowo utworzony obiekt
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        // 5. ustaw treści w wierszu 'position'
        holder.taskId.setText(String.valueOf(taskList.get(position).getId()));
        holder.taskTitle.setText(taskList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void clear() {
        taskList.clear();
    }

    public void addAll(ArrayList<Task> taskList) {
        this.taskList.addAll(taskList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskId;
        TextView taskTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            taskId = itemView.findViewById(R.id.task_id);
            taskTitle = itemView.findViewById(R.id.task_title);
        }
    }
}
