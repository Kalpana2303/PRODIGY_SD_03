# PRODIGY_SD_03

# Contact Manager - Java Swing GUI
A simple desktop application that allows users to manage a list of contacts. Users can **add**, **view**, **edit**, and **delete** contacts with changes being saved persistently in a local file (`contacts.txt`).

## 💡 Features
- Add new contacts (Name, Phone, Email)  
- Edit existing contacts by double-clicking on a contact  
- Delete selected contacts  
- All data is saved to a local file (basic file persistence)  
- Simple and intuitive Java Swing interface  

## 🛠️ Built With
- **Java**  
- **Java Swing** – for the GUI components and layout  
- **File I/O (java.io)** – for saving and loading contact data  

## 🚀 How to Run
1. Open the `.java` file in your favorite Java IDE (like IntelliJ, Eclipse, or NetBeans), or a simple text editor.  
2. Ensure that Java JDK 8 or higher is installed.  
3. Compile and run the `ContactManager.java` file.
4. A GUI window will open where you can start managing contacts. 
   💾 The contacts.txt file is automatically created in the same directory to store contact data.

## ✏️ Usage
1. Add a contact using the text fields and click Add.
2. Edit a contact by double-clicking an entry in the list.
3. Delete a contact by selecting one and clicking Delete

##⚠️ Notes
1. All fields (Name, Phone, Email) must be filled before adding or editing.
2. The application stores data in plain text format (no encryption or database).
