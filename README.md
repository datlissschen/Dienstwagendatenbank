# Fleet Management & Driver Tracking System

A high-precision backend solution engineered in **Java 21**, designed to synchronize vehicle logistics with driver historical data. This system leverages **statistically typed time-series logic** and **Unix Epoch synchronization** to manage fleet operations and compliance.

## System Architecture

The application implements a decoupled architecture to ensure data integrity and clear separation of concerns between raw data management and business logic.



* **`Data`**: The persistence layer managing `Fahrer` (Drivers), `Dienstwagen` (Vehicles), and `Fahrt` (Trips).
* **`Logik`**: The core processing engine handling complex search algorithms and data filtering.
* **`HelperMethods`**: A utility suite utilizing the **JSR-310 (java.time)** API for nanosecond-precision Unix conversions.

---

## Core Functionality

### Speeding Compliance (`blitzer`)
Identifies the exact operator of a vehicle at a specific micro-moment.
* **Query**: `VEHICLE_ID;ISO-8601_TIMESTAMP`
* **Logic**: Performs a range-check within the Unix timeline to find the active driver session.

### Forensic Traceability (`fundsuche`)
A logic path used to identify all personnel associated with a specific vehicle footprint within a 24-hour window.
* **Process**: 
    1.  Resolves the target driver's daily fleet usage.
    2.  Aggregates all unique vehicle IDs.
    3.  Filters for overlapping sessions by *other* drivers within the defined epoch range.



---

## Tech Stack & Standards

* **Language**: **Java 21 (LTS)** – Utilizing modern collection enhancements and robust memory management.
* **Temporal Logic**: **Unix Epoch** – Chosen for computational efficiency and timezone-agnostic comparisons.
* **Data Integrity**: Managed via `Set<T>` for collision-free tracking and `HashMap<K, V>` for optimized entity resolution.
* **Search Logic**: Implements the **Overlap Interval Theorem**:
    $$(Trip_{Start} \le Day_{End}) \land (Trip_{End} \ge Day_{Start})$$

---

## Interface Specification

| Search Type | Input Protocol | Output Format |
| :--- | :--- | :--- |
| **Driver Trace** | `${FahrerID};YYYY-MM-DD` | `Name (License Plate), ...` |
| **Speeding Event** | `${FahrzeugID};YYYY-MM-DD'T'HH:mm:ss` | `Firstname Lastname` |
| **Fuzzy Lookup** | `${SearchString}` | `ID, Name, Metadata` |

---

## Getting Started

1.  **Prerequisites**: Ensure you have **JDK 21** installed.
2.  **Execution**: Run the `Main.java` class.
3.  **Arguments**: The system accepts CLI arguments for automated testing:
    * `--fahrerZeitpunkt="ID;Timestamp"`
    * `--fahrerDatum="ID;Date"`

---
