package org.isaknight.danmoore.coopapp;

import android.os.Bundle;
import android.app.TabActivity;
import android.view.*;
import android.widget.*;
import android.content.Context;
import android.database.Cursor;

public class CoopAppActivity extends TabActivity{
	public final static String ID_EXTRA="org.isaknight.danmoore.coopapp";
	Cursor model = null;
	CompanyAdapter adapter=null;
	EditText name=null;
	EditText notes=null;
	CompanyHelper helper = null;
		
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coop_app);
        
        helper = new CompanyHelper(this);
        
        name=(EditText)findViewById(R.id.comp_name);
        notes=(EditText)findViewById(R.id.notes);
        
        Button save = (Button)findViewById(R.id.save);
        Button edit = (Button)findViewById(R.id.edit);
        Button delete = (Button)findViewById(R.id.delete);
        
        save.setOnClickListener(onSave);
        edit.setOnClickListener(onEdit);
        delete.setOnClickListener(onDelete);
        
        
        ListView list = (ListView)findViewById(R.id.companies);
        
        model=helper.getAll();
        startManagingCursor(model);
        adapter=new CompanyAdapter(model);
        list.setAdapter(adapter);
        
        TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
        
        spec.setContent(R.id.companies);
        spec.setIndicator("Companies", getResources().getDrawable(R.drawable.ucf));
        
        
        getTabHost().addTab(spec);
        
        spec=getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details", getResources().getDrawable(R.drawable.ucf));
        
        getTabHost().addTab(spec);
        
        getTabHost().setCurrentTab(0);
        
     
        
        list.setOnItemClickListener(onListClick);
    }
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	
    	helper.close();
    }
    
    private View.OnClickListener onSave=new View.OnClickListener() {
		
		public void onClick(View v) {
						
			helper.insert(name.getText().toString(), notes.getText().toString());
			
			model.requery();
			
			
			getTabHost().setCurrentTab(0);
		}
	};
	
	private View.OnClickListener onEdit = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			
			helper.update(name.getText().toString(), notes.getText().toString());
			
			model.requery();
			
			
			getTabHost().setCurrentTab(0);
		}
	};
	
	private View.OnClickListener onDelete = new View.OnClickListener() {
		
		public void onClick(View v) {
			
						
			helper.remove(name.getText().toString());
			name.setText("");
			notes.setText("");
			
			model.requery();
			
			getTabHost().setCurrentTab(0);
					
		}
		
	};
	
	private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			
						
			model.moveToPosition(position);
			name.setText(helper.getName(model));
			notes.setText(helper.getNotes(model));
			
			getTabHost().setCurrentTab(1);
			
			
		}
	};
	
	class CompanyAdapter extends CursorAdapter {
		CompanyAdapter(Cursor c){
			super(CoopAppActivity.this, c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c){
			
			CompanyHolder holder = (CompanyHolder)row.getTag();
			
			holder.populateFrom(c, helper);			
		}
		
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent){
			
			LayoutInflater inflater=getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			CompanyHolder holder = new CompanyHolder(row);
			
			row.setTag(holder);
			
			return(row);
		}
		
				
	}
	
	static class CompanyHolder{
		private TextView name=null;
		
		CompanyHolder(View row){
			name=(TextView)row.findViewById(R.id.comp_name);
		}
		
		void populateFrom(Cursor c, CompanyHelper helper){
			name.setText(helper.getName(c));
		}
	}
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_coop_app, menu);
        //return true;
    //}
}
