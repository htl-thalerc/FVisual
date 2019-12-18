'use strict'

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Responder'];

var get = function (res, data){
  res.header('content-type', 'application/json');
  logger.debug('responded with {200}: valid');
  loggerModule.lineFeed();
  res.status(200).send(data);
}

var internalServerError = function(res, err){
    logger.error(err.stack);
    res.status(500).send(err.stack);
    logger.debug('responded with {500}: internalServerError');
    loggerModule.lineFeed();
}

var notFound = function(res, err){
  logger.warn(err.stack);
  res.status(404).send(err.stack);
  logger.debug('responded with {404}: notFound');
  loggerModule.lineFeed();
}

var invalidMetaData = function(res, err){
  res.status(400).send('invalid metadata supplied');
  logger.debug('responded with {400}: invalidMetaData');
  loggerModule.lineFeed();
}

/* exports */
module.exports.internalServerError = internalServerError;
module.exports.invalidMetaData = invalidMetaData;
module.exports.notFound = notFound;
module.exports.get = get;