'use strict';

/* ************************************************************************* */
/*                             stuetzpunkt-router.js                         */
/*                                                                           */
/*  HTTP Endpoints for the stuetzpunkt - REST API                            */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     | /stuetzpunkte                                                  */
/*  GET     | /stuetzpunkte?name=stuetzName                                  */
/*  GET     | /stuetzpunkte/:stuetzId                                        */
/*  POST    | /stuetzpunkte                                                  */
/*  PUT     | /stuetzpunkte/:stuetzId                                        */
/*  DELETE  | /stuetzpunkte/:stuetzId                                        */
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder                             */
/*  GET     | /stuetzpunkte/:stuetzId/mitglieder/:mitglId                    */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  GET     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*  POST    | /stuetzpunkte/:stuetzId/fahrzeuge                              */
/*  PUT     | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*  DELETE  | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId                       */
/*                                                                           */
/* ************************************************************************* */

/* node modules */
const express = require('express');

/* own modules */
const oracleJobs = require('../database/oracle-jobs');
const oracleQueryProvider = require('../database/oracle-query-provider');
const responseHandler = require('../modules/response-handler');
const loggerModule = require('../modules/logger-module');
const converterModule = require('../modules/converter-module');
const validatorModule = require('../modules/validator-module');

/* local variables */
const stuetzpunktRouter = express.Router();
const logger = loggerModule.loggers['Routing'];

// GET    | /stuetzpunkte
// GET    | /stuetzpunkte?name=stuetzName
stuetzpunktRouter.get('/', (req, res) => {
    logger.debug('GET /stuetzpunkte');
    if (!validatorModule.isValidSubQuery(req.query, validatorModule.patterns.getSubQueryNamePattern(), true)) {
        responseHandler.invalidSubQuery(res, null);
        return;
    } else if (req.query.name) {
        logger.debug('-- search: stuetzName');

        var params = [];
        params.push(req.query.name);

        oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, params, (err, result) => {
            if (err) {
                responseHandler.internalServerError(res, err);
            } else if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        });
    } else {
        logger.debug('-- all');
        oracleJobs.execute(oracleQueryProvider.STPNKT_GET, [], (err, result) => {
            if (err) {
                responseHandler.internalServerError(res, err);
            } else if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        });
    }
});

// GET    | /stuetzpunkte/stuetzId
stuetzpunktRouter.get('/:stuetzId', (req, res) => {
    logger.debug('GET /stuetzpunkte/:stuetzId');

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rows.length == 0) {
                responseHandler.notFound(res, err);
                return;
            }

            if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        }
    });
});

// POST   | /stuetzpunkte
stuetzpunktRouter.post('/', (req, res) => {
    logger.debug('POST /stuetzpunkte');

    var data;
    var metaData;
    if (req.headers.metadata) {
        try {
            metaData = JSON.parse(req.headers.metadata.substring(1, req.headers.metadata.length - 1));
            data = converterModule.convertSimpleInput(metaData, req.body);
            console.log(JSON.stringify(data));
            if (!data) {
                responseHandler.invalidMetaData(res, null);
                return;
            }
        } catch (ex) {
            responseHandler.invalidMetaData(res, null);
            return;
        }
    } else {
        data = {
            "NAME": req.body.name,
            "ORT": req.body.ort,
            "PLZ": req.body.plz,
            "STRASSE": req.body.strasse,
            "HAUSNR": req.body.hausnr
        };
    }

    if (!validatorModule.isValidBody(data, validatorModule.patterns.getStuetzpunktPattern())) {
        responseHandler.invalidBody(res, null);
        return;
    }

    var params = [];
    params.push(data.NAME);
    params.push(data.ORT);
    params.push(parseInt(data.PLZ));
    params.push(data.STRASSE);
    params.push(data.HAUSNR);

    oracleJobs.execute(oracleQueryProvider.STPNKT_POST, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        }
        else {
            var params = [];
            params.push(data.NAME);
            oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_NAME, params, (err, result) => {
                if (err) {
                    responseHandler.internalServerError(res, err);
                } else {
                    if (metaData) {
                        let data = converterModule.convertResult(converterModule.convertValuesToUpper(metaData), result);
                        if (data)
                            responseHandler.post(res, data);
                        else
                            responseHandler.invalidMetaData(res, null);
                    } else {
                        responseHandler.post(res, result.rows);
                    }
                }
            });
        }
    });
});

