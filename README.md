# TicTacToeGame application build instructions:  
Things you need to download and instal:  
-Clone or download the project.  
-Download and instal Java SE Runetime Environment - use this link: https://www.java.com/en/download/  
-Download and instal Python - use this link: https://www.python.org/downloads/  
-Download and instal MySql (OPTIONAL!) - if you want to save the accounts you register in an actual data base download MySql using this link: https://dev.mysql.com/downloads/mysql/. If you dont want to download MySql you can still test the application using H2 database.  

Running the application:  
-Go in the download project, enter client folder and then JS folder. Edit then "main.js" file (use can use notepad) by changing the value of "ip" variable (it should be the first line from "main.js") with the ip of your computer.  
-Open Command Prompt and go where downloaded project is located. If you downloaded and installed MySql go in "Server jar with MySql database" folder, but if you did not then go in "Server jar with H2 database" folder.  
-Use "java" command followed by "-jar" and the executable jar ("TicTacToe") to run the application. The command should look like this: "java -jar TicTacToe.jar".  
-Open Command Prompt again and go where the Client folder from the downloaded project is located. Create a local server with python using the following command: "pyhton -m http.server 8000", where "8000" is the server port.  
-Open a broswer and type the "ip" you inserted earlier on "main.js" followed by the port ("8000).  
Exemple: "192.168.1.100:8000"

The game requires two players for the game to start. You can register and login with two accounts from two different devices or you can do that on the same device using a normal browser page and a incognito browser page. 
