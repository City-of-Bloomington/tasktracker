# tasktracker

tasktracker is a web application to handle requests that need to be completed by certain group of workers in an organization. The app is built by using Java jsp and struts 2. You can use ant to compile, package and deploy the application.
Each group will have a number of users based on location or work type.

Each request can be added by any worker and assigned to other worker(s). Each request may include one or more tasks, if the last task is completed by 100% the requested is considered closed (completed). The request can be cancelled if no need for the request is still valid.

A closed or cancelled request can be reopen to continue the work that was not completed.

When a request is assigned to a worker that worker will be notified by email about the request. (in progress).

The interface offer a link for any work to find out what requests were assigned to them by clicking on "Assigned to me" top tab.

The application provide a log history about each change that is made by users.

Users need to be added to the system, then assigned to groups. The 'Admin' tab provide the interface to do that.

The app provides a number of tabs to make getting to current tasks and assignments simple and fast. Also a search page is available to lookup any request new or old.

The app will include a report that will provide some stats about the performance of works and requests that were acchieved. (in progress)

The application depends on a mysql database that need to be created and configured in META-INF/context.xml file, you can use cotext.xml.example to start just change the appropriate entries to point to your database.

We use CAS for single login system, that need to be available so that users can be authenticated. If you want to skip CAS, you need to make some changes to web.xml file and comment out the rows that are used to configure CAS.







