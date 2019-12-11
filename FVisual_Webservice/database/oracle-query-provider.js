'use strict';

/* ALL QUERYSTRINGS FOR DB */

module.exports.EARTEN_GET = "SELECT id, beschreibung FROM Einsatzarten ORDER BY id";
module.exports.ECODES_GET = "SELECT id, code FROM Einsatzcodes ORDER BY id";
module.exports.DG_GET = "SELECT id, kuerzel, bezeichnung FROM Dienstgrade ORDER BY id";

module.exports.AORGS_GET = "SELECT id, name FROM Andere_Organisationen ORDER BY id";
module.exports.AORGS_GETBY_AORGS_ID = "SELECT id, name FROM Andere_Organisationen WHERE id = :1";
module.exports.AORGS_GETBY_AORGS_NAME = "SELECT id, name FROM Andere_Organisationen WHERE name = :1";
module.exports.AORGS_POST = "INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, :1)";
module.exports.AORGS_PUT = "UPDATE Andere_Organisationen SET name = :1 WHERE id = :2";
module.exports.AORGS_DELETE = "DELETE FROM Andere_Organisationen WHERE id = :1";

module.exports.STPNKT_GET = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte ORDER BY name";
module.exports.STPNKT_GETBY_STPNKT_ID = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE id = :1";
module.exports.STPNKT_GETBY_STPNKT_NAME = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE name = :1";
module.exports.STPNKT_POST = "INSERT INTO Stuetzpunkte(id, name, ort, plz, strasse, hausnr) VALUES(seq_Stuetzpunkte.nextval, :1, :2, :3,  :4, :5)";
module.exports.STPNKT_PUT = "UPDATE Stuetzpunkte SET name= :1, ort= :2, plz = :3, strasse = :4, hausnr = :5 WHERE id = :6";
module.exports.STPNKT_DELETE = "DELETE FROM Stuetzpunkte WHERE id = :1";

module.exports.MGT_GET = "SELECT Mitglieder.id, kuerzel, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder INNER JOIN Dienstgrade ON Dienstgrade.id=Mitglieder.id_dienstgrad ORDER BY isAdmin DESC, username ASC";
module.exports.MTG_GET_BY_USERNAME = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder WHERE username =: 1 ";
module.exports.ADMINS_GET = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder WHERE isAdmin = 'true'";
module.exports.MTG_GET_BASELESS = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder WHERE id_stuetzpunkt = -1";

module.exports.STPNKT_MTG_GET = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname FROM Mitglieder WHERE id_stuetzpunkt = :1 ORDER BY id_dienstgrad, nachname";
module.exports.STPNKT_MTG_GET_BY_MTG_ID = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname FROM Mitglieder WHERE id_stuetzpunkt = :1 AND id=:2";

module.exports.STPNKT_FZG_GET = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 ORDER BY bezeichnung";
module.exports.STPNKT_FZG_GET_BY_FZG_ID = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND id = :2";
module.exports.STPNKT_FZG_GET_BY_FZG_NAME = "SELECT id, id_stuetzpunkt, bezeichnung FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND bezeichnung = :2"
module.exports.STPNKT_FZG_POST = "INSERT INTO Einsatzfahrzeuge(id, id_stuetzpunkt, bezeichnung) VALUES(seq_Einsatzfahrzeuge.nextval, :1, :2)";
module.exports.STPNKT_FZG_PUT = "UPDATE Einsatzfahrzeuge SET bezeichnung = :1 WHERE id_stuetzpunkt = :2 AND id = :3";
module.exports.STPNKT_FZG_DELETE = "DELETE FROM Einsatzfahrzeuge WHERE id_stuetzpunkt = :1 AND id = :2";

module.exports.EINSATZ_GET = "SELECT E.id, E.id_einsatzart, EA.beschreibung, E.id_einsatzcode, EC.code, E.titel, E.kurzbeschreibung, E.adresse, E.datum FROM Einsaetze E INNER JOIN Einsatzcodes EC ON E.id_einsatzcode = EC.id INNER JOIN Einsatzarten EA on E.id_einsatzart = EA.id";
module.exports.EINSATZ_GET_BY_ID = "";
module.exports.EINSATZ_GET_BY_NAME_ZEIT = "";

/* IS support */
module.exports.FZG_GET_GROUPED = "SELECT bezeichnung FROM Einsatzfahrzeuge GROUP BY bezeichnung ORDER BY bezeichnung";
