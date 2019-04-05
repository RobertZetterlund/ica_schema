# :calendar: ica_schema 

My workplace sends out an xls file containing scheduling for employees. 
So I created ica_schema to turn the xls files into iCal events using
Apache POI and iCal4j.

## The old excel File
![Screenshot 2019-03-26 at 11 47 57](https://user-images.githubusercontent.com/31474146/54991342-07377900-4fbd-11e9-8a90-5af7c3dfe2e0.png)

The days that you are working are determined by the week and weekday, meaning week 24 and monday needs to be manually translated to an actual day. I found this frustrating so I decided to automate the process to help myself and coworkers escape this tedious process. 

## UPDATE: ICA has changed their formatting.

### The new excel File
![Screenshot 2019-04-05 at 19 22 48](https://user-images.githubusercontent.com/31474146/55645474-f830a200-57d8-11e9-8d4a-f466e15954af.png)


Much more readable for humans, but still... I updated the ExcelReader class to handle this new format and the output is the same except more information was added to the events. The old format is still convertable using Dated_ExcelReader :blush:

## The output
Events are downloaded by clicking on the .ics file in emails and appears like this using Apples Calendar on macOS

![Screenshot 2019-03-27 at 14 43 30](https://user-images.githubusercontent.com/31474146/55080640-bef08780-509e-11e9-8b2f-940045826a98.png)

## Used Dependencies

### Apoache POI
https://poi.apache.org/
### iCal4j
https://github.com/ical4j/ical4j
