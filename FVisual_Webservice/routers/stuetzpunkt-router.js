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
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder/;mitglId                    */
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
'use strict';

const express = require('express');
const stuetzpunktRouter = express.Router();

// GET    | /stuetzpunkte
stuetzpunktRouter.get('/', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// GET    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.get('/:stuetzId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// POST   | /stuetzpunkte
stuetzpunktRouter.post('/', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// PUT    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.put('/:stuetzId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
});

// DELETE | /stuetzpunkte/:stuetzId
stuetzpunktRouter.delete('/:stuetzId', (req, res) => {
    res.status(200).send('{"working": "fine"}');
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

module.exports = stuetzpunktRouter;