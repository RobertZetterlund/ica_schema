# :calendar: ica_schema 

My workplace sends out an xls file containing scheduling for employees. 
So I created ica_schema to turn the xls files into iCal events using
Apache POI and iCal4j.

## The excel File
![Screenshot 2019-03-26 at 11 47 57](https://user-images.githubusercontent.com/31474146/54991342-07377900-4fbd-11e9-8a90-5af7c3dfe2e0.png)

As spotted, the days that you are working are determined by the week and weekday. I found this frustrating and when I started translating week and day into my calendar on my cellphone I decided to do automate the process and help coworkers escape from this tedious process. 

### Apoache POI
https://poi.apache.org/
### iCal4j
https://github.com/ical4j/ical4j
