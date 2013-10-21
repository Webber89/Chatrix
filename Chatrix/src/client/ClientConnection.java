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

    public ClientConnection(String ip, int port, String conType)
    {
	try
	{
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
	} catch (UnknownHostException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    public void sendMessage(String content, String user)
    {
	try
	{
	    Message message = new Message(Message.Type.CLIENT_MESSAGE);
	    message.addKeyValue("user", user);
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
	} catch (JsonParseException e)
	{
	    e.printStackTrace();
	} catch (JsonMappingException e)
	{
	    e.printStackTrace();
	} catch (IOException e)
	{
	    e.printStackTrace();
	} catch (IllegalMessageException e)
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

		// TODO server sends rooms

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

}
