package frc.robot;

public interface MotorIO {

    /**
     *
     * @param velocity Speed of motor in RPM
     */

    public void setVelocity(double velocity);

    public void periodic();
}
