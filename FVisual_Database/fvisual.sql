﻿DROP TABLE Dienstgrade CASCADE CONSTRAINTS;
DROP TABLE Mitglieder CASCADE CONSTRAINTS;
DROP TABLE Einsatzfahrzeuge CASCADE CONSTRAINTS;
DROP TABLE Stuetzpunkte CASCADE CONSTRAINTS;
DROP TABLE Einsatzarten CASCADE CONSTRAINTS;
DROP TABLE Einsatzcodes CASCADE CONSTRAINTS;
DROP TABLE Andere_Organisationen CASCADE CONSTRAINTS;
DROP TABLE Einsaetze CASCADE CONSTRAINTS;
DROP TABLE AOrg_wb_Einsatz CASCADE CONSTRAINTS;
DROP TABLE EKraft_wb_Einsatz CASCADE CONSTRAINTS;
DROP TABLE Mtg_wb_Einsatz CASCADE CONSTRAINTS;
DROP TABLE FZG_wb_Einsatz CASCADE CONSTRAINTS;

DROP SEQUENCE seq_Einsatzarten;
DROP SEQUENCE seq_Einsatzcodes;
DROP SEQUENCE seq_Dienstgrade;
DROP SEQUENCE seq_Andere_Organisationen;
DROP SEQUENCE seq_Stuetzpunkte;
DROP SEQUENCE seq_Mitglieder;
DROP SEQUENCE seq_Einsatzfahrzeuge;
DROP SEQUENCE seq_Einsaetze;

CREATE SEQUENCE seq_Einsatzarten START WITH 1;
CREATE SEQUENCE seq_Einsatzcodes START WITH 1;
CREATE SEQUENCE seq_Dienstgrade START WITH 1;
CREATE SEQUENCE seq_Andere_Organisationen START WITH 1;
CREATE SEQUENCE seq_Stuetzpunkte START WITH 1;
CREATE SEQUENCE seq_Mitglieder START WITH 1;
CREATE SEQUENCE seq_Einsatzfahrzeuge START WITH 1;
CREATE SEQUENCE seq_Einsaetze START WITH 1;

CREATE TABLE Einsatzarten
(
  id          INTEGER,
  beschreibung VARCHAR(100),
  CONSTRAINT pk_Einsatzarten PRIMARY KEY (id),
  CONSTRAINT uq_Einsatzart UNIQUE(beschreibung)
);

CREATE TABLE Einsatzcodes
(
  id   INTEGER,
  code VARCHAR(30),
  CONSTRAINT pk_Einsatzcode PRIMARY KEY (id),
  CONSTRAINT uq_Einsatzcode UNIQUE (code)
);

CREATE TABLE Dienstgrade
(
  id          INTEGER,
  kuerzel     VARCHAR(3),
  bezeichnung VARCHAR(25),
  CONSTRAINT pk_Dienstgrade PRIMARY KEY (id)
);

CREATE TABLE Andere_Organisationen(
  id  INTEGER,
  name VARCHAR(100),
  CONSTRAINT pk_Andere_Organisationen PRIMARY KEY (id),
  CONSTRAINT uq_Andere_Organisation UNIQUE(name)
);

CREATE TABLE Stuetzpunkte
(
  id   INTEGER,
  name VARCHAR(50),
  ort     VARCHAR(50),
  plz     INTEGER,
  strasse VARCHAR(50),
  hausnr  VARCHAR(50),
  CONSTRAINT pk_Stuetzpunkte PRIMARY KEY (id),
  CONSTRAINT uq_Stuetzpunkt_Name UNIQUE (name),
  CONSTRAINT uq_Stuetzpunkt_Ort UNIQUE (ort, plz, strasse, hausnr)
);

CREATE TABLE Mitglieder
(
  id            INTEGER,
  id_dienstgrad INTEGER,
  id_stuetzpunkt INTEGER,
  vorname       VARCHAR(30),
  nachname      VARCHAR(30),
  username		VARCHAR(30),
  password		VARCHAR(250),
  isAdmin       VARCHAR(5),
  CONSTRAINT pk_Mitglieder PRIMARY KEY (id, id_stuetzpunkt),
  CONSTRAINT fk_Mitglied_refStpnkt FOREIGN KEY (id_stuetzpunkt) REFERENCES Stuetzpunkte (id),
  CONSTRAINT fk_Mitglied_refDG FOREIGN KEY (id_dienstgrad) REFERENCES Dienstgrade (id),
  CONSTRAINT uq_Mitglied_Username UNIQUE(username)
);

