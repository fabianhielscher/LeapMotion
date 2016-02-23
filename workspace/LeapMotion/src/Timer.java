import com.leapmotion.leap.Vector;

public class Timer {

	public static Vector dropTimeStartSpot;
	public static double dropTime;
	public static double lastFrameTime;
	public static double currentFrameTime;
	public static int counter = 10;

	public static boolean timerActive = false;

	public static void initDrop() {
		// System.out.println("init drop");
		dropTimeStartSpot = LeapListener.handPos; // alle 0.1s Distanz zur
													// aktuellen handposition
		currentFrameTime = LeapListener.zeit;
		lastFrameTime = LeapListener.zeit;
		dropTime = 100; // 0.1 Sekunden Zeitintervall
		timerActive = true;
	}

	public static void updateTime() {

		if (timerActive && LeapListener.handZuSehen && GeschwindigkeitsRegelung.steuerung_aktuell == 2) {

			// Zeit aktualisieren
			currentFrameTime = LeapListener.zeit;

			if (lastFrameTime > 0) {
				dropTime -= (currentFrameTime - lastFrameTime) / 1000;
			}

			if (dropTime <= 0) {

				// Ein 0.1s Intervall

				dropTime = 0;
				if (dropTimeStartSpot.distanceTo(LeapListener.handPos) < GeschwindigkeitsRegelung.r_min) {
					// Hand war vor 0.1 Sekunden in der Deadzone
					counter++;

					// System.out.println("count " + counter);

				} else {
					// Hand hat Deadzone verlassen
					// Counter auf 0
					counter = 0;
					dropTimeStartSpot = LeapListener.handPos;

				}

				if (counter >= 5) {

					// f�ge neue Deadzone ein
					// setze Ursprung

					// GeschwindigkeitsRegelung.setOrigin(LeapListener.handPos);
					counter = 0;

					// Deadzoneradius wird erh�ht f�r pr�ziseres loslassen
					GeschwindigkeitsRegelung.r_min+=30;
					GeschwindigkeitsRegelung.deadZoneBigRadius=true;
					GeschwindigkeitsRegelung.ursprungSet = false;
					

					// System.out.println("new dead");
				}

				dropTime = 100;
			}

			lastFrameTime = currentFrameTime;
		} else {
			timerActive = false;
		}
	}
}
