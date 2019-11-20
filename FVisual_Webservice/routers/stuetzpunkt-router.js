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
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge?id=fzgId                     */
/*  POST    | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  PUT     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*  DELETE  | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*                                                                           */
/* ************************************************************************* */

/* node modules */
const express = require('express');
const log4js = require('log4js');

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const oracleQueryProvider = require('../database/oracle-query-provider');
const responseHandler = require('../modules/response-handler');
const classNameParser = require('../modules/classname-parser');
const loggerModule = require('../modules/logger-module');

/* local variables */
const stuetzpunktRouter = express.Router();
const logger = loggerModule.loggers['Routing'];

// GET    | /stuetzpunkte
// GET    | /stuetzpunkte?id=stuetzId
// GET    | /stuetzpunkte?name=stuetzName
stuetzpunktRouter.get('/', (req, res) => {
    if (req.query.name) {
        logger.debug('GET /stuetzpunkte?name=stuetzName');
        oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, [req.query.name], responseHandler.GET_DEFAULT(res, classNameParser.parseStuetzpunkt));
    } else if (req.query.id) {
        logger.debug('GET /stuetzpunkte?name=stuetzId');
        oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, [parseInt(req.query.id)], responseHandler.GET_DEFAULT(res, classNameParser.parseStuetzpunkt));
    } else {
        logger.debug('GET /stuetzpunkte');
        oracleJobs.execute(oracleQueryProvider.STPNKT_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseStuetzpunkt));
    }
});

// POST   | /stuetzpunkte
stuetzpunktRouter.post('/', (req, res) => {
    logger.debug('POST /stuetzpunkte');
    let params = [req.body.name, req.body.ort, parseInt(req.body.plz), req.body.strasse, req.body.hausnr];
    oracleJobs.execute(oracleQueryProvider.STPNKT_POST, params, responseHandler.POST_DEFAULT(res, oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, [req.body.name], classNameParser.parseStuetzpunkt));
});

// PUT    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.put('/:stuetzId', (req, res) => {
    logger.debug('PUT /stuetzpunkte');
    let params = [req.body.name, req.body.ort, parseInt(req.body.plz), req.body.strasse, req.body.hausnr, req.params.stuetzId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_PUT, params, responseHandler.PUT_DEFAULT(res, oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, [parseInt(req.params.stuetzId)], classNameParser.parseStuetzpunkt));
});

// DELETE | /stuetzpunkte/:stuetzId
stuetzpunktRouter.delete('/:stuetzId', (req, res) => {
    logger.debug('DELETE /stuetzpunkte');
    oracleJobs.execute(oracleQueryProvider.STPNKT_DELETE, [parseInt(req.params.stuetzId)], responseHandler.DELETE_DEFAULT(res));

});

// GET    | /stuetzpunkte/:stuetzId/mitglieder
// GET    | /stuetzpunkte/:stuetzId/mitglieder?id=mitglId
stuetzpunktRouter.get('/:stuetzId/mitglieder', (req, res) => {
    if (req.query.id) {
        logger.debug('GET /stuetzpunkte/:stuetzId/mitglieder?id=mitglId');
        oracleJobs.execute(oracleQueryProvider.STPNKT_MTG_GET_BY_MTG_ID, [req.params.stuetzId, parseInt(req.query.id)], responseHandler.GET_DEFAULT(res, classNameParser.parseMitglied));
    } else {
        logger.debug('GET /stuetzpunkte/:stuetzId/mitglieder');
        oracleJobs.execute(oracleQueryProvider.STPNKT_MTG_GET, [req.params.stuetzId], responseHandler.GET_DEFAULT(res, classNameParser.parseMitglied));
    }
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge
// GET    | /stuetzpunkte/:stuetzId/fahrzeuge?id=fzgId
stuetzpunktRouter.get('/:stuetzId/fahrzeuge', (req, res) => {
    if (req.query.id) {
        logger.debug('GET /stuetzpunkte/:stuetzId/fahrzeuge?id=fzgId');
        oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_ID, [req.params.stuetzId, parseInt(req.query.id)], responseHandler.GET_DEFAULT(res, classNameParser.parseFahrzeug));
    } else {
        logger.debug('GET /stuetzpunkte/:stuetzId/fahrzeuge');
        oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET, [req.params.stuetzId], responseHandler.GET_DEFAULT(res, classNameParser.parseFahrzeug));
    }
});

// POST   | /stuetzpunkte/:stuetzId/fahrzeuge
stuetzpunktRouter.post('/:stuetzId/fahrzeuge', (req, res) => {
    logger.debug('POST /stuetzpunkte/:stuetzId/fahrzeuge');
    let params = [req.params.stuetzId, req.body.bezeichnung];
    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_POST, params, responseHandler.POST_DEFAULT(res, oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_NAME, [req.params.stuetzId, req.body.bezeichnung], classNameParser.parseFahrzeug));
});

// PUT    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.put('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('PUT /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId');
    let params = [req.body.bezeichnung, req.params.stuetzId, req.params.fzgId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_PUT, params, responseHandler.PUT_DEFAULT(res, oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_ID, [req.params.stuetzId, req.params.fzgId], classNameParser.parseFahrzeug));
});

// DELETE | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.delete('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('DELETE /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId');
    let params = [req.params.stuetzId, req.params.fzgId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_DELETE, params, responseHandler.DELETE_DEFAULT(res));
});

/* exports */
module.exports = stuetzpunktRouter;