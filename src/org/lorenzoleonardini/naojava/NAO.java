package org.lorenzoleonardini.naojava;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

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

public class NAO
{
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

	public boolean isEmulated()
	{
		return false;
	}

	public void lookForward() throws CallError, InterruptedException
	{
	}

	public void lookDown() throws CallError, InterruptedException
	{
	}

	public void lookAround() throws CallError, InterruptedException
	{
	}

	public void changeLedColor(String colorID, Color color)
	{
	}

	public void posture(String pst, float speed)
	{
	}

	public void motionSetPosition(String chainName, int space, List<Float> position, int axisMask, List<Float> time) throws CallError, InterruptedException
	{
	}

	public void positionInterpolations(String chainName, int space, List<List<Float>> positions, int axisMask, List<Float> time) throws CallError, InterruptedException
	{
	}

	public void openHand(String hand) throws CallError, InterruptedException
	{
	}

	public void closeHand(String hand) throws CallError, InterruptedException
	{
	}

	public void setAngles(String effector, List<Float> pos, float speed) throws CallError, InterruptedException
	{
	}

	public BufferedImage getCameraImage() throws CallError, InterruptedException
	{
		return null;
	}

	public List<Object> getImageRemote() throws CallError, InterruptedException
	{
			return null;
	}

	public void releaseImage() throws CallError, InterruptedException
	{
	}

	public String getName()
	{
		return null;
	}

	public String getIP()
	{
		return null;
	}

	public String toString()
	{
		return null;
	}

	public String getHeadVersion()
	{
		return null;
	}

	public String getBodyVersion()
	{
		return null;
	}

	public String getColor()
	{
		return null;
	}
}
