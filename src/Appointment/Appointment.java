package Appointment;

import Prescription.Prescription;
import Prescription.PrescriptionList;

import java.util.Date;
import java.util.Scanner;

enum APT_STATUS {PENDING, APPORVED, REJECTED, CANCELLED, COMPLETED} // Will CANCELLED and REJECTED be used?

public class Appointment implements Comparable<Appointment> {

    // Private attributes
    private APT_STATUS status;
    private String nameOfApt;
    private int patientID;
    private int doctorID;
    private Date appointmentTime;
    private String notes;
    private PrescriptionList prescriptionList;

    // Constructor
    public Appointment() {
        Scanner sc = new Scanner(System.in);

        this.status = APT_STATUS.PENDING;
        System.out.println("Enter name of appointment: ");
        this.nameOfApt = sc.nextLine();
        System.out.print("Enter patient's hosptial ID: ");
        this.patientID = sc.nextInt();
        System.out.print("Enter doctors's hosptial ID: ");
        this.doctorID = sc.nextInt();
        System.out.print("Enter YYYY-MM-DD-HH-MM: ");
        this.appointmentTime = StrToDate(sc.nextLine());
        this.notes = "Empty";
        this.prescriptionList = new PrescriptionList();
    }

    public Appointment(APT_STATUS status, String nameOfApt, int patientID, int doctorID, String date, String notes, PrescriptionList prescriptionList ) {
        this.status = status;
        this.nameOfApt = nameOfApt;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = StrToDate(date);
        this.notes = notes;
        this.prescriptionList = prescriptionList;
    }

    public Appointment(String DataInput){
        // Sample 0/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10
        String[] Inputs = DataInput.split("[/,]"); // Removed "-" to keep Date and Prescription data intact
        this.prescriptionList = new PrescriptionList();
        try {
            int index = 0;
            this.status = APT_STATUS.values()[Integer.parseInt(Inputs[index++])];
            this.nameOfApt = Inputs[index++];
            this.patientID = Integer.parseInt(Inputs[index++]);
            this.doctorID = Integer.parseInt(Inputs[index++]);
            this.appointmentTime = StrToDate(Inputs[index++]);
            this.notes = Inputs[index++];
            while (true) {
                try {
                    Prescription prescription = new Prescription(Inputs[index++]);
                    this.prescriptionList.addPrescription(prescription);
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect data input");
        }
    }

    // Public methods
    public void print(boolean Patient) {
        System.out.println("______________________________");
        System.out.printf("|%-8s:%-20s|\n", "Event", this.nameOfApt);
        if (Patient)
            System.out.printf("|%-8s:%03d%-17s|\n", "Doctor", this.doctorID, ""); // Need to change to name of doctor once classes are made
        else
            System.out.printf("|%-8s:%04d%-16s|\n", "Patient", this.patientID, ""); // Need to change to name of doctor once classes are made
        System.out.printf("|%-8s:%02d-%02d-%04d%-10s|\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth(), this.appointmentTime.getYear(), "");
        System.out.printf("|%-8s:%02d:%02d%-15s|\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("|%-8s:%-20s|\n", "Notes", this.notes);
        System.out.println("______________________________");
    }

    public void printPrescription() { this.prescriptionList.print();}

    public String getDataSave() {return DataSave();}

    @Override
    public int compareTo(Appointment o) {
        return this.appointmentTime.compareTo(o.appointmentTime);
    }

    // Private methods
    private String DataSave(){
        // Sample 0/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10
        String[] outputList = new String[] {
                String.valueOf(this.status.ordinal()), this.nameOfApt, String.valueOf(this.patientID),
                String.valueOf(this.doctorID), DateToStr(this.appointmentTime), this.notes
        };
        String output = convertStrArrayToStr(outputList, "/");
        output += prescriptionList.DataSave("/");
        return output;
    }

    private Date StrToDate (String string) {
        String[] Inputs = string.split("[-/,]");
        try {
            Date date = new Date(Integer.parseInt(Inputs[0]),
                                Integer.parseInt(Inputs[1]),
                                Integer.parseInt(Inputs[2]),
                                Integer.parseInt(Inputs[3]),
                                Integer.parseInt(Inputs[4]));
            return date;
        } catch (IndexOutOfBoundsException e) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Incorrect data input - manual input");
            System.out.print("Enter year: ");
            int year = sc.nextInt();
            System.out.print("Enter month: ");
            int month = sc.nextInt();
            System.out.print("Enter day: ");
            int day = sc.nextInt();
            System.out.print("Enter hours: ");
            int hours = sc.nextInt();
            System.out.print("Enter minutes: ");
            int minutes = sc.nextInt();
            return new Date(year-1900,month,day,hours,minutes);
        }
    }
    private String DateToStr (Date date) {
        // Sample 2024-08-21-16-00
        String[] output = new String[]
                {String.valueOf(date.getYear()), String.valueOf(date.getMonth()), String.valueOf(date.getDate()),
                String.valueOf(date.getHours()), String.valueOf(date.getMinutes())};
        return convertStrArrayToStr(output, "-");
    }

    private String convertStrArrayToStr(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
}
