./bin/bash
systemctl start docker.service
sudo docker run --publish 3306:3306 --detach --name TrombiBDD --env MARIADB_ROOT_PASSWORD=trombipw  mariadb:latest
sudo docker ps
sudo docker exec -it TrombiBDD mariadb --user root -ptrombipw
CREATE DATABASE datatest;
CREATE TABLE datatest.ELEVE ( nom TINYTEXT, prenom TINYTEXT);
