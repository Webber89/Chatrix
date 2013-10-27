package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import communication.InputConnection;
import communication.OutputConnection;
import communication.TCPInputConnection;
import communication.TCPOutputConnection;

import core.IllegalMessageException;
import core.Message;
import core.MotherConnection;

public class ClientConnection implements MotherConnection
{
    private Socket socket;
    private InputConnection input;
    private OutputConnection output;

    public ClientConnection()
    {
    }

    public void sendMessage(String room, String content, String token)
    {
	try
	{
	    Message message = new Message(Message.Type.CLIENT_MESSAGE);
	    message.addKeyValue("roomName", room);
	    message.addKeyValue("token", token);
	    message.addKeyValue("message", content);
	    output.send(message.toJson());
	} catch (IOException e)
	{
	    e.printStackTrace();
	} catch (IllegalMessageException e)
	{
	    e.printStackTrace();
	}
    }

    public void login(String user, String password)
    {
	Message message = new Message(Message.Type.CLIENT_JOIN);
	message.addKeyValue("user", user);
	message.addKeyValue("pass", password);
	try
	{
	    output.send(message.toJson());
	} catch (Exception e)
	{
	    e.printStackTrace();
	}
    }

    @Override
    public Socket getSocket()
    {
	return socket;
    }

    @Override
    public void inputReceived(String input)
    {
	try
	{
	    Message message = Message.parseJSONtoMessage(input);
	    switch (message.type.type)
	    {
	    case "SJOIN":
		ClientController.getInstance().handleLogin(message);
		break;

	    case "RINFO":
		ClientController.getInstance().handleJoinRoom(message);
		break;

	    case "SINFO":
		ClientController.getInstance().handleServerInfo(message);
		// TODO server sends rooms
		break;
	    case "SMSG":
		ClientController.getInstance().handleMessage(message);
		// TODO receive and present message
		break;

	    default:
		System.out.println(input);
	    }
	} catch (JsonParseException e)
	{
	    e.printStackTrace();
	} catch (JsonMappingException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    @Override
    public void lostConnection()
    {
	// TODO Auto-generated method stub

    }

    public void register(String user, String pass)
    {
	try
	{
	    Message regMsg = new Message(Message.Type.REGISTER);
	    regMsg.addKeyValue("user", user);
	    regMsg.addKeyValue("pass", pass);

	    output.send(regMsg.toJson());
	} catch (Exception e)
	{
	    e.printStackTrace();
	}

    }
    
    public void connect(String ip, int port, String conType) throws UnknownHostException, IOException{
	
	socket = new Socket(ip, port);
	    if (conType.equals("TCP"))
	    {
		output = new TCPOutputConnection(socket);
		input = new TCPInputConnection(this);
	    } else
	    {
		// TODO implementér UDP
		output = null;
		input = null;
	    }
	    new Thread(input).start();
    }

}
