package contollers;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
public class AppUsageTracker {

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.load("user32", User32.class);

        HWND GetForegroundWindow();  // Get handle to active window

        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);  // Get window title
    }

    public String getActiveWindowTitle() {
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        byte[] windowText = new byte[512];
        User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        return Native.toString(windowText);
    }
}
