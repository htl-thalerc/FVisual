'use strict';

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const oracleQueryProvider = require('../database/oracle-query-provider');

var res_GET_DEFAULT = function (req, res) {
    return function (err, result) {
        if (err) {
            console.warn(err);
            res.status(500).send({
                'code': err.errorNum,
                'message': err.message,
                'offset': err.offset
            });
        } else {
            res.status(200).send(result.rows);
        }
    }
}

var res_POST_DEFAULT = function (req, res) {
    return function (err, result) {
        if (err) {
            console.warn(err);
            res.status(500).send({
                'code': err.errorNum,
                'message': err.message,
                'offset': err.offset
            });
        } else {
            oracleJobs.execute(oracleQueryProvider.AORGS_GETBY_AORGS_NAME, [req.body.name], (err, result) => {
                if (err) {
                    console.warn(err);
                    res.status(500).send({
                        'code': err.errorNum,
                        'message': err.message,
                        'offset': err.offset
                    });
                } else {
                    res.status(201).send(result.rows);
                }
            });
        }
    }
}

var res_PUT_DEFAULT = function (req, res) {
    return function (err, result) {
        if (err) {
            console.warn(err);
            res.status(500).send({
                'code': err.errorNum,
                'message': err.message,
                'offset': err.offset
            });
        } else {
            res.status(200).send(result);
        }
    }
}

var res_DELETE_DEFAULT = function (req, res) {
    return function (err, result) {
        if (err) {
            console.warn(err);
            res.status(500).send({
                'code': err.errorNum,
                'message': err.message,
                'offset': err.offset
            });
        } else {
            res.status(204).send(result);
        }
    }
}

/* exports */
module.exports.GET_DEFAULT = res_GET_DEFAULT;
module.exports.POST_DEFAULT = res_POST_DEFAULT;
module.exports.PUT_DEFAULT = res_PUT_DEFAULT;
module.exports.DELETE_DEFAULT = res_DELETE_DEFAULT;