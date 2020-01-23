package NewCode.LeoLibraries.Leonardo;

import NewCode.LeoLibraries.LeoLibraries.TeleLib2;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp

public class TeleOp extends TeleLib2 {

    @Override
    public void loop(){
        arcadedrive();
        halfspeed();
        output();
        intake();
        cfmSpeed();

    }

}
