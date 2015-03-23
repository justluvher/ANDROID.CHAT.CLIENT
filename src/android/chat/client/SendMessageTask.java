package android.chat.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import android.util.Log;

public class SendMessageTask extends Thread {

	private String sender;
	private String message;
	private String msgReciever;
	
	public SendMessageTask(String sender, String message, String msgReciever) {
		super();
		this.sender = sender;
		this.message = message;
		this.msgReciever = msgReciever;
	}

	public static byte[] serialize(Message msg) throws IOException {
		Object obj = msg;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
		
	@Override
	public void run() {
		Log.d(Constants.DEBUG_TAG,"task started");
		if (!ConnectTask.getClient().isOpen()) {
			Log.i(Constants.DEBUG_TAG,"channel is not open");
			return;
		}
		byte[] byteMessage = null;
		try {
			Message myMessage = new Message(sender, message, msgReciever);  
			byteMessage = serialize(myMessage);
			Log.d(Constants.DEBUG_TAG,"message serialized lenght:" + byteMessage.length);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ByteBuffer buffer = ByteBuffer.wrap(byteMessage);
		Log.d(Constants.DEBUG_TAG,"message size is" + buffer.capacity() + " bytes." );
		int result=0;
		try {
			result = ConnectTask.getClient().write(buffer);
		} catch (IOException e1) {
			Log.d(Constants.DEBUG_TAG, "Write buffer error");
			e1.printStackTrace();
		}
		try {
			if ( result == -1 ){
				Log.d(Constants.DEBUG_TAG, "closing connection");
				ConnectTask.getClient().close();
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d(Constants.DEBUG_TAG,"Sent message to server: " + message);
		buffer.clear();
	}
}
