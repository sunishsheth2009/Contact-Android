/**
 * Group members : Sunish S Sheth Net ID. : sss140830 and Kanav Kaul Net ID. : kxk140730 Start date: September 29th, 2014
 * Purpose : Class Assignment - CS 6301.022 Summary: The assignment is on
 * Contact Manager. This program basically adds a new contact, deletes an
 * existing contact and lastly updates the contact.
 * This activity is used to display the details of the selected contact. The user can also delete a contact from this activity.
 * This activity is called from the Main activity by clicking on some activity for viewing its details. It can also be called from the back button of AddUpdateContact activity.
 */

// This activity is created by Kanav Kaul(kxk140730)
package com.sunish.contact;


import java.io.File;
import java.io.FileOutputStream;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends ActionBarActivity {

	String [] message; // The entire list of the contact details will be stored here.
	int position; // To define the position of interest i.e. the contact which is clicked.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		Intent intent = getIntent();
		// Getting the information from the previous intent.
		message = intent.getStringArrayExtra("xyz");
	    position = intent.getIntExtra("abc", 0);
	    ActionBar actionbar = getActionBar();
        //actionbar.hide();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		//Display the current details of the contact which is been clicked, we set each field to value of the contact person.
		String [] display = message[position].split("\t");
		TextView FirstName = (TextView)findViewById(R.id.fnameview);
		FirstName.setText(display[0]);
		
		TextView LastName = (TextView)findViewById(R.id.lnameview);
		LastName.setText(display[1]);
		
		TextView PhoneNumber = (TextView)findViewById(R.id.phoneview);
		PhoneNumber.setText(display[2]);
		
		TextView Email = (TextView)findViewById(R.id.emailview);
		Email.setText(display[3]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.edit_menu) { // Called when u click the pen icon on the action bar. It will call the update function for further operations.
			update();
			return true;
		}
		if (id == R.id.delete_menu) { // Called when u click the delete item on the action bar. It will call the delete function for further operations.
			delete();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	private void update() {
		// TODO Auto-generated method stub
		// This method will simply call the AddUpdateContact activity to update the contact. It will pass the entire array of contact details and the position of the contact being clicked from the list.
		this.finish();
		Intent intent = new Intent(this, AddUpdateContactActivity.class);
		intent.putExtra("xyz", message);
			intent.putExtra("abc", position);
			startActivity(intent);
	}
	// This method will delete the contact from the file by overwriting all the contacts except the contact being clicked.
	private void delete() {
		
		// Creating a dialog box for confirming if the user wants to delete a contact or no.
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the contact?")
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() { // Will delete if the sure confirms the delete
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
						String outputString="";
						for (int i =0;i<message.length;i++){
							if(i != position) // Removing the selected contact from being added in the file.
							outputString = outputString + message[i]+"\n";
						}
						//Toast.makeText(this, outputString, Toast.LENGTH_LONG).show();
						// Code to write back on to the file.
						try {
							String filename = "myfile.txt";
				             FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
				             outputStream.write(outputString.getBytes());
				             outputStream.close();
				             File fileDir = getFilesDir();
				             //Toast.makeText(getBaseContext(), fileDir.toString() , Toast.LENGTH_LONG).show();
				             System.out.print(fileDir.toString());
					         } catch (Exception e) {
					             e.printStackTrace();
					         }
						// After the contact is deleted, the main method is called to display the refreshed list.
						finish();
						Intent intent = new Intent(getBaseContext(), MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() { // will do nothing when the user says no.
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	  // finish();
						//Intent intent = new Intent(getBaseContext(), MainActivity.class);
						//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						//startActivity(intent);
						//Toast.makeText(getBaseContext(), "Cancel Clicked!!", Toast.LENGTH_SHORT).show();
                   }
               });
        builder.show();
	}
}
