import Appointment.AppointmentTesting;
import Prescription.PrescriptionTesting;

enum TESTING { TEST1, TEST2, TEST3}

public class Main {
    public static void main(String[] args) {

//        System.out.print(TESTING.valueOf(String.valueOf(TESTING.TEST1)));
//        System.out.print(TESTING.values()[1]);
//        System.out.print(TESTING.TEST1.ordinal());

        PrescriptionTesting.run();
        AppointmentTesting.run();

    }
}