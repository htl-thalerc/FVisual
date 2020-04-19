'use strict';

/* ALL QUERYSTRINGS FOR DB */

module.exports.EARTEN_GET = "SELECT id, beschreibung FROM Einsatzarten ORDER BY id";
module.exports.ECODES_GET = "SELECT id, code FROM Einsatzcodes ORDER BY id";
module.exports.DG_GET = "SELECT id, kuerzel, bezeichnung FROM Dienstgrade ORDER BY id";

module.exports.AORGS_GET = "SELECT AORG.id, AORG.name, NVL(AWBE.ID_EINSATZ, '0') AS ID_EINSATZ FROM Andere_Organisationen AORG LEFT JOIN AORG_WB_EINSATZ AWBE ON AORG.ID = AWBE.ID_ANDERE_ORG ORDER BY AORG.NAME";
module.exports.AORGS_GETBY_AORGS_ID = "SELECT id, name FROM Andere_Organisationen WHERE id = :1";
module.exports.AORGS_GETBY_AORGS_NAME = "SELECT id, name FROM Andere_Organisationen WHERE name = :1";
module.exports.AORGS_POST = "INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, :1)";
module.exports.AORGS_PUT = "UPDATE Andere_Organisationen SET name = :1 WHERE id = :2";
module.exports.AORGS_DELETE = "DELETE FROM Andere_Organisationen WHERE id = :1";

module.exports.STPNKT_GET = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE id >=0 ORDER BY name";
module.exports.STPNKT_GETBY_STPNKT_ID = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE id = :1";
module.exports.STPNKT_GETBY_STPNKT_NAME = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE name = :1";
module.exports.STPNKT_POST = "INSERT INTO Stuetzpunkte(id, name, ort, plz, strasse, hausnr) VALUES(seq_Stuetzpunkte.nextval, :1, :2, :3,  :4, :5)";
module.exports.STPNKT_PUT = "UPDATE Stuetzpunkte SET name= :1, ort= :2, plz = :3, strasse = :4, hausnr = :5 WHERE id = :6";
module.exports.STPNKT_DELETE = "DELETE FROM Stuetzpunkte WHERE id = :1";

module.exports.MGT_GET = "SELECT Mitglieder.id, id_dienstgrad, kuerzel, bezeichnung, id_stuetzpunkt, vorname, nachname, username, password, isAdmin FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id=Mitglieder.id_dienstgrad ORDER BY isAdmin DESC, username ASC";
module.exports.MTG_GET_BY_MTG_ID = "SELECT Mitglieder.id, id_dienstgrad, kuerzel, bezeichnung, id_stuetzpunkt, vorname, nachname, username, password, isAdmin FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id=Mitglieder.id_dienstgrad WHERE Mitglieder.id = :1 ORDER BY isAdmin DESC, username ASC";
module.exports.MTG_GET_BY_USERNAME = "SELECT Mitglieder.id, id_dienstgrad, kuerzel, bezeichnung, id_stuetzpunkt, vorname, nachname, username, password, isAdmin FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id=Mitglieder.id_dienstgrad WHERE username = :1 ORDER BY isAdmin DESC, username ASC";
module.exports.MTG_POST = "INSERT INTO Mitglieder(id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, password, isAdmin ) VALUES ( seq_Mitglieder.nextval, :1, :2, :3, :4, :5, :6, :7 )";
module.exports.MTG_PUT = "UPDATE Mitglieder SET id_dienstgrad= :1, id_stuetzpunkt = :2, vorname= :3, nachname= :4, username= :5, password= :6, isAdmin= :7 WHERE id = :8";
module.exports.MTG_DELETE = "DELETE FROM Mitglieder WHERE id = :1";
module.exports.ADMINS_GET = "SELECT Mitglieder.id, id_dienstgrad, kuerzel, bezeichnung, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder INNER JOIN dienstgrade ON dienstgrade.id = id_dienstgrad WHERE isAdmin = 'true'";
module.exports.MTG_GET_BASELESS = "SELECT Mitglieder.id, id_dienstgrad, kuerzel, bezeichnung, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id=Mitglieder.id_dienstgrad WHERE id_stuetzpunkt = -1 ORDER BY isAdmin DESC, username ASC";

module.exports.MTG_GET_EINSAETZE = "SELECT id, id_einsatzcode, id_einsatzart, titel, kurzbeschreibung, adresse, plz, zeit FROM Einsaetze INNER JOIN MTG_WB_EINSATZ ON id_einsatz = id WHERE id_mitglied = :1";

module.exports.STPNKT_MTG_GET = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username FROM Mitglieder WHERE id_stuetzpunkt = :1 ORDER BY id_dienstgrad, nachname";
module.exports.STPNKT_MTG_GET_BY_MTG_ID = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username FROM Mitglieder WHERE id_stuetzpunkt = :1 AND id=:2";

module.exports.STPNKT_FZG_GET = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 ORDER BY bezeichnung";
module.exports.STPNKT_FZG_GET_BY_FZG_ID = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND id = :2";
module.exports.STPNKT_FZG_GET_BY_FZG_BEZEICHNUNG = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND bezeichnung = :2"
module.exports.STPNKT_FZG_POST = "INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES(seq_Einsatzfahrzeuge.nextval, :1, :2)";
module.exports.STPNKT_FZG_PUT = "UPDATE Einsatzfahrzeuge SET bezeichnung = :1 WHERE id_stuetzpunkt = :2 AND id = :3";
module.exports.STPNKT_FZG_DELETE = "DELETE FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND id = :2";

