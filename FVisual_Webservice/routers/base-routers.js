/* ************************************************************************* */
/*                                base-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the base - REST API                                   */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /einsatzarten                                                 */
/*  GET     |  /einsatzcodes                                                 */
/*  GET     |  /dienstgrade                                                  */
/*  GET     |  /einsatzorte                                                  */
/*  GET     |  /andere_organisationen                                        */
/*  POST    |  /andere_organisationen                                        */
/*  PUT     |  /andere_organisationen/:aOrgId                                */
/*  DELETE  |  /andere_organisationen/:aOrgId                                */
/*                                                                           */
/* ************************************************************************* */

'use strict';

const express = require('express');
const baseRoutes = express.Router();

// GET    | /einsatzarten
baseRoutes.get('/einsatzarten', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    | /einsatzcodes
baseRoutes.get('/einsatzcodes', (req, res) => {

});

// GET    | /dienstgrad
baseRoutes.get('/dienstgrad', (req, res) => {

});

// GET    | /einsatzorte
baseRoutes.get('/einsatzorte', (req, res) => {

});

// GET    | /andere_organisationen
baseRoutes.get('/andere_organisationen', (req, res) => {

});

// POST   | /andere_organisationen
baseRoutes.post('/andere_organisationen', (req, res) => {

});

// PUT    | /andere_organisationen/:aOrgId
baseRoutes.put('/andere_organisationen/:aOrgId', (req, res) => {

});

// DELETE | /andere_organisationen/:aOrgId
baseRoutes.delete('/andere_organisationen/:aOrgId', (req, res) => {

});

module.exports = baseRoutes;