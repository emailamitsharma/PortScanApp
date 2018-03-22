-- PLEASE OPEN IN NOTEPAD++ OR STANARD TEST EDITOR FOR BETTER READABLE STRUCTURE.

==================================================================================================================================
# PORT SCAN API/APP 

		APPLICATION : - Single page application (SPA) for presenting results of scanning of ports on given host or ip address using NMAP utility.  

		API :- Restful API (JSON Media Type based) for providing back-end support (for NMAP scanning results) for multiple type of consumers including above application.


TECHNOLOGIES USED:-

		BACKEND
		1) Spring Boot [1.5.9]
		2) Spring REST [HTTP, JSON] 
		3) Hibernate & JPA [HikariCP Datasource]
		4) Spring Data
		5) Java -8 
		6) MYSQL  & H2 [schema.sql OR spring JPA Auto DDL Generation & Execution]
		7) Spring Advice Exception Handling Framework.
		8) Cucumber Test Cases, Junit
		9) Servlet Runtime Container: Tomcat


		FRONTEND
		1)Bootstrap
		2)FreemarkerTemplate
		3)AngularJS 1.0
		

FEATURES SUPPORTED OR COVERED [AS PER REQUIREMENT]

		1)Nmap Utility Flexibility :- Easy configuration support for multiple options of Nmap commands.Driven through configuration.
		2)Ports scanning :- 0-1000 (configurable through properties)
		3)Validation of inputs:- Frontend (for efficiency & high throughput) + Backend validation support with friendly messages.
		4)Thorough exception handling with friendly + developer messages for troubleshooting.
		5)Port Scanning History supported.
		6)Easy configurable flexibility to see history data of upto a specific time(last no. of scans).History of -1(All), 	0(NO), +N (upto N iterations).
		7)UI based easy change call out:- Changes call out relative to last scanning through CSS colors (intuitive with color information).
		8)WebService Support with JSON data. 
		9)Time of scanning added for better readability and distinguish among records.
		10)Fresh NMAP scanning requirement is configurable.
		11)Loosely coupled service for accomdating flexibility in business requirement.
		12)Parallel processing of records (only through API call)


WHAT I BORROWED FROM INTERNET

		1) REGEX Patterns for I.P address & domain validation.
		2) Understood concepts of better formatting of NMAP commands through company's & other documentations.

	
	
BUSINESS FLOW [SEE ATTACHED SCREENSHOTS]

You can specify ip address or domain name using same text to go for port scanning. It will run NMAP command utility latest scanning(scan no. 1). Based on results of open ports data will be transferred to UI by API in JSON format. On second hit of same identifier (ip or domain) again a latest scan (scan no. 2) will run and result will be compared with previous scan data (scan no. 1). Newly added , carry over open ports and substracted ports will be given finally to UI in this step along with history (scan no.1). In third hit it will again repeate step of latest scanning (scan no. 3 this time) and comparing with previous scan (this time it is scan no. 2) and result is prepared as per same logic. But in this step a new addition of history will also be added (scan no. 1 + scan.no.2). 

		So in Nth hit of same record , it will run a fresh scan(scan no. N) and compare it with (scan no. N-1). And in additional add history of previous iterations which is configurable like upto last 5 iterations or 6 iterations or all iterations or no history addition.[History will be added from scan no. N-1 UPTO scan no. X [configurable] ] 

		[X if X==0 , No history, X==-1 Add all history, X==P Add history upto last X iterations]
		
		Fresh NMAP scanning requirement is configurable as per requiremet as WebAPI call doesn't require latest scanning in each call. 

HOW TO RUN :- 

		Step 1: unzip project in a directory [say /usr/local/]
		Step 2: Execute maven command : mvn clean install
		Step 3: java -jar target/portScanApp-1.0.0.jar OR  mvn spring-boot:run
		Step 4: Manage SQL database [Run specially indexes scripts, as DDL of tables and foreign keys are taken care by Spring JPA]

		Play with URLs:- 
			Default context : /PortScanAPI
			Default Port : 8080
			
		APPLICATION URL: - http://localhost:8080/PortScanAPI
		API URL: http://localhost:8080/PortScanAPI/api/ports?input=akshima.in&isLatestScanRequired=true
		Concurrency URL: http://localhost:8080/PortScanAPI/api/ports?input=akshima.in,192.168.1.1&isLatestScanRequired=true

DEPLOYED [AWS Account[EC2-[LOCAL MYSQL , NOT RDS]]]
		AWS Cloud URLs.
		APPLICATION URL: - http://akshima.in:8082/PortScanAPI
		API URL: http://akshima.in:8082/PortScanAPI/api/ports?input=akshima.in&isLatestScanRequired=true
		Concurrency URL: http://akshima.in:8082/PortScanAPI/api/ports?input=akshima.in,192.168.1.1&isLatestScanRequired=true
		
		

LOCAL MACHINE

		APPLICATION URL: - http://localhost:8082/PortScanAPI
		API URL: http://localhost:8082/PortScanAPI/api/ports?input=akshima.in&isLatestScanRequired=true
		Concurrency URL: http://localhost:8082/PortScanAPI/api/ports?input=akshima.in,192.168.1.1&isLatestScanRequired=true
		
HOW TO RUN TEST CASES :- 
		
		Run CucumberRunner.java class [Run as Java Application]
		
IMPORTANT FILES & POINTS

		schema.sql + DB tables [Spring JPA will take care of database script execution. Refer schema.sql]. Although whole script can be configured for avoiding any manual work.

		API Controller : - PortScanController.java  
		API Service (Main) : PortScanService.java
		API configuration properties :- application.properties & application.yml
		
		CONFIGURATION CHANGES REQUIRED
		
		a)Need to change in MYSQL database username&password in application.yml file. (another format of properties file)
		b)Need to run database script [conditional]
		c)spring.profiles.active=prod FOR MYSQL.
		d)Few more properties changes for NMAP feature.
		
		Note:'hostId' used as ip & domain both in code.
		
NEXT IMPROVEMENTS
		1) Efficiency Improvement 
				a) Caching  Spring for metadata & mostly static information.
				b) Caching for history data as history is always history and will never change but scope of history can get changed.
				c) Other efficiency changes. 

		2)		Better Failover Mechanism.
			
		3) 		Security Changes [Spring security or Token based,etc standard API security]

		
KNOWN ISSUES
		1) Waiting loading image while looking for request to be served by service.
		2) Auto persistence columns like updated_by,created_date,updated_date not functional at the moment.
		
Time Taken 
		2 days
