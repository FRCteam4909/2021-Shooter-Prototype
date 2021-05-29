package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{

    public double targetSpeed;

    //TODO test values
    public double SHOOTER_kF = 0.067; 
    public double SHOOTER_kP = 1; 
    public double SHOOTER_kI = 0;
    public double SHOOTER_kD = 9;

    WPI_TalonFX flyWheel1;
    WPI_TalonFX flyWheel2;

    CSVOutput csv;
    
    public ShooterSubsystem(){
        flyWheel1 = new WPI_TalonFX(9);
        flyWheel2 = new WPI_TalonFX(10);

        flyWheel1.setInverted(TalonFXInvertType.Clockwise);
        flyWheel2.setInverted(TalonFXInvertType.Clockwise);

        flyWheel2.follow(flyWheel1);

        flyWheel1.configFactoryDefault();
        // flyWheel2.configFactoryDefault();

        csv = new CSVOutput();

        flyWheel1.config_kF(0, SHOOTER_kF);
        flyWheel1.config_kP(0, SHOOTER_kP);
        flyWheel1.config_kI(0, SHOOTER_kI);
        flyWheel1.config_kD(0, SHOOTER_kD);

        flyWheel2.config_kF(0, SHOOTER_kF);
        flyWheel2.config_kP(0, SHOOTER_kP);
        flyWheel2.config_kI(0, SHOOTER_kI);
        flyWheel2.config_kD(0, SHOOTER_kD);

        // flyWheel2.follow(flyWheel1);
        

        SmartDashboard.putNumber("TargetVelocity", 1000);
        SmartDashboard.putData("flyWheel1", flyWheel1);
        SmartDashboard.putData("flyWheel2", flyWheel2);

        SmartDashboard.putBoolean("UseSpeedControllerControl", false);
        SmartDashboard.putNumber("RequestedPercentOutput", 0);

        SmartDashboard.putBoolean("ApplyPID", false);
        SmartDashboard.putBoolean("UsePercentOutput", false);

        SmartDashboard.putNumber("kp", SHOOTER_kP);
        SmartDashboard.putNumber("ki", SHOOTER_kI);
        SmartDashboard.putNumber("kd", SHOOTER_kD);
        SmartDashboard.putNumber("kf", SHOOTER_kF);

        SmartDashboard.putBoolean("Enabled", false);
        
    }

    @Override
    public void periodic() {

        double vel1 = flyWheel1.getSelectedSensorVelocity(0);
        double vel2 = flyWheel2.getSelectedSensorVelocity(0);
        SmartDashboard.putNumber("MeasuredVelocity1", vel1);
        SmartDashboard.putNumber("MeasuredVelocity2", vel2);
        SmartDashboard.putNumber("MeasuredVelocityDiff", vel2-vel1);

        flyWheel2.follow(flyWheel1);

        SmartDashboard.putNumber("1ClosedLoopError", flyWheel1.getClosedLoopError());
        SmartDashboard.putNumber("2ClosedLoopError", flyWheel2.getClosedLoopError());

        if (SmartDashboard.getBoolean("Enabled", false)) {
            flyWheel1.set(TalonFXControlMode.Velocity, SmartDashboard.getNumber("TargetVelocity", 0));
        }

        SHOOTER_kP = SmartDashboard.getNumber("kp", 0);
        SHOOTER_kI = SmartDashboard.getNumber("ki", 0);
        SHOOTER_kD = SmartDashboard.getNumber("kd", 0);
        SHOOTER_kF = SmartDashboard.getNumber("kf", 0);

        if (SmartDashboard.getBoolean("ApplyPID", false)) {
            SmartDashboard.putBoolean("ApplyPID", false);

            flyWheel1.config_kF(0, SHOOTER_kF);
            flyWheel1.config_kP(0, SHOOTER_kP);
            flyWheel1.config_kI(0, SHOOTER_kI);
            flyWheel1.config_kD(0, SHOOTER_kD);
        }

        if (!SmartDashboard.getBoolean("Enabled", false)) {
            flyWheel1.set(TalonFXControlMode.PercentOutput, 0);
            return;
        }

        if (SmartDashboard.getBoolean("UseSpeedControllerControl", false)) {
            SmartDashboard.putData("flyWheel1", flyWheel1);
            SmartDashboard.putData("flyWheel2", flyWheel2);
            return;
        }

        if (SmartDashboard.getBoolean("UsePercentOutput", false)) {
            double reqSpeed = SmartDashboard.getNumber("RequestedPercentOutput", 0);
            flyWheel1.set(TalonFXControlMode.PercentOutput, reqSpeed);
            return;
        }
    }

}