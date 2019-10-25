'use strict';

/* ************************************************************************* */
/*                             stuetzpunkt-router.js                         */
/*                                                                           */
/*  HTTP Endpoints for the stuetzpunkt - REST API                            */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     | /stuetzpunkte                                                  */
/*  GET     | /stuetzpunkte/:stuetzId                                        */
/*  POST    | /stuetzpunkte                                                  */
/*  PUT     | /stuetzpunkte/:stuetzId                                        */
/*  DELETE  | /stuetzpunkte/:stuetzId                                        */
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder                             */
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */
/*  POST    | /stuetzpunkte/:stuetzId/mitglieder                             */
/*  PUT     | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */
/*  DELETE  | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
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

/* local variables */
const stuetzpunktRouter = express.Router();

// GET    | /stuetzpunkte
stuetzpunktRouter.get('/', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.STPNKT_GET, [], responseHandler.GET_DEFAULT(req, res));
});

// GET    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.get('/:stuetzId', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, [parseInt(req.params.stuetzId)], responseHandler.GET_DEFAULT(req, res));
});

// POST   | /stuetzpunkte
stuetzpunktRouter.post('/', (req, res) => {
    let params = [req.body.name, req.body.ort, parseInt(req.body.plz), req.body.strasse, req.body.hausnr];
    oracleJobs.execute(oracleQueryProvider.STPNKT_POST, params, responseHandler.POST_DEFAULT(res, oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, req.body.name));
});

// PUT    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.put('/:stuetzId', (req, res) => {
    let params = [req.body.name, req.body.ort, parseInt(req.body.plz), req.body.strasse, req.body.hausnr, req.params.stuetzId];
    oracleJobs.execute(oracleQueryProvider.STPNKT_POST, params, responseHandler.PUT_DEFAULT(res, oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, parseInt(req.params.stuetzId)));
});

// DELETE | /stuetzpunkte/:stuetzId
stuetzpunktRouter.delete('/:stuetzId', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.STPNKT_POST, [parseInt(req.params.stuetzId)], responseHandler.DELETE_DEFAULT(res));

});

// GET    | /stuetzpunkte/:stuetzId/mitglieder
stuetzpunktRouter.get('/:stuetzId/mitglieder', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    | /stuetzpunkte/:stuetzId/mitglieder/:mitglId
stuetzpunktRouter.get('/:stuetzId/mitglieder/:mitglId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   | /stuetzpunkte/:stuetzId/mitglieder
stuetzpunktRouter.post('/:stuetzId/mitglieder', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    | /stuetzpunkte/:stuetzId/mitglieder/:mitglId
stuetzpunktRouter.put('/:stuetzId/mitglieder/:mitglId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE | /stuetzpunkte/:stuetzId/mitglieder/:mitglId
stuetzpunktRouter.delete('/:stuetzId/mitglieder/:mitglId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge
stuetzpunktRouter.get('/:stuetzId/fahrzeuge', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.get('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   | /stuetzpunkte/:stuetzId/fahrzeuge
stuetzpunktRouter.post('/:stuetzId/fahrzeuge', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.put('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.delete('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

/* exports */
module.exports = stuetzpunktRouter;