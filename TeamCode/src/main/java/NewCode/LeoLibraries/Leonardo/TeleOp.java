package NewCode.LeoLibraries.Leonardo;

import NewCode.LeoLibraries.LeoLibraries.TeleLib;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp

public class TeleOp extends TeleLib {

    @Override
    public void loop(){
        arcadedrive();
        halfspeed();
        //output();
        //intake();

    }

}
