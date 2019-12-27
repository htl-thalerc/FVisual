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
/*  GET     |  /fahrzeuge                                                    */
/*  GET     |  /andere_organisationen                                        */
/*  POST    |  /andere_organisationen                                        */
/*  PUT     |  /andere_organisationen/:aOrgId                                */
/*  DELETE  |  /andere_organisationen/:aOrgId                                */
/*  GET     |  /mitglieder                                                   */
/*  GET     |  /mitglieder/:mitglId                                          */
/*  GET     |  /mitglieder/baseless                                          */
/*  POST    |  /mitglieder                                                   */ //notImplementedYet
/*  PUT     |  /mitglieder/:mitglId                                          */ //notImplementedYet
/*  DELETE  |  /mitglieder/:mitglId                                          */ //notTested
/*  GET     |  /admins                                                       */
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

// GET    | /fahrzeuge
baseRoutes.get('/fahrzeuge', (req, res) => {
  logger.debug('GET /fahrzeuge');
  oracleJobs.execute(oracleQueryProvider.FZG_GET, [], (err, result) => {
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
    data = {
      "NAME": req.body.name
    };
  }

  if (!validatorModule.isValidBody(data, validatorModule.patterns.getAOrgsPattern())) {
    responseHandler.invalidBody(res, null);
    return;
  }

  oracleJobs.execute(oracleQueryProvider.AORGS_POST, [data.NAME], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      oracleJobs.execute(oracleQueryProvider.AORGS_GETBY_AORGS_NAME, [data.NAME], (err, result) => {
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
  var data;
  var metaData;

  if (!validatorModule.isValidParamId(req.params.aOrgId)) {
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
      "NAME": req.body.name
    };
  }

  if (!validatorModule.isValidBody(data, validatorModule.patterns.getAOrgsPattern())) {
    responseHandler.invalidBody(res, null);
    return;
  }

  oracleJobs.execute(oracleQueryProvider.AORGS_PUT, [data.NAME, parseInt(req.params.aOrgId)], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      if (result.rowsAffected == 0) {
        responseHandler.notFound(res, null);
        return;
      }
      oracleJobs.execute(oracleQueryProvider.AORGS_GETBY_AORGS_ID, [req.params.aOrgId], (err, result) => {
        if (err) {
          responseHandler.internalServerError(res.err);
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

// DELETE | /andere_organisationen/:aOrgId
baseRoutes.delete('/andere_organisationen/:aOrgId', (req, res) => {
  logger.debug('DELETE /andere_organisationen');

  if (!validatorModule.isValidParamId(req.params.aOrgId)) {
    responseHandler.invalidParamId(res, null);
    return;
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

// GET    | /mitglieder
baseRoutes.get('/mitglieder', (req, res) => {
  logger.debug('GET /mitglieder');
  oracleJobs.execute(oracleQueryProvider.MGT_GET, [], (err, result) => {
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

// GET    | /mitglieder/baseless
// GET    | /mitglieder/:mitglId
baseRoutes.get('/mitglieder/:mitglId', (req, res) => {
  if (req.params.mitglId == 'baseless') {
    logger.debug('GET /mitglieder/baseless');
    oracleJobs.execute(oracleQueryProvider.MTG_GET_BASELESS, [], (err, result) => {
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
  } else {
    logger.debug('GET /mitglieder/:mitglId');
    if (!validatorModule.isValidParamId(req.params.mitglId)) {
      responseHandler.invalidParamId(res, null);
      return;
    }
    oracleJobs.execute(oracleQueryProvider.MTG_GET_BY_MTG_ID, [parseInt(req.params.mitglId)], (err, result) => {
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
  }
});

// POST   |  /mitglieder //notImplementedYet
baseRoutes.post('/mitglieder', (req, res) => {
  logger.debug('POST /mitglieder');

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
      "ID_DIENSTGRAD": req.body.id_dienstgrad,
      "ID_STUETZPUNKT": req.body.id_stuetzpunkt,
      "VORNAME": req.body.vorname,
      "NACHNAME": req.body.nachname,
      "USERNAME": req.body.username,
      "PASSWORD": req.body.password,
      "ISADMIN": req.body.isAdmin
    };
  }

  if (!validatorModule.isValidBody(data, validatorModule.patterns.getMitgliedPattern())) {
    responseHandler.invalidBody(res, null);
    return;
  }

  oracleJobs.execute(oracleQueryProvider.MTG_POST, [parseInt(data.ID_DIENSTGRAD), parseInt(data.ID_STUETZPUNKT), data.VORNAME, data.NACHNAME, data.USERNAME, data.PASSWORD, data.ISADMIN], (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      oracleJobs.execute(oracleQueryProvider.MTG_GET_BY_USERNAME, [data.USERNAME], (err, result) => {
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

// PUT    |  /mitglieder/:mitglId //notImplementedYet
baseRoutes.put('/mitglieder/:mitglId', (req, res) => {
  logger.debug('PUT /mitglieder/:username');
  responseHandler.notImplementedYet(res, null);
});

// DELETE |  /mitglieder/:mitglId
baseRoutes.delete('/mitglieder/:mitglId', (req, res) => {
  logger.debug('DELETE /mitglieder/:mitglId');

  if (!validatorModule.isValidParamId(req.params.mitglId)) {
    responseHandler.invalidParamId(res, null);
    return;
  }

  oracleJobs.execute(oracleQueryProvider.MTG_DELETE, [parseInt(req.params.mitglId)], (err, result) => {
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

// GET    | /admins
baseRoutes.get('/admins', (req, res) => {
  logger.debug('GET /admins');
  oracleJobs.execute(oracleQueryProvider.ADMINS_GET, [], (err, result) => {
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

/* exports */
module.exports = baseRoutes;