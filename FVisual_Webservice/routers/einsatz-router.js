/* ************************************************************************* */
/*                             einsatz-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the einsatz - REST API                                */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /einsaetze                                                    */
/*  GET     |  /einsaetze?                                                   */
/*  GET     |  /einsaetze/:eId                                               */
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

'use strict';

const express = require('express');
const einsatzRouter = express.Router();


/**
 * GET    |  /einsaetze
 * GET    |  /einsaetze?
 */
einsatzRouter.get('/', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    |  /einsaetze/:eId
einsatzRouter.get('/:eId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   |  /einsaetze
einsatzRouter.get('/', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    |  /einsaetze/:eId
einsatzRouter.put('/:eId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE |  /einsaetze/:eId
einsatzRouter.delete('/:eId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    |  /einsaetze/:eId/mitglieder
einsatzRouter.get('/:eId/mitglieder', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   |  /einsaetze/:eId/mitglieder
einsatzRouter.post('/:eId/mitglieder', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    |  /einsaetze/:eId/mitglieder/:mgtId
einsatzRouter.put('/:eId/mitglieder/:mgtId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE |  /einsaetze/:eId/mitglieder/:mgtId
einsatzRouter.delete('/:eId/mitglieder/:mgtId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    |  /einsaetze/:eId/fahrzeuge
einsatzRouter.get('/:eId/fahrzeuge/', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   |  /einsaetze/:eId/fahrzeuge
einsatzRouter.post('/:eId/fahrzeuge', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    |  /einsaetze/:eId/fahrzeuge/:fzgId
einsatzRouter.put('/:eId/fahrzeuge/:mgtId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE |  /einsaetze/:eId/fahrzeuge/:fzgId
einsatzRouter.delete('/:eId/fahrzeuge/:mgtId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    |  /einsaetze/:eId/andere_organisationen
einsatzRouter.get('/:eId/andere_organisationen', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   |  /einsaetze/:eId/andere_organisationen
einsatzRouter.post('/:eId/andere_organisationen', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    |  /einsaetze/:eId/andere_organisationen/:aOrgId
einsatzRouter.put('/:eId/andere_organisationen/:aOrgId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE |  /einsaetze/:eId/andere_organisationen/:aOrgId
einsatzRouter.delete('/:eId/andere_organisationen/:aOrgId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

module.exports = einsatzRouter;