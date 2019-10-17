/* ************************************************************************* */
/*                               admin-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the admin - REST API                                  */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /admin                                                        */
/*  GET     |  /admin?search                                                 */
/*  GET     |  /admin/:userId                                                */
/*  POST    |  /admin                                                        */
/*  PUT     |  /admin/:userId                                                */
/*  DELETE  |  /admin/:userid                                                */
/*                                                                           */
/* ************************************************************************* */

'use strict';

const express = require('express');
const adminRouter = express.Router();

/**
 * GET    | /admin
 * GET    | /admin?search
 */
adminRouter.get('/', (req, res) => {
    res.status(200).send('{"subquery": "fine"}');
});

// GET    | /admin/:userId
userRouter.get('/:userId', (req, res) => {
    res.status(200).send('found: ' + req.params.userId);
});

// POST   | /admin
userRouter.post('/', (req, res) => {
    res.status(201).send("added:  1 - " + req.body.username + "-" + req.body.password + "-" + "true");
});

// PUT    | /admin/userId
userRouter.put('/:userId', (req, res) => {
    res.status(200).send("updated " + userId + ": " + req.body.username + "-" + req.body.password + "-" + "true");
})

// DELETE | /admin/:userId
userRouter.delete('/:userId', (req, res) => {
    res.status(204).send();
})


module.exports = adminRouter;