### Project Overview

The project consists of an Android application designed to query and visualize data related to taxi services or transportation.

### Components and Functionalities

1. **MainActivity.java**:
   - Main activity responsible for:
     - Establishing a connection to an Azure SQL Server database.
     - Executing queries based on user input.
     - Handling exceptions related to SQL operations.
   - Provides buttons for executing different types of queries:
     - Query 1: Retrieves top 5 start dates and sum of passenger numbers for each date.
     - Query 2: Redirects to another activity (QueryActivity2).
     - Query 3: Redirects to another activity (QueryActivity3).
   - Opens QueryActivity1 to display results after executing Query 1.

2. **MapsActivity.java**:
   - Activity for displaying Google Maps with markers and routes.
   - Utilizes the Google Maps API to:
     - Render the map.
     - Draw routes between source and destination points.
   - Retrieves route information using the Directions API.
   - Draws polylines on the map to represent routes.
   - Receives source and destination points from QueryActivity3.
   - Configures polyline color, width, and map zoom level.

3. **QueryActivity1.java**, **QueryActivity2.java**, **QueryActivity3.java**:
   - Activities for displaying query results or providing user interfaces for input.
   - Specific functionalities not described in the provided code.

4. **Layout files**:
   - XML files defining user interface components and layouts for each activity.

5. **Dependencies**:
   - Relies on external libraries and APIs:
     - Google Maps API for Android.
     - JTDS JDBC driver for connecting to Azure SQL Server.

### Purpose
The application is designed to analyze transportation-related data, possibly related to taxi services. It allows users to query data from an Azure SQL Server database, visualize routes on Google Maps, and interact with different activities to view query results or perform additional queries.
