'use strict';

/* node modules */
const log4js = require('log4js');

/* local variables */
const logger = log4js.getLogger("Central-Error-Handler");
logger.level = 'error';

module.exports.errorHandler = function (err, req, res, next) {
    logger.fatal(err);
    logger.error(err.stack);
    if (err instanceof TypeError)
        res.status(400).send('400 Bad Request. ' + err.message);
    else if (err instanceof RangeError)
        res.status(404).send('404 Not Found. ' + err.message);
    else
        res.status(500).send('500 Internal server error.' + err.message);
}