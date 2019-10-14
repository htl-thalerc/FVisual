DROP TABLE Dienstgrade CASCADE CONSTRAINTS;
DROP TABLE Mitglieder CASCADE CONSTRAINTS;
DROP TABLE Einsatzfahrzeuge CASCADE CONSTRAINTS;
DROP TABLE Stuetzpunktorte CASCADE CONSTRAINTS;
DROP TABLE Einsatzorte CASCADE CONSTRAINTS;
DROP TABLE Stuetzpunkte CASCADE CONSTRAINTS;
DROP TABLE Einsatzarten CASCADE CONSTRAINTS;
DROP TABLE Einsatzcodes CASCADE CONSTRAINTS;
DROP TABLE Andere_Organisationen CASCADE CONSTRAINTS;
DROP TABLE Einsaetze CASCADE CONSTRAINTS;
DROP TABLE AOrg_war_bei_Einsatz CASCADE CONSTRAINTS;
DROP TABLE Einsatzkraft_war_bei_Einsatz CASCADE CONSTRAINTS;
DROP TABLE Mitglied_war_bei_Einsatz CASCADE CONSTRAINTS;
DROP TABLE Fahrzeug_war_bei_Einsatz CASCADE CONSTRAINTS;

CREATE TABLE Dienstgrade
(
  id          INTEGER,
  kuerzel     VARCHAR(3),
  bezeichnung VARCHAR(25),
  CONSTRAINT pk_Dienstgrade PRIMARY KEY (id)
);

CREATE TABLE Mitglieder
(
  id            INTEGER,
  vorname       VARCHAR(30),
  nachname      VARCHAR(30),
  id_stuetzpunkt INTEGER,
  id_dienstgrad INTEGER,
  CONSTRAINT pk_Mitglieder PRIMARY KEY (id, id_stuetzpunkt),
  CONSTRAINT fk_Einsatzfahrzeug_Stuetzpunkt FOREIGN KEY (id_stuetzpunkt) REFERENCES Stuetzpunkte (id),
  CONSTRAINT fk_Dienstgrad FOREIGN KEY (id_dienstgrad) REFERENCES Dienstgrade (id)
);


CREATE TABLE Stuetzpunktorte
(
  id      INTEGER,
  ort     VARCHAR(50),
  plz     INTEGER,
  strasse VARCHAR(50),
  hausnr  VARCHAR(50),
  CONSTRAINT pk_Stuetzpunktorte PRIMARY KEY (id),
  CONSTRAINT uq_Stuetzpunktort UNIQUE (ort, plz, strasse, hausnr)
);

CREATE TABLE Einsatzorte
(
  id      INTEGER,
  adresse VARCHAR(100),
  CONSTRAINT pk_Einsatzort PRIMARY KEY (id),
  CONSTRAINT uq_Einsatzort UNIQUE (adresse)
);

CREATE TABLE Stuetzpunkte
(
  id   INTEGER,
  name VARCHAR(50),
  CONSTRAINT pk_STPNKT PRIMARY KEY (id),
  CONSTRAINT uq_Stuetzpunkt UNIQUE (name)
);

CREATE TABLE Einsatzfahrzeuge
(
  id      INTEGER,
  rufname VARCHAR(30),
  id_stuetzpunkt INTEGER,
  CONSTRAINT pk_Einsatzfahrzeuge PRIMARY KEY (id, id_stuetzpunkt),
  CONSTRAINT fk_Einsatzfahrzeug_Stuetzpunkt FOREIGN KEY (id_stuetzpunkt) REFERENCES Stuetzpunkte (id),
  CONSTRAINT uq_Einsatzfahrzeug UNIQUE (rufname)
);

CREATE TABLE Einsatzarten
(
  id           INTEGER,
  beschreibung VARCHAR(100),
  CONSTRAINT pk_Einsatzarten PRIMARY KEY (id),
  CONSTRAINT uq_Einsatzart UNIQUE(beschreibung)
);

CREATE TABLE Einsatzcodes
(
  id   INTEGER,
  code VARCHAR(30),
  CONSTRAINT pk_Einsatzcodes PRIMARY KEY (id),
  CONSTRAINT uq_Einsatzcode UNIQUE (code)

);

