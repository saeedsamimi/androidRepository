package ir.saeed.start.livadata;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class FactorResult extends AppCompatActivity {
    TableLayout TL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor_result);
        TL = findViewById(R.id.table_layout);
        ItemsDB db = new ItemsDB(this);
        ArrayList<CellItem> items = db.getItemsList();
        long t = 0;
        long c = 0;
        for (int i = 0; i < items.size(); i++) {
            CellItem item = items.get(i);
            t += item.getCount()*item.getUnitPrice();
            c += item.getCount();
            new ViewHolder(TL, item.setID(i + 1));
        }
        new ViewHolder(TL,"Total: ",t,items.size());
    }

    public class ViewHolder{
        private LayoutInflater inflater;
        private TableRow TR;
        public ViewHolder(TableLayout parent,CellItem item) {
            inflater = getLayoutInflater();
            TR = (TableRow) inflater.inflate(R.layout.table_row,parent,false);
            FindAndTextView(R.id.item_name_view,item.getName());
            FindAndTextView(R.id.number_view, String.valueOf(item.getID()));
            FindAndTextView(R.id.unit_price_view,String.valueOf(item.getUnitPrice()));
            FindAndTextView(R.id.count_view,String.valueOf(item.getCount()));
            FindAndTextView(R.id.total_view,String.valueOf(item.getCount()*item.getUnitPrice()));
            parent.addView(TR);
        }

        public ViewHolder(TableLayout parent,String s,long total,int count) {
            inflater = getLayoutInflater();
            TR = (TableRow) inflater.inflate(R.layout.table_row,parent,false);
            for (int i = 0; i < TR.getChildCount(); i++) {
                ((TextView)TR.getChildAt(i)).setTextAppearance(R.style.table_layout_style_last_row_appearance);
            }
            FindAndTextView(R.id.item_name_view,s);
            FindAndTextView(R.id.total_view,String.valueOf(total));
            FindAndTextView(R.id.count_view,String.valueOf(count));
            FindAndTextView(R.id.number_view,"*");
            parent.addView(TR);
        }


        public void FindAndTextView(@IdRes int id, String text){
            ((TextView) TR.findViewById(id)).setText(text);
        }
    }
}