'use strict'

/* own modules */
const oracleJobs = require('../database/oracle-jobs');

var res_GET_DEFAULT = function (res, parser) {
  return function (err, result) {
    if (err) {
      console.warn(err)
      res.status(500).send({
        code: err.errorNum,
        message: err.message,
        offset: err.offset
      });
    } else {
      res.header('content-type', 'application/json');
      res.status(200).send(parser(JSON.stringify(result.rows)));
    }
  };
};

var res_POST_DEFAULT = function (res, querystring, params, parser) {
  return function (err, result) {
    if (err) {
      console.warn(err);
      res.status(500).send({
        code: err.errorNum,
        message: err.message,
        offset: err.offset
      });
    } else {
      oracleJobs.execute(querystring, params, (err, result) => {
        if (err) {
          console.warn(err);
          res.status(500).send({
            code: err.errorNum,
            message: err.message,
            offset: err.offset
          });
        } else {
          res.header('content-type', 'application/json');
          res.status(201).send(parser(JSON.stringify(result.rows)));
        }
      });
    }
  };
}

var res_PUT_DEFAULT = function (res, querystring, params, parser) {
  return function (err, result) {
    if (err) {
      console.warn(err);
      res.status(500).send({
        code: err.errorNum,
        message: err.message,
        offset: err.offset
      });
    } else {
      oracleJobs.execute(querystring, params, (err, result) => {
        if (err) {
          console.warn(err);
          res.status(500).send({
            code: err.errorNum,
            message: err.message,
            offset: err.offset
          });
        } else {
          res.header('content-type', 'application/json');
          res.status(200).send(parser(JSON.stringify(result.rows)));
        }
      })
    }
  };
}

var res_DELETE_DEFAULT = function (res) {
  return function (err, result) {
    if (err) {
      console.warn(err);
      res.status(500).send({
        code: err.errorNum,
        message: err.message,
        offset: err.offset
      });
    } else {
      res.status(204).send(result);
    }
  };
}

/* exports */
module.exports.GET_DEFAULT = res_GET_DEFAULT;
module.exports.POST_DEFAULT = res_POST_DEFAULT;
module.exports.PUT_DEFAULT = res_PUT_DEFAULT;
module.exports.DELETE_DEFAULT = res_DELETE_DEFAULT;