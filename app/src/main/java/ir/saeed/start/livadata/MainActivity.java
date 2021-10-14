package ir.saeed.start.livadata;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{
    private ItemsDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new ItemsDB(this);
        FloatingActionButton addButton = findViewById(R.id.add_btn);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(this,db.getItemsList(),db);
        recyclerView.setAdapter(adapter);
        addButton.setOnClickListener(view -> {
            AddDialog dialog = new AddDialog(MainActivity.this);
            dialog.setOnConfirmClickListener(adapter::AddItem);
            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.get_result:
                onClick();
                break;
            case R.id.close:
                (new AlertDialog.Builder(this)).setMessage("Are you sure to exit? ").setCancelable(false).setPositiveButton("Exit now!",(dialog, which) -> finish()).setNegativeButton("Cancel",((dialog, which) -> dialog.cancel())).create().show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick() {
        int t = 0;
        for (CellItem item:db.getItemsList()) t += item.getCount()*item.getUnitPrice();
        (new AlertDialog.Builder(this)).setTitle("Factor Sum: ").setMessage("Total: "+t+"\nCount: " +db.getCount()).create().show();
    }
}