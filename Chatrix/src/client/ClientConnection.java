package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    private volatile InputConnection input;
    private volatile OutputConnection output;
    ExecutorService pingService = Executors.newFixedThreadPool(1);
    private volatile boolean gotPing = false;
    private Semaphore sem = new Semaphore(1);
    private String ip;
    private int port;
    private String conType;
    private volatile boolean isReconnected;

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
	    case "BMSG":
		ClientController.getInstance().handleBufferMessages(message);
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
	System.out.println("Lost connection");
	input.setInactive();
	ClientController.getInstance().lostConnection();
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

    public void rejoin(String user, String pass)
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

    public void connect(String ip, int port, String conType)
	    throws UnknownHostException, IOException
    {
	this.ip = ip;
	this.port = port;
	this.conType = conType;
	connect();
    }

    private void connect() throws UnknownHostException, IOException
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
    }

    public void ping()
    {
	pingService.submit(new Callable<Object>()
	{

	    @Override
	    public Object call()
	    {
		try
		{
		    sem.release();
		    gotPing = false;
		    output.send("ping");
		    Thread.sleep(1500);
		    if (!gotPing)
		    {
			System.out.println("Didn't get ping");
			lostConnection();
		    }

		} catch (Exception e)
		{
		    e.printStackTrace();
		}
		return null;
	    }
	});

    }

    @Override
    public void gotPing()
    {
	gotPing = true;
	try
	{
	    if (sem.availablePermits() == 0)
	    {
	    } else
	    {
		sem.acquire();
		ping();
	    }
	} catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
    }

    public void reconnect() throws IOException
    {
	socket.close();
	isReconnected = false;
	long delay = 1;
	ExecutorService reconnectService = Executors.newFixedThreadPool(1);
	while (!isReconnected)
	{
	    System.out.println("Trying to reconnect in " + delay);
	    Future<Boolean> futureSuccess = reconnectService
		    .submit(new Callable<Boolean>()
		    {

			@Override
			public Boolean call() throws Exception
			{
			    try
			    {
				Thread.sleep(500);
				if (!isReconnected)
				{
				    InputConnection tempCon = input;
				    connect();
				    Message m = new Message(Message.Type.REJOIN);
				    m.addKeyValue("token", ClientController
					    .getInstance().getToken());
				    output.send(m.toJson());
				    System.out.println("succesfull connect");
				    tempCon.setMother(null);
				    isReconnected = true;
				    gotPing = true;
				    pingService.shutdownNow();
				    pingService = Executors.newFixedThreadPool(1);
				    ClientController.getInstance().regainedConnection();
				}
				return true;
			    } catch (Exception e)
			    {
				System.out.println("Sleep interupted");
				return false;
			    }
			}
		    });
	    try
	    {
		isReconnected = futureSuccess.get(delay++, TimeUnit.SECONDS);
		if(isReconnected){
		    reconnectService.shutdownNow();
		}
		else{
		    Thread.sleep(delay*1000);
		}
	    } catch (TimeoutException e2)
	    {
		System.out.println("Timed out delay: " + delay);
		
	    } catch (Exception e)
	    {
	    } finally
	    {
		futureSuccess.cancel(true);
	    }

	}

    }

    public void reconnect2()
    {
	long delay = 1;
	isReconnected = false;
	try
	{
	    socket.close();
	} catch (IOException e1)
	{
	    System.out.println(e1.getMessage());
	}
	while (!isReconnected)
	{
	    System.out.println("Trying to reconnect in " + delay);
	    try
	    {
		connect();
		Message m = new Message(Message.Type.REJOIN);
		m.addKeyValue("token", ClientController.getInstance()
			.getToken());
		output.send(m.toJson());
		System.out.println("succesfull connect");
		isReconnected= true;
		ClientController.getInstance().regainedConnection();

	    } catch (Exception e)
	    {
		delay++;
		e.printStackTrace();

	    }

	}

    }
}
