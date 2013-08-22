package org.isaknight.danmoore.coopapp;

import android.content.Context;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.Cursor;

class CompanyHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="coopapp.db";
	private static final int SCHEMA_VERSION = 1;
	private static final String KEY_ID = "_id";
	public CompanyHelper(Context context){
		
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		
	}//end method
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE companies(_id INTEGER PRIMARY KEY AUTOINCREMENT, comp_name TEXT, notes TEXT);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		
	}
	
	public Cursor getAll(){
		return(getReadableDatabase().rawQuery("SELECT _id, comp_name, notes FROM companies ORDER BY comp_name", null));
		
	}
	
	public void insert(String name, String notes){
		ContentValues cv = new ContentValues();
		
		cv.put("comp_name", name);
		cv.put("notes", notes);
		
		getWritableDatabase().insert("companies", "name", cv);
	}
	
	public void update(String name, String notes){
		String[] strName = new String[] {name};
		ContentValues cv = new ContentValues();
	
		
		cv.put("comp_name", name);
		cv.put("notes", notes);
		
		getWritableDatabase().delete("companies", "comp_name = ?", strName);
		getWritableDatabase().insert("companies", "name", cv);
	}
	
	public void remove(String name){
		String[] strName = new String[] {name};
				
		getWritableDatabase().delete("companies", "comp_name = ?", strName);
	}
	
	public String getName(Cursor c){
		return(c.getString(1));
	}
	
	public String getNotes(Cursor c){
		return(c.getString(2));
	}
	
}//end class