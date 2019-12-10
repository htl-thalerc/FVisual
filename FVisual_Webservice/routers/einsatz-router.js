'use strict';

/* ************************************************************************* */
/*                             einsatz-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the einsatz - REST API                                */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /einsaetze                                                    */
/*  GET     |  /einsaetze?id=eId                                             */
/*  GET     |  /einsaetze?id=eName&eZeit                                     */
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
const classNameParser = require('../modules/classname-parser');
const loggerModule = require('../modules/logger-module');
const validatorModule = require('../modules/validator-module');

/* local variables */
const einsatzRouter = express.Router();
const logger = loggerModule.loggers['Routing'];
// GET     |  /einsaetze
// GET     |  /einsaetze?id=eId
// GET     |  /einsaetze?id=eName&eZeit
einsatzRouter.get('/', (req, res) => {
  logger.debug('GET /einsaetze');
  oracleJobs.execute(oracleQueryProvider.EINSATZ_GET, [], responseHandler.GET_DEFAULT(res, classNameParser.parseEinsatz));
});

// POST   |  /einsaetze
einsatzRouter.get('/', (req, res) => {
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
  res.status(501).send('not implemented yet');
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
einsatzRouter.get('/:eId/fahrzeuge/', (req, res) => {
  res.status(501).send('not implemented yet');
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
  res.status(501).send('not implemented yet');
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