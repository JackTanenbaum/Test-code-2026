package frc.robot;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.Logger;

public class Kraken implements MotorIO {
    private final TalonFX motor;
    private final TalonFXConfiguration config;

    private final StatusSignal<AngularVelocity> velocitySignal;
    private final StatusSignal<Current> supplyCurrentSignal;
    private final StatusSignal<Current> satorCurrentSignal;
    private final StatusSignal<Voltage> voltageSignal;

    private final VelocityVoltage control;





    public Kraken(int id) {
        motor = new TalonFX(id);


        config = new TalonFXConfiguration();
        config.CurrentLimits.SupplyCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        config.Voltage.PeakForwardVoltage = 12;
        config.Voltage.PeakReverseVoltage = -12;

        config.Audio.BeepOnConfig = true;
        config.Audio.BeepOnBoot = true;

        config.Slot0.kV = .125;

        motor.getConfigurator().apply(config);

        velocitySignal = motor.getVelocity();
        supplyCurrentSignal = motor.getSupplyCurrent();
        satorCurrentSignal = motor.getSupplyCurrent();
        voltageSignal = motor.getMotorVoltage();

        control = new VelocityVoltage(0);
        control.Slot = 0;
        control.EnableFOC = true;
        control.IgnoreHardwareLimits = false;
        control.LimitForwardMotion = false;
        control.LimitReverseMotion = false;

        motor.setControl(control);
    }
    @Override
    public void setVelocity(double velocity) {
        control.Velocity = velocity/60;
        motor.setControl(control);

    }

    @Override
    public void periodic() {
        voltageSignal.refresh();
        Logger.recordOutput("Velocity", control.Velocity);

    }

}