// PUT    | /stuetzpunkte/:stuetzId
stuetzpunktRouter.put('/:stuetzId', (req, res) => {
    logger.debug('PUT /stuetzpunkte/:stuetzId');

    var metaData;
    var data;

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    if (req.headers.metadata) {
        try {
            metaData = JSON.parse(req.headers.metadata.substring(1, req.headers.metadata.length - 1));
            data = converterModule.convertSimpleInput(metaData, req.body);
            if (!data) {
                responseHandler.invalidMetaData(res, null);
                return;
            }
        } catch (ex) {
            responseHandler.invalidMetaData(res, null);
            return;
        }
    } else {
        data = {
            "NAME": req.body.name,
            "ORT": req.body.ort,
            "PLZ": req.body.plz,
            "STRASSE": req.body.strasse,
            "HAUSNR": req.body.hausnr
        };
    }

    if (!validatorModule.isValidBody(data, validatorModule.patterns.getStuetzpunktPattern())) {
        responseHandler.invalidBody(res, null);
        return;
    }
    var params = [];

    params.push(data.NAME);
    params.push(data.ORT);
    params.push(parseInt(data.PLZ));
    params.push(data.STRASSE);
    params.push(data.HAUSNR);
    params.push(parseInt(req.params.stuetzId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_PUT, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        }
        else {
            if (result.rowsAffected == 0) {
                responseHandler.notFound(res, null);
                return;
            }

            var params = [];
            params.push(req.params.stuetzId);

            oracleJobs.execute(oracleQueryProvider.STPNKT_GETBY_STPNKT_ID, params, (err, result) => {
                if (err) {
                    responseHandler.internalServerError(res, err);
                } else {
                    if (metaData) {
                        let data = converterModule.convertResult(converterModule.convertValuesToUpper(metaData), result);
                        if (data)
                            responseHandler.put(res, data);
                        else
                            responseHandler.invalidMetaData(res, null);
                    } else {
                        responseHandler.put(res, result.rows);
                    }
                }
            });
        }
    });
});

// DELETE | /stuetzpunkte/:stuetzId
stuetzpunktRouter.delete('/:stuetzId', (req, res) => {
    logger.debug('DELETE /stuetzpunkte');

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_DELETE, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rowsAffected == 0) {
                responseHandler.notFound(res, null);
            } else {
                responseHandler.delete(res);
            }
        }
    });
});

// GET    | /stuetzpunkte/:stuetzId/mitglieder
stuetzpunktRouter.get('/:stuetzId/mitglieder', (req, res) => {
    logger.debug("GET /stuetzpunkte/:stuetzId/mitglieder")

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_MTG_GET, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rows.length == 0) {
                responseHandler.notFound(res, err);
                return;
            }

            if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        }
    });
});

// GET    | /stuetzpunkte/:stuetzId/mitglieder/:mitglId
stuetzpunktRouter.get('/:stuetzId/mitglieder/:mitglId', (req, res) => {
    logger.debug('GET /:stuetzId/mitglieder/:mitglId');

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
    } else if (!validatorModule.isValidParamId(req.params.mitglId)) {
        responseHandler.invalidParamId(res, null);
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));
    params.push(parseInt(req.params.mitglId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_MTG_GET_BY_MTG_ID, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rows.length == 0) {
                responseHandler.notFound(res, err);
                return;
            }

            if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        }
    });
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge
stuetzpunktRouter.get('/:stuetzId/fahrzeuge', (req, res) => {
    logger.debug("GET /stuetzpunkte/:stuetzId/fahrzeuge");
    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rows.length == 0) {
                responseHandler.notFound(res, err);
                return;
            }

            if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        }
    });
});

// GET    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.get('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('GET /:stuetzId/fahrzeuge/:fzgId');

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
    } else if (!validatorModule.isValidParamId(req.params.fzgId)) {
        responseHandler.invalidParamId(res, null);
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));
    params.push(parseInt(req.params.fzgId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_ID, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rows.length == 0) {
                responseHandler.notFound(res, err);
                return;
            }

            if (req.headers.metadata) {
                let data = converterModule.convertResult(req.headers.metadata, result);
                if (data)
                    responseHandler.get(res, data);
                else
                    responseHandler.invalidMetaData(res, null);
            } else {
                responseHandler.get(res, result.rows);
            }
        }
    });
});

