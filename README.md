# 🥗 Planty of Food (POF) - E-commerce Management Software

**Planty of Food** is a Java application designed to manage the operational workflows of a sustainable food e-commerce platform. The system allows for centralized management of organic products, customer registries, and tracking of sales/returns, reflecting the company's commitment to a transparent supply chain.

## 🌱 Vision & Mission
* **Vision:** To make plant-based, ethical, and organic food accessible to everyone.
* **Mission:** To support local producers and reduce environmental impact through compostable packaging and efficient digital management.

---

## 📂 Project Structure (Tree)
The project follows Maven conventions and a clear separation of packages:

```text
├── pom.xml                     # Maven configuration and dependencies
├── README.md                   # This file
├── products.csv                # Product database
├── users.csv                   # User registry
├── sales.csv                   # Transaction log
└── src
    └── main
        └── java
            └── it
                └── pof
                    ├── Main.java              # User Interface (CLI)
                    ├── models                 # Package: Domain entities
                    │   ├── User.java        
                    │   ├── Product.java      
                    │   └── Sale.java       
                    └── service                # Package: Application logic
                        └── ManagementService.java # Business Logic and I/O
```

📊 CSV File Organization (Database)
Data is persisted in CSV format using the semicolon (`;`) as a delimiter.

### 1. `products.csv` (Food catalog)
* **Structure:** `ID;Name;Insertion Date;Price;Brand;Available`
* **Example:** `1;Almond Milk;22/07/2021;2.15 €;Bjorg;YES`

### 2. `users.csv` (POF Customer Registry)
* **Structure:** `ID;Name;Last Name;Birth Date;Address;Document ID`
* **Example:** `1;Mario;Rossi;23/02/1990;Via Roma 15;AS348945`

### 3. `sales.csv` (Transaction log)
* **Structure:** `ID;Product ID;User ID`
* **Example:** `1;3;1`

## 🏗️ Architecture and Technical Implementation

### 1. Object-Oriented Programming (OOP)
The software is structured to maximize **Encapsulation**. Classes in the `models` package protect the state of objects using private fields and provide access through **Getter** and **Setter** methods. The presentation logic (`Main`) is strictly separated from the business logic (`ManagementService`), facilitating code maintenance.

### 2. Technical Focus: Functional Programming (Java 17)
The system utilizes modern paradigms for data manipulation:
* **Functional Interfaces:** The `loadCSV` method accepts a `Consumer<String[]>` s a parameter, making the parsing engine generic and reusable for different models (Users, Products, Sales).
* **Lambda Expressions:** Used to quickly map CSV file rows into Java objects during the `startup` phase, reducing code verbosity.
* **Stream API:** Implemented in the `exportAvailable()` method to filter in-stock products and generate dynamic reports, including the current date in the output filename.

### 3. Robustness and Validation
* **Exception Handling:** Implementation of `try-catch` blocks in `Main` to prevent crashes from `NumberFormatException` (invalid user inputs) and in the `Service` to handle `IOException` during access to persistence files.
* **Synchronization:** Every sale or return operation dynamically updates the availability state (`YES`/`NO`) of the objects and immediately synchronizes the physical files to prevent data loss.

## 📥 Cloning the Repository
To download the project locally to your PC, open the terminal and run:

```bash
git clone https://github.com/sadsotti/pof-system.git
```

After cloning, enter the project folder:
```bash
cd pof-system
```

## 🛠️ Build and Execution Instructions

### Build
The project uses **Apache Maven** for build management. To compile the source code and generate the executable JAR file, run the following command from the project root:

```bash
mvn clean package
```

At the end of the process, you will find the generated file in the path:
`target/pof-system-0.0.1.jar`

### Execution
To start the application correctly, ensure that the persistence files (`products.csv`, `users.csv`, `sales.csv`) are present in the same directory from which you launch the command. Then run:

```bash
java -jar target/pof-system-0.0.1.jar
```

**System Requirements:** Java 17 or higher must be installed, as specified in the Maven compiler configuration.

## 🔗 Useful Links

- https://www.start2impact.it/  
- https://linkedin.com/in/lorenzo-sottile  

---
