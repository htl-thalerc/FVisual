'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Validator'];

var paramIsInteger = function (req, res, next) {
    let paramParts = req.path.split('/');
    let paramId = paramParts[paramParts.length - 1];
    if (!Number.isNaN(paramId) && paramId > 0) {
        logger.debug('valid');
        next();
    }
    else {
        logger.warn('invalid ID parameter');
        res.status(400).send('invalid ID parameter');
    }
}

var paramIsText = function (req, res, next) {
    let paramParts = req.path.split('/');
    let paramString = paramParts[paramParts.length - 1];
    if (paramString !== 'undefined' && paramString != null && paramString.length > 2) {
        logger.debug('valid');
        next();
    }
    else {
        logger.warn('invalid ID parameter');
        res.status(400).send('invalid ID parameter');
    }
}

var validate_POST_Stuetzpunkt = function () {

}
var validate_PUT_Stuetzpunkt = function () {

}

module.exports = {
    'paramIsInteger': paramIsInteger,
    'paramIsText': paramIsText,
    'body': {
        'POST_Stuetzpunkt': validate_POST_Stuetzpunkt,
        'PUT_Stuetzpunkt': validate_PUT_Stuetzpunkt
    }
}