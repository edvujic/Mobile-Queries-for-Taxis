### Project Overview

The Android application is designed to query and visualize transportation-related data, likely pertaining to taxi services. It incorporates functionalities for querying data from an Azure SQL Server database, displaying results, selecting date ranges, and visualizing locations on Google Maps.

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

2. **QueryActivity1.java**:
   - Displays the result of Query 1 in a ListView.
   - Retrieves result data from the `date` and `passNumber` arrays in MainActivity.
   - Formats the data into strings and populates the ListView using an ArrayAdapter.

3. **QueryActivity2.java**:
   - Allows users to select a date range using two spinners.
   - Checks the validity of the date range and connects to an Azure SQL Server.
   - Executes a query to retrieve data based on the selected date range.
   - Stores the query result in the `resultString` array and opens QueryActivity2Child to display the result.

4. **QueryActivity2Child.java**:
   - Displays the result of QueryActivity2 in a ListView.
   - Retrieves the query result data from the `resultString` array and populates the ListView using an ArrayAdapter.

5. **QueryActivity3.java**:
   - Allows users to select a date from a spinner and fetches corresponding latitude, longitude, and zone information from an Azure SQL Server database.
   - Retrieves the latitude, longitude, and zone information for both source and destination locations based on the selected date.
   - Opens the MapsActivity to display the source and destination locations on Google Maps.

6. **MapsActivity.java**:
   - Activity for displaying Google Maps with markers and routes.
   - Utilizes the Google Maps API to render the map and draw routes between source and destination points.
   - Receives source and destination points from QueryActivity3.
   - Configures polyline color, width, and map zoom level.

### Purpose

The application serves as a comprehensive tool for analyzing and visualizing transportation-related data. Users can perform various queries to extract relevant information from the database, view results, select date ranges for analysis, and visualize locations on Google Maps. It enhances decision-making processes and insights into taxi services or transportation operations.
