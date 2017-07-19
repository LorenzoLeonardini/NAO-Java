package org.lorenzoleonardini.naojava;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.lorenzoleonardini.naojava.ui.Window;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALAutonomousMoves;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class NAO
{
	private String name;

	private Application application;
	private ALLeds leds;

	private ALRobotPosture posture;
	private ALAutonomousMoves autoMove;
	private ALMotion motion;

	private String cameraID;

	private ALVideoDevice camera;

	private String ip;

	private JSch ssh;
	private Session session;
	private Channel channel;

	private List<List<String>> config;
	
	private boolean emulated;

	private String color;

	@SuppressWarnings("unchecked")
	public NAO(Window window, String ip) throws NAOConnectionException
	{
		if(ip.split(":")[0].length() == 0)
			throw new NAOConnectionException(window.lang.getString("noAddr"));
		if(ip.split(":")[1].length() == 0)
			throw new NAOConnectionException(window.lang.getString("noPort"));
		this.ip = ip;
		
		Random r = new Random();
		color = r.nextInt(100) < 50 ? "orange" : "blue";
		
		// Emulated NAO, for testing :3
		if(ip.split(":")[0].equalsIgnoreCase("localhost") || ip.split(":")[0].equalsIgnoreCase("127.0.0.1"))
		{
			name = "Localhost";
			emulated = true;
			return;
		}
		
		// Checking if NAO is online
		try
		{
			if (!InetAddress.getByName(ip.split(":")[0]).isReachable(3000))
				throw new NAOConnectionException(window.lang.getString("cantReach"));
		}
		catch (IOException e)
		{
			throw new NAOConnectionException(window.lang.getString("cantReach"));
		}

		// Trying to connect to the NAO
		try
		{
			application = new Application(new String[0], "tcp://" + ip);
		}
		catch (Exception e)
		{
			throw new NAOConnectionException(window.lang.getString("cantConnect"));
		}

		// Trying connecting ssh
		ssh = new JSch();
		try
		{
			session = ssh.getSession("nao", ip.split(":")[0], 22);
			session.setPassword("nao");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(5000);

			channel = session.openChannel("shell");
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			channel.setOutputStream(baos);
			channel.connect(5000);

			while (baos.toByteArray().length == 0)
			{
			}
			String shell = new String(baos.toByteArray());
			name = shell.split("\\[|m")[2];

			channel.disconnect();

			// ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
			// try
			// {
			// System.out.println(sftp.ls(""));
			// sftp.cd(".config/hal");
			// InputStream stream = sftp.get("Device_Body_dump.xml");
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(stream));
			//
			// String color = br.readLine();
			// color = br.readLine();
			// color = br.readLine();
			// color = br.readLine();
			// color = br.readLine();
			// color = br.readLine();
			// color = br.readLine();
			// color = br.readLine();
			// String[] split = color.split("value=\"|\"");
			// color = split[1];
			// System.out.println(color);
			//
			// br.close();
			// stream.close();
			// }
			// catch(IOException | SftpException e)
			// {
			// e.printStackTrace();
			// return;
			// }
			//
			// sftp.disconnect();
			session.disconnect();
		}
		catch (JSchException e)
		{
//			e.printStackTrace();
			throw new NAOConnectionException(window.lang.getString("cantConnect"));
		}

		// Trying initializing all its modules
		try
		{
			application.start();
			leds = new ALLeds(application.session());

			posture = new ALRobotPosture(application.session());
			autoMove = new ALAutonomousMoves(application.session());
			autoMove.setExpressiveListeningEnabled(false);
			motion = new ALMotion(application.session());
			config = (List<List<String>>) motion.getRobotConfig();
			camera = new ALVideoDevice(application.session());
			cameraID = camera.subscribeCamera(new BigInteger(130, new SecureRandom()).toString(32), 0, 2, 13, 30);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		window.update(true);
	}

	public void changeLedColor(String colorID, Color color)
	{
		if(emulated)
			return;
		Thread t = new Thread(() -> {
			try
			{
				leds.fadeRGB(colorID, color.getRGB(), 0.5f);
			}
			catch (CallError | InterruptedException e)
			{
				e.printStackTrace();
			}
		});
		t.start();
	}

	public void posture(String pst, float speed)
	{
		if(emulated)
			return;
		Thread t = new Thread(() -> {
			try
			{
				posture.goToPosture(pst, speed);
			}
			catch (CallError | InterruptedException e)
			{
				e.printStackTrace();
			}
		});
		t.start();
	}
	
	public void motionSetPosition(String chainName, int space, List<Float> position, int axisMask, List<Float> time)
	{
		if(emulated)
			return;
		try
		{
			motion.positionInterpolations(chainName, space, position, axisMask, 1);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void openHand(String hand)
	{
		if(emulated)
			return;
		try
		{
			motion.openHand(hand);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeHand(String hand)
	{
		if(emulated)
			return;
		try
		{
			motion.closeHand(hand);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public BufferedImage getCameraImage()
	{
		int HEIGHT = 480, WIDTH = 640;
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		int pixels[] = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		
		if(emulated)
		{
			for(int i = 0; i < pixels.length; i++)
				pixels[i] = 0x00000000;
			return bufferedImage;
		}
		
		List<Object> image = getImageRemote();
		ByteBuffer buffer = (ByteBuffer) image.get(6);
		byte[] rawData = buffer.array();


		int[] intArray = new int[HEIGHT * WIDTH];
		for (int i = 0; i < HEIGHT * WIDTH; i++)
		{
			// ((255 & 0xFF) << 24) | // alpha
			intArray[i] = ((rawData[(i * 3 + 2)] & 0xFF) << 16) | // red
			((rawData[i * 3 + 1] & 0xFF) << 8) | // green
			((rawData[i * 3] & 0xFF)); // blue
		}


		for (int i = 0; i < pixels.length; i++)
			pixels[i] = intArray[i];

		releaseImage();
		
		return bufferedImage;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getImageRemote()
	{
		try
		{
			return (List<Object>) camera.getImageRemote(cameraID);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void releaseImage()
	{
		try
		{
			camera.releaseImage(cameraID);
		}
		catch (CallError | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getName()
	{
		return name;
	}

	public String getIP()
	{
		return ip;
	}

	public String toString()
	{
		return "NAO " + name + " connected at " + ip;
	}

	public String getHeadVersion()
	{
		return config.get(1).get(1);
	}

	public String getBodyVersion()
	{
		return config.get(1).get(2);
	}
	
	public String getColor()
	{
		return color;
	}
}
