# Library Management System (Console Application)

## Description

This is a console-based Java application that simulates a library management system.

The project allows managing books, members, and employees, as well as handling book borrowing and returning. It uses in-memory data structures and file storage for saving and loading the library state.

The main goal of this project is to demonstrate object-oriented programming (OOP), clean code structure, and basic application architecture.

---

## Features

### Employee Management

* Register employees
* Login / logout system
* Role-based access (Admin / Employee)
* Delete employees

### Member Management

* Register members
* Delete members
* View borrowed books
* View borrowing history
* Search members by name

### Book Management

* Add, edit, delete books
* List all books
* List available / borrowed books
* Search books by title or author

### Borrowing System

* Borrow books
* Return books
* Track borrowed books per member

### File Storage

* Save library data to file
* Load library data from file

---

## Technologies Used

* Java 17
* Object-Oriented Programming (OOP)
* Console-based UI
* File I/O (Object Serialization)

---

## Project Structure

The project is organized into several layers:

* ui – handles user interaction
* service – contains business logic
* model – data models (Book, Member, Employee)
* storage – file persistence
* util – helper classes (input handling)
* config – application setup

---

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/Atanas-Engineer/library-management-system.git
```

2. Open the project in IntelliJ IDEA (or another IDE)

3. Run:

```
Main.java
```

---

## Project Purpose

This project was created as part of my learning journey in Java.

It focuses on:

* understanding OOP principles
* building structured applications
* working with multiple layers (UI, service, storage)
* handling user input and validation

---

## Future Improvements

* Better separation between UI and service layers
* Adding database support
* Creating a graphical user interface (GUI)

---

## Author

Atanas Gavrailov
