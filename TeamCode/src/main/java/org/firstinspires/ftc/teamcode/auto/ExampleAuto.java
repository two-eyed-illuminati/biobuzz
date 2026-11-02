package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.api.Paths.path;
import static com.pedropathing.api.Paths.through;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.utils.AutoBuilder;
import org.firstinspires.ftc.teamcode.auto.utils.AutoOpMode;
import org.firstinspires.ftc.teamcode.utils.Robot;

@Autonomous(name = "Example Auto", group = "Auto")
public final class ExampleAuto extends AutoOpMode {
    public ExampleAuto() {
        super(ExampleAuto::createRoutine);
    }

    public static AutoBuilder createRoutine(Follower follower) {
        Pose start = new Pose(110, 132, 3*Math.PI/2);

        // Example of a "Tweak" - meant to be used for last-minute emergency tweaks to auto
        AutoBuilder.GoToCenter gtcConfig = new AutoBuilder.GoToCenter();
        gtcConfig.endPose = new Pose(80, 72, Math.PI/2);

        Pose bottomRight = new Pose(100, 24, 3*Math.PI/2);
        Pose bottomRightCtrl = new Pose(140, 0, 3*Math.PI/2);
        Pose bottomLeft = new Pose(40, 32, 3*Math.PI/2);

        Pose topLeft = new Pose(30, 95, Math.PI);

        Pose end = new Pose(90, 110, Math.PI);

        return new AutoBuilder(follower)
                .startAt(start)
                .followPath(
                        through(
                                start, bottomRight, bottomLeft, topLeft, end
                        ).linear(start, topLeft)
                );
    }
}
