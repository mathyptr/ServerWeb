rem ################# 
rem  Install or Start Service# 
rem ################# 
echo "Starting ......."

rem  Install or Start Service
set  /p Cont="Press I for installation,  other key to start Service:"

echo %Cont%
if  not "%Cont%" == "i"   if not "%Cont%" == "I"   goto startService

rem Install java
rem Install maven
rem  Install mysql     
	
	timeout 1
	echo "......."
	timeout 1
	echo "......."
	timeout 1
	echo "......."	
	echo "Now I need the Mysql username and password to build the database TIPSIT "
rem ##################### 
rem  Read the mysql user name   # 
rem ##################### 
	set  /p   mysqluser="Enter mysql user name:"

rem ####################### 
rem  Read the mysql user password# 
rem ####################### 
	set mysqlpassw=""
	set  /p  mysqlpassw="Enter the mysql password: "
	
	if  %mysqlpassw% =="" set strpw=""
	else 	set strpw="-p"%mysqlpassw%
		
	set  /p   mysqlpath="Enter mysql client path:"
	set mysqlpath=%mysqlpath%\
rem ################# 
rem  create db create table # 
rem ################# 
rem mysql -uroot -p < tipsitPerson.sql
rem mysql -uroot -p -e "CREATE DATABASE IF NOT EXISTS tipsit; USE tipsit; 
rem CREATE TABLE IF NOT EXISTS person(id int(11) NOT NULL AUTO_INCREMENT, firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL,
rem   address text DEFAULT NULL,  passport varchar(50) DEFAULT NULL,  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
rem INSERT INTO person (id, firstName, lastName, address, passport) VALUES (1, 'Mathilde', 'Patrissi', 'Via....', NULL),(2, 'Mat', 'Pat', 'Via----', NULL);"
	echo "Create TIPSIT database and populate it"
	%mysqlpath%mysql -u%mysqluser% %strpw% -e "CREATE DATABASE IF NOT EXISTS tipsit; CREATE TABLE IF NOT EXISTS tipsit.person(id int(11) NOT NULL AUTO_INCREMENT, firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL,address text DEFAULT NULL,  passport varchar(50) DEFAULT NULL,  PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;"
	%mysqlpath%mysql -u%mysqluser% %strpw% -e "INSERT INTO tipsit.person (id, firstName, lastName, address, passport) VALUES (1, 'Mathilde', 'Patrissi', 'Via....', NULL),(2, 'Mat', 'Pat', 'Via----', NULL);"

rem ###################### 
rem  create user mathy grant user # 
rem ###################### 
rem CREATE USER 'mathy'@'localhost' IDENTIFIED BY 'mathy';
rem GRANT select ON tipsit.person TO 'mathy'@'localhost';
	echo "Create user mysql for conncecting to database TIPSIT"
	%mysqlpath%mysql -u%mysqluser% %strpw% -e "CREATE USER 'mathy'@'localhost' IDENTIFIED BY 'mathy';GRANT select ON tipsit.person TO 'mathy'@'localhost';"

	echo "Clone Serverweb repository......"
	timeout 1
	echo "......."
	timeout 1
	echo "......."
	timeout 1
	echo "......."	
	git clone https://github.com/mathyptr/ServerWeb.git

rem ############ 
rem  maven build    # 
rem ############ 
	echo "Maven building....."
	timeout 1
	echo "......."
	timeout 1
	echo "......."
	timeout 1
	echo "......."	
	cd ServerWeb
	mvn clean compile assembly:single
	cd  ..
	mkdir ServerWebMathy
	mkdir ServerWebMathy/www
	copy ServerWeb/target/HTTPWebServer-jar-with-dependencies.jar ./ServerWebMathy

	xcopy  ServerWeb/src/main/resources/* ./ServerWebMathy/www

rem ####################################################################################################################### 
rem  config
rem 	"serverInfo":"Server: Java HTTP Server from Mathy : 1.0",
rem 	"srvPORT": porta di ascolto dell'applicativo,
rem 	"webroot": document root (directory in cui sono presenti le risorse),
rem 	"redirectTO": url per il redirect,
rem 	"mysqluser": username per l'accesso a Mysql,
rem 	"mysqlpassw": password per l'accesso a Mysql,
rem 	"mysqlhost": nome host del server su cui si trova Mysql (localhost se è sullo stesso server dell'applicativo),
rem 	"mysqlport": porta su cui è in ascolto Mysql,
rem 	"mysqldb": nome database su cui si trova la tabella utilizzata
rem ###################################################################################################################### 	

rem  Read the URL for redirect
	set  /p   urlredirect="Enter the URL for redirect (example http://JavaMavenApache-mathyptr935870.codeanyapp.com)"


	echo {"serverInfo":"Server: Java HTTP Server from Mathy : 1.0",> ./ServerWebMathy/JavaHTTPServer.json
	echo "srvPORT":"3000",>>   ./ServerWebMathy/JavaHTTPServer.json	
	echo "webroot":"./www", >>  ./ServerWebMathy/JavaHTTPServer.json
	echo "redirectTO":"%urlredirect%/newpath",>>  ./ServerWebMathy/JavaHTTPServer.json
	echo "mysqluser":"mathy",>>  ./ServerWebMathy/JavaHTTPServer.json
	echo "mysqlpassw":"mathy",>>  ./ServerWebMathy/JavaHTTPServer.json
	echo "mysqlhost":"localhost",>>  ./ServerWebMathy/JavaHTTPServer.json
	echo "mysqlport":"3306",>> ./ServerWebMathy/JavaHTTPServer.json
	echo "mysqldb":"tipsit"} >>  ./ServerWebMathy/JavaHTTPServer.json


:startService
cd ./ServerWebMathy
java -jar HTTPWebServer-jar-with-dependencies.jar