CREATE TABLE Andere_Organisationen(
  id  INTEGER,
  name VARCHAR(100),
  CONSTRAINT pk_Andere_Organisationen PRIMARY KEY (id),
  CONSTRAINT uq_Andere_Organisationen UNIQUE(name)
);

CREATE TABLE Einsaetze
(
  id               INTEGER,
  id_einsatzcode   INTEGER,
  id_einsatzart    INTEGER,
  id_einsatzort    INTEGER,
  titel            VARCHAR(50),
  kurzbeschreibung VARCHAR(250),
  datum            DATE,
  zeit             TIMESTAMP,
  CONSTRAINT pk_Einsatz PRIMARY KEY (id),
  CONSTRAINT fk_Einsaetzte_Einsatzort FOREIGN KEY (id_einsatzort) REFERENCES Einsatzorte (id),
  CONSTRAINT fk_Einsaetzte_Einsatzart FOREIGN KEY (id_einsatzart) REFERENCES Einsatzarten (id),
  CONSTRAINT fk_Einsaetzte_Einsatzcode FOREIGN KEY (id_einsatzcode) REFERENCES Einsatzcodes (id)
);

CREATE TABLE AOrg_war_bei_Einsatz(
    id_einsatz INTEGER,
    id_andere_org INTEGER,
    CONSTRAINT fk_Einsatz FOREIGN KEY (id_Einsatz) REFERENCES Einsaetze (id),
     CONSTRAINT fk_ANDERE_ORGANISATIONEN FOREIGN KEY (id_andere_org) REFERENCES ANDERE_ORGANISATIONEN (id)

);

CREATE TABLE Einsatzkraft_war_bei_Einsatz
(
  id_Stuetzpunkt INTEGER,
  id_Einsatz     INTEGER,
  CONSTRAINT pk_Einsatzkraft_war_im_Einsatz PRIMARY KEY (id_Stuetzpunkt, id_Einsatz),
  CONSTRAINT fk_Einsatz FOREIGN KEY (id_Einsatz) REFERENCES Einsaetze (id),
  CONSTRAINT fk_Stuetzpunkt FOREIGN KEY (id_Stuetzpunkt) REFERENCES Stuetzpunkte (id)
);

CREATE TABLE Mitglied_war_bei_Einsatz
(
  id_Mitglied    INTEGER,
  id_Stuetzpunkt INTEGER,
  id_Einsatz     INTEGER,
  CONSTRAINT pk_Mitglied_war_bei_Einsatz PRIMARY KEY (id_Mitglied, id_Stuetzpunkt, id_einsatz),
  CONSTRAINT fk_Mitglied_war_bei_Einsatz FOREIGN KEY (id_Mitglied, id_Stuetzpunkt) REFERENCES Mitglieder (id_mitglied, id_Stuetzpunkt),
  CONSTRAINT fk_Einsatzkraft_bei_Einsatz FOREIGN KEY (id_Stuetzpunkt, id_Einsatz) REFERENCES Einsatzkraft_war_bei_Einsatz (id_Stuetzpunkt, id_Einsatz)
);

CREATE TABLE Fahrzeug_war_bei_Einsatz
(
  id_Einsatzfahrzeug INTEGER,
  id_Stuetzpunkt     INTEGER,
  id_Einsatz         INTEGER,
  CONSTRAINT pk_Fahrzeug_war_bei_Einsatz PRIMARY KEY (id_Einsatzfahrzeug, id_Stuetzpunkt, id_einsatz),
  CONSTRAINT fk_FZG_war_bei_Einsatz FOREIGN KEY (id_Einsatzfahrzeug, id_Stuetzpunkt) REFERENCES Einsatzfahrzeuge (id_einsatzfahrzeug, id_Stuetzpunkt),
  CONSTRAINT fk_EKR_war_bei_Einsatz FOREIGN KEY (id_Stuetzpunkt, id_Einsatz) REFERENCES Einsatzkraft_war_bei_Einsatz (id_Stuetzpunkt, id_Einsatz)
);

