'use strict';

/* ************************************************************************* */
/*                             stuetzpunkt-router.js                         */
/*                                                                           */
/*  HTTP Endpoints for the stuetzpunkt - REST API                            */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     | /stuetzpunkte                                                  */
/*  GET     | /stuetzpunkte?id=stuetzId                                      */
/*  GET     | /stuetzpunkte?name=stuetzName                                  */
/*  POST    | /stuetzpunkte                                                  */
/*  PUT     | /stuetzpunkte/:stuetzId                                        */
/*  DELETE  | /stuetzpunkte/:stuetzId                                        */
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder                             */
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder?id=mitglId                  */
/*  POST    | /stuetzpunkte/:stuetzId/mitglieder                             */
/*  PUT     | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */
/*  DELETE  | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge?id=fzgId                     */
/*  POST    | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  PUT     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*  DELETE  | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*                                                                           */
/* ************************************************************************* */

/* node modules */
const express = require('express');

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const oracleQueryProvider = require('../database/oracle-query-provider');
const responseHandler = require('../modules/response-handler');
const classNameParser = require('../modules/classname-parser');

/* local variables */
const stuetzpunktRouter = express.Router();

// GET    | /stuetzpunkte
// GET    | /stuetzpunkte?id=stuetzId
// GET    | /stuetzpunkte?name=stuetzName
stuetzpunktRouter.get('/', (req, res) => {
    if (req.query.name)
        oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, [req.query.name], responseHandler.GET_DEFAULT(res, classNameParser.parseStuetzpunkt));
    else if (req.query.id)
        oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, [parseInt(req.query.id)], responseHandler.GET_DEFAULT(res, classNameParser.parseStuetzpunkt));
    else
        oracleJobs.execute(oracleQueryProvider.STPNKT_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseStuetzpunkt));
});

// POST   | /stuetzpunkte
stuetzpunktRouter.post('/', (req, res) => {
    let params = [req.body.name, req.body.ort, parseInt(req.body.plz), req.body.strasse, req.body.hausnr];
    oracleJobs.execute(oracleQueryProvider.STPNKT_POST, params, responseHandler.POST_DEFAULT(res, oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, [req.body.name], classNameParser.parseStuetzpunkt));
});

// PUT    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.put('/:stuetzId', (req, res) => {
    let params = [req.body.name, req.body.ort, parseInt(req.body.plz), req.body.strasse, req.body.hausnr, req.params.stuetzId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_PUT, params, responseHandler.PUT_DEFAULT(res, oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, [parseInt(req.params.stuetzId)], classNameParser.parseStuetzpunkt));
});

// DELETE | /stuetzpunkte/:stuetzId
stuetzpunktRouter.delete('/:stuetzId', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.STPNKT_DELETE, [parseInt(req.params.stuetzId)], responseHandler.DELETE_DEFAULT(res));

});

// GET    | /stuetzpunkte/:stuetzId/mitglieder
// GET    | /stuetzpunkte/:stuetzId/mitglieder?id=mitglId
stuetzpunktRouter.get('/:stuetzId/mitglieder', (req, res) => {
    if (req.query.id)
        oracleJobs.execute(oracleQueryProvider.STPNKT_MTG_GET_BY_MTG_ID, [req.params.stuetzId, parseInt(req.query.id)], responseHandler.GET_DEFAULT(res, classNameParser.parseMitglied));
    else
        oracleJobs.execute(oracleQueryProvider.STPNKT_MTG_GET, [req.params.stuetzId], responseHandler.GET_DEFAULT(res, classNameParser.parseMitglied));
});

// POST   | /stuetzpunkte/:stuetzId/mitglieder
stuetzpunktRouter.post('/:stuetzId/mitglieder', (req, res) => {

});

// UPDATE Mitglieder SET id_dienstgrad = :1, id_stuetzpunkt = :2, vorname = :3, nachname = :4;
// UPDATE Mitglieder SET password = :1 WHERE id = :2 AND id_stuetzpunkt = :3;
// UPDATE Mitglieder SET id = :1, id_dienstgrad = :1, id_stuetzpunkt = :1, vorname = :1, nachname = :1, isAdmin = :1 WHERE id = :2 AND id_stuetzpunkt = :3;

// PUT    | /stuetzpunkte/:stuetzId/mitglieder/:mitglId
stuetzpunktRouter.put('/:stuetzId/mitglieder/:mitglId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE | /stuetzpunkte/:stuetzId/mitglieder/:mitglId
stuetzpunktRouter.delete('/:stuetzId/mitglieder/:mitglId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge
// GET    | /stuetzpunkte/:stuetzId/fahrzeuge?id=fzgId
stuetzpunktRouter.get('/:stuetzId/fahrzeuge', (req, res) => {
    if (req.query.id)
        oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_ID, [req.params.stuetzId, parseInt(req.query.id)], responseHandler.GET_DEFAULT(res, classNameParser.parseFahrzeug));
    else
        oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET, [req.params.stuetzId], responseHandler.GET_DEFAULT(res, classNameParser.parseFahrzeug));
});

// POST   | /stuetzpunkte/:stuetzId/fahrzeuge
stuetzpunktRouter.post('/:stuetzId/fahrzeuge', (req, res) => {
    let params = [req.params.stuetzId, req.body.bezeichnung];
    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_POST, params, responseHandler.POST_DEFAULT(res, oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_NAME, [req.params.stuetzId, req.body.bezeichnung], classNameParser.parseFahrzeug));
});

// PUT    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.put('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    let params = [req.body.bezeichnung, req.params.stuetzId, req.params.fzgId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_PUT, params, responseHandler.PUT_DEFAULT(res, oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_ID, [req.params.stuetzId, req.params.fzgId], classNameParser.parseFahrzeug));
});

// DELETE | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.delete('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    let params = [req.params.stuetzId, req.params.fzgId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_DELETE, params, responseHandler.DELETE_DEFAULT(res));
});

/* exports */
module.exports = stuetzpunktRouter;