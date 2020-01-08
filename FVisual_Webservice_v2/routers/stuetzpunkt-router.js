'use strict';

/* ************************************************************************* */
/*                             stuetzpunkt-router.js                         */
/*                                                                           */
/*  HTTP Endpoints for the stuetzpunkt - REST API                            */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     | /stuetzpunkte                                                  */
/*  GET     | /stuetzpunkte?name=stuetzName                                  */
/*  GET     | /stuetzpunkte/:stuetzId                                        */
/*  POST    | /stuetzpunkte                                                  */ //notImplementedYet
/*  PUT     | /stuetzpunkte/:stuetzId                                        */ //notImplementedYet
/*  DELETE  | /stuetzpunkte/:stuetzId                                        */ //notImplementedYet
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder                             */ //notImplementedYet
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */ //notImplementedYet
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge                              */ //notImplementedYet
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */ //notImplementedYet
/*  POST    | /stuetzpunkte/:stuetzId/fahrzeuge                              */ //notImplementedYet
/*  PUT     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */ //notImplementedYet
/*  DELETE  | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */ //notImplementedYet
/*                                                                           */
/* ************************************************************************* */

/* node modules */
const express = require('express');

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const oracleQueryProvider = require('../database/oracle-query-provider');
const responseHandler = require('../modules/response-handler');
const loggerModule = require('../modules/logger-module');
const converterModule = require('../modules/converter-module');
const validatorModule = require('../modules/validator-module');

/* local variables */
const stuetzpunktRouter = express.Router();
const logger = loggerModule.loggers['Routing'];

// GET    | /stuetzpunkte
// GET    | /stuetzpunkte?name=stuetzName
stuetzpunktRouter.get('/', (req, res) => {
    logger.debug('GET /stuetzpunkte');
    if (!validatorModule.isValidSubQuery(req.query, validatorModule.patterns.getSubQueryNamePattern(), true)) {
        responseHandler.invalidSubQuery(res, null);
        return;
    } else if (req.query.name) {
        logger.debug('-- search: stuetzName');
        oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, [req.query.name], (err, result) => {
            if (err) {
                responseHandler.internalServerError(res, err);
            } else if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        });
    } else {
        logger.debug('-- all');
        oracleJobs.execute(oracleQueryProvider.STPNKT_GET, [], (err, result) => {
            if (err) {
                responseHandler.internalServerError(res, err);
            } else if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        });
    }
});

// GET    | /stuetzpunkte/stuetzId
stuetzpunktRouter.get('/stuetzpunkte/:stuetzId', (req, res) => {
    logger.debug('GET /stuetzpunkte/:stuetzId');
    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
    }
    oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, [parseInt(req.params.stuetzId)], (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rows.length == 0) {
                responseHandler.notFound(res, err);
                return;
            }

            if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        }
    });
});

// POST   | /stuetzpunkte //notImplementedYet
stuetzpunktRouter.post('/', (req, res) => {
    logger.debug('POST /stuetzpunkte');
    responseHandler.notImplementedYet(res, null);
});

// PUT    | /stuetzpunkte/:stuetzId //notImplementedYet
stuetzpunktRouter.put('/:stuetzId', (req, res) => {
    logger.debug('PUT /stuetzpunkte');
    responseHandler.notImplementedYet(res, null);
});

// DELETE | /stuetzpunkte/:stuetzId //notImplementedYet
stuetzpunktRouter.delete('/:stuetzId', (req, res) => {
    logger.debug('DELETE /stuetzpunkte');
    responseHandler.notImplementedYet(res, null);
});

// GET    | /stuetzpunkte/:stuetzId/mitglieder //notImplementedYet
// GET    | /stuetzpunkte/:stuetzId/mitglieder?id=mitglId //notImplementedYet
stuetzpunktRouter.get('/stuetzpunkte/:stuetzId/mitglieder', (req, res) => {
    responseHandler.notImplementedYet(res, null);
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge //notImplementedYet
// GET    | /stuetzpunkte/:stuetzId/fahrzeuge?id=fzgId //notImplementedYet
stuetzpunktRouter.get('/:stuetzId/fahrzeuge', (req, res) => {
    responseHandler.notImplementedYet(res, null);
});

// POST   | /stuetzpunkte/:stuetzId/fahrzeuge //notImplementedYet
stuetzpunktRouter.post('/:stuetzId/fahrzeuge', (req, res) => {
    logger.debug('POST /stuetzpunkte/:stuetzId/fahrzeuge');
    responseHandler.notImplementedYet(res, null);
});

// PUT    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId //notImplementedYet
stuetzpunktRouter.put('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('PUT /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId');
    responseHandler.notImplementedYet(res, null);
});

// DELETE | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId //notImplementedYet
stuetzpunktRouter.delete('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('DELETE /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId');
    responseHandler.notImplementedYet(res, null);
});

/* exports */
module.exports = stuetzpunktRouter;