INSERT INTO Einsatzarten VALUES(1, 'Verkehrsunfall');
INSERT INTO Einsatzarten VALUES(2, 'Brandeinsatz');
INSERT INTO Einsatzarten VALUES(3, 'Technischer Einsatz');
INSERT INTO Einsatzarten VALUES(4, 'Hilfeleistung');
INSERT INTO Einsatzarten VALUES(5, 'Technische Hilfeleistung');
INSERT INTO Einsatzarten VALUES(6, 'Personensuche');
INSERT INTO Einsatzarten VALUES(7, 'Brandmeldealarm');
INSERT INTO Einsatzarten VALUES(8, 'Wespen / Hornissen / Bienen');

INSERT INTO Einsatzcodes VALUES (1, 'T KDO');
INSERT INTO Einsatzcodes VALUES (2, 'T 0');
INSERT INTO Einsatzcodes VALUES (3, 'T 1');
INSERT INTO Einsatzcodes VALUES (4, 'T 2');
INSERT INTO Einsatzcodes VALUES (5, 'T 3');
INSERT INTO Einsatzcodes VALUES (6, 'T 4');
INSERT INTO Einsatzcodes VALUES (7, 'T UNWETTER 1');
INSERT INTO Einsatzcodes VALUES (8, 'T UNWETTER 2');
INSERT INTO Einsatzcodes VALUES (9, 'T PERSON 1');
INSERT INTO Einsatzcodes VALUES (10, 'T PERSON 2');
INSERT INTO Einsatzcodes VALUES (11, 'T PERSON GAS');
INSERT INTO Einsatzcodes VALUES (12, 'T PERSON WASSER 1');
INSERT INTO Einsatzcodes VALUES (13, 'T PERSON WASSER 2');
INSERT INTO Einsatzcodes VALUES (14, 'T WASSER');
INSERT INTO Einsatzcodes VALUES (15, 'T GAS 1');
INSERT INTO Einsatzcodes VALUES (16, 'T GAS 2');
INSERT INTO Einsatzcodes VALUES (17, 'T SCHADSTOFF 1');
INSERT INTO Einsatzcodes VALUES (18, 'T SCHADSTOFF 2');
INSERT INTO Einsatzcodes VALUES (19, 'T SCHADSTOFF 3');
INSERT INTO Einsatzcodes VALUES (20, 'T SCHADSTOFF 4');
INSERT INTO Einsatzcodes VALUES (21, 'T VU 1');
INSERT INTO Einsatzcodes VALUES (22, 'T VU 2');
INSERT INTO Einsatzcodes VALUES (23, 'T VU 3');
INSERT INTO Einsatzcodes VALUES (24, 'T VU 4');
INSERT INTO Einsatzcodes VALUES (25, 'T VU 5');
INSERT INTO Einsatzcodes VALUES (26, 'T SONDERLAGE');
INSERT INTO Einsatzcodes VALUES (27, 'B 0');
INSERT INTO Einsatzcodes VALUES (28, 'B 1');
INSERT INTO Einsatzcodes VALUES (29, 'B 2');
INSERT INTO Einsatzcodes VALUES (30, 'B 3');
INSERT INTO Einsatzcodes VALUES (31, 'B 3 PERSON');
INSERT INTO Einsatzcodes VALUES (32, 'B 4');
INSERT INTO Einsatzcodes VALUES (33, 'B 4 PERSON');
INSERT INTO Einsatzcodes VALUES (34, 'B 5');
INSERT INTO Einsatzcodes VALUES (35, 'B 6');
INSERT INTO Einsatzcodes VALUES (36, 'B 7');
INSERT INTO Einsatzcodes VALUES (37, 'B 8');
INSERT INTO Einsatzcodes VALUES (38, 'B 9');
INSERT INTO Einsatzcodes VALUES (39, 'BMA 1');
INSERT INTO Einsatzcodes VALUES (40, 'BMA 2');
INSERT INTO Einsatzcodes VALUES (41, 'B WASSER');
INSERT INTO Einsatzcodes VALUES (42, 'FLUGNOT 1');
INSERT INTO Einsatzcodes VALUES (43, 'FLUGNOT 2');

INSERT INTO Stuetzpunkte VALUES(1, 'Feuerwehr St. Peter Spittal');

INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, rufname) VALUES (1, 1, 'KRFA');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, rufname) VALUES (2, 1, 'TLFA-2000');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, rufname) VALUES (3, 1, 'LF-A');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, rufname) VALUES (4, 1, 'RTB-50');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, rufname) VALUES (5, 1, 'Ölwehranhänger');