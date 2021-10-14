package ir.saeed.start.livadata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    private final Context mContext;
    private final ArrayList<CellItem> mNames;
    private final ItemsDB mDB;
    public ItemRecyclerViewAdapter(Context context, ArrayList<CellItem> names, ItemsDB db) {
        mContext =context;
        mNames = names;
        mDB = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_view_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CellItem Current = mNames.get(position);
        holder.setItemName(Current.getName());
        holder.setUnitPrice(Current.getUnitPrice());
        holder.setItemCount(Current.getCount());
        holder.setTotalPrice(Current.getCount(),Current.getUnitPrice());
        holder.setPositionAndCallbacks(position);
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public void AddItem(CellItem name){
        this.mNames.add(name);
        mDB.AddItem(name);
        this.notifyItemInserted(mNames.size()-1);
    }

    public void RemoveItem(int index){
        int id = mNames.get(index).getID();
        mNames.remove(index);
        mDB.RewoveItem(id);
        this.notifyItemRemoved(index);
        this.notifyItemRangeChanged(index,mNames.size());
    }

    public void EditItem(int index){
        final CellItem item = mNames.get(index);
        AddDialog dialog = new AddDialog(mContext,item.getName(),item.getUnitPrice(),item.getCount());
        dialog.setOnConfirmClickListener(Name -> {
            item.setName(Name.getName());
            item.setCount(Name.getCount());
            item.setUnitPrice(Name.getUnitPrice());
            mDB.EditItem(item.getID(),item);
            notifyItemChanged(index);
        });
        dialog.show();
    }
    @SuppressLint("SetTextI18n")
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mTitleView, mUnitPriceView,mCountView,mTotalView;
        int mPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleView = itemView.findViewById(R.id.item_name_view);
            mUnitPriceView = itemView.findViewById(R.id.unit_price_view);
            mCountView = itemView.findViewById(R.id.count_view);
            mTotalView = itemView.findViewById(R.id.total_view);
        }
        public void setPositionAndCallbacks(int pos){
            mPosition = pos;
            (itemView.findViewById(R.id.edit_btn)).setOnClickListener(v -> EditItem(mPosition));
            (itemView.findViewById(R.id.del_btn)).setOnClickListener(view -> RemoveItem(mPosition));
        }

        public void setTotalPrice(long txt1, long txt2){mTotalView.setText("Total:"+ txt1 * txt2);}
        public void setItemName(String txt){
            mTitleView.setText("Name: \t"+txt);
        }
        public void setUnitPrice(long txt) {
            mUnitPriceView.setText("Unit Price: \t"+txt);
        }
        public void setItemCount(long txt){
            mCountView.setText("Count: \t"+txt);
        }
    }
}