CREATE TABLE Einsatzfahrzeuge
(
  id      INTEGER,
  bezeichnung VARCHAR(30),
  id_stuetzpunkt INTEGER,
  CONSTRAINT pk_Einsatzfahrzeuge PRIMARY KEY (id, id_stuetzpunkt),
  CONSTRAINT fk_Einsatzfahrzeug_refStpnkt FOREIGN KEY (id_stuetzpunkt) REFERENCES Stuetzpunkte (id),
  CONSTRAINT uq_Einsatzfahrzeug UNIQUE (bezeichnung, id_stuetzpunkt)
);

CREATE TABLE Einsaetze
(
  id               INTEGER,
  id_einsatzcode   INTEGER,
  id_einsatzart    INTEGER,
  titel            VARCHAR(50),
  kurzbeschreibung VARCHAR(250),
  adresse          VARCHAR(100),
  datum            DATE,
  zeit             TIMESTAMP,
  CONSTRAINT pk_Einsaetze PRIMARY KEY (id),
  CONSTRAINT fk_Einsatz_Einsatzart FOREIGN KEY (id_einsatzart) REFERENCES Einsatzarten (id),
  CONSTRAINT fk_Einsatz_Einsatzcode FOREIGN KEY (id_einsatzcode) REFERENCES Einsatzcodes (id)
);

CREATE TABLE AOrg_wb_Einsatz(
    id_einsatz INTEGER,
    id_andere_org INTEGER,
    CONSTRAINT fk_AORG_wb_E_refEinsatz FOREIGN KEY (id_Einsatz) REFERENCES Einsaetze (id),
     CONSTRAINT fk_AORG_wb_E_refAOrg FOREIGN KEY (id_andere_org) REFERENCES ANDERE_ORGANISATIONEN (id)

);

CREATE TABLE EKraft_wb_Einsatz
(
  id_Stuetzpunkt INTEGER,
  id_Einsatz     INTEGER,
  CONSTRAINT pk_EKraft_wb_Einsatz PRIMARY KEY (id_Stuetzpunkt, id_Einsatz),
  CONSTRAINT fk_EKraft_wb_E_refEinsatz FOREIGN KEY (id_Einsatz) REFERENCES Einsaetze (id),
  CONSTRAINT fk_EKraft_wb_E_refStpnkt FOREIGN KEY (id_Stuetzpunkt) REFERENCES Stuetzpunkte (id)
);

CREATE TABLE Mtg_wb_Einsatz
(
  id_Mitglied    INTEGER,
  id_Stuetzpunkt INTEGER,
  id_Einsatz     INTEGER,
  CONSTRAINT pk_Mtg_wb_Einsatz PRIMARY KEY (id_Mitglied, id_Stuetzpunkt, id_einsatz),
  CONSTRAINT fk_Mtg_wb_Einsatz_refMtg FOREIGN KEY (id_Mitglied, id_Stuetzpunkt) REFERENCES Mitglieder (id, id_Stuetzpunkt),
  CONSTRAINT fk_Mtg_wb_Einsatz_refEinsatz FOREIGN KEY (id_Stuetzpunkt, id_Einsatz) REFERENCES EKraft_wb_Einsatz (id_Stuetzpunkt, id_Einsatz)
);

CREATE TABLE FZG_wb_Einsatz
(
  id_Einsatzfahrzeug INTEGER,
  id_Stuetzpunkt     INTEGER,
  id_Einsatz         INTEGER,
  CONSTRAINT pk_FZG_wb_Einsatz PRIMARY KEY (id_Einsatzfahrzeug, id_Stuetzpunkt, id_einsatz),
  CONSTRAINT fk_FZG_wb_Einsatz_FZG FOREIGN KEY (id_Einsatzfahrzeug, id_Stuetzpunkt) REFERENCES Einsatzfahrzeuge (id, id_Stuetzpunkt),
  CONSTRAINT fk_FZG_wb_Einsatz_EKraft FOREIGN KEY (id_Stuetzpunkt, id_Einsatz) REFERENCES EKraft_wb_Einsatz (id_Stuetzpunkt, id_Einsatz)
);

INSERT INTO Einsatzarten(id, beschreibung) VALUES(1, 'Verkehrsunfall');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Brandeinsatz');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Technischer Einsatz');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Hilfeleistung');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Technische Hilfeleistung');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Personensuche');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Brandmeldealarm');
INSERT INTO Einsatzarten(id, beschreibung) VALUES(seq_Einsatzarten.nextval, 'Wespen / Hornissen / Bienen');

