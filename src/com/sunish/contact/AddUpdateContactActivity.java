/**
 * Group members : Sunish S Sheth Net ID. : sss140830 and Kanav Kaul Net ID. : kxk140730 Start date: September 29th, 2014
 * Purpose : Class Assignment - CS 6301.022 Summary: The assignment is on
 * Contact Manager. This program basically adds a new contact, deletes an
 * existing contact and lastly updates the contact.
 * This activity is used to add a new contact and also to update an existing contact.
 * This activity will be called from the MainActivity when the user clicked add to contact from the action bar or from the DisplayActivity when user is trying to edit some data.
 */

package com.sunish.contact;


//This activity is created by Sunish Sheth (sss140830)
import java.io.File;
import java.io.FileOutputStream;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AddUpdateContactActivity extends ActionBarActivity {
	int position; // To define the position of interest i.e. the contact which is clicked.
	String [] message; // The entire list of the contact details will be stored here.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String appname;
		// Getting the information from the previous intent.
		Intent intent = getIntent();
		message = intent.getStringArrayExtra("xyz"); 
	    position = intent.getIntExtra("abc", 0);
		if(position == -1){
			appname = "New Contact"; // If no contact is selected. This means the position is -1 and user has clicked on add a new contact. This is used to change the application name on the header.
		}else{
			appname = "Edit Contact"; // If a particular contact is selected, the position will be defined. This means the user is trying to update an existing contact.
		}
		setContentView(R.layout.activity_add_update_contact);
			this.setTitle(appname);
		    ActionBar actionbar = getActionBar();
	        //actionbar.hide();
			actionbar.setDisplayHomeAsUpEnabled(true); // setting up a back on the action bar.
			// If the position is defined i.e. it is greater than 0. This means the user is updating an existing contact. So to display the current details of the contact, we set each field to value of the contact person.
		    if(position >= 0){ 
				String [] display = message[position].split("\t");
				TextView FirstName = (TextView)findViewById(R.id.fnametext);
				FirstName.setText(display[0]);
				
				TextView LastName = (TextView)findViewById(R.id.lnametext);
				LastName.setText(display[1]);
				
				TextView PhoneNumber = (TextView)findViewById(R.id.phonetext);
				PhoneNumber.setText(display[2]);
				
				TextView Email = (TextView)findViewById(R.id.emailtext);
				Email.setText(display[3]);
		    }
		    else{
		    	//Toast.makeText(this, "You are adding", Toast.LENGTH_LONG).show();
		    }
		    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_update_contact, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem menuItem=menu.findItem(R.id.done_menu);
		if(position>=0){
			menuItem.setTitle("Update Contact");
		}else{
			menuItem.setTitle("Add to contact");
		}
		return true;
        
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
	        case R.id.done_menu: // When the user saves the changes by clicking on the tick icon. A submit method is called to complete the functioning.
	        	submit();
	            return true;
	        case android.R.id.home: // This case is executed when you click the home button on the top. 
	        	if(position != -1){ // If the position not -1, this means user was currently updating the contact. So without clicking on the save or done, the user goes back. This means it should go on the display activity.
	        		this.finish();
					Intent intent = new Intent(this, DisplayActivity.class);
					intent.putExtra("xyz", message);
						intent.putExtra("abc", position);
						startActivity(intent);
				}else{ // If the position is -1, this means user was currently adding a contact. So without clicking on the save or done, the user goes back. This means it should go on the MainActivity where the list of contacts are displayed.
					this.finish();
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
				}
				return true;
	        default:
	        	return super.onOptionsItemSelected(item);
    	}
	}
	
	/**
	 * When the user clicked on the submit button i.e. the tick icon. This function is called. This function will perform add contact i.e. it will write a new contact to the file or it will update a contact and then write is to a file. 
	 */
	private void submit() {
		// TODO Auto-generated method stub
		if(position == -1){ // Position = -1 means submit will perform an add to contact.
			// The following is used to take all the data from the text fields into a string.
			TextView FirstName = (TextView)findViewById(R.id.fnametext);
			String FirstNameString = FirstName.getText().toString();
			
			TextView LastName = (TextView)findViewById(R.id.lnametext);
			String LastNameString = LastName.getText().toString();
			
			TextView PhoneNumber = (TextView)findViewById(R.id.phonetext);
			String PhoneNumberString = PhoneNumber.getText().toString();
			
			TextView Email = (TextView)findViewById(R.id.emailtext);
			String EmailString = Email.getText().toString();
			
			//Toast.makeText(this, FirstNameString, Toast.LENGTH_LONG).show();
			if(FirstNameString.length() <= 0){
				Toast.makeText(this, "Please enter first name", Toast.LENGTH_LONG).show(); // Validation of text field being blank.
			}else{
				if(LastNameString.length() <= 0){
					LastNameString = " ";
				}
				if( PhoneNumberString.length() <= 0){
					PhoneNumberString = " ";
				}
				if(EmailString.length() <= 0){
					EmailString = " ";
				}
				//Concatenate the details of the user given to add to contact in a string and then appending the details to a file. 
				String outputString = FirstNameString.toString() +"\t"+ LastNameString.toString()+"\t"+ PhoneNumberString.toString()+"\t"+ EmailString.toString()+"\n";
				//Toast.makeText(this, outputString, Toast.LENGTH_LONG).show();
				//Writing to a file.
				try {
					String filename = "myfile.txt";
		             FileOutputStream outputStream = openFileOutput(filename, Context.MODE_APPEND);
		             outputStream.write(outputString.getBytes());
		             outputStream.close();
		             File fileDir = getFilesDir();
		             //Toast.makeText(getBaseContext(), fileDir.toString() , Toast.LENGTH_LONG).show();
		             System.out.print(fileDir.toString());
			         } catch (Exception e) {
			             e.printStackTrace();
			         }
				this.finish();
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear stack of the activities which are started.
				startActivity(intent);
			}
		}else{
			// Position is not -1 means submit will perform an update operation of a contact.
			
			// The following statements are used to take all the data from the text fields into a string for update operation.
			TextView FirstName = (TextView)findViewById(R.id.fnametext);
			String FirstNameString = FirstName.getText().toString();
			
			TextView LastName = (TextView)findViewById(R.id.lnametext);
			String LastNameString = LastName.getText().toString();
			
			TextView PhoneNumber = (TextView)findViewById(R.id.phonetext);
			String PhoneNumberString = PhoneNumber.getText().toString();
			
			TextView Email = (TextView)findViewById(R.id.emailtext);
			String EmailString = Email.getText().toString();
			
			if(FirstNameString.length() <= 0){ // While update the FirstName should not be kept blank.
				Toast.makeText(this, "Please enter first name", Toast.LENGTH_LONG).show();
			}else{
				if(LastNameString.length() <= 0){
					LastNameString = " ";
				}
				if( PhoneNumberString.length() <= 0){
					PhoneNumberString = " ";
				}
				if(EmailString.length() <= 0){
					EmailString = " ";
				}
				// Changing the contents of the current data to a modified updated data and then writing it to the file.
				message[position] = FirstNameString.toString() +"\t"+ LastNameString.toString()+"\t"+ PhoneNumberString.toString()+"\t"+ EmailString.toString();
				String outputString="";
				
				for (int i =0;i<message.length;i++){
					outputString = outputString + message[i]+"\n";
				}
				//Toast.makeText(this, outputString, Toast.LENGTH_LONG).show();
				//Writing to a file.
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
				this.finish();
				Intent intent = new Intent(this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		}
		
	}
}
