package org.firstinspires.ftc.teamcode.TeleOp.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeleOp.TeleOpMode;

import org.firstinspires.ftc.teamcode.utils.Robot;

@TeleOp(name="Test Pollen Shooter", group="Testing")
public class TestPollenShooter extends TeleOpMode {
    @Override
    protected void onLoop(){
        if (gamepad1.a) {
            Robot.pollenShooter.motor.setPower(0);
        }
        else{
            Robot.pollenShooter.motor.setPower(
                    Robot.pollenShooter.motor.getPower()
                            - gamepad1.left_stick_y * 0.1
                            - gamepad1.right_stick_y * 0.006
            );
        }
        Robot.telemetry.addData("Power", Robot.pollenShooter.motor.getPower());
    }
}
