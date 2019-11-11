'use strict';

/* node modules */
var log4js = require('log4js');

/**
 * Name: Application
 * Used in: index.js
 */
var logger_Application = log4js.getLogger("Application");

/**
 * Name: Response-Handler
 * Used in: resonse-handler.js
 */
var logger_Response_Handler = log4js.getLogger("Responder");

/**
 * Name: Security_Module
 * Used in: security-module
 */
var logger_Security_Module = log4js.getLogger('Security');

/**
 * Name: Central_Error_Handler
 * Used in: central_error_handler.js
 */
var logger_Central_Error_Handler = log4js.getLogger('Central-Error-Handler');

/**
 * Name: Routing
 * Used in: base-routers.js, einsatz-router.js, stuetzpunkt-router.js
 */
var logger_Routing = log4js.getLogger('Routing');

/**
 * Name: Validator
 * Used in validator-module.js
 */
var logger_Validator = log4js.getLogger('Validator');

/* Settings */
var runDevSettings = function () {
    logger_Application.level = 'info';
    logger_Response_Handler.level = 'warn';
    logger_Security_Module.level = 'debug';
    logger_Central_Error_Handler.level = 'warn';
    logger_Routing.level = 'debug';
    logger_Validator.level = 'warn';
}

var runReleaseSettings = function () {
    logger_Application.level = 'info';
    logger_Response_Handler.level = 'warn';
    logger_Security_Module.level = 'warn';
    logger_Central_Error_Handler.level = 'warn';
    logger_Routing.level = 'warn';
    logger_Validator.level = 'error';

}

var runReportAll = function () {
    logger_Application.level = 'info';
    logger_Response_Handler.level = 'info';
    logger_Security_Module.level = 'info';
    logger_Central_Error_Handler.level = 'info';
    logger_Routing.level = 'info';
    logger_Validator.level = 'info';
}


module.exports.loggers = {
    'Application': logger_Application,
    'Responder': logger_Response_Handler,
    'Security-Module': logger_Security_Module,
    'Central-Error-Handler': logger_Central_Error_Handler,
    'Routing': logger_Routing,
    'Validator': logger_Validator
}

module.exports.settings = {
    'Development': runDevSettings,
    'Release': runReleaseSettings,
    'ReportAll': runReportAll
}