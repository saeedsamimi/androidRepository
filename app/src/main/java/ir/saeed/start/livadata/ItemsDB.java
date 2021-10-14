package ir.saeed.start.livadata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemsDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "AppDB",TB_NAME = "Items",
            CL_ID = "ID",
            CL_NAME = "Title",
            CL_UNIT_PRICE ="UnitPrice",
            CL_COUNT = "ItemCount";
    public ItemsDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TB_NAME+"("+CL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CL_NAME +" nchar(20) NOT NULL,"+ CL_UNIT_PRICE +" integer NOT NULL ,"+CL_COUNT+" integer NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(sqLiteDatabase);
    }

    public void AddItem(CellItem name){
        ContentValues CV = new ContentValues();
        CV.put(CL_NAME,name.getName());
        CV.put(CL_UNIT_PRICE,name.getUnitPrice());
        CV.put(CL_COUNT,name.getCount());
        this.getWritableDatabase().insert(TB_NAME, null, CV);
    }

    public void RewoveItem(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TB_NAME, CL_ID + " = ?", new String[]{String.valueOf(id)});
        db.execSQL("UPDATE "+TB_NAME+" SET "+CL_ID+" = "+CL_ID+"-1 WHERE "+CL_ID+" > "+id);
    }

    public void EditItem(int id,CellItem newItem){
        ContentValues CV = new ContentValues();
        CV.put(CL_NAME,newItem.getName());
        CV.put(CL_COUNT,newItem.getCount());
        CV.put(CL_UNIT_PRICE,newItem.getUnitPrice());
        this.getWritableDatabase().update(TB_NAME,CV,"ID = ?",new String[]{String.valueOf(id)});
    }

    public int getCount(){
        Cursor c = this.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM "+TB_NAME,null);
        c.moveToFirst();
        int t = c.getInt(0);
        c.close();
        return t;
    }

    public ArrayList<CellItem> getItemsList(){
        ArrayList<CellItem> temp = new ArrayList<>();
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM "+TB_NAME,null);
        if(c.getCount() > 0)
        {
            c.moveToFirst();
            do{
                CellItem n = new CellItem(c.getString(1),c.getInt(2),c.getInt(3));
                n.setID(c.getInt(0));
                temp.add(n.setID(c.getInt(0)));
            }while (c.moveToNext());
            c.close();
        }
        if(temp.size() ==0){
            this.getWritableDatabase().execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='" +TB_NAME+"'");
        }
        return temp;
    }
}
