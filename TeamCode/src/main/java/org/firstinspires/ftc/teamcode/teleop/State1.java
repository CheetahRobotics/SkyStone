package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.stateMachine.StateBase;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;

import static org.firstinspires.ftc.teamcode.stateMachine.LoggerWrapper.log;

public class State1 extends StateBase {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive ;
    private DcMotor rightDrive ;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;
    private boolean triggerModeOn = false;


    static final double INCREMENT   = 0.01;
    static final int    CYCLE_MS    =   50;
    static final double MAX_POS     =  1.0;
    static final double MIN_POS     =  0.0;
    Servo servo;
    Servo servo_2;
    double  position_1 = (MAX_POS - MIN_POS) / 2;
    double  position_2 = (MAX_POS - MIN_POS) / 2;




    public State1(StateMachine stateMachine) {
        super(stateMachine);
        leftDrive  = hardwareMap.get(DcMotor.class, "motor_1");
        rightDrive = hardwareMap.get(DcMotor.class, "motor_2");
        leftBackDrive = hardwareMap.get(DcMotor.class, "motor_3");
        rightBackDrive = hardwareMap.get(DcMotor.class, "motor_4");
        //arm = hardwareMap.get(DcMotor.class,"motor_3");
        servo = hardwareMap.get(Servo.class, "right_servo");
        servo_2 = hardwareMap.get(Servo.class, "left_servo");

    }


    @Override
    public void timeUpdate(double sinceOpModePlay, double sinceStateStart) {
        addTelemetry("Time", "%f %f", sinceOpModePlay, sinceStateStart);
    }

    @Override
    public void touchSensorCallback(String key, TouchSensor touchSensor) {
        addTelemetry(key, "%f", touchSensor.getValue());
    }

    @Override
    public void postEventsCallback() {

        if (!triggerModeOn) {
            double leftPower;
            double rightPower;

            double drive = -gamepad.right_stick_x;
            double turn = gamepad.left_stick_y;

            this.servo.setPosition(position_1);
            addTelemetry("Servo Positon", "%s", this.servo.getPosition());
            this.servo_2.setPosition(position_2);
            addTelemetry("Servo Positon", "%s", this.servo_2.getPosition());

            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);

            leftDrive.setPower(leftPower);
            leftBackDrive.setPower(rightPower);

            rightDrive.setPower(rightPower);
            rightBackDrive.setPower(leftPower);

        }

        // this is called after all events
        addTelemetry("LeftStick", "X: %f, Y: %f",
                gamepad.left_stick_x, gamepad.left_stick_y);
        addTelemetry("triggerModeOn", "%s", triggerModeOn);
    }
    public void leftBumperChanged(boolean left_bumper) {
        if (left_bumper == true){
            position_1= position_1+.25;
            position_2= position_2-.25;
        }
        addTelemetry("Left Bumper", "%s", Boolean.toString(left_bumper));
        addTelemetry( "Positon","%s",position_1);
        addTelemetry( "Positon","%s",position_2);
    }
    public void rightBumperChanged(boolean right_bumper) {
        if (right_bumper == true){
            position_1= position_1-.25;
            position_2= position_2+.25;
        }
        addTelemetry("Right Bumper", "%s", Boolean.toString(right_bumper));
        addTelemetry( "Positon","%s",position_1);
        addTelemetry( "Positon","%s",position_2);
    }

    public void leftTriggerChanged(float power) {
        triggerModeOn = power > 0.01;

        addTelemetry( "leftTrigger","%s",power);
        leftDrive.setPower(power);
        leftBackDrive.setPower(-power);

        rightDrive.setPower(power);
        rightBackDrive.setPower(-power);
    }
    public void rightTriggerChanged(float power) {
        triggerModeOn = power > 0.01;

        addTelemetry( "rightTrigger","%s",power);
        leftDrive.setPower(-power);
        leftBackDrive.setPower(power);

        rightDrive.setPower(-power);
        rightBackDrive.setPower(power);
    }
}

