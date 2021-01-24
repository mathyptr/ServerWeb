#!/bin/bash

Install(){
#Install java
#Install maven
	echo "Start updating......."
	sleep 1
	echo "......."
	sleep 1
	echo "......."
	sleep 1
	echo "......."	
	sudo apt update
	sleep 1
	echo "......."
	sleep 1
	echo "......."
	sleep 1
	echo "......."	
#############
# Install mysql	#
#############
	echo "Press M to install MYSQL, other key to skip:"
	read InstallMysql
	# Convert to lowercase
	InstallMysql=`echo "$InstallMysql" | tr -s '[:upper:]' '[:lower:]'`
	if [ "$InstallMysql" = "m" ]; then
		echo "Start installing MYSQL......"
		sleep 1
		echo "......."
		sleep 1
		echo "......."
		sleep 1
		echo "......."		
		sudo apt install mysql-server
		echo "End MYSQL installation"
	fi
	
	sleep 1
	echo "......."
	sleep 1
	echo "......."
	sleep 1
	echo "......."	
	echo "Now I need the Mysql username and password to build the database TIPSIT "
######################
# Read the mysql user name    #
######################
	echo "Enter mysql user name:"
	read mysqluser

########################
# Read the mysql user password #
########################
	echo "Enter the mysql password: "
	read mysqlpassw
	if [ -z "$mysqlpassw" ]; then
		strpw=""
	else
		strpw="-p"$mysqlpassw
	fi

##################
# create db create table  #
##################
#mysql -uroot -p < tipsitPerson.sql
#mysql -uroot -p -e "CREATE DATABASE IF NOT EXISTS tipsit; USE tipsit; 
#CREATE TABLE IF NOT EXISTS person(id int(11) NOT NULL AUTO_INCREMENT, firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL,
#  address text DEFAULT NULL,  passport varchar(50) DEFAULT NULL,  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
#INSERT INTO person (id, firstName, lastName, address, passport) VALUES (1, 'Mathilde', 'Patrissi', 'Via....', NULL),(2, 'Mat', 'Pat', 'Via----', NULL);"
	echo "Create TIPSIT database and populate it"
	mysql -u$mysqluser $strpw -e "CREATE DATABASE IF NOT EXISTS tipsit; CREATE TABLE IF NOT EXISTS tipsit.person(id int(11) NOT NULL AUTO_INCREMENT, firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL,address text DEFAULT NULL,  passport varchar(50) DEFAULT NULL,  PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;"
	mysql -u$mysqluser $strpw -e "INSERT INTO tipsit.person (id, firstName, lastName, address, passport) VALUES (1, 'Mathilde', 'Patrissi', 'Via....', NULL),(2, 'Mat', 'Pat', 'Via----', NULL);"

#######################
# create user mathy grant user  #
#######################
#CREATE USER 'mathy'@'localhost' IDENTIFIED BY 'mathy';
#GRANT select ON tipsit.person TO 'mathy'@'localhost';
	echo "Create user mysql for conncecting to database TIPSIT"
	mysql -u$mysqluser $strpw -e "CREATE USER 'mathy'@'localhost' IDENTIFIED BY 'mathy';GRANT select ON tipsit.person TO 'mathy'@'localhost';"

	echo "Clone Serverweb repository......"
	sleep 1
	echo "......."
	sleep 1
	echo "......."
	sleep 1
	echo "......."	
	git clone https://github.com/mathyptr/ServerWeb.git

#############
# maven build     #
#############
	echo "Maven building....."
	sleep 1
	echo "......."
	sleep 1
	echo "......."
	sleep 1
	echo "......."	
	cd ServerWeb
	mvn clean compile assembly:single
	cd  ..
	mkdir ./ServerWebMathy
	mkdir ./ServerWebMathy/www
	cp ServerWeb/target/HTTPWebServer-jar-with-dependencies.jar ./ServerWebMathy

	cp -r ServerWeb/src/main/resources/* ./ServerWebMathy/www

########################################################################################################################
# config
#	"serverInfo":"Server: Java HTTP Server from Mathy : 1.0",
#	"srvPORT": porta di ascolto dell'applicativo,
#	"webroot": document root (directory in cui sono presenti le risorse),
#	"redirectTO": url per il redirect,
#	"mysqluser": username per l'accesso a Mysql,
#	"mysqlpassw": password per l'accesso a Mysql,
#	"mysqlhost": nome host del server su cui si trova Mysql (localhost se è sullo stesso server dell'applicativo),
#	"mysqlport": porta su cui è in ascolto Mysql,
#	"mysqldb": nome database su cui si trova la tabella utilizzata
#######################################################################################################################	

# Read the URL for redirect
	echo "Enter the URL for redirect (example http://JavaMavenApache-mathyptr935870.codeanyapp.com)"
	read urlredirect

	echo '{"serverInfo":"Server: Java HTTP Server from Mathy : 1.0",'> ./ServerWebMathy/JavaHTTPServer.json
	echo '"srvPORT":"3000",'>>   ./ServerWebMathy/JavaHTTPServer.json	
	echo '"webroot":"./www",' >>  ./ServerWebMathy/JavaHTTPServer.json
	echo '"redirectTO":"'$urlredirect'/newpath",'>>  ./ServerWebMathy/JavaHTTPServer.json
	echo '"mysqluser":"mathy",'>>  ./ServerWebMathy/JavaHTTPServer.json
	echo '"mysqlpassw":"mathy",'>>  ./ServerWebMathy/JavaHTTPServer.json
	echo '"mysqlhost":"localhost",'>>  ./ServerWebMathy/JavaHTTPServer.json
	echo '"mysqlport":"3306",'>> ./ServerWebMathy/JavaHTTPServer.json
	echo '"mysqldb":"tipsit"} '>>  ./ServerWebMathy/JavaHTTPServer.json

}
##################
# Install or Start Service #
##################
echo "Starting ......."

# Install or Start Service
echo "Press I for installation,  other key to start Service:"
read Cont

# Convert to lowercase
Cont=`echo "$Cont" | tr -s '[:upper:]' '[:lower:]'`

if [ "$Cont" = "i" ]; then
  Install
fi

cd ./ServerWebMathy
java -jar HTTPWebServer-jar-with-dependencies.jar