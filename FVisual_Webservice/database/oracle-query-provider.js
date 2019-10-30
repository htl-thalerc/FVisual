'use strict';

/* ALL QUERYSTRINGS FOR DB */

module.exports.EARTEN_GET = "SELECT id, beschreibung FROM Einsatzarten ORDER BY id";
module.exports.ECODES_GET = "SELECT id, code FROM Einsatzcodes ORDER BY id";
module.exports.DG_GET = "SELECT id, kuerzel, bezeichnung FROM Dienstgrade ORDER BY id";

module.exports.AORGS_GET = "SELECT id, name FROM Andere_Organisationen";
module.exports.AORGS_GETBY_AORGS_ID = "SELECT id, name FROM Andere_Organisationen WHERE id = :1";
module.exports.AORGS_GETBY_AORGS_NAME = "SELECT id, name FROM Andere_Organisationen WHERE name = :1";
module.exports.AORGS_POST = "INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, :1)";
module.exports.AORGS_PUT = "UPDATE Andere_Organisationen SET name = :1 WHERE id = :2";
module.exports.AORGS_DELETE = "DELETE FROM Andere_Organisationen WHERE id = :1";

module.exports.STPNKT_GET = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte";
module.exports.STPNKT_GETBY_STPNKT_ID = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE id = :1";
module.exports.STPNKT_GETBY_STPNKT_NAME = "SELECT id, name, ort, plz, strasse, hausnr FROM Stuetzpunkte WHERE name = :1";
module.exports.STPNKT_POST = "INSERT INTO Stuetzpunkte(id, name, ort, plz, strasse, hausnr) VALUES(seq_Stuetzpunkte.nextval, :1, :2, :3,  :4, :5)";
module.exports.STPNKT_PUT = "UPDATE Stuetzpunkte SET name= :1, ort= :2, plz = :3, strasse = :4, hausnr = :5 WHERE id = :6";
module.exports.STPNKT_DELETE = "DELETE FROM Stuetzpunkte WHERE id = :1";

module.exports.MTG_GET_BY_USERNAME = "SELECT id, id_dienstgrad, id_stuetzpunkt, vorname, nachname, username, isAdmin FROM Mitglieder WHERE username =: 1 ";
module.exports.MTG_GETBY_STPNKT_ID = "";
module.exports.MTG_GETBY_MTG_ID = "";
module.exports.MTG_POST = "";
module.exports.MTG_PUT = "";
module.exports.MTG_DELETE = "";

module.exports.EFZG_GETBY_STPNKT_ID = "";
module.exports.EFZG_GETBY_EFZG_ID_AND_STPNKT_ID = "";
module.exports.EFZG_POST = "";
module.exports.EFZG_PUT = "";
module.exports.EFZG_DELETE = "";