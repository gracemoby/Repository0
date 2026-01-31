import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private int appointmentId;
    private final int patientId;
    private final int doctorId;
    private final LocalDateTime appointmentDate;

    public Appointment(int appointmentId, int patientId, int doctorId, LocalDateTime appointmentDate) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Appointment [ID: " + appointmentId + ", PatientID: " + patientId + ", DoctorID: " + doctorId + ", Date: " + appointmentDate.format(fmt) + "]";
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClinicManager {
    private Scanner scanner;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public ClinicManager() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        ClinicManager manager = new ClinicManager();
        manager.run();
    }

    private void run() {
        int choice;
        do {
            System.out.println("\n--- Clinic Management System ---");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Schedule Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. View Patients");
            System.out.println("6. View Doctors");
            System.out.println("7. View Appointments");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addPatient();
                case 2 -> addDoctor();
                case 3 -> scheduleAppointment();
                case 4 -> cancelAppointment();
                case 5 -> viewPatients();
                case 6 -> viewDoctors();
                case 7 -> viewAppointments();
                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice, try again!");
            }
        } while (choice != 0);
    }

    private void addPatient() {
        try {
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine();
            System.out.print("Enter contact number: ");
            String contact = scanner.nextLine();
            int id = DatabaseManager.addPatient(name, contact);
            System.out.println("Patient added with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDoctor() {
        try {
            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine();
            System.out.print("Enter contact number: ");
            String contact = scanner.nextLine();
            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine();
            int id = DatabaseManager.addDoctor(name, contact, specialization);
            System.out.println("Doctor added with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleAppointment() {
        try {
            System.out.println("Available patients:");
            for (DatabaseManager.PatientRecord p : DatabaseManager.getAllPatients()) System.out.println(p);
            System.out.print("Enter patient ID: ");
            int pid = Integer.parseInt(scanner.nextLine());

            System.out.println("Available doctors:");
            for (DatabaseManager.DoctorRecord d : DatabaseManager.getAllDoctors()) System.out.println(d);
            System.out.print("Enter doctor ID: ");
            int did = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter appointment date (dd-MM-yyyy HH:mm): ");
            String dt = scanner.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(dt, DTF);

            int apptId = DatabaseManager.addAppointment(pid, did, dateTime);
            System.out.println("Appointment scheduled with ID: " + apptId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cancelAppointment() {
        try {
            viewAppointments();
            System.out.print("Enter appointment ID to cancel: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean ok = DatabaseManager.cancelAppointment(id);
            if (ok) System.out.println("Appointment canceled."); else System.out.println("Appointment not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewPatients() {
        try {
            for (DatabaseManager.PatientRecord p : DatabaseManager.getAllPatients()) System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewDoctors() {
        try {
            for (DatabaseManager.DoctorRecord d : DatabaseManager.getAllDoctors()) System.out.println(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewAppointments() {
        try {
            for (DatabaseManager.AppointmentRecord a : DatabaseManager.getAllAppointments()) System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ClinicManagerGUI extends JFrame {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private JTextArea displayArea;

    public ClinicManagerGUI() {
        setTitle("Clinic Appointment Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addPatientBtn = new JButton("Add Patient");
        JButton addDoctorBtn = new JButton("Add Doctor");
        JButton scheduleApptBtn = new JButton("Schedule Appointment");
        JButton cancelApptBtn = new JButton("Cancel Appointment");
        JButton viewPatientsBtn = new JButton("View Patients");
        JButton viewDoctorsBtn = new JButton("View Doctors");
        JButton viewApptBtn = new JButton("View Appointments");

        buttonPanel.add(addPatientBtn);
        buttonPanel.add(addDoctorBtn);
        buttonPanel.add(scheduleApptBtn);
        buttonPanel.add(cancelApptBtn);
        buttonPanel.add(viewPatientsBtn);
        buttonPanel.add(viewDoctorsBtn);
        buttonPanel.add(viewApptBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        addPatientBtn.addActionListener(e -> addPatient());
        addDoctorBtn.addActionListener(e -> addDoctor());
        scheduleApptBtn.addActionListener(e -> scheduleAppointment());
        cancelApptBtn.addActionListener(e -> cancelAppointment());
        viewPatientsBtn.addActionListener(e -> viewPatients());
        viewDoctorsBtn.addActionListener(e -> viewDoctors());
        viewApptBtn.addActionListener(e -> viewAppointments());
    }

    private void addPatient() {
        JTextField name = new JTextField();
        JTextField contact = new JTextField();
        Object[] fields = {"Name:", name, "Contact:", contact};
        int option = JOptionPane.showConfirmDialog(this, fields, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = DatabaseManager.addPatient(name.getText(), contact.getText());
                displayArea.append("Added Patient ID:" + id + " Name:" + name.getText() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void addDoctor() {
        JTextField name = new JTextField();
        JTextField contact = new JTextField();
        JTextField spec = new JTextField();
        Object[] fields = {"Name:", name, "Contact:", contact, "Specialization:", spec};
        int option = JOptionPane.showConfirmDialog(this, fields, "Add Doctor", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = DatabaseManager.addDoctor(name.getText(), contact.getText(), spec.getText());
                displayArea.append("Added Doctor ID:" + id + " Name:" + name.getText() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void scheduleAppointment() {
        try {
            java.util.List<DatabaseManager.PatientRecord> patients = DatabaseManager.getAllPatients();
            java.util.List<DatabaseManager.DoctorRecord> doctors = DatabaseManager.getAllDoctors();
            if (patients.isEmpty() || doctors.isEmpty()) { JOptionPane.showMessageDialog(this, "Add patients and doctors first."); return; }

            String[] pItems = patients.stream().map(p->p.id+" - "+p.name).toArray(String[]::new);
            String[] dItems = doctors.stream().map(d->d.id+" - "+d.name+" ("+d.specialization+")").toArray(String[]::new);
            JComboBox<String> pBox = new JComboBox<>(pItems);
            JComboBox<String> dBox = new JComboBox<>(dItems);

            SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE);
            JSpinner dateTimeSpinner = new JSpinner(model);
            JSpinner.DateEditor editor = new JSpinner.DateEditor(dateTimeSpinner, "dd-MM-yyyy HH:mm");
            dateTimeSpinner.setEditor(editor);

            Object[] fields = {"Select Patient:", pBox, "Select Doctor:", dBox, "Date & Time:", dateTimeSpinner};
            int option = JOptionPane.showConfirmDialog(this, fields, "Schedule Appointment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int pid = patients.get(pBox.getSelectedIndex()).id;
                int did = doctors.get(dBox.getSelectedIndex()).id;
                Date sel = (Date) dateTimeSpinner.getValue();
                LocalDateTime dt = LocalDateTime.ofInstant(sel.toInstant(), ZoneId.systemDefault());
                int apptId = DatabaseManager.addAppointment(pid, did, dt);
                displayArea.append("Scheduled Appointment ID:"+apptId+" P:"+pid+" D:"+did+" " + dt.format(DTF) + "\n");
            }
        } catch (SlotUnavailableException sue) {
            JOptionPane.showMessageDialog(this, "Slot unavailable: " + sue.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cancelAppointment() {
        try {
            java.util.List<DatabaseManager.AppointmentRecord> appts = DatabaseManager.getAllAppointments();
            if (appts.isEmpty()) { JOptionPane.showMessageDialog(this, "No appointments to cancel."); return; }
            String[] items = appts.stream().map(a->a.id+" - P:"+a.patientId+" D:"+a.doctorId+" "+a.dateTime.format(DTF)).toArray(String[]::new);
            JComboBox<String> box = new JComboBox<>(items);
            Object[] fields = {"Select appointment to cancel:", box};
            int option = JOptionPane.showConfirmDialog(this, fields, "Cancel Appointment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int idx = box.getSelectedIndex();
                int id = appts.get(idx).id;
                boolean ok = DatabaseManager.cancelAppointment(id);
                if (ok) displayArea.append("Canceled appointment ID:"+id+"\n"); else displayArea.append("Failed to cancel ID:"+id+"\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void viewPatients() {
        try {
            for (DatabaseManager.PatientRecord p : DatabaseManager.getAllPatients()) displayArea.append(p.toString()+"\n");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    private void viewDoctors() {
        try {
            for (DatabaseManager.DoctorRecord d : DatabaseManager.getAllDoctors()) displayArea.append(d.toString()+"\n");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    private void viewAppointments() {
        try {
            for (DatabaseManager.AppointmentRecord a : DatabaseManager.getAllAppointments()) displayArea.append(a.toString()+"\n");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new ClinicManagerGUI().setVisible(true)); }
}
[22/10/2025, 10:45 pm] Athul Sibi: import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:clinic.db";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    static {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Patient (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, contact TEXT)");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Doctor (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, contact TEXT, specialization TEXT)");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Appointment (id INTEGER PRIMARY KEY AUTOINCREMENT, patientId INTEGER, doctorId INTEGER, appointmentDate TEXT, FOREIGN KEY(patientId) REFERENCES Patient(id), FOREIGN KEY(doctorId) REFERENCES Doctor(id))");
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int addPatient(String name, String contact) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Patient (name, contact) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, contact);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = -1;
            if (rs.next()) id = rs.getInt(1);
            rs.close();
            ps.close();
            return id;
        }
    }

    public static int addDoctor(String name, String contact, String specialization) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Doctor (name, contact, specialization) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setString(3, specialization);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = -1;
            if (rs.next()) id = rs.getInt(1);
            rs.close();
            ps.close();
            return id;
        }
    }

    public static int addAppointment(int patientId, int doctorId, LocalDateTime dateTime) throws SQLException, SlotUnavailableException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // check slot availability
            PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM Appointment WHERE doctorId=? AND appointmentDate=?");
            check.setInt(1, doctorId);
            check.setString(2, dateTime.format(DTF));
            ResultSet crs = check.executeQuery();
            if (crs.next() && crs.getInt(1) > 0) {
                crs.close();
                check.close();
                throw new SlotUnavailableException("Slot unavailable for selected doctor at that time.");
            }
            crs.close();
            check.close();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO Appointment (patientId, doctorId, appointmentDate) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, dateTime.format(DTF));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int id = -1;
            if (rs.next()) id = rs.getInt(1);
            rs.close();
            ps.close();
            return id;
        }
    }

    public static boolean cancelAppointment(int appointmentId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Appointment WHERE id=?");
            ps.setInt(1, appointmentId);
            int affected = ps.executeUpdate();
            ps.close();
            return affected > 0;
        }
    }

    public static ArrayList<PatientRecord> getAllPatients() throws SQLException {
        ArrayList<PatientRecord> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Patient");
            while (rs.next()) {
                list.add(new PatientRecord(rs.getInt("id"), rs.getString("name"), rs.getString("contact")));
            }
            rs.close();
        }
        return list;
    }

    public static ArrayList<DoctorRecord> getAllDoctors() throws SQLException {
        ArrayList<DoctorRecord> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Doctor");
            while (rs.next()) {
                list.add(new DoctorRecord(rs.getInt("id"), rs.getString("name"), rs.getString("contact"), rs.getString("specialization")));
            }
            rs.close();
        }
        return list;
    }

    public static ArrayList<AppointmentRecord> getAllAppointments() throws SQLException {
        ArrayList<AppointmentRecord> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Appointment");
            while (rs.next()) {
                int id = rs.getInt("id");
                int pid = rs.getInt("patientId");
                int did = rs.getInt("doctorId");
                LocalDateTime dt = LocalDateTime.parse(rs.getString("appointmentDate"), DTF);
                list.add(new AppointmentRecord(id, pid, did, dt));
            }
            rs.close();
        }
        return list;
    }

    // Simple record classes to transfer DB rows
    public static class PatientRecord {
        public final int id; public final String name; public final String contact;
        public PatientRecord(int id, String name, String contact) { this.id=id; this.name=name; this.contact=contact; }
        @Override public String toString() { return "ID:"+id+" Name:"+name+" Contact:"+contact; }
    }

    public static class DoctorRecord {
        public final int id; public final String name; public final String contact; public final String specialization;
        public DoctorRecord(int id, String name, String contact, String specialization) { this.id=id; this.name=name; this.contact=contact; this.specialization=specialization; }
        @Override public String toString() { return "ID:"+id+" Name:"+name+" Spec:"+specialization; }
    }

    public static class AppointmentRecord {
        public final int id; public final int patientId; public final int doctorId; public final LocalDateTime dateTime;
        public AppointmentRecord(int id, int patientId, int doctorId, LocalDateTime dateTime) { this.id=id; this.patientId=patientId; this.doctorId=doctorId; this.dateTime=dateTime; }
        @Override public String toString() { return "ID:"+id+" P:"+patientId+" D:"+doctorId+" "+dateTime.format(DTF); }
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: public class Doctor extends Person {
    private String specialization;

    public Doctor(String name, String contactNumber, String specialization) {
        super(name, contactNumber);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public String toString() {
        return "Doctor [" + super.toString() + ", Specialization: " + specialization + "]";
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: public class Patient extends Person {
    public Patient(String name, String contactNumber) {
        super(name, contactNumber);
    }

    @Override
    public String toString() {
        return "Patient [" + super.toString() + "]";
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: public abstract class Person {
    private static int nextId = 1;

    protected int id;
    protected String name;
    protected String contactNumber;

    public Person(String name, String contactNumber) {
        this.id = nextId++;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Contact: " + contactNumber;
    }
}
[22/10/2025, 10:45 pm] Athul Sibi: class SlotUnavailableException extends Exception {
    public SlotUnavailableException(String message) {
        super(message);
    }
}



