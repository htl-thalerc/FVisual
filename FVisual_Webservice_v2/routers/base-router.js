'use strict';

/* ************************************************************************* */
/*                                base-router.js                             */
/*                                                                           */
/*  HTTP Endpoints for the base - REST API                                   */
/*                                                                           */
/*  Method  |  URL                                                           */
/*  GET     |  /einsatzarten                                                 */
/*  GET     |  /einsatzcodes                                                 */
/*  GET     |  /dienstgrade                                                  */
/*  GET     |  /andere_organisationen                                        */
/*  POST    |  /andere_organisationen                                        */
/*  PUT     |  /andere_organisationen/:aOrgId                                */
/*  DELETE  |  /andere_organisationen/:aOrgId                                */
/*  GET     |  /mitglieder                                                   */
/*  GET     |  /mitglieder/:username                                         */
/*  POST    |  /mitglieder                                                   */
/*  PUT     |  /mitglieder/:username                                         */
/*  DELETE  |  /mitglieder/:username                                         */
/*  GET     |  /admins                                                       */
/*  GET     |  /mitglieder/baseless                                          */
/*  GET     |  /fahrzeuge/grouped                                            */
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
var validatorModule = require('../modules/validator-module');

/* local variables */
const baseRoutes = express.Router();
const logger = loggerModule.loggers['Routing'];

// GET    | /einsatzarten
baseRoutes.get('/einsatzarten', (req, res) => {
  logger.debug('GET /einsatzarten');
  oracleJobs.execute(oracleQueryProvider.EARTEN_GET, [], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else if (req.headers.metadata) {
      let data = converterModule.convertResult(req.headers.metadata, result);
      if (data)
        responseHandler.get(res, data);
      else
        responseHandler.invalidMetaData(res, null);
    } else {
      responseHandler.get(res, result.rows);
    }
  });
});

// GET    | /einsatzcodes
baseRoutes.get('/einsatzcodes', (req, res) => {
  logger.debug('GET /einsatzcodes');
  oracleJobs.execute(oracleQueryProvider.ECODES_GET, [], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else if (req.headers.metadata) {
      let data = converterModule.convertResult(req.headers.metadata, result);
      if (data)
        responseHandler.get(res, data);
      else
        responseHandler.invalidMetaData(res, null);
    } else {
      responseHandler.get(res, result.rows);
    }
  });
});

// GET    | /dienstgrade
baseRoutes.get('/dienstgrade', (req, res) => {
  logger.debug('GET /dienstgrade');
  oracleJobs.execute(oracleQueryProvider.DG_GET, [], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else if (req.headers.metadata) {
      let data = converterModule.convertResult(req.headers.metadata, result);
      if (data)
        responseHandler.get(res, data);
      else
        responseHandler.invalidMetaData(res, null);
    } else {
      responseHandler.get(res, result.rows);
    }
  });
});

// GET    | /andere_organisationen
baseRoutes.get('/andere_organisationen', (req, res) => {
  logger.debug('GET /andere_organisationen');
  oracleJobs.execute(oracleQueryProvider.AORGS_GET, [], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else if (req.headers.metadata) {
      let data = converterModule.convertResult(req.headers.metadata, result);
      if (data)
        responseHandler.get(res, data);
      else
        responseHandler.invalidMetaData(res, null);
    } else {
      responseHandler.get(res, result.rows);
    }
  });
});

// POST   | /andere_organisationen
baseRoutes.post('/andere_organisationen', (req, res) => {
  logger.debug('POST /andere_organisationen');

  var data;
  var metaData;
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
    data = { "name": req.body.name };
  }

  if (!validatorModule.isValidBody(data, validatorModule.patterns.getAOrgsPattern())) {
    responseHandler.invalidBody(res, null);
    return;
  }

  oracleJobs.execute(oracleQueryProvider.AORGS_POST, [data.name], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      oracleJobs.execute(oracleQueryProvider.AORGS_GETBY_AORGS_NAME, [data.name], (err, result) => {
        if (err) {
          responseHandler.internalServerError(res.err);
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

// PUT    | /andere_organisationen/:aOrgId
baseRoutes.put('/andere_organisationen/:aOrgId', (req, res) => {
  logger.debug('PUT /andere_organisationen');

  if (!validatorModule.isValidParamId(req.params.aOrgId)) {
    responseHandler.invalidItemId(res, null);
  }

  oracleJobs.execute(oracleQueryProvider.AORGS_PUT, [req.body.name, parseInt(req.params.aOrgId)], responseHandler.PUT_DEFAULT(res, oracleQueryProvider.AORGS_GETBY_AORGS_ID, [parseInt(req.params.aOrgId)], classNameParser.parseAOrg));
});

// DELETE | /andere_organisationen/:aOrgId
baseRoutes.delete('/andere_organisationen/:aOrgId', (req, res) => {
  logger.debug('DELETE /andere_organisationen');

  if (!validatorModule.isValidParamId(req.params.aOrgId)) {
    responseHandler.invalidItemId(res, null);
  }

  oracleJobs.execute(oracleQueryProvider.AORGS_DELETE, [parseInt(req.params.aOrgId)], (err, result) => {
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
module.exports = baseRoutes;



/*
} else if (result.rowsAffected != 0) {
      responseHandler.notFound(res, err);
    }
*/