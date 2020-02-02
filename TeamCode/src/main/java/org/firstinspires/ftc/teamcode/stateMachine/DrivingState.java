package org.firstinspires.ftc.teamcode.stateMachine;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class DrivingState extends StateBase {
    protected  DcMotor leftDrive;
    protected  DcMotor rightDrive;
    protected  DcMotor leftBackDrive;
    protected  DcMotor rightBackDrive;
    private  double timeToDriveInSeconds;
    private  double amountToDriveInInches;
    protected  Class<? extends StateBase> nextState;
    private  double leftPower;
    private  double rightPower;
    private double slidePower;
    private  boolean usingEncoders;

    private final boolean slideModeOn;
    
    public DrivingState(StateMachine stateMachine,
                        double timeToDriveInSeconds,
                        double amountToDriveInInches,
                        Class<? extends StateBase> nextState,
                        double leftPower,
                        double rightPower) {
        super(stateMachine);
        init(amountToDriveInInches, timeToDriveInSeconds);
        slideModeOn = false;
        this.nextState = nextState;
        this.leftPower = leftPower;
        this.rightPower = rightPower;
    }
    public DrivingState(StateMachine stateMachine,
                        double timeToDriveInSeconds,
                        Class<? extends StateBase> nextState,
                        double slidePower) {    // slidePower < 0 means slide left
        super(stateMachine);
        init(0.0, timeToDriveInSeconds);
        slideModeOn = true;
        this.nextState = nextState;
        this.slidePower = slidePower;

    }

    private void init(double amountToDriveInInches, double timeToDriveInSeconds) {
        if (amountToDriveInInches > 0 && timeToDriveInSeconds > 0) {
            throw new RuntimeException("Specify either seconds, or inches, not both.");
        }
        leftDrive = hardwareMap.get(DcMotor.class, "motor_2");
        rightDrive = hardwareMap.get(DcMotor.class, "motor_1");
        leftBackDrive = hardwareMap.get(DcMotor.class, "motor_3");
        rightBackDrive = hardwareMap.get(DcMotor.class, "motor_4");

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
            leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        addTelemetry("FRT Left Motor Pos: ", "%d", leftBackDrive.getCurrentPosition());
        addTelemetry("FRT Right Motor Pos: ", "%d", rightBackDrive.getCurrentPosition());
        if (usingEncoders &&
                Math.abs(leftDrive.getCurrentPosition()) > stateMachine.getRobotCalibration().getCounts(amountToDriveInInches)) {
            addTelemetry("BK Left Motor Pos: ", "%d", leftDrive.getCurrentPosition());
            addTelemetry("BK Right Motor Pos: ", "%d", rightDrive.getCurrentPosition());
            addTelemetry("FRT Left Motor Pos: ", "%d", leftBackDrive.getCurrentPosition());
            addTelemetry("FRT Right Motor Pos: ", "%d", rightBackDrive.getCurrentPosition());
            stopMotors();
            sleep(1000);
            stateMachine.updateState(this.nextState);
        } else {
            leftDrive.setDirection(DcMotor.Direction.FORWARD);
            rightDrive.setDirection(DcMotor.Direction.FORWARD);
            leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
            rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
            if (slideModeOn) {
                leftDrive.setPower(slidePower);
                rightDrive.setPower(slidePower);
                leftBackDrive.setPower(-slidePower);
                rightBackDrive.setPower(-slidePower);
            }
            else {
                double power = rightPower == 0.0 ? leftPower : rightPower;
                leftDrive.setPower(power);
                rightDrive.setPower(power);
                leftBackDrive.setPower(power);
                rightBackDrive.setPower(power);
            }
        }
    }

    protected void stopMotors() {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setPower(0.0);
        rightBackDrive.setPower(0.0);
    }
}
