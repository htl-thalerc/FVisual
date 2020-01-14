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
/*  GET     |  /mitglieder/:mitglId/einsaetze                                */
/*  POST    |  /mitglieder                                                   */ 
/*  PUT     |  /mitglieder/:mitglId                                          */
/*  DELETE  |  /mitglieder/:mitglId                                          */
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

  var params = [];
  params.push(data.NAME);

  oracleJobs.execute(oracleQueryProvider.AORGS_POST, params, (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {

      var params = [];
      params.push(data.NAME);

      oracleJobs.execute(oracleQueryProvider.AORGS_GETBY_AORGS_NAME, params, (err, result) => {
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

  var params = [];
  params.push(data.NAME);
  params.push(parseInt(req.params.aOrgId));

  oracleJobs.execute(oracleQueryProvider.AORGS_PUT, params, (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      if (result.rowsAffected == 0) {
        responseHandler.notFound(res, null);
        return;
      }

      var params = [];
      params.push(req.params.aOrgId);

      oracleJobs.execute(oracleQueryProvider.AORGS_GETBY_AORGS_ID, params, (err, result) => {
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

// DELETE | /andere_organisationen/:aOrgId
baseRoutes.delete('/andere_organisationen/:aOrgId', (req, res) => {
  logger.debug('DELETE /andere_organisationen');

  if (!validatorModule.isValidParamId(req.params.aOrgId)) {
    responseHandler.invalidParamId(res, null);
    return;
  }

  var params = [];
  params.push(parseInt(req.params.aOrgId));

  oracleJobs.execute(oracleQueryProvider.AORGS_DELETE, params, (err, result) => {
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

    var params = [];
    params.push(parseInt(req.params.mitglId));

    oracleJobs.execute(oracleQueryProvider.MTG_GET_BY_MTG_ID, params, (err, result) => {
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

// GET    | /mitglieder/:mitglId/einsaetze
baseRoutes.get('/mitglieder/:mitglId/einsaetze', (req, res) => {
  logger.debug('GET /mitglieder/:mitglId/einsaetze');
    if (!validatorModule.isValidParamId(req.params.mitglId)) {
      responseHandler.invalidParamId(res, null);
      return;
    }

    var params = [];
    params.push(parseInt(req.params.mitglId));

    oracleJobs.execute(oracleQueryProvider.MTG_GET_EINSAETZE, params, (err, result) => {
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

// POST   |  /mitglieder
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

  var params = [];
  params.push(parseInt(data.ID_DIENSTGRAD));
  params.push(parseInt(data.ID_STUETZPUNKT));
  params.push(data.VORNAME);
  params.push(data.NACHNAME);
  params.push(data.USERNAME);
  params.push(data.PASSWORD);
  params.push(data.ISADMIN);

  oracleJobs.execute(oracleQueryProvider.MTG_POST, params, (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      var params = [];
      params.push(data.USERNAME);
      oracleJobs.execute(oracleQueryProvider.MTG_GET_BY_USERNAME, params, (err, result) => {
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

// PUT    |  /mitglieder/:mitglId
baseRoutes.put('/mitglieder/:mitglId', (req, res) => {
  logger.debug('PUT /mitglieder/:username'); 
  
  var data;
  var metaData;  

  if (!validatorModule.isValidParamId(req.params.mitglId)) {
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
  var params = [];

  params.push(parseInt(data.ID_DIENSTGRAD));
  params.push(parseInt(data.ID_STUETZPUNKT));
  params.push(data.VORNAME);
  params.push(data.NACHNAME);
  params.push(data.USERNAME);
  params.push(data.PASSWORD);
  params.push(data.ISADMIN);
  params.push(parseInt(req.params.mitglId));

  oracleJobs.execute(oracleQueryProvider.MTG_PUT, params, (err, result) => {
    if (err) {
      responseHandler.internalServerError(res, err);
    }
    else {
      if (result.rowsAffected == 0) {
        responseHandler.notFound(res, null);
        return;
      }

      var params = [];
      params.push(req.params.mitglId);

      oracleJobs.execute(oracleQueryProvider.MTG_GET_BY_MTG_ID, params, (err, result) => {
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

// DELETE |  /mitglieder/:mitglId
baseRoutes.delete('/mitglieder/:mitglId', (req, res) => {
  logger.debug('DELETE /mitglieder/:mitglId');

  if (!validatorModule.isValidParamId(req.params.mitglId)) {
    responseHandler.invalidParamId(res, null);
    return;
  }

  var params = [];
  params.push(parseInt(req.params.mitglId));

  oracleJobs.execute(oracleQueryProvider.MTG_DELETE, params, (err, result) => {
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