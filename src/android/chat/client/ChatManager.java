package android.chat.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChatManager extends Activity {

	private static TextView textView;
	private static EditText messageText, reciever;
	private static Button sendButton;
	private static UserListener listener;
	private static String sender;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_manager);
		textView = (TextView) findViewById(R.id.textView_msg);
		messageText = (EditText) findViewById(R.id.editText1);
		reciever = (EditText) findViewById(R.id.editText_sender);
		sendButton = (Button) findViewById(R.id.button1);
		Intent intent = getIntent();
		sender = intent.getStringExtra(Constants.USER_TAG);
		listener = new UserListener();
		listener.start();
		sendButtonListener();
	}	
	
	static TextView getTextView(){
		return textView;
	}
	
	public void sendButtonListener(){
		 sendButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 String message = messageText.getText().toString();
            	 messageText.setText("");
            	 Toast.makeText(ChatManager.this, message + " is sent", Toast.LENGTH_SHORT).show();
            	 SendMessageTask task = new SendMessageTask(sender, message,reciever.getText().toString());
            	 task.start();
            	 }
         });

	}
	
}
