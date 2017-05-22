package hackeru.edu.savingdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private EditText etNote;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        etNote = (EditText) findViewById(R.id.etNote);
        etNote.addTextChangedListener(this);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("Notes", MODE_PRIVATE);

        String userName = prefs.getString("UserName", null);

        if (userName == null){
            //Not Logged in yet.
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            //We already have a user:
            Toast.makeText(this, "Hi, " + userName, Toast.LENGTH_SHORT).show();
        }
        //if we have a userName in the prefs->
        //say Hi, +"User Name"
        //else-> send the user to the login Activity.
        /*
        * Intent intent = new Intent(this , LoginActivity.class);
        * startActivity(intent);
        * */

        loadNote();
    }

    private void loadNote() {
        String note = prefs.getString("note1", ""/*if no data yet, give me an Empty String*/);
        etNote.setText(note);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        saveNote();
    }

    private void saveNote() {
        //xml Notes.xml
        //1) get a reference to sharedPreferences:  -> Notes.xml
        //   SharedPreferences prefs = getSharedPreferences("Notes", MODE_PRIVATE);

        //2) use the prefs Editor to write some data in key value pairs
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("note1", etNote.getText().toString());

        //3) commit the changes / Or apply them
        //editor.commit(); //immediately save the changes. Blocking code.
        editor.apply(); //eventually save the changes. Non Blocking code.
        /*
        * Notes.xml
        * <item name = "note1">
        *   Hi, mom. I'm writing a note.
        * </item>
        *
        * <item name = "note2">
        *   Hi, mom. I Quit my Job Don't call me -> I'll call you.
        * </item>
        *
        * <item name = "UserName">
        *   Hi, mom. I Quit my Job Don't call me -> I'll call you.
        * </item>
        *
        * */
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
