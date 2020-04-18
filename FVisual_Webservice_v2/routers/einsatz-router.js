'use strict';

/* ************************************************************************* */
/*                             einsatz-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the einsatz - REST API                                */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /einsaetze                                                    */
/*  GET     |  /einsaetze/:Id                                                */
/*  GET     |  /einsaetze?name=eName&zeit=eZeit                              */
/*  POST    |  /einsaetze                                                    */
/*  PUT     |  /einsaetze/:eId                                               */
/*  DELETE  |  /einsaetze/:eId                                               */
/*  GET     |  /einsaetze/:eId/mitglieder                                    */
/*  POST    |  /einsaetze/:eId/mitglieder                                    */
/*  PUT     |  /einsaetze/:eId/mitglieder/:mgtId                             */
/*  DELETE  |  /einsaetze/:eId/mitglieder/:mgtId                             */
/*  GET     |  /einsaetze/:eId/fahrzeuge                                     */
/*  POST    |  /einsaetze/:eId/fahrzeuge                                     */
/*  PUT     |  /einsaetze/:eId/fahrzeuge/:fzgId                              */
/*  DELETE  |  /einsaetze/:eId/fahrzeuge/:fzgId                              */
/*  GET     |  /einsaetze/:eId/andere_organisationen                         */
/*  POST    |  /einsaetze/:eId/andere_organisationen                         */
/*  PUT     |  /einsaetze/:eId/andere_organisationen/:aOrgId                 */
/*  DELETE  |  /einsaetze/:eId/andere_organisationen/:aOrgId                 */
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
const einsatzRouter = express.Router();
const logger = loggerModule.loggers['Routing'];

//  GET     |  /einsaetze
//  GET     |  /einsaetze?name=eName&zeit=eZeit
einsatzRouter.get('/', (req, res) => {

    if (!validatorModule.isValidSubQuery(req.query, validatorModule.patterns.getSubQueryEinsatzPattern(), true)) {
        responseHandler.invalidSubQuery(res, null);
        return;
    } else if (req.query.name && req.query.zeit) {
        logger.debug('-- search: einsatzName&zeit');

        var params = [];
        params.push(req.query.name);
        params.push(req.query.zeit);

        oracleJobs.execute(oracleQueryProvider.EINSATZ_GET_BY_NAME_ZEIT, params, (err, result) => {
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
        oracleJobs.execute(oracleQueryProvider.EINSATZ_GET, [], (err, result) => {
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

//  GET     |  /einsaetze/:eId
einsatzRouter.get('/:eId', (req, res) => {
    logger.debug('GET /einsaetze/:eId');

    if (!validatorModule.isValidParamId(req.params.eId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.eId));

    oracleJobs.execute(oracleQueryProvider.EINSATZ_GET_BY_ID, [req.params.eId], (err, result) => {
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

// POST   |  /einsaetze
einsatzRouter.post('/', (req, res) => {
    res.status(501).send('not implemented yet');
});

// PUT    |  /einsaetze/:eId
einsatzRouter.put('/:eId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// DELETE |  /einsaetze/:eId
einsatzRouter.delete('/:eId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// GET    |  /einsaetze/:eId/mitglieder
einsatzRouter.get('/:eId/mitglieder', (req, res) => {
    logger.debug('GET /:eId/mitglieder');

    if (!validatorModule.isValidParamId(req.params.eId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.eId));

    oracleJobs.execute(oracleQueryProvider.EINSATZ_GET_MITGLIEDER, [req.params.eId], (err, result) => {
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

// POST   |  /einsaetze/:eId/mitglieder
einsatzRouter.post('/:eId/mitglieder', (req, res) => {
    res.status(501).send('not implemented yet');
});

// PUT    |  /einsaetze/:eId/mitglieder/:mgtId
einsatzRouter.put('/:eId/mitglieder/:mgtId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// DELETE |  /einsaetze/:eId/mitglieder/:mgtId
einsatzRouter.delete('/:eId/mitglieder/:mgtId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// GET    |  /einsaetze/:eId/fahrzeuge
einsatzRouter.get('/:eId/fahrzeuge', (req, res) => {
    logger.debug('GET /:eId/fahrzeuge');

    if (!validatorModule.isValidParamId(req.params.eId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.eId));

    oracleJobs.execute(oracleQueryProvider.EINSATZ_GET_FAHRZEUGE, [req.params.eId], (err, result) => {
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

// POST   |  /einsaetze/:eId/fahrzeuge
einsatzRouter.post('/:eId/fahrzeuge', (req, res) => {
    res.status(501).send('not implemented yet');
});

// PUT    |  /einsaetze/:eId/fahrzeuge/:fzgId
einsatzRouter.put('/:eId/fahrzeuge/:mgtId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// DELETE |  /einsaetze/:eId/fahrzeuge/:fzgId
einsatzRouter.delete('/:eId/fahrzeuge/:mgtId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// GET    |  /einsaetze/:eId/andere_organisationen
einsatzRouter.get('/:eId/andere_organisationen', (req, res) => {
    logger.debug('GET /:eId/andere_organisationen');

    if (!validatorModule.isValidParamId(req.params.eId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.eId));

    oracleJobs.execute(oracleQueryProvider.EINSATZ_GET_AORGS, [req.params.eId], (err, result) => {
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

// POST   |  /einsaetze/:eId/andere_organisationen
einsatzRouter.post('/:eId/andere_organisationen', (req, res) => {
    res.status(501).send('not implemented yet');
});

// PUT    |  /einsaetze/:eId/andere_organisationen/:aOrgId
einsatzRouter.put('/:eId/andere_organisationen/:aOrgId', (req, res) => {
    res.status(501).send('not implemented yet');
});

// DELETE |  /einsaetze/:eId/andere_organisationen/:aOrgId
einsatzRouter.delete('/:eId/andere_organisationen/:aOrgId', (req, res) => {
    res.status(501).send('not implemented yet');
});

/* exports */
module.exports = einsatzRouter;