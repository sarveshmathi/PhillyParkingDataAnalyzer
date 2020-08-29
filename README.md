# PhillyParkingDataAnalyzer

This team project uses data structures, design principles, and design patterns learnt throughout the Summer 2020 semester of CIT 594
in developing a Java application to read text, json and csv files as input and perform some analysis.

The full details of the project and its functionality is described in **CIT_594_Group_Project.pdf**.

The program uses three data sets: parking violations from OpenDataPhilly, property values from OpenDataPhilly, and population data provided by course instructor.

The runtime arguments to the program should be as follows, in this order:
* The format of the parking violations input file, either “csv” or “json”
* The name of the parking violations input file
* The name of the property values input file
* The name of the population input file
* The name of the log file

The program will process all the input files and give users the following options: 
1. Total Population for All ZIP Codes
1. Total Fines Per Capita
1. Average Market Value by Zipcode
1. Average Total Livable Area by Zipcode
1. Total Residential Market Value Per Capita by Zipcode
