package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.stateMachine.StateBase;
import org.firstinspires.ftc.teamcode.stateMachine.StateMachine;

public abstract class DrivingState extends StateBase {
    protected final DcMotor leftDrive;
    protected final DcMotor rightDrive;
    protected final DcMotor leftDr;
    protected final DcMotor rightDr;
    private final double timeToDriveInSeconds;
    private final double amountToDriveInInches;
    protected final Class<? extends StateBase> nextState;
    private final double leftPower;
    private final double rightPower;
    private final boolean usingEncoders;

    public DrivingState(StateMachine stateMachine,
                        double timeToDriveInSeconds,
                        double amountToDriveInInches,
                        Class<? extends StateBase> nextState,
                        double leftPower,
                        double rightPower) {
        super(stateMachine);
        if (amountToDriveInInches > 0 && timeToDriveInSeconds > 0) {
            throw new RuntimeException("Specify either seconds, or inches, not both.");
        }
        leftDrive = hardwareMap.get(DcMotor.class, "motor_2");
        rightDrive = hardwareMap.get(DcMotor.class, "motor_1");
        leftDr = hardwareMap.get(DcMotor.class, "motor_3");
        rightDr = hardwareMap.get(DcMotor.class, "motor_4");
        this.nextState = nextState;
        this.leftPower = leftPower;
        this.rightPower = rightPower;

        this.amountToDriveInInches = amountToDriveInInches;
        if (timeToDriveInSeconds > 0.0) {
            // time based driving
            this.timeToDriveInSeconds = timeToDriveInSeconds;
            this.usingEncoders = false;
        } else {
            // encoder based driving
            this.timeToDriveInSeconds = 100000;
            this.usingEncoders = true;

            // TODO: we may want to enable stop and FLOAT.
            leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftDr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightDr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftDr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftDr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void timeUpdate(double sinceOpModePlay, double sinceStateStart) {
        if (sinceStateStart > timeToDriveInSeconds) {
            stateMachine.updateState(nextState);
        }
        addTelemetry("Time", "%f %f", sinceOpModePlay, sinceStateStart);
    }

    @Override
    public void postEventsCallback() {
        addTelemetry("BK Left Motor Pos: ", "%d", leftDrive.getCurrentPosition());
        addTelemetry("BK Right Motor Pos: ", "%d", rightDrive.getCurrentPosition());
        addTelemetry("FRT Left Motor Pos: ", "%d", leftDr.getCurrentPosition());
        addTelemetry("FRT Right Motor Pos: ", "%d", rightDr.getCurrentPosition());
        if (usingEncoders &&
                Math.abs(leftDrive.getCurrentPosition()) > stateMachine.getRobotCalibration().getCounts(amountToDriveInInches)) {
            addTelemetry("BK Left Motor Pos: ", "%d", leftDrive.getCurrentPosition());
            addTelemetry("BK Right Motor Pos: ", "%d", rightDrive.getCurrentPosition());
            addTelemetry("FRT Left Motor Pos: ", "%d", leftDr.getCurrentPosition());
            addTelemetry("FRT Right Motor Pos: ", "%d", rightDr.getCurrentPosition());
            stopMotors();
            sleep(1000);
            stateMachine.updateState(this.nextState);
        } else {
            leftDrive.setDirection(DcMotor.Direction.FORWARD);
            leftDrive.setPower(leftPower);
            rightDrive.setDirection(DcMotor.Direction.FORWARD);
            rightDrive.setPower(rightPower);
            leftDr.setDirection(DcMotor.Direction.REVERSE);
            leftDr.setPower(rightPower);
            rightDr.setDirection(DcMotor.Direction.REVERSE);
            rightDr.setPower(leftPower);
        }
    }

    protected void stopMotors() {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        leftDr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDr.setPower(0.0);
        rightDr.setPower(0.0);
    }
}
