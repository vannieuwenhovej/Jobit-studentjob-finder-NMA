# Native Mobile App

The Native Mobile App is meant for the administrative people who'd like to put job applications online. That could be one company but it also could be more companies. As an employee you can add as many jobs as you want, you can also see if there is a job with sollicitants.

# 1 Technical Features

A breakdown of all included technical features plus where in the application the source code can be found.

## 1.1 Activities

The NMA consists of 4 activities. `MainActivity.java`, the one launched on launch of the app. `AddNewJobActivity.java` is launched after pressing the button to create a new job. `DisplayJobActivity.java` is launched after touching one of the jobs listed. `DisplaySollicitants.java` is launched after pressing the button to view the sollicitants if your on a job activity.

## 1.2 Interfaces

The NMA also consists of 3 interfaces for retrieving an async response of an API call. This comes in handy for future developing of the app.

## 1.3 Asynchronous tasks

There are 7 asynchronous tasks. They all send 1 API call to the NodeJS server and wait for a reply. The reply is handled in the same class and returns it to the activity.


## 1.4 Other Packages

`company`, `config` and `student` are packages which hold specific classes to the subject. For example in the `config` package you find `ConnectionConfig.java` **which is very important.** You need to change the IP address in here, also in `network_security_config.xml`you'll have to change the IP address in order to get the app working.

# 2 Get it working

Before you can launch the app you'll have to edit two lines in the project. The easiest way is to go to the TODO tab and edit the IP addresses with your own IP address.
The two files that need to be edited are: 
- `ConnectionConfig.java:5`
- `network_security_config.xml:4`

Also the Laravel server needs to be up and running to be able to connect to its database via NodeJS. 
After that the connection should be established.

# 3 How it works

## 3.1 Installation and Initalisation

When the Laravel and NodeJS server are up and running it's time to launch the app. There's also an APK delivered in the root of the project. (Probably won't work because of the IP address).

## 3.2 Overview of jobs

When the app is launched, it opens a view of all the available jobs you created. You can sroll trough them and see basic information about the job. The digit between the brackets after the function name is the ID of the joboffer.


## 3.3 Job view

If you touch on one of the open jobs you get a filled in form of the job where you can edit all the information about the job. You can also see how many sollicitants already have signed up for the job. You can delete the job by touching _Delete_.

## 3.4 Sollicitants view

If you touch the sollicitants button of the job you get to see who the sollicitants are nd you see some details. If there are no sollicitants, nothing will be showed. If you see a sollicitant in who you are interested to you can contact them by email. their email address is shown beneath there name and birthdate. Again the digit between brackets is the sollicitant's ID.

## 3.5 Create a job

On the home activity you will see there's a button **_Create a job_**. Once clicked you get to see a form where you need to fill in all necessary information.  Next you click **_Save job_** and the job is saved to the database! The activity closes and you go back to the home activity. Your new job should be on the list now. You can edit and view this job by clicking on it.