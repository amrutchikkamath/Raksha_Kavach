package com.rakshakavach.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rakshakavach.R;
import com.rakshakavach.data.TaskRepository;
import com.rakshakavach.model.WorkTask;
import com.rakshakavach.ui.adapter.TaskAdapter;
import com.rakshakavach.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class TaskSelectorActivity extends AppCompatActivity {

    private TaskAdapter adapter;
    private PreferenceManager prefs;
    private String selectedTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_selector);

        prefs = new PreferenceManager(this);
        selectedTaskId = prefs.getSelectedTask();

        RecyclerView rv = findViewById(R.id.rv_tasks);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<WorkTask> tasks = TaskRepository.getAllTasks();
        adapter = new TaskAdapter(tasks, selectedTaskId, taskId -> selectedTaskId = taskId);
        rv.setAdapter(adapter);

        EditText etSearch = findViewById(R.id.et_search_task);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTasks(s.toString(), tasks);
            }
        });

        MaterialButton btnConfirm = findViewById(R.id.btn_confirm_task);
        btnConfirm.setOnClickListener(v -> {
            if (selectedTaskId == null) {
                Toast.makeText(this, "Please select a task first", Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.setSelectedTask(selectedTaskId);
            Toast.makeText(this, "Task selected! Stay safe 🦺", Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void filterTasks(String query, List<WorkTask> allTasks) {
        if (query.isEmpty()) {
            adapter.updateTasks(allTasks);
            return;
        }
        List<WorkTask> filtered = new ArrayList<>();
        for (WorkTask t : allTasks) {
            if (t.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(t);
            }
        }
        adapter.updateTasks(filtered);
    }
}
