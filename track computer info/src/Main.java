import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import contollers.*;

public class Main {

    public static void main(String[] args) {
        AppUsageTracker tracker = new AppUsageTracker();
        while (true) {
            String title = tracker.getActiveWindowTitle();
            System.out.println("Active Window: " + title);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}