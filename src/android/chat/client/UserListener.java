package android.chat.client;

import java.io.IOException;
import java.nio.ByteBuffer;

import android.util.Log;

public class UserListener extends Thread {
	
	static String newMessage;
	
	UserListener(){}
	
	@Override
	public void run(){
	
		Log.d(Constants.DEBUG_TAG, "listener started");
		while (!ConnectTask.getClient().isConnected()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//waits to connect
		Log.d(Constants.DEBUG_TAG, "connected to server");
		while( ConnectTask.getClient().isOpen() & !isInterrupted()){
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int result=0;
				try {
					result = ConnectTask.getClient().read(readBuffer);
				} catch (IOException e1) {
					Log.d(Constants.DEBUG_TAG, "Message read exception");
				}
				if (result >0) { 
					Log.d(Constants.DEBUG_TAG, "Bytes read: " + result);
				}
				readBuffer.flip();
				if (readBuffer.hasArray()) {
					newMessage = new String(readBuffer.array()).trim();
					readBuffer.clear();
					Log.d(Constants.DEBUG_TAG, newMessage);
					publishMessage(newMessage);
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		Log.d(Constants.DEBUG_TAG, "listener is stopped");
	}
	
	private void publishMessage(final String text){
		Log.d(Constants.DEBUG_TAG, "publishing starts");
		ChatManager.getTextView().post(new Runnable() {
			@Override
			public void run() {
				 ChatManager.getTextView().setText(text);
			}
		});
	}
}
