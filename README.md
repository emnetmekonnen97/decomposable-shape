# Shape Decomposer Project

This project is part of the CSC 202 Object Oriented Programming and Data Structures course at Augustana College. It involves developing a shape decomposition tool using a circular doubly linked list and a stack in Java.

## Project Overview

The Shape Decomposer project visualizes and manipulates shapes (polygons) by removing or restoring points based on their calculated importance. The project includes functionalities to display shapes, decompose them by removing points, and restore them using a slider.

### Objectives

- Practice with circular doubly linked lists and stacks.
- Implement shape decomposition and restoration.

## Files

- `DecomposableShape.java`: Manages shape decomposition.
- `DecomposerFrame.java`: Provides a GUI for the project.

## Class Specifications

### DecomposableShape Class

- **Fields**: 
  - `int numberOfPointsInitial`
  - `int numberOfPointsCurrent`
  - `PointNode front`
  - `Stack<PointNode> stack`
- **Constructor**: `public DecomposableShape(Scanner input)`
- **Methods**:
  - `toPolygon()`: Converts shape to Java Polygon.
  - `setToSize(int target)`: Sets the number of points based on the target percentage.
  - `toString()`: Returns a string representation of the shape.

### PointNode Class (Nested in DecomposableShape)

- **Fields**: `int x, y`, `double importance`, `PointNode prev, next`
- **Constructor**: `public PointNode(int x, int y)`
- **Methods**:
  - `calculateImportance()`: Calculates the importance of the point.
  - `toString()`: Returns a string representation of the point.

## Running the Project

1. **Setup the Environment**:
   - Ensure Java is installed.
   - Use an IDE like Eclipse or IntelliJ IDEA.

2. **Compile and Run**:
   - Compile `DecomposableShape.java` and `DecomposerFrame.java`.
   - Execute `DecomposerFrame.java` to launch the graphical display.

