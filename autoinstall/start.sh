usermod -d /var/lib/mysql/ mysql
service mysql start 
cd  /home/Mathy/ServerWebMathy 
java -jar HTTPWebServer-jar-with-dependencies.jar