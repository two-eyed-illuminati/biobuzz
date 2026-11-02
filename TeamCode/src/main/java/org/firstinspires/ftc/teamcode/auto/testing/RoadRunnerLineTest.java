package org.firstinspires.ftc.teamcode.auto.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.rr.MecanumDrive;

@Config
@Autonomous(name = "RoadRunner Line Test", group = "Testing")
public final class RoadRunnerLineTest extends LinearOpMode {
  public static double DISTANCE = 48;

  @Override
  public void runOpMode() {
    telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    Pose2d start = new Pose2d(0, 0, 0);
    MecanumDrive drive = new MecanumDrive(hardwareMap, start);

    telemetry.addData("Distance (in)", DISTANCE);
    telemetry.addData("maxWheelVel (in/s)", MecanumDrive.PARAMS.maxWheelVel);
    telemetry.addData("Accel (in/s^2)", "%.1f to %.1f", MecanumDrive.PARAMS.minProfileAccel, MecanumDrive.PARAMS.maxProfileAccel);
    telemetry.addLine("Ready. Press start to run forward/back line laps.");
    telemetry.update();

    waitForStart();

    ElapsedTime legTimer = new ElapsedTime();
    int legs = 0;
    double totalSeconds = 0;
    double fastestSeconds = Double.MAX_VALUE;
    double slowestSeconds = 0;

    while (opModeIsActive()) {
      legTimer.reset();
      Actions.runBlocking(drive.actionBuilder(start).lineToX(DISTANCE).build());
      double forwardSeconds = legTimer.seconds();

      legTimer.reset();
      Actions.runBlocking(drive.actionBuilder(new Pose2d(DISTANCE, 0, 0)).lineToX(0).build());
      double backSeconds = legTimer.seconds();

      for (double legSeconds : new double[] {forwardSeconds, backSeconds}) {
        legs++;
        totalSeconds += legSeconds;
        fastestSeconds = Math.min(fastestSeconds, legSeconds);
        slowestSeconds = Math.max(slowestSeconds, legSeconds);
      }

      Pose2d pose = drive.localizer.getPose();
      telemetry.addData("Legs", legs);
      telemetry.addData("Last forward / back (s)", "%.3f / %.3f", forwardSeconds, backSeconds);
      telemetry.addData("Mean / fastest / slowest (s)", "%.3f / %.3f / %.3f", totalSeconds / legs, fastestSeconds, slowestSeconds);
      telemetry.addData("Pose (x, y, deg)", "%.2f, %.2f, %.1f", pose.position.x, pose.position.y, Math.toDegrees(pose.heading.toDouble()));
      telemetry.update();
    }
  }
}