INSERT INTO Einsatzcodes(id, code) VALUES (1, 'T KDO');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T 0');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T 3');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T 4');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T UNWETTER 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T UNWETTER 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T PERSON 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T PERSON 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T PERSON GAS');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T PERSON WASSER 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T PERSON WASSER 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T WASSER');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T GAS 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T GAS 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T SCHADSTOFF 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T SCHADSTOFF 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T SCHADSTOFF 3');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T SCHADSTOFF 4');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T VU 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T VU 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T VU 3');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T VU 4');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T VU 5');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'T SONDERLAGE');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 0');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 3');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 3 PERSON');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 4');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 4 PERSON');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 5');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 6');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 7');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 8');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B 9');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'BMA 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'BMA 2');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'B WASSER');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'FLUGNOT 1');
INSERT INTO Einsatzcodes(id, code) VALUES (seq_Einsatzcodes.nextval, 'FLUGNOT 2');

INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (1, 'PFM', 'Probefeuerwehrmann');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'FM', 'Feuerwehrmann');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'OFM', 'Oberfeuerwehrmann');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'HFM', 'Hauptfeuerwehrmann');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'LM', 'Löschmeister');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'OLM', 'Oberlöschmeister');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'HFM', 'Hauptlöschmeister');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'BM', 'Brandmeister');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'OBM', 'Oberbrandmeister');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'HBM', 'Hauptbrandmeister');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'BI', 'Brandinspektor');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'OBI', 'Oberbrandinspektor');
INSERT INTO Dienstgrade(id, kuerzel, bezeichnung) VALUES (seq_Dienstgrade.nextval, 'HBI', 'Hauptbrandinspektor');

INSERT INTO Andere_Organisationen(id, name) VALUES(1, 'Polizei');
INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, 'Polizeihubschrauber');
INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, 'Rotes Kreuz');
INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, 'Notarzt');
INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, 'Rettungshubschrauber');

INSERT INTO Stuetzpunkte(id, name, ort, plz, strasse, hausnr) VALUES(1, 'Feuerwehr St. Peter Spittal', 'Spittal/Drau', 9800,  'St. Peter', 47);
INSERT INTO Stuetzpunkte(id, name, ort, plz, strasse, hausnr) VALUES(seq_Stuetzpunkte.nextval, 'Feuerwehr Olsach-Molzbichl', 'Rothenthurn', 9701, 'Molzbichl', 67 );

INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(1, 2, 1, 'Florian', 'Graf', 'graff', 'lauch', 'true');
INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(seq_Mitglieder.nextval, 2, 1, 'Sandro', 'Assek', 'asseks', 'lauch', 'true');
INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(seq_Mitglieder.nextval, 2, 1, 'Christoph', 'Thaler', 'thalerc', 'lauch', 'true');
INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(seq_Mitglieder.nextval, 3, 2, 'Andreas', 'Drabosenig', 'drabosa', 'lauch', 'true');
INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(seq_Mitglieder.nextval, 4, 2, 'User', 'Normal', 'user', 'user', 'false');
INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(seq_Mitglieder.nextval, 5, 2, 'User', 'Admin', 'admin', 'admin', 'true');

INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (1, 1, 'KRFA');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 1, 'TLFA-2000');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 1, 'LF-A');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 1, 'RTB-50');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 1, 'Ölwehranhänger');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 2, 'TLFA-4000');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 2, 'LFA');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (seq_Einsatzfahrzeuge.nextval, 2, 'Katastrophenschutzanhänger');

SELECT Mitglieder.id, id_dienstgrad, id_stuetzpunkt, vorname, nachname FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id = Mitglieder.id_dienstgrad WHERE id_stuetzpunkt = :1;
SELECT Mitglieder.id, id_dienstgrad, id_stuetzpunkt, vorname, nachname FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id = Mitglieder.id_dienstgrad WHERE id_stuetzpunkt = :1 AND Mitglieder.id = :2;
INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin) VALUES(:1, :2, :3, :4, :5, :6, :7, :8);

UPDATE Mitglieder SET id_dienstgrad = :1, id_stuetzpunkt = :2, vorname = :3, nachname = :4;
UPDATE Mitglieder SET password = :1 WHERE id = :2 AND id_stuetzpunkt = :3;
UPDATE Mitglieder SET id = :1, id_dienstgrad = :1, id_stuetzpunkt = :1, vorname = :1, nachname = :1, isAdmin = :1 WHERE id = :2 AND id_stuetzpunkt = :3;


COMMIT;