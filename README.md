# Packer-Unpacker – Java GUI

A **Java GUI-based Packer-Unpacker application** designed to combine multiple files into a single packed file and extract the original files whenever required.

The project demonstrates practical concepts of **Java File Handling, GUI Programming, Byte Streams, Serialization, and Object-Oriented Programming**.

## Features

* 📦 Pack multiple files into a single packed file.
* 📂 Unpack and restore files from the packed file.
* 🖥️ User-friendly GUI using Java Swing.
* 📁 Supports file selection through a file chooser.
* 🔄 Preserves the original file data during packing and unpacking.
* ⚡ Efficient file reading and writing using byte streams.
* 🛡️ Handles file operations and exceptions appropriately.

## Technologies Used

* **Java**
* **Java Swing**
* **File Handling**
* **Byte Streams**
* **Object-Oriented Programming**
* **Serialization**

## Concepts Demonstrated

* `JFrame`, `JButton`, `JTextField` and other Swing components
* `FileInputStream` and `FileOutputStream`
* Byte-level file operations
* File metadata handling
* Exception handling
* Object-Oriented Programming
* GUI event handling

## How It Works

### Packing

1. Select the files that need to be packed.
2. The application reads the selected files.
3. File information and file contents are written into a single packed file.
4. The resulting packed file can be stored or transferred as a single file.

### Unpacking

1. Select the previously created packed file.
2. The application reads the stored file information.
3. Individual files are extracted from the packed file.
4. The original files are recreated with their respective contents.

## Project Structure

```text
Packer-Unpacker/
│
├── Packer.java
├── Unpacker.java
├── GUI.java
└── README.md
```

> File names may vary depending on the implementation.

## How to Run

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Packer-Unpacker
```

### 2. Compile

```bash
javac *.java
```

### 3. Run

```bash
java GUI
```

## Use Cases

* Combining multiple files into a single file for easier storage.
* Demonstrating file handling concepts in Java.
* Understanding how custom packing and unpacking mechanisms work.
* Learning GUI-based Java application development.

## Learning Outcomes

Through this project, I gained practical understanding of:

* Java GUI development using Swing.
* File input/output operations.
* Byte-stream based data processing.
* Handling multiple files programmatically.
* Event-driven programming.
* Designing a simple desktop application using Java.

## Author

**Siddharth Tapkir**

Java | C | C++ | Linux | System Programming
