# University Management System

A comprehensive Java-based University Management System built using JavaFX that provides a complete solution for managing university operations, including student, faculty, and administrative functionalities.

## Features

### Student Module
- Student registration and profile management
- Course registration and enrollment
- Grade viewing and academic progress tracking
- Attendance management
- Fee payment and financial records

### Faculty Module
- Course management and assignment
- Grade submission and management
- Attendance tracking
- Student performance monitoring
- Course material management

### Administrative Module
- User management (Students, Faculty, Staff)
- Course and program management
- Department management
- Academic calendar management
- System configuration and maintenance

## Technical Details

- **Programming Language:** Java
- **Framework:** JavaFX
- **Architecture:** MVC (Model-View-Controller)
- **Database:** [Database type used in the project]

## Project Structure

```
src/
├── aController/     # Administrative controller classes
├── aView/          # Administrative view components
├── fController/    # Faculty controller classes
├── fView/          # Faculty view components
├── sController/    # Student controller classes
├── sView/          # Student view components
├── mainP/          # Main program components
├── utilities/      # Utility classes and helpers
└── img/            # Image resources
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- JavaFX SDK
- [Any other dependencies]

## Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   ```

2. Open the project in your preferred IDE (Eclipse/IntelliJ IDEA)

3. Configure JavaFX in your IDE:
   - Add JavaFX SDK to your project
   - Configure VM arguments for JavaFX

4. Build and run the project

## Usage
1. Start the application by running `Main.java` located in the `src/mainP` folder
2. The application will open with a login interface
3. For first-time users:
   - Click on the "Create New User" option
   - Fill in the required registration details
   - Choose your user type (Student/Faculty/Administrator)
   - Complete the registration process
4. For existing users:
   - Enter your credentials in the login form
   - Select your user type
   - Click login to access your dashboard

Each user type (Student/Faculty/Administrator) will have access to different features and functionalities based on their role in the system.
