git clone https://github.com/mathyptr/ServerWeb.git
cd  /home/Mathy/ServerWeb
mvn clean compile assembly:single
cp /home/Mathy/ServerWeb/target/HTTPWebServer-jar-with-dependencies.jar /home/Mathy/ServerWebMathy
usermod -d /var/lib/mysql/ mysql
service mysql start 
cd  /home/Mathy/ServerWebMathy 
java -jar HTTPWebServer-jar-with-dependencies.jar