./bin/bash
systemctl start docker.service
sudo docker run --publish 3306:3306 --detach --name TrombiBDD --env MARIADB_ROOT_PASSWORD=trombipw  mariadb:latest
sudo docker ps
sudo docker exec -it TrombiBDD mariadb --user root -ptrombipw
CREATE DATABASE datatest;
CREATE TABLE ELEVE (
    prenom TINYTEXT,
    nom TINYTEXT,
    email TINYTEXT,
    specialite TINYTEXT,
    option TINYTEXT,
    td TINYTEXT,
    tp TINYTEXT,
    tdMut TINYTEXT,
    tpMut TINYTEXT,
    ang TINYTEXT,
    innov TINYTEXT,
    mana TINYTEXT,
    expr TINYTEXT,
    annee TINYTEXT,
    CONSTRAINT antiDoublon UNIQUE (email)
    );
