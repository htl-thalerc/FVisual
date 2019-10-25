DROP TABLE Dienstgrade CASCADE CONSTRAINTS;
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
  vorname       VARCHAR(30),
  nachname      VARCHAR(30),
  username		VARCHAR(30),
  password		VARCHAR(250),
  isAdmin       VARCHAR(3),
  id_stuetzpunkt INTEGER,
  id_dienstgrad INTEGER,
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

INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Verkehrsunfall');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Brandeinsatz');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Technischer Einsatz');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Hilfeleistung');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Technische Hilfeleistung');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Personensuche');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Brandmeldealarm');
INSERT INTO Einsatzarten VALUES(seq_Einsatzarten.nextval, 'Wespen / Hornissen / Bienen');

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

INSERT INTO Stuetzpunkte VALUES(1, 'Feuerwehr St. Peter Spittal', 'Spittal/Drau', 9800,  'St. Peter', 47);
INSERT INTO Stuetzpunkte VALUES(2, 'Feuerwehr Olsach-Molzbichl', 'Rothenthurn', 9701, 'Molzbichl', 67 );

INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (1, 1, 'KRFA');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (2, 1, 'TLFA-2000');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (3, 1, 'LF-A');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (4, 1, 'RTB-50');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (5, 1, 'Ölwehranhänger');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (6, 2, 'TLFA-4000');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (7, 2, 'LFA');
INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES (8, 2, 'Katastrophenschutzanhänger');

INSERT INTO DIENSTGRADE VALUES (1, 'PFM', 'Probefeuerwehrmann');
INSERT INTO DIENSTGRADE VALUES (2, 'FM', 'Feuerwehrmann');
INSERT INTO DIENSTGRADE VALUES (3, 'OFM', 'Oberfeuerwehrmann');
INSERT INTO DIENSTGRADE VALUES (4, 'HFM', 'Hauptfeuerwehrmann');
INSERT INTO DIENSTGRADE VALUES (5, 'LM', 'Löschmeister');
INSERT INTO DIENSTGRADE VALUES (6, 'OLM', 'Oberlöschmeister');
INSERT INTO DIENSTGRADE VALUES (7, 'HFM', 'Hauptlöschmeister');
INSERT INTO DIENSTGRADE VALUES (8, 'BM', 'Brandmeister');
INSERT INTO DIENSTGRADE VALUES (9, 'OBM', 'Oberbrandmeister');
INSERT INTO DIENSTGRADE VALUES (10, 'HBM', 'Hauptbrandmeister');
INSERT INTO DIENSTGRADE VALUES (11, 'BI', 'Brandinspektor');
INSERT INTO DIENSTGRADE VALUES (12, 'OBI', 'Oberbrandinspektor');
INSERT INTO DIENSTGRADE VALUES (13, 'HBI', 'Hauptbrandinspektor');


COMMIT;