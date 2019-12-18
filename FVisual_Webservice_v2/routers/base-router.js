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
1
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
    else {
      var data = converterModule.convert(req.headers.metadata, result);
      if(data)
        responseHandler.get(res, data);
      else
        responseHandler.invalidMetaData(res, null);
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