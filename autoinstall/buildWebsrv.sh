mkdir -p /home/Mathy /home/Mathy/ServerWeb /home/Mathy/ServerWebMathy /home/Mathy/ServerWebMathy/www
chmod o+x  /home/Mathy/ServerWebMathy/www
cd /home/Mathy
git clone+ https://github.com/mathyptr/ServerWeb.git 
cd  /home/Mathy/ServerWeb
mvn clean compile assembly:single
cp /home/Mathy/ServerWeb/target/HTTPWebServer-jar-with-dependencies.jar /home/Mathy/ServerWebMathy
cp  /home/Mathy/ServerWeb/src/main/resources/JavaHTTPServer.json /home/Mathy/ServerWebMathy/JavaHTTPServer.json
cp -r /home/Mathy/ServerWeb/src/main/resources/* /home/Mathy/ServerWebMathy/www