package LeoLibraries.LeoLibraries;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

public class ZeroMapBit {

    private VuforiaLocalizer vuforia;

    public static final String vuKey =
            "AdzMYbL/////AAABmflzIV+frU0RltL/ML+2uAZXgJiI" +
                    "Werfe92N/AeH7QsWCOQqyKa2G+tUDcgvg8uE8QjHeBZPcpf5hAwlC5qCfvg76eBoaa2b" +
                    "MMZ73hmTiHmr9fj3XmF4LWWZtDC6pWTFrzRAUguhlvgnck6Y4jjM16Px5TqgWYuWnpcxNM" +
                    "HMyOXdnHLlyysyE64PVzoN7hgMXgbi2K8+pmTXvpV2OeLCag8fAj1Tgdm/kKGr0TX86aQsC2" +
                    "RVjToZXr9QyAeyODi4l1KEFmGwxEoteNU8yqNbBGkPXGh/+IIm6/s/KxCJegg8qhxZDgO8110F" +
                    "RzwA5a6EltfxAMmtO0G8BB9SSkApxkcSzpyI0k2LxWof2YZG6x4H";

    public ZeroMapBit(LinearOpMode opMode) {
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id",
                        opMode.hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters =
                new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = vuKey;
        parameters.cameraName = opMode.hardwareMap.
                get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(3);

    }

    public Bitmap getZero() throws InterruptedException{

        VuforiaLocalizer.CloseableFrame frame =
                vuforia.getFrameQueue().take();
        Image image = frame.getImage(1);

        long number = frame.getNumImages();

        for (int i = 0; i < number; i++) {
            int format = frame.getImage(i).getFormat();

            if (format == PIXEL_FORMAT.RGB565) {
                image = frame.getImage(i);
            }
        }

        Bitmap bit = Bitmap.createBitmap(image.getWidth(),
                image.getHeight(), Bitmap.Config.RGB_565);
        bit.copyPixelsFromBuffer(image.getPixels());

        frame.close();

        return bit;
    }

    public double senseZero(LinearOpMode opModeX) throws InterruptedException {

        Bitmap bitmap = getZero();
        ArrayList<Integer> list =
                new ArrayList<>();

        int position = 0;
        int average = 0;

        if (opModeX.opModeIsActive()) {

            for (int column = basicSet(bitmap.getWidth()); column <
                    bitmap.getWidth(); column++) {

                for (int row = basicSet(bitmap.getHeight()); row <
                        fullSet(bitmap.getHeight()); row++) {

                    int pixel = bitmap.getPixel(column, row);

                    int redPixel = red(pixel);
                    int greenPixel = green(pixel);
                    int bluePixel = blue(pixel);

                    if (redPixel < 30 && greenPixel
                            < 30 && bluePixel < 30) {
                        list.add(column);
                    }

                }
            }
            for (int x : list) average += x;

            if (list.size() != 0) {
                average /= list.size();
                average -= 635;
                position = average / 34;
            }

            else position = 0;

        }
        opModeX.telemetry.addData("Location", position);
        opModeX.telemetry.update();
        return position;
    }

    public int basicSet (int stat) { return (stat / 2) + 50; }
    public int fullSet (int stat) {return (stat / 2) + 200; }

}