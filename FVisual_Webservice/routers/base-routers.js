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
var oracledb = require('oracledb');
const connAttrs = require('./../database/oracle-connection');

// GET    | /einsatzarten
baseRoutes.get('/einsatzarten', (req, res) => {
    oracledb.getConnection(connAttrs, function (err, connection) {
        if (err) {
            // Error connecting to DB
            res.set('Content-Type', 'application/json');
            res.status(500).send(JSON.stringify({
                status: 500,
                message: "Error connecting to DB",
                detailed_message: err.message
            }));
            return;
        }

        connection.execute("SELECT * FROM einsatzarten", {}, {
            outFormat: oracledb.OBJECT // Return the result as Object
        }, function (err, result) {
            if (err) {
                res.set('Content-Type', 'application/json');
                res.status(500).send(JSON.stringify({
                    status: 500,
                    message: "Error getting the user profile",
                    detailed_message: err.message
                }));
            } else {
                res.contentType('application/json').status(200);
                res.send(JSON.stringify(result.rows));
            }
            // Release the connection
            connection.release(
                function (err) {
                    if (err) {
                        console.error(err.message);
                    } else {
                        console.log("GET /user_profiles : Connection released");
                    }
                });
        });
    });
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