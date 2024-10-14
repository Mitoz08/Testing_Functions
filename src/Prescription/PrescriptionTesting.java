package Prescription;

import java.io.PrintStream;

public class PrescriptionTesting {
    public static void run() {
        System.out.println();;
        System.out.println("Prescription Test");
        Prescription prescription1 = new Prescription("Meth",10, MED_STATUS.PENDING);
        Prescription prescription2 = new Prescription("0-Cocaine-10");
        Prescription prescription3 = new Prescription("0-Fentanyl-10");

        PrescriptionList list1 = new PrescriptionList();
        list1.addPrescription(prescription1);
        list1.addPrescription(prescription2);
        list1.addPrescription(prescription3);

        System.out.println("Printing sorted list\n");
        list1.print();

        System.out.println("Removing index 1");
        list1.removePrescription(1);

        System.out.println("Printing sorted list\n");
        list1.print();


    }
}
