package frc.robot;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.PIDController;

public class Neo implements MotorIO{
    private final SparkMax motor;
    private final SparkMaxConfig config;

    private final SparkClosedLoopController control;
    private PIDController Pid;

    public Neo(int id) {
        motor = new SparkMax(id, SparkMax.MotorType.kBrushless);
        control = motor.getClosedLoopController();
        Pid = new PIDController(.125,0,0);

        config = new SparkMaxConfig();
        config.smartCurrentLimit(40);
        config.idleMode(SparkMaxConfig.IdleMode.kBrake);
        config.inverted(false);
        config.voltageCompensation(12);

        motor.configure(config , ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void setVelocity(double velocity) {
        double RMP = velocity/60;
        Pid.setSetpoint(RMP);
        control.setSetpoint(RMP,SparkMax.ControlType.kVelocity);
    }

    @Override
    public void periodic() {

    }
}
