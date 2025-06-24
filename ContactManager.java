/*
 * Contact Manager - GUI Application
 * Allows users to add, view, edit, and delete contacts with file persistence.
 */

package prodigy_3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ContactManager {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactApp().createAndShowGUI());
    }

    // Contact class to represent a contact
    static class Contact {
        String name;
        String phone;
        String email;

        Contact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }

        @Override
        public String toString() {
            return name + " | " + phone + " | " + email;
        }

        String toFileString() {
            return name + "," + phone + "," + email;
        }

        static Contact fromFileString(String line) {
            String[] parts = line.split(",", 3);
            if (parts.length == 3) {
                return new Contact(parts[0], parts[1], parts[2]);
            }
            return null;
        }
    }

    // GUI app class
    static class ContactApp {
        private final DefaultListModel<Contact> contactListModel = new DefaultListModel<>();
        private final File contactFile = new File("contacts.txt");

        void createAndShowGUI() {
            JFrame frame = new JFrame("Contact Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            JList<Contact> contactList = new JList<>(contactListModel);
            JScrollPane scrollPane = new JScrollPane(contactList);

            JLabel editHintLabel = new JLabel("Double-click a contact to edit it.");
            editHintLabel.setForeground(Color.GRAY);
            frame.add(editHintLabel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel formPanel = new JPanel(new GridLayout(4, 2));
            JTextField nameField = new JTextField();
            JTextField phoneField = new JTextField();
            JTextField emailField = new JTextField();

            formPanel.add(new JLabel("Name:"));
            formPanel.add(nameField);
            formPanel.add(new JLabel("Phone:"));
            formPanel.add(phoneField);
            formPanel.add(new JLabel("Email:"));
            formPanel.add(emailField);

            JButton addButton = new JButton("Add");
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");

            formPanel.add(addButton);
            formPanel.add(deleteButton);

            // Load contacts from file
            loadContacts();

            // Add contact
            addButton.addActionListener(e -> {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();

                if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                    Contact contact = new Contact(name, phone, email);
                    contactListModel.addElement(contact);
                    saveContacts();
                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "All fields are required.");
                }
            });

            // Edit contact (double-click)
            contactList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int index = contactList.getSelectedIndex();
                        if (index != -1) {
                            Contact contact = contactListModel.get(index);
                            String newName = JOptionPane.showInputDialog(frame, "Edit Name", contact.name);
                            String newPhone = JOptionPane.showInputDialog(frame, "Edit Phone", contact.phone);
                            String newEmail = JOptionPane.showInputDialog(frame, "Edit Email", contact.email);

                            if (newName != null && newPhone != null && newEmail != null &&
                                !newName.isEmpty() && !newPhone.isEmpty() && !newEmail.isEmpty()) {

                                contactListModel.set(index, new Contact(newName, newPhone, newEmail));
                                saveContacts();
                            }
                        }
                    }
                }
            });

            // Delete contact
            deleteButton.addActionListener(e -> {
                int selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex != -1) {
                    contactListModel.remove(selectedIndex);
                    saveContacts();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a contact to delete.");
                }
            });

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(formPanel, BorderLayout.SOUTH);
            frame.setVisible(true);
        }

        private void loadContacts() {
            if (contactFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(contactFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Contact contact = Contact.fromFileString(line);
                        if (contact != null) {
                            contactListModel.addElement(contact);
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to load contacts.");
                }
            }
        }

        private void saveContacts() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(contactFile))) {
                for (int i = 0; i < contactListModel.size(); i++) {
                    writer.write(contactListModel.get(i).toFileString());
                    writer.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to save contacts.");
            }
        }
    }
}
