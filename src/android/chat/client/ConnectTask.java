package android.chat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import android.os.AsyncTask;
import android.util.Log;

public class ConnectTask extends AsyncTask<String, Void, String> {

	private static SocketChannel client;
	public static SocketChannel getClient(){
		return client;
	}
	
	@Override
	protected String doInBackground(String... params) {
		try {
			Log.d(Constants.DEBUG_TAG, "System started");
			makeConnection(params[1], Integer.parseInt(params[2]),params[0]);
		} catch (IOException e) {
			Log.d(Constants.DEBUG_TAG, "Unable to connect to server");
			e.printStackTrace();
		} 
		Log.d(Constants.DEBUG_TAG, "Connecting method finished");
		return null;
	}
	
	static void makeConnection(String host_Adress, int listening_port, String user) throws IOException {
		client = SocketChannel.open();
		Log.d(Constants.DEBUG_TAG, "channel open");
		InetSocketAddress hostAddress = new InetSocketAddress(host_Adress, listening_port);
		Log.d(Constants.DEBUG_TAG, "constructor passed");
		client.connect(hostAddress); //waiting to connect
		Log.d(Constants.DEBUG_TAG, "connected");
		byte [] byteMessage = user.getBytes(); //Constants.NICK_NAME.getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(byteMessage);
		int writeResult = client.write(buffer);
		while (writeResult < -1) {}	//waits until NICK_NAME is sent to the server	
	}

}
