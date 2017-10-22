package org.lorenzoleonardini.naojava;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import org.lorenzoleonardini.naojava.ui.Window;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALAudioDevice;
import com.aldebaran.qi.helper.proxies.ALAudioPlayer;
import com.aldebaran.qi.helper.proxies.ALAudioRecorder;
import com.aldebaran.qi.helper.proxies.ALAutonomousLife;
import com.aldebaran.qi.helper.proxies.ALAutonomousMoves;
import com.aldebaran.qi.helper.proxies.ALBacklightingDetection;
import com.aldebaran.qi.helper.proxies.ALBarcodeReader;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALBattery;
import com.aldebaran.qi.helper.proxies.ALBehaviorManager;
import com.aldebaran.qi.helper.proxies.ALBodyTemperature;
import com.aldebaran.qi.helper.proxies.ALChestButton;
import com.aldebaran.qi.helper.proxies.ALColorBlobDetection;
import com.aldebaran.qi.helper.proxies.ALConnectionManager;
import com.aldebaran.qi.helper.proxies.ALDarknessDetection;
import com.aldebaran.qi.helper.proxies.ALDiagnosis;
import com.aldebaran.qi.helper.proxies.ALDialog;
import com.aldebaran.qi.helper.proxies.ALEngagementZones;
import com.aldebaran.qi.helper.proxies.ALFaceCharacteristics;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import com.aldebaran.qi.helper.proxies.ALFsr;
import com.aldebaran.qi.helper.proxies.ALGazeAnalysis;
import com.aldebaran.qi.helper.proxies.ALInfrared;
import com.aldebaran.qi.helper.proxies.ALLaser;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALLocalization;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALMovementDetection;
import com.aldebaran.qi.helper.proxies.ALNavigation;
import com.aldebaran.qi.helper.proxies.ALNotificationManager;
import com.aldebaran.qi.helper.proxies.ALPeoplePerception;
import com.aldebaran.qi.helper.proxies.ALPhotoCapture;
import com.aldebaran.qi.helper.proxies.ALPreferenceManager;
import com.aldebaran.qi.helper.proxies.ALRedBallDetection;
import com.aldebaran.qi.helper.proxies.ALResourceManager;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALSensors;
import com.aldebaran.qi.helper.proxies.ALSittingPeopleDetection;
import com.aldebaran.qi.helper.proxies.ALSonar;
import com.aldebaran.qi.helper.proxies.ALSoundDetection;
import com.aldebaran.qi.helper.proxies.ALSoundLocalization;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALStore;
import com.aldebaran.qi.helper.proxies.ALSystem;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTouch;
import com.aldebaran.qi.helper.proxies.ALTracker;
import com.aldebaran.qi.helper.proxies.ALUserSession;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;
import com.aldebaran.qi.helper.proxies.ALVideoRecorder;
import com.aldebaran.qi.helper.proxies.ALVisionRecognition;
import com.aldebaran.qi.helper.proxies.ALVisualCompass;
import com.aldebaran.qi.helper.proxies.ALVisualSpaceHistory;
import com.aldebaran.qi.helper.proxies.ALWorldRepresentation;
import com.aldebaran.qi.helper.proxies.DCM;
import com.aldebaran.qi.helper.proxies.PackageManager;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class NAO
{
	private String name;

	private Application application;

	public ALAutonomousLife autonomousLife;
	public ALBehaviorManager behaviorManager;
	public ALConnectionManager connectionManager;
	public ALMemory memory;
	public ALNotificationManager notificationManager;
	public ALPreferenceManager preferenceManager;
	public ALResourceManager resourceManager;
	public ALStore store;
	public ALSystem system;
	public ALUserSession userSession;
	public ALWorldRepresentation worldRepresentation;
	public PackageManager packageManager;

	public ALAutonomousMoves autonomousMoves;
	public ALMotion motion;
	public ALNavigation navigation;
	public ALRobotPosture robotPosture;

	public ALAnimatedSpeech animatedSpeech;
	public ALAudioDevice audioDevice;
	public ALAudioPlayer audioPlayer;
	public ALAudioRecorder audioRecorder;
	public ALDialog dialog;
	public ALSoundDetection soundDetection;
	public ALSoundLocalization soundLocalization;
	public ALSpeechRecognition speechRecognition;
	public ALTextToSpeech textToSpeech;

	public ALBacklightingDetection backlightingDetection;
	public ALBarcodeReader barcodeReader;
	public ALColorBlobDetection colorBlobDetection;
	public ALDarknessDetection darknessDetection;
	public ALLocalization localization;
	public ALMovementDetection movementDetection;
	public ALPhotoCapture photoCapture;
	public ALRedBallDetection redBallDetection;
	public ALVideoDevice videoDevice;
	public ALVideoRecorder videoRecorder;
	public ALVisionRecognition visionRecognition;
	public ALVisualCompass visualCompass;
	public ALVisualSpaceHistory visualSpaceHistory;

	public ALBasicAwareness basicAwareness;
	public ALEngagementZones engagementZones;
	public ALFaceCharacteristics faceCharacteristics;
	public ALFaceDetection faceDetection;
	public ALGazeAnalysis gazeAnalysis;
	public ALPeoplePerception peoplePerception;
	public ALSittingPeopleDetection sittingPeopleDetection;

	public ALBattery battery;
	public ALBodyTemperature bodyTemperature;
	public ALChestButton chestButton;
	public ALFsr fsr;
	public ALInfrared infrared;
	public ALLaser laser;
	public ALLeds leds;
	public ALSensors sensors;
	public ALSonar sonar;
	public ALTouch touch;

	public ALTracker tracker;

	public ALDiagnosis diagnosis;

	public DCM dcm;

	private String cameraID;

	private String ip;

	private JSch ssh;
	private Session session;
	private Channel channel;

	private List<List<String>> config;

	private boolean emulated;

	public boolean isEmulated()
	{
		return emulated;
	}

	private String color;

	@SuppressWarnings("unchecked")
	public NAO(Window window, String ip) throws NAOConnectionException
	{
		if (ip.split(":")[0].length() == 0)
			throw new NAOConnectionException(window.lang.getString("noAddr"));
		if (ip.split(":")[1].length() == 0)
			throw new NAOConnectionException(window.lang.getString("noPort"));
		this.ip = ip;

		Random r = new Random();
		color = r.nextInt(100) < 50 ? "orange" : "blue";

		// Emulated NAO, for testing :3
		if (ip.split(":")[0].equalsIgnoreCase("localhost") || ip.split(":")[0].equalsIgnoreCase("127.0.0.1"))
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

		System.out.println("connecting");

		// Trying to connect to the NAO
		try
		{
			application = ApplicationCache.newApplication(ip);
		}
		catch (Exception e)
		{
			disconnect();
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

			ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
			sftp.connect();
			try
			{
				InputStream stream = sftp.get(".config/hal/Device_Body_dump.xml");
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));

				String color = br.readLine();
				color = br.readLine();
				color = br.readLine();
				color = br.readLine();
				color = br.readLine();
				color = br.readLine();
				color = br.readLine();
				color = br.readLine();
				String[] split = color.split("value=\"")[1].split("\"");
				color = split[0];
				color = color.toLowerCase();
				if (color.startsWith("blue"))
					this.color = "blue";
				if (color.startsWith("orange"))
					this.color = "orange";

				br.close();
				stream.close();
			}
			catch (IOException | SftpException e)
			{
				sftp.disconnect();
				session.disconnect();
				e.printStackTrace();
				disconnect();
				throw new NAOConnectionException("SFTP");
			}

			sftp.disconnect();
			session.disconnect();
		}
		catch (JSchException e)
		{
			session.disconnect();
			disconnect();
			throw new NAOConnectionException(window.lang.getString("cantConnect"));
		}

		// Trying initializing all its modules
		try
		{
			application.start();

			autonomousLife = new ALAutonomousLife(application.session());
			behaviorManager = new ALBehaviorManager(application.session());
			connectionManager = new ALConnectionManager(application.session());
			memory = new ALMemory(application.session());
			notificationManager = new ALNotificationManager(application.session());
			preferenceManager = new ALPreferenceManager(application.session());
			resourceManager = new ALResourceManager(application.session());
			store = new ALStore(application.session());
			system = new ALSystem(application.session());
			userSession = new ALUserSession(application.session());
			worldRepresentation = new ALWorldRepresentation(application.session());
			packageManager = new PackageManager(application.session());

			autonomousMoves = new ALAutonomousMoves(application.session());
			autonomousMoves.setExpressiveListeningEnabled(false);
			motion = new ALMotion(application.session());
			config = (List<List<String>>) motion.getRobotConfig();
			navigation = new ALNavigation(application.session());
			robotPosture = new ALRobotPosture(application.session());

			animatedSpeech = new ALAnimatedSpeech(application.session());
			audioDevice = new ALAudioDevice(application.session());
			audioPlayer = new ALAudioPlayer(application.session());
			audioRecorder = new ALAudioRecorder(application.session());
			dialog = new ALDialog(application.session());
			soundDetection = new ALSoundDetection(application.session());
			soundLocalization = new ALSoundLocalization(application.session());
			speechRecognition = new ALSpeechRecognition(application.session());
			textToSpeech = new ALTextToSpeech(application.session());

			backlightingDetection = new ALBacklightingDetection(application.session());
			barcodeReader = new ALBarcodeReader(application.session());
			colorBlobDetection = new ALColorBlobDetection(application.session());
			darknessDetection = new ALDarknessDetection(application.session());
			localization = new ALLocalization(application.session());
			movementDetection = new ALMovementDetection(application.session());
			photoCapture = new ALPhotoCapture(application.session());
			redBallDetection = new ALRedBallDetection(application.session());
			videoDevice = new ALVideoDevice(application.session());
			cameraID = videoDevice.subscribeCamera(new BigInteger(130, new SecureRandom()).toString(32), 0, 2, 13, 30);
			videoRecorder = new ALVideoRecorder(application.session());
			visionRecognition = new ALVisionRecognition(application.session());
			visualCompass = new ALVisualCompass(application.session());
			visualSpaceHistory = new ALVisualSpaceHistory(application.session());

			basicAwareness = new ALBasicAwareness(application.session());
			engagementZones = new ALEngagementZones(application.session());
			faceCharacteristics = new ALFaceCharacteristics(application.session());
			faceDetection = new ALFaceDetection(application.session());
			gazeAnalysis = new ALGazeAnalysis(application.session());
			peoplePerception = new ALPeoplePerception(application.session());
			sittingPeopleDetection = new ALSittingPeopleDetection(application.session());

			battery = new ALBattery(application.session());
			bodyTemperature = new ALBodyTemperature(application.session());
			chestButton = new ALChestButton(application.session());
			fsr = new ALFsr(application.session());
			infrared = new ALInfrared(application.session());
			laser = new ALLaser(application.session());
			leds = new ALLeds(application.session());
			sensors = new ALSensors(application.session());
			sonar = new ALSonar(application.session());
			touch = new ALTouch(application.session());

			tracker = new ALTracker(application.session());

			diagnosis = new ALDiagnosis(application.session());

			dcm = new DCM(application.session());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		window.update(true);
	}

	public void lookForward() throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.setAngles("HeadPitch", 0f, .1f);
		motion.setAngles("HeadYaw", 0f, .1f);
	}

	public void lookDown() throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.setAngles("HeadPitch", .45f, .1f);
	}

	Random r = new Random();

	public void lookAround() throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.setAngles("HeadYaw", r.nextFloat() / 2 - .25f, .07f);
	}

	public void changeLedColor(String colorID, Color color)
	{
		if (emulated)
			return;
		Thread t = new Thread(() ->
		{
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
		if (emulated)
			return;
		Thread t = new Thread(() ->
		{
			try
			{
				robotPosture.goToPosture(pst, speed);
			}
			catch (CallError | InterruptedException e)
			{
				e.printStackTrace();
			}
		});
		t.start();
	}

	public void motionSetPosition(String chainName, int space, List<Float> position, int axisMask, List<Float> time) throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.positionInterpolations(chainName, space, position, axisMask, 3f);
	}

	public void positionInterpolations(String chainName, int space, List<List<Float>> positions, int axisMask, List<Float> time) throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.positionInterpolations(chainName, space, positions, axisMask, time);
	}

	public void openHand(String hand) throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.openHand(hand);
	}

	public void closeHand(String hand) throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.setStiffnesses("RHand", 1);
		motion.angleInterpolation("RHand", 0.3f, 1, true);
	}

	public void setAngles(String effector, List<Float> pos, float speed) throws CallError, InterruptedException
	{
		if (emulated)
			return;
		motion.setAngles(effector, pos, speed);
	}

	public BufferedImage getCameraImage() throws CallError, InterruptedException
	{
		int HEIGHT = 480, WIDTH = 640;
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		int pixels[] = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

		if (emulated)
		{
			Random r = new Random();
			int lum = 56;
			for (int y = 0; y < HEIGHT; y++)
			{
				for (int x = 0; x < WIDTH; x++)
				{
					int rgb = r.nextInt(256 - lum) + lum;
					pixels[x + y * WIDTH] = rgb << 16 | rgb << 8 | rgb;
				}
				if (r.nextInt(20) < 2)
					lum = r.nextInt(130);
			}
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
	public List<Object> getImageRemote() throws CallError, InterruptedException
	{
		if (emulated)
			return null;
		return (List<Object>) videoDevice.getImageRemote(cameraID);
	}

	public void releaseImage() throws CallError, InterruptedException
	{
		if (emulated)
			return;
		videoDevice.releaseImage(cameraID);
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

	public void disconnect()
	{
		if (emulated)
			return;
		application.stop();
		System.out.println("DISCONNECTED");
	}
}
