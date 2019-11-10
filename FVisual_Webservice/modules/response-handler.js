'use strict'

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Response-Handler'];

var res_GET_DEFAULT = function (res, parser) {
  return function (err, result) {
    if (err) {
      logger.error(err.stack);
      res.status(500).send(err.stack);
      logger.debug("execute error sent!");
    } else {
      res.header('content-type', 'application/json');
      res.status(200).send(parser(JSON.stringify(result.rows)));
      logger.debug("response sent!");
    }
  };
};

var res_POST_DEFAULT = function (res, querystring, params, parser) {
  return function (err, result) {
    if (err) {
      logger.error(err.stack);
      res.status(500).send(err.stack);
      logger.debug("execute error sent!");
    } else {
      oracleJobs.execute(querystring, params, (err, result) => {
        if (err) {
          logger.error(err.stack);
          res.status(500).send(err.stack);
          logger.debug("nested execute error sent!");
        } else {
          res.header('content-type', 'application/json');
          res.status(201).send(parser(JSON.stringify(result.rows)));
          logger.debug("response sent!");
        }
      });
    }
  };
}

var res_PUT_DEFAULT = function (res, querystring, params, parser) {
  return function (err, result) {
    if (err) {
      logger.error(err.stack);
      res.status(500).send(err.stack);
      logger.debug("execute error sent!");
    } else {
      oracleJobs.execute(querystring, params, (err, result) => {
        if (err) {
          logger.error(err.stack);
          res.status(500).send(err.stack);
          logger.debug("nested execute error sent!");
        } else {
          res.header('content-type', 'application/json');
          res.status(200).send(parser(JSON.stringify(result.rows)));
          logger.debug("response sent!");
        }
      })
    }
  };
}

var res_DELETE_DEFAULT = function (res) {
  return function (err, result) {
    if (err) {
      logger.error(err.stack);
      res.status(500).send(err.stack);
      logger.debug("execute error sent!");
    } else {
      res.status(204).send(result);
      logger.debug("response sent!");
    }
  };
}

/* exports */
module.exports.GET_DEFAULT = res_GET_DEFAULT;
module.exports.POST_DEFAULT = res_POST_DEFAULT;
module.exports.PUT_DEFAULT = res_PUT_DEFAULT;
module.exports.DELETE_DEFAULT = res_DELETE_DEFAULT;