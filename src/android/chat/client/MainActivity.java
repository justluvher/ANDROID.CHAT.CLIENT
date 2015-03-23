package android.chat.client;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void loginButton(View view){
		Intent intent_ChatManager = new Intent(this,ChatManager.class);
		EditText editText_nickName = (EditText) findViewById(R.id.editText_nickname);
	    String nickName = editText_nickName.getText().toString();
	    EditText editText_IP = (EditText) findViewById(R.id.editText_IP);
	    String host = editText_IP.getText().toString();
	    EditText editText_port = (EditText) findViewById(R.id.editText_port);
	    String port = editText_port.getText().toString();
	    String[] params = {nickName,host,port};
	    new ConnectTask().execute(params);
	    intent_ChatManager.putExtra(Constants.USER_TAG, nickName);
	    startActivity(intent_ChatManager);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
