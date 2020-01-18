package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.stateMachine.DrivingState;
import org.firstinspires.ftc.teamcode.stateMachine.StateBase;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.stateMachine.Stop;


public class DropOff5 extends StateBase {
    protected Servo servo;
    protected Servo servo_2;;
    private Boolean doneWithServo = false;

    public DropOff5(StateMachine stateMachine) {
        super(stateMachine);

    }

    @Override
    public void postEventsCallback() {
        sleep(2000);
        this.stateMachine.updateState(State6.class);
    }

    @Override
    public void timeUpdate(double sinceOpModePlay, double sinceStateStart) {
        this.servo = hardwareMap.get(Servo.class, "right_servo");
        this.servo_2 = hardwareMap.get(Servo.class, "left_servo");
        if (sinceStateStart > 5) {
            stateMachine.updateState(Stop.class);
        }
        else if (sinceStateStart > 3) {
            this.servo.setPosition(.25);

        }
        else  {
            this.servo.setPosition(1.0);
        }
        addTelemetry("Time", "%f %f", sinceOpModePlay, sinceStateStart);
        addTelemetry("Position", "%f ",this.servo.getPosition());
        addTelemetry("Position", "%f ",this.servo_2.getPosition());
}
}