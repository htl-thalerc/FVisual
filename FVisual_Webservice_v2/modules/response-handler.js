'use strict'

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Responder'];

var get = function (res, data) {
  res.header('content-type', 'application/json');
  res.status(200).send(data);
  logger.debug('responded with {200}: valid');
  loggerModule.lineFeed();  
}

var post = function (res, data) {
  res.header('content-type', 'application/json');
  res.status(201).send(data);
  logger.debug('responded with {201}: valid');
  loggerModule.lineFeed();
}

var post2 = function (res, data) {
  res.status(201).send(data);
  logger.debug('responded with {201}: valid');
  loggerModule.lineFeed();
}

var put = function (res, data) {
  res.header('content-type', 'application/json');
  res.status(200).send(data);
  logger.debug('responded with {200}: valid');
  loggerModule.lineFeed();
}

var del = function (res) {
  res.status(204).send();
  logger.debug('responded with {204}: valid');
  loggerModule.lineFeed();
}

var internalServerError = function (res, err) {
  logger.error(err.message);
  
  var errObj = {
    "errorNum": "ORA-" + err.errorNum,
    "message": err.message
  }

  res.header('content-type', 'application/json');
  res.status(500).send(JSON.stringify(errObj));
  logger.debug('responded with {500}: internalServerError');
  loggerModule.lineFeed();
}

var notFound = function (res, err) {
  var errObj = {
    "errorNum": "ORA-19090",
    "message": "entry not found"
  }
  
  res.header('content-type', 'application/json');
  res.status(404).send(JSON.stringify(errObj));
  logger.debug('responded with {404}: notFound');
  loggerModule.lineFeed();
}

var invalidParamId = function (res, err) {
  var errObj = {
    "errorNum": "WS-00001",
    "message": "invalid paramId supplied"
  }
  
  res.header('content-type', 'application/json');
  res.status(400).send(JSON.stringify(errObj));
  logger.debug('responded with {400}: invalidParamId');
  loggerModule.lineFeed();
}

var invalidMetaData = function (res, err) {
  var errObj = {
    "errorNum": "WS-00002",
    "message": "invalid metaData supplied"
  }
  res.header('content-type', 'application/json');
  res.status(400).send(JSON.stringify(errObj));
  logger.debug('responded with {400}: invalidMetaData');
  loggerModule.lineFeed();
}

var invalidBody = function (res, err) {
  var errObj = {
    "errorNum": "WS-00003",
    "message": "invalid bodyData supplied"
  }
  
  res.header('content-type', 'application/json');
  res.status(400).send(JSON.stringify(errObj));
  logger.debug('responded with {400}: invalidBody');
  loggerModule.lineFeed();
}
var invalidSubQuery = function (res, err) {
  var errObj = {
    "errorNum": "WS-00004",
    "message": "invalid subQuery supplied"
  }
  
  res.header('content-type', 'application/json');
  res.status(400).send(JSON.stringify(errObj));
  logger.debug('responded with {400}: invalidSubQuery');
  loggerModule.lineFeed();
}

var notImplementedYet = function (res, err) {
  var errObj = {
    "errorNum": "WS-00005",
    "message": "notImplementedYet"
  }
  
  res.header('content-type', 'application/json');
  res.status(501).send(JSON.stringify(errObj));
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