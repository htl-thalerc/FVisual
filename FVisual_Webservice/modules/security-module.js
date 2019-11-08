'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Security-Module'];

/* login */
function login(req, res) {
    if (req.headers.flow == "mobile") {
        res.status(200).send({
            "username": req.body.username,
            "flow": req.headers.flow
        });
        logger.debug('mobile login successful / ' + req.body.username);
    } else if (req.headers.flow == "management") {
        res.status(200).send({
            "username": req.body.username,
            "flow": req.headers.flow
        });
        logger.debug('desktop login successful / ' + req.body.username);
    } else {
        logger.warn("invalid flow: " + req.headers.flow);
        res.status(400).send("invalid flow");
    }
}

/* authenticate */
function authenticate(req, res, next) {
    var token = req.headers['authorization'];

    if (token == undefined || token == '') {
        res.status(401).send('Unauthorized');
        logger.warn("Unauthorzized auth / " + token);
        return;
    }

    req.login = token;

    next();
}

/* exports */
module.exports.login = login;
module.exports.authenticate = authenticate;