'use strict';

/**
 * 
 * These parsers support the Client by parsing the variable names into the wanted formats
 * 
 */

String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

var parseStuetzpunkt = function (toParse) {
    toParse = toParse.replaceAll('"ID"', '"baseId"');
    toParse = toParse.replaceAll('"NAME"', '"name"');
    toParse = toParse.replaceAll('"ORT"', '"place"');
    toParse = toParse.replaceAll('"PLZ"', '"postCode"');
    toParse = toParse.replaceAll('"STRASSE"', '"street"');
    toParse = toParse.replaceAll('"HAUSNR"', '"houseNr"');

    return toParse;
}

var parseEinsatzcode = function (toParse) {
    toParse = toParse.replaceAll('"ID"', '"operationCodeId"');
    toParse = toParse.replaceAll('"CODE"', '"code"');

    return toParse;
}

var parseEinsatzart = function (toParse) {
    toParse = toParse.replaceAll('"ID"', '"operationTypeId"');
    toParse = toParse.replaceAll('"BESCHREIBUNG"', '"description"');

    return toParse;
}

var parseDienstgrad = function (toParse) {
    toParse = toParse.replaceAll('"ID"', '"id"');
    toParse = toParse.replaceAll('"KUERZEL"', '"contraction"');
    toParse = toParse.replaceAll('"BEZEICHNUNG"', '"description"');

    return toParse;
}

var parseAOrg = function (toParse) {
    toParse = toParse.replaceAll('"ID"', '"otherOrganisationId"');
    toParse = toParse.replaceAll('"NAME"', '"name"');

    return toParse;
}

var parseMitglied = function (toParse) {
    toParse = toParse.replaceAll('"ID"', '"memberId"');
    toParse = toParse.replaceAll('"ID_DIENSTGRAD"', '"rankId"');
    toParse = toParse.replaceAll('"ID_STUETZPUNKT"', '"baseId"');
    toParse = toParse.replaceAll('"VORNAME"', '"firstname"');
    toParse = toParse.replaceAll('"NACHNAME"', '"lastname"');
    toParse = toParse.replaceAll('"USERNAME"', '"username"');
    toParse = toParse.replaceAll('"ISADMIN"', '"isAdmin"');

    return toParse;
}

module.exports.parseStuetzpunkt = parseStuetzpunkt;
module.exports.parseEinsatzcode = parseEinsatzcode;
module.exports.parseEinsatzart = parseEinsatzart;
module.exports.parseDienstgrad = parseDienstgrad;
module.exports.parseAOrg = parseAOrg;
module.exports.parseMitglied = parseMitglied;