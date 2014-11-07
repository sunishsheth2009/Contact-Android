/**
 * Group members : Sunish S Sheth Net ID. : sss140830 and Kanav Kaul Net ID. : kxk140730 Start date: September 29th, 2014
 * Purpose : Class Assignment - CS 6301.022 Summary: The assignment is on
 * Contact Manager. This program basically adds a new contact, deletes an
 * existing contact and lastly updates the contact.
 * This is the Main Activity of the program. This activity if the first one to be executed when the program starts.
 * This activity is used to display the List of the contacts using a List View. All the contacts are arranged in sorted order on the First name.
 * On clicking any of the contacts, one can view the contact. On clicking, a new activity is called which is the DisplayActivity.java for further operations.
 * Action Bar has an Add to contact menu item which is used to add a new contact. On clicking, a new activity is called which is the AddUpdateContactActivity.java for further operations.
 */


// This activity is created by Sunish Sheth (sss140830)
package com.sunish.contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is the MainActivity where all the processing is done. The list creation and the action bar configuration is done here.
 * A file read is used extract the contacts from the file. Each line in the file is then processed and useful informations are divided into diffirent arrays and then displayed on the screen using the List view.
 * The action bar is used to display add to contact menu item.
 */
public class MainActivity extends ActionBarActivity{
	String [] input; // Every index in this string array will store the entire contact details of each person.
	Intent intent; // Defining an intent to call DisplayActivity and AddUpdateContactActivity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// onCreate is the method which will be initially called when the activity is started.
    	ListView listView ; // creating a List View Object.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    
		listView = (ListView) findViewById(R.id.list); // In the layout file called main.xml, a list view is created having ID list which is used to reference the layout onto the screen.	 
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);        
		listView.setTextFilterEnabled(true);
         String filename = "myfile.txt"; //This the file where all the contact details are stored.
         // Code to read the file and each single line is stored in input String which is further processed.
         try {
             FileInputStream inputStream = openFileInput(filename);
             BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
             StringBuilder total = new StringBuilder();
             String eachinputline ="";
             String line;
             line = r.readLine();
             if (line != null) {
	             while (line != null) {
	                 total.append(line);
	                 total.append("\n");
	                 eachinputline = total.toString();
	                 line = r.readLine();
	             }
	             input =  eachinputline.split("\n"); // Used to obtain every line and to be stored seperately.
	             Arrays.sort(input);// Sorting the array.
	             String [] names = new String[input.length] ; // Array to store every details of each person.
	             String [] Phones = new String[input.length] ; // Array to store the phone number.
	             HashMap<String,String> map = new HashMap<String, String>(); // Hash map to every name to its phone number.
	             String ScreenDisplay[] = new String[input.length]; // Array to store the First name and Last name
	             for(int i =0;i<input.length;i++){
	            	 names=input[i].split("\t");
	            	 ScreenDisplay[i] = names[0]+" "+names[1]; // Appending the First name and Last name into a ScreenDisplay array
	            	 Phones[i] = names[2]; // Putting the phone number in an seperate array. 
	            	 map.put(ScreenDisplay[i],Phones[i]); // Mapping the phone number to each individual person.
	             }       
	             MyListAdapter my = new MyListAdapter(this,R.layout.custom_row_view, ScreenDisplay, map); // Creating a custom view of the list.
	             listView.setAdapter(my); 
	             r.close();
	             inputStream.close();
	             
	             intent = new Intent(this, DisplayActivity.class);
	             // OnClicking the list a new activity will be called to display the details on that person.
	             listView.setOnItemClickListener(new OnItemClickListener() {
	          	   @Override
	          	   public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
	          			intent.putExtra("xyz", input); // Passing the array of all the contacts to next intent.
	          			intent.putExtra("abc", position); // Passing the position or the index of the person being clicked on the list.
	          			startActivity(intent); // Starting DisplayActivity
				}
          	});
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

/**
 * Creating the menu for the action bar.
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu); // Taking the id of the menu created in menu.xml
        super.onCreateOptionsMenu(menu);
        return true;
    }

    
    @Override
    // This function will be called on clicking any item on the action bar.
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch (item.getItemId()) {
        case R.id.add_menu:// This case is for the click on add to contacts.
            addContact();
            return true;
        default:
        	return super.onOptionsItemSelected(item);
    	}
    }

// This function is used to call the AddUpdateContactActivity.
	private void addContact() {
		// TODO Auto-generated method stub
		//this.finish();
		int position = -1;
		intent = new Intent(this, AddUpdateContactActivity.class);
		intent.putExtra("xyz", input);
			intent.putExtra("abc", position);
			startActivity(intent);
	}
}
/**
 * This class is created for creating a custom view for the list view for displaying the details of each contact.
 */
class MyListAdapter extends ArrayAdapter<String>{
	int listItemView;
	String[] ScreenDisplay;
	HashMap<String,String> map;	
	Context context;
	// This constructor takes in id of the ListView, array of names and hash map which will help map each phone number to its name.
	public MyListAdapter(Context context, int listItemView, String [] ScreenDisplay, HashMap<String,String> map) {
		    super(context, listItemView, ScreenDisplay);
		    this.context = context;
		    this.listItemView = listItemView;	    
		    this.ScreenDisplay = ScreenDisplay;
		    this.map = map;
		}
		public View getView(int position, View convertView, ViewGroup parent){
		    View v = convertView;
		    if (v == null) {
		        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        v = inflater.inflate(listItemView, null);
		    }
		    String Name = ScreenDisplay[position];
		    String Number = map.get(Name);       
		    if (Name.length() > 0) {
		        TextView cName = (TextView) v.findViewById(R.id.name); // Taking the id of the name in the list view described in custom_row_view and displaying the name of that contact.
		        TextView cNumber = (TextView) v.findViewById(R.id.phone); // Taking the id of the phone in the list view described in custom_row_view and displaying phone number of that contact.
		        cName.setText(Name); 
		        cNumber.setText(Number);
		    }
		    return v;
		}
}