module.exports.STPNKT_FZG_GET = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 ORDER BY bezeichnung";
module.exports.STPNKT_FZG_GET_BY_FZG_ID = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND id = :2";
module.exports.STPNKT_FZG_GET_BY_FZG_NAME = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND bezeichnung = :2"
module.exports.STPNKT_FZG_POST = "INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES(seq_Einsatzfahrzeuge.nextval, :1, :2)";
module.exports.STPNKT_FZG_PUT = "UPDATE Einsatzfahrzeuge SET bezeichnung = :1 WHERE id_stuetzpunkt = :2 AND id = :3";
module.exports.STPNKT_FZG_DELETE = "DELETE FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND id = :2";

module.exports.EINSATZ_GET = "SELECT E.id, E.id_einsatzart, EA.beschreibung, MTG_WB_EINSATZ.ID_STUETZPUNKT, E.id_einsatzcode, EC.code, E.titel, E.kurzbeschreibung, E.adresse, E.plz, E.zeit FROM Einsaetze E INNER JOIN Einsatzcodes EC ON E.id_einsatzcode = EC.id INNER JOIN Einsatzarten EA on E.id_einsatzart = EA.id INNER JOIN MTG_WB_EINSATZ ON MTG_WB_EINSATZ.ID_EINSATZ = E.ID";
module.exports.EINSATZ_GET_BY_ID = "SELECT E.id, E.id_einsatzart, EA.beschreibung, E.id_einsatzcode, EC.code, E.titel, E.kurzbeschreibung, E.adresse, E.zeit FROM Einsaetze E INNER JOIN Einsatzcodes EC ON E.id_einsatzcode = EC.id INNER JOIN Einsatzarten EA on E.id_einsatzart = EA.id WHERE E.id = :1";
module.exports.EINSATZ_GET_BY_TITEL = "SELECT E.id, E.id_einsatzart, EA.beschreibung, E.id_einsatzcode, EC.code, E.titel, E.kurzbeschreibung, E.adresse, E.zeit FROM Einsaetze E INNER JOIN Einsatzcodes EC ON E.id_einsatzcode = EC.id INNER JOIN Einsatzarten EA on E.id_einsatzart = EA.id WHERE E.id = :1 WHERE E.titel = :1";
module.exports.EINSATZ_GET_MITGLIEDER = "SELECT Mitglieder.id, id_dienstgrad, MTG_WB_EINSATZ.id_einsatz, Mitglieder.ID_STUETZPUNKT, kuerzel, bezeichnung, vorname, nachname, username, password, isAdmin FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id=Mitglieder.id_dienstgrad INNER JOIN MTG_WB_EINSATZ ON MTG_WB_EINSATZ.ID_STUETZPUNKT = Mitglieder.ID_STUETZPUNKT WHERE MTG_WB_EINSATZ.ID_EINSATZ = :1 AND MTG_WB_EINSATZ.ID_MITGLIED = Mitglieder.id";
module.exports.EINSATZ_GET_FAHRZEUGE = "SELECT EF.ID, EF.BEZEICHNUNG, EF.ID_STUETZPUNKT, FZWBE.ID_EINSATZ FROM EINSATZFAHRZEUGE EF INNER JOIN FZG_wb_Einsatz FZWBE ON EF.ID_STUETZPUNKT = FZWBE.ID_STUETZPUNKT WHERE FZWBE.ID_EINSATZ = :1 AND EF.ID = FZWBE.ID_EINSATZFAHRZEUG ORDER BY EF.BEZEICHNUNG DESC";
module.exports.EINSATZ_GET_AORGS = "SELECT AORG.ID, AORG.name, AORGWBE.ID_EINSATZ FROM ANDERE_ORGANISATIONEN AORG INNER JOIN AORG_WB_EINSATZ AORGWBE ON AORG.ID = AORGWBE.ID_ANDERE_ORG WHERE AORGWBE.ID_EINSATZ = :1 AND AORGWBE.ID_ANDERE_ORG = AORG.ID ORDER BY AORG.name DESC";
module.exports.EINSATZ_POST_AORG = "INSERT INTO AORG_WB_EINSATZ(ID_EINSATZ, ID_ANDERE_ORG)VALUES(:1, :2)";
module.exports.EINSATZ_POST_FAHRZEUG = "INSERT INTO FZG_WB_EINSATZ(ID_EINSATZ, ID_STUETZPUNKT, ID_EINSATZFAHRZEUG)VALUES(:1, :2, :3)";
module.exports.EINSATZ_POST_EINSATZKRAFT = "INSERT INTO EKraft_wb_Einsatz(id_einsatz, id_stuetzpunkt) VALUES(:1, :2)";
module.exports.EINSATZ_POST_MITGLIED = "INSERT INTO Mtg_wb_Einsatz(id_einsatz, id_stuetzpunkt, id_mitglied) VALUES (:1, :2, :3)";

module.exports.EINSATZ_POST = "INSERT INTO Einsaetze(id, id_einsatzcode, id_einsatzart, titel, kurzbeschreibung, adresse, plz, zeit) VALUES(seq_Einsaetze.nextval, :1, :2, :3, :4, :5, :6,TO_TIMESTAMP(:7, 'YYYY-MM-DD HH24:MI:SS'));"

/* IS support */
//module.exports.FZG_GET = "SELECT * FROM Einsatzfahrzeuge ORDER BY bezeichnung";
module.exports.FZG_GET = "SELECT E.ID, E.BEZEICHNUNG, E.ID_STUETZPUNKT, NVL(FWBE.ID_EINSATZ, '0') AS ID_EINSATZ FROM Einsatzfahrzeuge E LEFT JOIN FZG_wb_Einsatz FWBE ON E.ID = FWBE.ID_EINSATZFAHRZEUG ORDER BY bezeichnung";