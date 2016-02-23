
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

import javafx.scene.paint.Color;

class LeapListener extends Listener {

	public static int fingerAnzahl;
	public static Vector handPos;
	public static double zeit;
	public static boolean handZuSehen;

	public void OnInit(Controller controller) {
		System.out.println("init");
	}

	public void onConnect(Controller controller) {
		System.out.println("connect");
	}

	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information
		Frame frame = controller.frame();
		// System.out.println("Frame id: " + frame.id());
		System.out.println("Frame id: " + frame.hands().count());
		// System.out.println("Timestamp: " + frame.timestamp());
		// System.out.println("Hands: "+ frame.hands().count());
		// System.out.println("Fingers: " + frame.fingers().count());
		// System.out.println("Frame id: " + frame.id());

		// �berpr�fe ob eine hand zu sehen ist
		if (frame.hands().count() == 0) {
			handZuSehen = false;
			App.hand.setFill(Color.RED);
			//			System.out.println("faa");
		} else {
			handZuSehen = true;
			App.hand.setFill(Color.BLUE);
			// System.out.println("jaa");
		}
		// App.updateText();

		// Get hands
		for (Hand hand : frame.hands()) {
			// String handType = hand.isLeft() ? "Left hand" : "Right hand";
			// System.out.println(hand.fingers().extended().count()+" " +
			// handType + ", id: " + hand.id() + ", palm position: " +
			// hand.palmPosition());

			hand.palmNormal();
			hand.direction();
			fingerAnzahl = hand.fingers().extended().count();
			if (hand.palmPosition() != null) {
				handPos = hand.palmPosition();
			}

		}

		zeit = frame.timestamp();
		GeschwindigkeitsRegelung.update();

		// System.out.println("handpos: "+handPos);
		// System.out.println("handzusehen: "+handZuSehen);
		// System.out.println(fingerAnzahl);

	}

	public Vector getHandPos() {
		return handPos;
	}
}

public class LeapController {

	public static void main(String[] args) {

		init();

	}

	public static void init() {

		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		controller.addListener(listener);

		App app = new App();
		String[] args = null;
		App.main(args);
		// kreis.init();
		// kreis.paint(Graphics ge);
		// kreis.paint();

		System.out.println("Initialized");

	}

}
