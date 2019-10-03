DROP TABLE Dienstgrade CASCADE CONSTRAINTS;
DROP TABLE Mitglieder CASCADE CONSTRAINTS;
DROP TABLE Einsatzfahrzeuge CASCADE CONSTRAINTS;
DROP TABLE Stuetzpunktorte CASCADE CONSTRAINTS;
DROP TABLE Einsatzorte CASCADE CONSTRAINTS;
DROP TABLE Stuetzpunkte CASCADE CONSTRAINTS;
DROP TABLE Fahrzeug_gehoert_Stuetzpunkt CASCADE CONSTRAINTS;
DROP TABLE Mitglied_gehoert_Stuetzpunkt CASCADE CONSTRAINTS;
DROP TABLE Einsatzarten CASCADE CONSTRAINTS;
DROP TABLE Einsatzcodes CASCADE CONSTRAINTS;
DROP TABLE Einsaetze CASCADE CONSTRAINTS;
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
  id_dienstgrad INTEGER,
  CONSTRAINT pk_Mitglieder PRIMARY KEY (id),
  CONSTRAINT fk_Dienstgrad FOREIGN KEY (id_dienstgrad) REFERENCES Dienstgrade (id)
);

CREATE TABLE Einsatzfahrzeuge
(
  id      INTEGER,
  rufname VARCHAR(30),
  CONSTRAINT pk_Einsatzfahrzeuge PRIMARY KEY (id),
  CONSTRAINT uq_Einsatzfahrzeug UNIQUE (rufname)
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
  CONSTRAINT pk_Einsatzort PRIMARY KEY (id)
);

CREATE TABLE Stuetzpunkte
(
  id   INTEGER,
  name VARCHAR(50),
  CONSTRAINT pk_STPNKT PRIMARY KEY (id)
);

CREATE TABLE Fahrzeug_gehoert_Stuetzpunkt
(
  id_stuetzpunkt     INTEGER,
  id_einsatzfahrzeug INTEGER,
  CONSTRAINT pk_FZG_gehoert_STPNKT PRIMARY KEY (id_stuetzpunkt, id_einsatzfahrzeug),
  CONSTRAINT fk_FZG_in_STPNKT FOREIGN KEY (id_stuetzpunkt) REFERENCES Stuetzpunkte (id),
  CONSTRAINT fk_FZG_Reference FOREIGN KEY (id_einsatzfahrzeug) REFERENCES Einsatzfahrzeuge (id),
  CONSTRAINT uq_FZG UNIQUE (id_einsatzfahrzeug)
);

CREATE TABLE Mitglied_gehoert_Stuetzpunkt
(
  id_stuetzpunkt INTEGER,
  id_mitglied    INTEGER,
  CONSTRAINT pk_M_gehoert_zu_STPNKT PRIMARY KEY (id_stuetzpunkt, id_mitglied),
  CONSTRAINT fk_STPNKT_von_M FOREIGN KEY (id_stuetzpunkt) REFERENCES Stuetzpunkte (id),
  CONSTRAINT fk_Mitglied_Reference FOREIGN KEY (id_mitglied) REFERENCES Mitglieder (id),
  CONSTRAINT uq_Mitglied UNIQUE (id_mitglied)
);

CREATE TABLE Einsatzarten
(
  id           INTEGER,
  beschreibung VARCHAR(100),
  CONSTRAINT pk_Einsatzarten PRIMARY KEY (id)
);

CREATE TABLE Einsatzcodes
(
  id   INTEGER,
  code VARCHAR(30),
  CONSTRAINT pk_Einsatzcodes PRIMARY KEY (id)

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
  CONSTRAINT fk_Mitglied_bei_Mitglied FOREIGN KEY (id_Mitglied, id_Stuetzpunkt) REFERENCES Mitglied_gehoert_Stuetzpunkt (id_mitglied, id_Stuetzpunkt),
  CONSTRAINT fk_Einsatzkraft_bei_Einsatz FOREIGN KEY (id_Stuetzpunkt, id_Einsatz) REFERENCES Einsatzkraft_war_bei_Einsatz (id_Stuetzpunkt, id_Einsatz)
);

CREATE TABLE Fahrzeug_war_bei_Einsatz
(
  id_Einsatzfahrzeug INTEGER,
  id_Stuetzpunkt     INTEGER,
  id_Einsatz         INTEGER,
  CONSTRAINT pk_Fahrzeug_war_bei_Einsatz PRIMARY KEY (id_Einsatzfahrzeug, id_Stuetzpunkt, id_einsatz),
  CONSTRAINT fk_FZG_war_bei_Einsatz FOREIGN KEY (id_Einsatzfahrzeug, id_Stuetzpunkt) REFERENCES Fahrzeug_gehoert_Stuetzpunkt (id_einsatzfahrzeug, id_stuetzpunkt),
  CONSTRAINT fk_EKR_war_bei_Einsatz FOREIGN KEY (id_Stuetzpunkt, id_Einsatz) REFERENCES Einsatzkraft_war_bei_Einsatz (id_Stuetzpunkt, id_Einsatz)
);