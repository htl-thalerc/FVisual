'use strict'

/* ************************************************************************* */
/*                                base-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the base - REST API                                   */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /einsatzarten                                                 */
/*  GET     |  /einsatzcodes                                                 */
/*  GET     |  /dienstgrade                                                  */
/*  GET     |  /andere_organisationen                                        */
/*  POST    |  /andere_organisationen                                        */
/*  PUT     |  /andere_organisationen/:aOrgId                                */
/*  DELETE  |  /andere_organisationen/:aOrgId                                */
/*  GET     |  /mitglieder/:username                                         */
/*  GET     |  /admins                                                       */
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
const baseRoutes = express.Router();

// GET    | /einsatzarten
baseRoutes.get('/einsatzarten', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.EARTEN_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseEinsatzart));
});

// GET    | /einsatzcodes
baseRoutes.get('/einsatzcodes', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.ECODES_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseEinsatzcode));
});

// GET    | /dienstgrade
baseRoutes.get('/dienstgrade', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.DG_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseDienstgrad));
});

// GET    | /andere_organisationen
baseRoutes.get('/andere_organisationen', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.AORGS_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseAOrg));
});

// POST   | /andere_organisationen
baseRoutes.post('/andere_organisationen', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.AORGS_POST, [req.body.name], responseHandler.POST_DEFAULT(res, oracleQueryProvider.AORGS_GETBY_AORGS_NAME, [req.body.name]));
});

// PUT    | /andere_organisationen/:aOrgId
baseRoutes.put('/andere_organisationen/:aOrgId', (req, res) => {
  console.log(parseInt(req.params.aOrgId));
  oracleJobs.execute(oracleQueryProvider.AORGS_PUT, [req.body.name, parseInt(req.params.aOrgId)], responseHandler.PUT_DEFAULT(res, oracleQueryProvider.AORGS_GETBY_AORGS_ID, [parseInt(req.params.aOrgId)]));
});

// DELETE | /andere_organisationen/:aOrgId
baseRoutes.delete('/andere_organisationen/:aOrgId', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.AORGS_DELETE, [parseInt(req.params.aOrgId)], responseHandler.DELETE_DEFAULT(res));
});

// GET    | /mitglieder/:username
baseRoutes.get('/mitglieder/:username', (req, res) => {
  oracleJobs.execute(oracleQueryProvider.MTG_GET_BY_USERNAME, [req.params.username], responseHandler.GET_DEFAULT(res, classNameParser.parseMitglied));
});

// GET    | /mitglieder/admins
baseRoutes.get('/mitglieder/admins', (req, res) => {
  res.status(200).send('ok');
});

/* exports */
module.exports = baseRoutes;