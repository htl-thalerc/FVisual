'use strict';

/* ************************************************************************* */
/*                                base-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the base - REST API                                   */
/*                                                                           */
/*  Method  |  URL                                                           */
//loginmanagement
//loginandroid
/*  GET     |  /einsatzarten                                                 */
/*  GET     |  /einsatzcodes                                                 */
/*  GET     |  /dienstgrade                                                  */
/*  GET     |  /andere_organisationen                                        */
/*  POST    |  /andere_organisationen                                        */
/*  PUT     |  /andere_organisationen/:aOrgId                                */
/*  DELETE  |  /andere_organisationen/:aOrgId                                */
/*                                                                           */
/* ************************************************************************* */

/* node modules */
const express = require('express');

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const oracleQueryProvider = require('../database/oracle-query-provider');
const responseHandler = require('../modules/response-handler');

/* local variables */
const baseRoutes = express.Router();

// GET    | /einsatzarten
baseRoutes.get('/einsatzarten', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.EARTEN_GET, [], responseHandler.GET_DEFAULT(req, res));
});

// GET    | /einsatzcodes
baseRoutes.get('/einsatzcodes', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.ECODES_GET, [], responseHandler.GET_DEFAULT(req, res));
});

// GET    | /dienstgrade
baseRoutes.get('/dienstgrade', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.DG_GET, [], responseHandler.GET_DEFAULT(req, res));
});

// GET    | /andere_organisationen
baseRoutes.get('/andere_organisationen', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.AORGS_GET, [], responseHandler.GET_DEFAULT(req, res));
});

// POST   | /andere_organisationen
baseRoutes.post('/andere_organisationen', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.AORGS_POST, [req.body.name], responseHandler.POST_DEFAULT(req, res));
});

// PUT    | /andere_organisationen/:aOrgId
baseRoutes.put('/andere_organisationen/:aOrgId', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.AORGS_PUT, [req.params.id, req.body.name], responseHandler.PUT_DEFAULT(req, res));
});

// DELETE | /andere_organisationen/:aOrgId
baseRoutes.delete('/andere_organisationen/:aOrgId', (req, res) => {
    oracleJobs.execute(oracleQueryProvider.AORGS_PUT, [req.params.id], responseHandler.DELETE_DEFAULT(req, res));
});

/* exports */
module.exports = baseRoutes;