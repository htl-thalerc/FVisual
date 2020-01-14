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

var post = function(res, data){
  res.header('content-type', 'application/json');
  logger.debug('responded with {201}: valid');
  loggerModule.lineFeed();
  res.status(201).send(data);
}

var put = function(res, data){
  res.header('content-type', 'application/json');
  logger.debug('responded with {200}: valid');
  loggerModule.lineFeed();
  res.status(200).send(data);
}

var del = function(res){
  logger.debug('responded with {204}: valid');
  loggerModule.lineFeed();
  res.status(204).send();
}

var internalServerError = function(res, err){
    logger.error(err.stack);
    res.status(500).send(err.stack);
    logger.debug('responded with {500}: internalServerError');
    loggerModule.lineFeed();
}

var notFound = function(res, err){
  res.status(404).send('entry not found');
  logger.debug('responded with {404}: notFound');
  loggerModule.lineFeed();
}

var invalidMetaData = function(res, err){
  res.status(400).send('invalid metadata supplied');
  logger.debug('responded with {400}: invalidMetaData');
  loggerModule.lineFeed();
}

var invalidParamId = function(res, err){
  res.status(400).send('invalid param id supplied');
  logger.debug('responded with {400}: invalidParamId');
  loggerModule.lineFeed();
}

var invalidBody = function(res, err){
  res.status(400).send('invalid body supplied');
  logger.debug('responded with {400}: invalidBody');
  loggerModule.lineFeed();
}

var invalidSubQuery = function(res, err){
  res.status(400).send('invalid subquery supplied');
  logger.debug('responded with {400}: invalidSubQuery');
  loggerModule.lineFeed();
}

var notImplementedYet = function (res, err){
  res.status(501).send('url not implemented yet');
  logger.debug('responded with {501}: notImplementedYet');
  loggerModule.lineFeed();
}

/* exports */
module.exports.internalServerError = internalServerError;
module.exports.invalidMetaData = invalidMetaData;
module.exports.invalidParamId = invalidParamId;
module.exports.invalidBody = invalidBody;
module.exports.invalidSubQuery = invalidSubQuery;
module.exports.notImplementedYet = notImplementedYet;
module.exports.notFound = notFound;
module.exports.get = get;
module.exports.post = post;
module.exports.put = put;
module.exports.delete = del;