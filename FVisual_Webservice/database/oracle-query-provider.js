'use strict';

/*ALL QUERYSTRINGS FOR DB*/

module.exports.EARTEN_GET = "SELECT id, beschreibung FROM Einsatzarten";
module.exports.ECODES_GET = "SELECT id, code FROM Einsatzcodes";
module.exports.DG_GET = "SELECT id, kuerzel, bezeichnung FROM Dienstgrade";

module.exports.AORGS_GET = "SELECT id, name FROM Andere_Organisationen";
module.exports.AORGS_GETBY_AORGS_ID = "";
module.exports.AORGS_GETBY_AORGS_NAME = "SELECT id, name FROM Andere_Organisationen WHERE name = :1";
module.exports.AORGS_POST = "INSERT INTO Andere_Organisationen(id, name) VALUES(seq_Andere_Organisationen.nextval, :1)";
module.exports.AORGS_PUT = "UPDATE Andere_Organisationen SET name = :2 WHERE id = :1";
module.exports.AORGS_DELETE = "DELETE FROM Andere_Organisationen WHERE id = :1";

module.exports.STPNKT_GET = "";
module.exports.STPNKT_GETBY_STPNKT_ID = "";
module.exports.STPNKT_POST = "";
module.exports.STPNKT_PUT = "";
module.exports.STPNKT_DELETE = "";

module.exports.MTGA_ADMIN_GET = "";
module.exports.MTGA_ADMIN_GET_BY_MTGA_ID = "";
module.exports.MTGA_ADMIN_GET_BY_MTGA_ACC_SEARCH = "";
module.exports.MTGA_ADMIN_POST = "";
module.exports.MTGA_ADMIN_PUT = "";
module.exports.MTGA_ADMIN_DELETE = "";

module.exports.MTG_GETBY_STPNKT_ID = "";
module.exports.MTG_GETBY_MTG_ID_AND_STPNKT_ID = "";
module.exports.MTG_POST = "";
module.exports.MTG_PUT = "";
module.exports.MTG_DELETE = "";

module.exports.EFZG_GETBY_STPNKT_ID = "";
module.exports.EFZG_GETBY_EFZG_ID_AND_STPNKT_ID = "";
module.exports.EFZG_POST = "";
module.exports.EFZG_PUT = "";
module.exports.EFZG_DELETE = "";