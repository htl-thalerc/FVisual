'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Central-Error-Handler'];

module.exports = function (err, req, res, next) {
    logger.fatal(err.message);
    console.log(err.stack);
    if (err instanceof TypeError)
        res.status(400).send('400 Bad Request. ' + err.message);
    else if (err instanceof RangeError)
        res.status(404).send('404 Not Found. ' + err.message);
    else
        res.status(500).send('500 Internal server error.' + err.message);
}