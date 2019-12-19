'use strict';

/* own modules */
const loggerModule = require('../modules/logger-module');

/* local variables */
const logger = loggerModule.loggers['Converter'];

var convertResult = function (metaData, result) {
    if (typeof metaData !== 'string') {
        logger.warn('invalid metadata supplied');
        return null;
    }
    try {
        metaData = JSON.parse(metaData);
        var resultArray = [];
        var current;
        for (let idx1 in result.rows) {
            current = JSON.parse(JSON.stringify(metaData));
            resultArray.push(generateFilledRow(current, result.rows[idx1]));
        }
        var toReturn = resultArray.map((val) => {
            return val.substring(1, val.length - 1);
        });

    } catch (ex) {
        logger.warn(ex.stack);
        return null;
    }

    return '[' + toReturn.toString() + ']';
}

function generateFilledRow(curMetaData, curRow) {
    for (let idx2 in curMetaData[0]) {
        let val = curRow[curMetaData[0][idx2]];
        if (typeof curRow[curMetaData[0][idx2]] === 'undefined') {
            throw { message: "invalid metadata supplied", stack: "typeof metadata === undefined" };
        } else if (typeof val === 'object') {
            curMetaData[0][idx2] = generateFilledRow(val, curRow);
        } else {
            curMetaData[0][idx2] = val;
        }
    }
    return JSON.stringify(curMetaData);
}

var convertSimpleInput = function (metaData, curBody) {
    try {
        var resultObject = {};
        resultObject = generateInputRow(metaData, curBody);
        return resultObject;
    } catch (ex) {
        logger.warn("invalid metadata supplied");
        return null;
    }
}

function generateInputRow(metaData, curBody){
    var result = {};
    Object.keys(metaData).forEach((element) => {
        if (typeof curBody[element] === 'object'){
            generateInputRow(metaData[element], curBody[element]);
        } else if(typeof curBody[element] !== 'undefined') {
            result[metaData[element]] = curBody[element];
        }
    });
    return result;
}

var convertValuesToUpper = function (metadata) {
    for (var key in metadata) {
        metadata[key] = metadata[key].toUpperCase();
    }
    return '['+JSON.stringify(metadata)+']';
}

module.exports.convertResult = convertResult;
module.exports.convertSimpleInput = convertSimpleInput;
module.exports.convertValuesToUpper = convertValuesToUpper;