// POST   | /stuetzpunkte/:stuetzId/fahrzeuge //notImplementedYet
stuetzpunktRouter.post('/:stuetzId/fahrzeuge', (req, res) => {
    logger.debug('POST /stuetzpunkte/:stuetzId/fahrzeuge');

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var data;
    var metaData;

    if (req.headers.metadata) {
        try {
            metaData = JSON.parse(req.headers.metadata.substring(1, req.headers.metadata.length - 1));
            data = converterModule.convertSimpleInput(metaData, req.body);
            console.log(JSON.stringify(data));
            if (!data) {
                responseHandler.invalidMetaData(res, null);
                return;
            }
        } catch (ex) {
            responseHandler.invalidMetaData(res, null);
            return;
        }
    } else {
        data = {
            "BEZEICHNUNG": req.body.bezeichnung
        };
    }

    if (!validatorModule.isValidBody(data, validatorModule.patterns.getFahrzeugPattern())) {
        responseHandler.invalidBody(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));
    params.push(data.BEZEICHNUNG);

    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_POST, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        }
        else {
            var params = [];
            params.push(parseInt(req.params.stuetzId));
            params.push(data.BEZEICHNUNG);
            oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_BEZEICHNUNG, params, (err, result) => {
                if (err) {
                    responseHandler.internalServerError(res,err);
                } else {
                    if (metaData) {
                        let data = converterModule.convertResult(converterModule.convertValuesToUpper(metaData), result);
                        if (data)
                            responseHandler.post(res, data);
                        else
                            responseHandler.invalidMetaData(res, null);
                    } else {
                        responseHandler.post(res, result.rows);
                    }
                }
            });
        }
    });
});

// PUT    | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.put('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('PUT /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId');

    var metaData;
    var data;

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }else if(!validatorModule.isValidParamId(req.params.fzgId)){
        responseHandler.invalidParamId(res, null);
        return;
    }

    if (req.headers.metadata) {
        try {
            metaData = JSON.parse(req.headers.metadata.substring(1, req.headers.metadata.length - 1));
            data = converterModule.convertSimpleInput(metaData, req.body);
            if (!data) {
                responseHandler.invalidMetaData(res, null);
                return;
            }
        } catch (ex) {
            responseHandler.invalidMetaData(res, null);
            return;
        }
    } else {
        data = {
            "BEZEICHNUNG": req.body.bezeichnung
        };
    }

    if (!validatorModule.isValidBody(data, validatorModule.patterns.getFahrzeugPattern())) {
        responseHandler.invalidBody(res, null);
        return;
    }
    var params = [];

    params.push(data.BEZEICHNUNG);
    params.push(parseInt(req.params.stuetzId));
    params.push(parseInt(req.params.fzgId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_PUT, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        }
        else {
            if (result.rowsAffected == 0) {
                responseHandler.notFound(res, null);
                return;
            }

            var params = [];
            params.push(parseInt(req.params.stuetzId));
            params.push(parseInt(req.params.fzgId));

            oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_GET_BY_FZG_ID, params, (err, result) => {
                if (err) {
                    responseHandler.internalServerError(res, err);
                } else {
                    if (metaData) {
                        let data = converterModule.convertResult(converterModule.convertValuesToUpper(metaData), result);
                        if (data)
                            responseHandler.put(res, data);
                        else
                            responseHandler.invalidMetaData(res, null);
                    } else {
                        responseHandler.put(res, result.rows);
                    }
                }
            });
        }
    });
});

// DELETE | /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId
stuetzpunktRouter.delete('/:stuetzId/fahrzeuge/:fzgId', (req, res) => {
    logger.debug('DELETE /stuetzpunkte/:stuetzId/fahrzeuge/:fzgId');

    if (!validatorModule.isValidParamId(req.params.stuetzId)) {
        responseHandler.invalidParamId(res, null);
        return;
    } else if (!validatorModule.isValidParamId(req.params.fzgId)) {
        responseHandler.invalidParamId(res, null);
        return;
    }

    var params = [];
    params.push(parseInt(req.params.stuetzId));
    params.push(parseInt(req.params.fzgId));

    oracleJobs.execute(oracleQueryProvider.STPNKT_FZG_DELETE, params, (err, result) => {
        if (err) {
            responseHandler.internalServerError(res, err);
        } else {
            if (result.rowsAffected == 0) {
                responseHandler.notFound(res, null);
            } else {
                responseHandler.delete(res);
            }
        }
    });
});

/* exports */
module.exports = stuetzpunktRouter;