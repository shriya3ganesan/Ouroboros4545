package Doobie.DoobiLibraries.DoobiOps;

import org.firstinspires.ftc.teamcode.DoobiLibraries.DoobiLibraries.TeleLib;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp


public class TeleOp extends TeleLib {
    @Override
    public void loop() {
        arcadedrive();
        telemetry.update();
    }
}
