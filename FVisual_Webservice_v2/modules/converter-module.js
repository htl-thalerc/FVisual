'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Converter'];

var convert = function (metaData, result) {
    if (!metaData || typeof metaData !== 'string') {
        logger.warn('invalid metadata supplied');
        return null;
    }
    metaData = JSON.parse(metaData);

    var resultObject = [];
    var current;
    for (let idx1 in result.rows) {
        current = JSON.parse(JSON.stringify(metaData));
        resultObject.push(getFilledRow(current, result.rows[idx1]));
    }
    var toReturn = resultObject.map((val) => {
        return val.substring(1, val.length - 1);
    });
    return '[' + toReturn.toString() + ']';
}

function getFilledRow(curMetaData, curRow) {
    for (let idx2 in curMetaData[0]) {
        let val = curRow[curMetaData[0][idx2]];
        if (typeof val === 'object') {
            curMetaData[0][idx2] = getFilledRow(val, curRow);
        } else {
            curMetaData[0][idx2] = val;
        }
    }
    return JSON.stringify(curMetaData);
}

module.exports.convert = convert;